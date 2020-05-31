package krakit.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;

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

    //
}
