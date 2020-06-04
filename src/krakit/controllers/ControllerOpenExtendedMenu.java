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
import javafx.stage.Stage;
import krakit.Main;
import krakit.modeles.Krakit;

import java.util.List;

public class ControllerOpenExtendedMenu extends Controller {

    // ATTRIBUT
    private ObservableList<Node> observableListColumn3;
    private Stage stage;

    // Composants graphique
    @FXML
    private ListView column3;

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerOpenExtendedMenu(Krakit krakit, Stage stage, ListView column3, ObservableList<Node> observableListColum3)
    {
        super(krakit);

        this.column3 = column3;
        this.observableListColumn3 = observableListColum3;

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
        // Supprime tout les éléments dans la liste
        observableListColumn3.clear();

        // Insérer les buttons dans la liste suivante
        try
        {
            ControllerOpenExtendedMenu cmmr = new ControllerOpenExtendedMenu(krakit,stage,column3,observableListColumn3);

            // Charge la vue associé a ce modèle
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/openExtendedMenu.fxml"));
            loader.setControllerFactory(ic->cmmr);

            VBox vbox = loader.load();

            for(Object node : vbox.getChildren().toArray())
            {
                Button b = (Button)node;
                b.prefWidthProperty().bind(column3.prefWidthProperty());
                b.getStylesheets().add(Main.class.getResource("css/dark.css").toExternalForm());
                b.getStyleClass().add("buttonRepoTab");
                observableListColumn3.add(b);
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
    public void recentlyOpen(ActionEvent actionEvent)
    {
        // Supprime tout les éléments dans la liste
        observableListColumn3.clear();

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
                    observableListColumn3.add(h);
                }
                else
                {
                    if(node instanceof ListView)
                    {
                        ListView l = (ListView)node;
                        l.prefWidthProperty().bind(column3.prefWidthProperty());
                        l.prefHeightProperty().bind(column3.heightProperty().subtract(60.0));
                        observableListColumn3.add(l);
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
        observableListColumn3.clear();

    }

    //

}
