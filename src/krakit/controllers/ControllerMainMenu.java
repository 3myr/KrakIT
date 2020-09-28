package krakit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import krakit.Main;
import krakit.modeles.Krakit;

import java.io.File;
import java.io.IOException;
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
        Stage stage = new Stage();

        try
        {
            ControllerRepoManager crm = new ControllerRepoManager(krakit,stage);

            // Indique quel menu mettre par defaut
            crm.setShowClone(true);

            // FXML loader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/repoManager.fxml"));
            loader.setControllerFactory(ic->crm);

            BorderPane borderPane = loader.load();
            borderPane.setStyle("-fx-background-color: #171717");

            Scene scene = new Scene(borderPane,Screen.getPrimary().getVisualBounds().getWidth()*0.5 ,Screen.getPrimary().getVisualBounds().getHeight()*0.5); // *0.7 et 0*9 par default
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

            // Indique quel menu mettre par defaut
            crm.setShowOpen(true);

            // FXML loader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/repoManager.fxml"));
            loader.setControllerFactory(ic->crm);

            BorderPane borderPane = loader.load();
            borderPane.setStyle("-fx-background-color: #171717");

            Scene scene = new Scene(borderPane,Screen.getPrimary().getVisualBounds().getWidth()*0.7 ,Screen.getPrimary().getVisualBounds().getHeight()*0.9);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     *
     * @param actionEvent
     */
    public void init(ActionEvent actionEvent) throws IOException, InterruptedException {

        // Initialisation d'une fenetre
        Stage stage = new Stage();

        // Initialisation d'un choix de répertoire
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // Emplacement du dernier projet utilisé
        if(this.krakit.getCurrentTab()!=null)
        {
            directoryChooser.setInitialDirectory(new File(this.krakit.getCurrentTab().getPath()));
        }

        // Affiche la fenetre de choix
        File selectedDirectory = directoryChooser.showDialog(stage);


        // Ajoute le projet
        if(selectedDirectory!=null)
        {
            this.krakit.gitInit(selectedDirectory.toPath());
            this.krakit.ajouterRepo(selectedDirectory.getName(),selectedDirectory.getAbsolutePath());
        }

    }

    public void openCMD(ActionEvent actionEvent) throws IOException, InterruptedException {
        this.krakit.openCMD();
    }

    //
}
