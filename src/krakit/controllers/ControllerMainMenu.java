package krakit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import krakit.modeles.Krakit;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainMenu extends Controller
{
    // ATTRIBUT

    // Composants graphique
    @FXML
    private MenuItem newTab;


    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerMainMenu(Krakit krakit)
    {
        super(krakit);
    }


    //

    // GETTER / SETTER



    //

    // PROCEDURES

    /**
     *
     */
    public void mettreAJour()
    {

    }

    /**
     * Ajoute un projet vide ( un nouvel onglet )
     * @param actionEvent
     */
    public void ajouterRepo(ActionEvent actionEvent)
    {
        this.krakit.ajouterRepo();
    }

    /**
     * A CHANGER PAR LA SUITE, TEST DES REPO
     * @param actionEvent
     */
    public void cloneRepo(ActionEvent actionEvent)
    {
        this.krakit.ajouterRepo("krakit"+(int)(Math.random()*10),"C://Users/remyd/Documents/Projets/krakit");
    }

    //
}
