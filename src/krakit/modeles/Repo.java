package krakit.modeles;

public class Repo {

    // ATTRIBUT

    private String nom;
    private String path;

    //

    // CONSTRUCTEUR

    /**
     *
     */
    public Repo()
    {
        this.nom = "New Tab";
    }

    /**
     *
     * @param nom
     */
    public Repo(String nom)
    {
        this.nom = nom;
    }

    /**
     *
     * @param nom
     * @param path
     */
    public Repo(String nom, String path)
    {
        this.nom = nom;
        this.path = path;
    }

    //

    // GETTER / SETTER

    /**
     * Retourne le nom du projet
     * @return
     */
    public String getNom()
    {
        return nom;
    }

    /**
     *
     * @return
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Fixe le nom du projet
     * @param nom
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * Fixe le chemin menant au projet
     * @param path
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    //

    // PROCEDURES



    //
}
