package krakit.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import krakit.Main;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDefaultTab extends Controller implements Initializable {

    // ATTRIBUT

    // Composants graphique
    @FXML
    private ListView listView;

    // Attribut
    private ObservableList<HBox> repoList;


    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerDefaultTab(Krakit krakit)
    {
        super(krakit);
        repoList = FXCollections.<HBox>observableArrayList();
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
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // ListView
        listView.setItems(repoList);

        // Empeche la selection des hbox contenant les informations propre aux projets
        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldvalue, newValue) -> {
            Platform.runLater(() -> {
                listView.getSelectionModel().clearSelection();
            });
        });

        // Affiche les projets non vide
        for(Repo r : krakit.getRepos())
        {
            // Si le projet n'est pas encore choisit par l'utilisateur, ne l'affiche pas dans les projets récents
            if(r.getPath()!=null)
            {
                HBox nodeContainer = new HBox();

                // Initialise le titre du projet
                Button repoName = new Button(r.getNom());

                // Initialise le chemin de la position du projet
                Label label = new Label(r.getPath());

                // Ajoute les deux composants dans la hbox
                nodeContainer.getChildren().addAll(repoName,label);

                // Ajoute la HBox dans la ListView
                repoList.add(nodeContainer);

                // Style
                nodeContainer.setStyle("-fx-alignment: CENTER_LEFT");
                repoName.setStyle("-fx-text-fill: cyan; -fx-border-color: transparent;-fx-border-width: 0; -fx-background-radius: 0; -fx-background-color: transparent;");
                label.setStyle("-fx-text-fill: #939393"); // A mettre dans le css

                // Evenement
                repoName.setOnAction(event->
                {
                    this.krakit.currentTab(r);
                });
            }
            // Sinon n'ajoute pas de projet
        }
    }

    public void clone(ActionEvent actionEvent)
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

            Scene scene = new Scene(borderPane, Screen.getPrimary().getVisualBounds().getWidth()*0.5 ,Screen.getPrimary().getVisualBounds().getHeight()*0.5);
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
        if(selectedDirectory!=null && selectedDirectory.exists())
        {
            // Verifie si le dossier contient un dossier .git
            boolean isGitFile=false;
            for(File f : selectedDirectory.listFiles())
            {
                if(f.getName().equals(".git"))
                {
                    isGitFile = true;
                }
            }

            // Si le dossier contient un .git, ajoute le dossier en tant que projet
            if(isGitFile)
            {
                this.krakit.ajouterRepo(selectedDirectory.getName(),selectedDirectory.getAbsolutePath());
            }
        }
    }

    //
}
