package krakit.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRecentlyOpenedMenu extends Controller implements Initializable {

    // ATTRIBUT
    private ObservableList<VBox> repoList;
    private Stage stage;

    // Composants graphique
    @FXML
    private ListView listView;


    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerRecentlyOpenedMenu(Krakit krakit, Stage stage)
    {
        super(krakit);
        repoList = FXCollections.<VBox>observableArrayList();

        this.stage = stage;
    }

    //

    // GETTER / SETTER



    //

    // PROCEDURES

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // ListView
        listView.setItems(repoList);

        // Affiche les projets non vide
        for(Repo r : krakit.getRepos())
        {
            // Si le projet n'est pas encore choisit par l'utilisateur, ne l'affiche pas dans les projets récents
            if(r.getPath()!=null)
            {
                VBox nodeContainer = new VBox();

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
                label.setStyle("-fx-text-fill: #939393; -fx-padding: 0 0 0 10"); // A mettre dans le css

                // Evenement
                repoName.setOnAction(event->
                {
                    this.krakit.currentTab(r);
                    this.stage.close();
                });
            }
            // Sinon n'ajoute pas de projet
        }
    }

    //
}
