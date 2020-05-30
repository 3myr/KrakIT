package krakit.modeles;

import java.util.ArrayList;

public class Krakit extends Sujet
{
    // ATTRIBUT

    private ArrayList<Repo> reposInTabPane;
    private ArrayList<Repo> repos;
    private Repo currentTab;

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


    //
}
