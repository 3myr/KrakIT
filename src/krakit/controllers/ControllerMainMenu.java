package krakit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import krakit.Main;
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
        this.krakit.ajouterRepo("krakit"+(int)(Math.random()*10),System.getProperty("user.dir"));
    }

    /**
     *
     * @param actionEvent
     */
    public void open(ActionEvent actionEvent)
    {
        Stage stage = new Stage();

        try
        {
            ControllerRepoManager crm = new ControllerRepoManager(krakit,stage);

            // FXML loader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/repoManager.fxml"));
            loader.setControllerFactory(ic->crm);

            BorderPane borderPane = loader.load();
            borderPane.setStyle("-fx-background-color: #171717");

            Scene scene = new Scene(borderPane,1000,900);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //
}
