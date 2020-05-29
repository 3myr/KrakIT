package krakit.modeles;

import krakit.controllers.Controller;

import java.util.ArrayList;

public abstract class Sujet
{
    // ATTRIBUT

    protected ArrayList<Controller> controllers;

    //

    // CONSTRUCTEUR

    /**
     *
     */
    public Sujet()
    {
        controllers = new ArrayList<Controller>(10); // Taille a changer ?
    }

    //

    // GETTER / SETTER

    /**
     *
     * @return
     */
    public ArrayList<Controller> getControllers()
    {
        return controllers;
    }

    //

    // PROCEDURES

    /**
     * Ajoute un Controller dans la liste
     * @param controller
     */
    public void ajouterControllers(Controller controller)
    {
        controllers.add(controller);
    }

    /**
     * Met a jour les Controllers
     */
    public void reagir()
    {
        for(Controller c : this.controllers)
        {
            c.mettreAJour();
        }
    }

    //
}
