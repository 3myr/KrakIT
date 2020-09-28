package krakit.modeles;

import krakit.exceptions.GitException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class Krakit extends Sujet
{
    // ATTRIBUT

    private ArrayList<Repo> reposInTabPane;
    private ArrayList<Repo> repos;
    private Repo currentTab;
    private ArrayList<String> commits;

    //

    // CONSTRUCTEUR

    /**
     * Constructeur
     */
    public Krakit()
    {
        super();

        reposInTabPane = new ArrayList<Repo>(10);
        repos = new ArrayList<Repo>(10);
    }

    //

    // GETTER / SETTER

    /**
     * Retourne les projets contenu dans le modèle dans le menu d'onglets
     * @return
     */
    public ArrayList<Repo> getReposInTabPane()
    {
        return reposInTabPane;
    }

    /**
     * Retourne les projets contenu dans le modèle
     * @return
     */
    public ArrayList<Repo> getRepos()
    {
        return repos;
    }

    /**
     * Retourne l'onglet selectionné
     * @return
     */
    public Repo getCurrentTab()
    {
        return currentTab;
    }

    /**
     * Fixe l'onglet selectionné
     * @param repo
     */
    public void currentTab(Repo repo)
    {
        this.currentTab = repo;

        // Met a jour les controllers
        this.reagir();
    }

    /**
     * Récupère la liste des commits
     * @return
     */
    public ArrayList<String> commits()
    {
        return commits;
    }

    /**
     * Récupère tout les commits ( et ces informations ) du projet
     */
    public ArrayList<String> getCommits() throws Exception, GitException
    {
        // Recupere le nombre de commit
        // VERIFIER SI GIT EST INSTALLE
        ProcessBuilder builder = new ProcessBuilder();

        // Commandes a executer ( && permet de lancer plusieurs commandes d'affilée )
        builder.command("cmd.exe", "/c", "git branch&&echo $&&git rev-list --all --count&&echo $&&git rev-list --all --pretty=format:\"%an | %ar | %s\"");

        // Fixe le repertoire a partir duquel lancer la CMD
        builder.directory(new File(this.getCurrentTab().getPath()));

        // Execute les commandes

        Process p = builder.start();

        // Buffer pour lire les prints / erreurs
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        // Récupère les sorties des différentes commandes
        String s = null;
        ArrayList<ArrayList<String>> stdout = new ArrayList<ArrayList<String>>();
        boolean error=false;
        ArrayList<String> tmp = new ArrayList<String>();

        while ((s = stdInput.readLine()) != null)
        {
            // Caractère de séparation de sortie de commandes
            if(!s.equals("$"))
            {
                tmp.add(s);
            }
            else // Cas ou on se trouve dans une nouvelle commande
            {
                stdout.add(tmp);
                tmp = new ArrayList<String>();
            }
        }

        // Ajoute la dernière ligne si elle ne se trouve pas deja dans la liste
        if(!stdout.contains(tmp))
        {
            stdout.add(tmp);
        }

        // Inutile pour le moment
        while ((s = stdError.readLine()) != null) {
            System.out.println("Error : "+s);
            error=true;
        }

        // Traitement des informations récupéré

        // Si il y a une erreur, executer une action ( A FAIRE )
        if(error)
        {
            throw new GitException("pas de git");
        }
        else
        {
            if(Integer.parseInt(stdout.get(1).get(0))>0)
            {
                // Si pas d'erreur, répuère les informations des commits dans une liste
                commits = new ArrayList<String>(Integer.parseInt(stdout.get(1).get(0)));
                int cmp=0;
                for(String info : stdout.get(2))
                {
                    info.replace("^[^,]*,","");
                    if(cmp%2!=0)
                    {
                        commits.add(info);
                    }
                    else
                    {
                        commits.add(info.substring(info.indexOf("commit")+7,info.length()));
                    }
                    cmp++;
                }
            }
            else
            {
                commits = new ArrayList<String>(Integer.parseInt(stdout.get(1).get(0)));
            }
        }

        return commits;
    }

    //

    // PROCEDURES

    /**
     * Ajoute un projet Git dans le modele
     */
    public void ajouterRepo()
    {
        this.reposInTabPane.add(new Repo());

        // Met a jour les controllers
        this.reagir();
    }

    /**
     * Ajoute un projet Git dans le modele
     * @param nom
     * @param path
     */
    public void ajouterRepo(String nom, String path)
    {
        Repo repo = new Repo(nom,path);

        // Ajoute les projets dans les listes
        this.reposInTabPane.add(repo);
        this.repos.add(repo);

        // Quand il y a plus de 8 anciens projets, supprime les anciens de la liste
        while(this.repos.size()>15)
        {
            int i=0;
            this.repos.remove(i);
        }

        // Fixe le nouveau projet en tant que projet en cours
        this.currentTab(repo);

        // Met a jour les controllers
        //this.reagir();
    }

    /**
     * Supprime un projet Git dans le modele
     * @param r
     */
    public void supprimerRepo(Repo r)
    {
        this.reposInTabPane.remove(r);

        // Met a jour les controllers
        //this.reagir();
    }

    /**
     * Fonction appellant la commande clone de git
     * @param directory emplacement où cloner le projet
     * @param originUrl url contenant le projet
     * @throws IOException
     * @throws InterruptedException
     */
    public static void gitClone(Path directory, String originUrl) throws IOException, InterruptedException
    {
        runCommand(directory.getParent(), "git", "clone", originUrl, directory.getFileName().toString());

        if(!directory.toFile().exists())
        {
            throw new RuntimeException("Le projet n'a pas pu être importé, ressayer ( mot de passe incorrect ? )");
        }
    }

    /**
     * Fonction appellant la commande commit de git
     * @param directory
     * @param message
     * @throws IOException
     * @throws InterruptedException
     */
    public void gitCommit(Path directory, String message) throws IOException, InterruptedException
    {
        gitAdd(directory);

        // Permet de mettre la première ligne du message de commit en avant
        message = message.replace("\n","\" -m \"");
        runCommand(directory, "git", "commit", "-m", message); // Impossible de mettre de " dans les commentaires
    }

    /**
     * Fonction appellant la commande push de git
     * @param directory
     * @throws IOException
     * @throws InterruptedException
     */
    public static void gitPush(Path directory) throws IOException, InterruptedException
    {
        runCommand(directory, "git", "push");
    }

    /**
     * Fonction appellant la commande add de git
     * @param directory
     * @throws IOException
     * @throws InterruptedException
     */
    public static void gitAdd(Path directory) throws IOException, InterruptedException {
        runCommand(directory, "git", "add", ".");
    }

    /**
     * Fonction appellant la commande init de git
     * @param directory
     * @throws IOException
     * @throws InterruptedException
     */
    public void gitInit(Path directory) throws IOException, InterruptedException {
        if(directory!=null)
        {
            runCommand(directory,"git", "init", directory.toFile().getAbsolutePath());
        }
    }

    /**
     * Fonction ouvrant un terminal a partir du dossier du projet
     * @throws IOException
     * @throws InterruptedException
     */
    public void openCMD() throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("cmd /c start cmd.exe");
        p.waitFor();
    }


    /**
     * Permet de lancer une liste de commande
     * @param directory
     * @param command
     * @throws IOException
     * @throws InterruptedException
     */
    public static void runCommand(Path directory, String... command) throws IOException, InterruptedException
    {
        Objects.requireNonNull(directory, "directory");

        // Verifie si le dossier choisit existe
        if (!Files.exists(directory)) {
            throw new RuntimeException("le repertoire : '" + directory + "' n'existe pas");
        }

        ProcessBuilder pb = new ProcessBuilder().command(command).directory(directory.toFile());
        Process p = pb.start();
        int exit = p.waitFor();
    }

    /**
     * Verifie que des modifications ont été effectué sur le projet
     * @return
     */
    public ArrayList<String> getModifyFile()
    {
        // Liste contenant les noms des fichiers modifiés
        ArrayList<String> modiFile = new ArrayList<String>(); // Taille a changer ?

        ProcessBuilder builder = new ProcessBuilder();

        // Commandes a executer
        builder.command("cmd.exe", "/c", "git ls-files --other --modified --exclude-standard");

        // Fixe le repertoire a partir duquel lancer la CMD
        builder.directory(new File(this.getCurrentTab().getPath()));

        try
        {
            // Lance la commande
            Process p = builder.start();

            // Buffer pour lire les prints / erreurs
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // Récupère les sorties des différentes commandes
            String s = null;
            ArrayList<ArrayList<String>> stdout = new ArrayList<ArrayList<String>>();

            // Récupère les lignes sur la sortie standard
            while ((s = stdInput.readLine()) != null)
            {
                modiFile.add(s);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return modiFile;
    }

    //
}
