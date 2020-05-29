package krakit.vues;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import krakit.controllers.Controller;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDefaultTab extends Controller implements Initializable {

    // ATTRIBUT

    // Composants graphique
    @FXML
    private VBox fileContainer;
    @FXML
    private VBox repoContainer;


    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerDefaultTab(Krakit krakit)
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
        repoContainer.getChildren().clear();

        for(Repo r : krakit.getRepos())
        {
            // Si le projet est un projet vide, il n'a pas de chemin
            if(r.getPath()!=null)
            {
                HBox nodeContainer = new HBox();

                // Initialise le titre du projet
                Button repoName = new Button(r.getNom());

                // Initialise le chemin de la position du projet
                Label label = new Label(r.getPath());

                // Ajoute les deux composants dans la hbox
                nodeContainer.getChildren().addAll(repoName,label);

                // Ajoute la HBox dans le VBox
                repoContainer.getChildren().add(nodeContainer);

                // Style
                nodeContainer.setStyle("-fx-text-alignment: center; -fx-alignment: center");
                repoName.setStyle("-fx-text-fill: blue; -fx-border-color: transparent;-fx-border-width: 0; -fx-background-radius: 0; -fx-background-color: transparent;");

                // Evenement
                repoName.setOnAction(event->
                {
                    this.krakit.currentTab(r);
                });
            }
            // Sinon n'ajoute pas de projet
        }
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
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

                // Ajoute la HBox dans le VBox
                repoContainer.getChildren().add(nodeContainer);

                // Style
                nodeContainer.setStyle("-fx-text-alignment: center; -fx-alignment: center");
                repoName.setStyle("-fx-text-fill: blue; -fx-border-color: transparent;-fx-border-width: 0; -fx-background-radius: 0; -fx-background-color: transparent;");

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
