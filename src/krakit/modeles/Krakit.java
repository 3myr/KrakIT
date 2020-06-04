package krakit.modeles;

import java.io.*;
import java.util.ArrayList;

public class Krakit extends Sujet
{
    // ATTRIBUT

    private ArrayList<Repo> reposInTabPane;
    private ArrayList<Repo> repos;
    private Repo currentTab;
    private ArrayList<String> commits;

    //

    // CONSTRUCTEUR

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
    public ArrayList<String> getCommits() throws Exception
    {
        // Recupere le nombre de commit
        // VERIFIER SI GIT EST INSTALLE
        ProcessBuilder builder = new ProcessBuilder();

        // Commandes a executer ( && permet de lancer plusieurs commandes d'affilée )
        builder.command("cmd.exe", "/c", "git branch&&echo $&&git rev-list --all --count&&echo $&&git rev-list --all --pretty=format:\"%an | %ar | %s\"");

        // Fixe le repertoire a partir duquel lancer la CMD
        builder.directory(new File("C:\\Users\\remyd\\Documents\\Projets\\Informatique\\Personnel\\KrakIT\\KrakIT"));
        //builder.directory(new File(repo.getPath()));

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

            /*
            // Affiche les sorties des commandes
            for(ArrayList<String> sortie: stdout)
            {
                System.out.println(sortie);
            }
             */

        // Traitement des informations récupéré

        // Si il y a une erreur, executer une action ( A FAIRE )
        if(error)
        {
            throw new Exception("pas de git");
        }

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

        /*
        // Affichage des commits
        for(String info : commits)
        {
            System.out.println(info);
        }
         */

        return commits;
    }

    /**
     * Ajoute un commit a la liste des commits ( A SUPPRIMER, POUR LES TESTS )
     * @param s
     */
    public void setCommits(String s)
    {
        commits.add(0,s);
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


        // Met a jour les controllers
        this.reagir();
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
     * Commit un projet
     * @param s
     */
    public void sendCommit(String s)
    {

    }


    //
}
