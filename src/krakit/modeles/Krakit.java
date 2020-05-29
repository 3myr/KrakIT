package krakit.modeles;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Krakit extends Sujet
{
    // ATTRIBUT

    private ArrayList<Repo> repos;

    //

    // CONSTRUCTEUR

    public Krakit()
    {
        super();

        repos = new ArrayList<Repo>(10);
    }

    //

    // GETTER / SETTER

    /**
     * Retourne les projets contenu dans le modèle
     * @return
     */
    public ArrayList<Repo> getRepos()
    {
        return repos;
    }

    //

    // PROCEDURES

    /**
     * Ajoute un projet Git dans le modele
     */
    public void ajouterRepo()
    {
        this.repos.add(new Repo());

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
        this.repos.add(new Repo(nom,path));

        // Met a jour les controllers
        this.reagir();
    }

    /**
     * Supprime un projet Git dans le modele
     * @param r
     */
    public void supprimerRepo(Repo r)
    {
        this.repos.remove(r);

        // Met a jour les controllers
        this.reagir();
    }

    //
}
