package krakit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import krakit.Main;
import krakit.modeles.Krakit;

import java.io.File;
import java.util.List;

public class ControllerOpenExtendedMenu extends Controller {

    // ATTRIBUT
    private Stage stage;

    // Composants graphique


    @FXML
    private VBox column3;

    // CONSTRUCTEUR


    /**
     *
     * @param krakit
     */
    public ControllerOpenExtendedMenu(Krakit krakit, Stage stage, VBox column3)
    {
        super(krakit);

        this.column3 = column3;

        this.stage = stage;

    }

    //

    // GETTER / SETTER



    //

    // PROCEDURES

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

                // Ferme la fenetre quand un projet existe
                this.stage.close();
            }
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void recentlyOpen(ActionEvent actionEvent)
    {
        // Supprime tout les éléments dans la liste
        column3.getChildren().clear();

        // Insérer les buttons dans la liste suivante
        try
        {
            ControllerRecentlyOpenedMenu crom = new ControllerRecentlyOpenedMenu(krakit,stage);

            // Charge la vue associé a ce modèle
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/recentlyOpenedMenu.fxml"));
            loader.setControllerFactory(ic->crom);

            VBox vbox = loader.load();

            // Ajoute les différents éléments
            for(Object node : vbox.getChildren().toArray())
            {
                if(node instanceof HBox)
                {
                    HBox h = (HBox)node;
                    h.prefWidthProperty().bind(column3.prefWidthProperty());
                    h.setPrefHeight(25.0);
                    //observableListColumn3.add(h);
                    column3.getChildren().add(h);
                }
                else
                {
                    if(node instanceof ListView)
                    {
                        ListView l = (ListView)node;
                        l.prefWidthProperty().bind(column3.prefWidthProperty());
                        l.prefHeightProperty().bind(column3.heightProperty().subtract(60.0));
                        column3.getChildren().add(l);
                    }
                }
            }
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
    public void favorite(ActionEvent actionEvent)
    {
        // Supprime tout les éléments dans la liste
        column3.getChildren().clear();

    }

    //

}
