package krakit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import krakit.Main;
import krakit.modeles.Krakit;

public class ControllerCloneExtendedMenu extends Controller {

    // ATTRIBUT
    private ObservableList<Node> observableListColumn3;
    private Stage stage;

    // Composants graphique
    /*
    @FXML
    private ListView column3;
     */
    @FXML
    private VBox column3;

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerCloneExtendedMenu(Krakit krakit, Stage stage , ListView column3, ObservableList<Node> observableListColum3)
    {
        super(krakit);

        //this.column3 = column3;
        this.observableListColumn3 = observableListColum3;
        this.stage = stage;

    }

    /**
     *
     * @param krakit
     */
    public ControllerCloneExtendedMenu(Krakit krakit, VBox column3, ObservableList<Node> observableListColum3)
    {
        super(krakit);

        this.column3 = column3;
        this.observableListColumn3 = observableListColum3;

    }

    //

    // GETTER / SETTER



    //

    // PROCEDURES

    /**
     *
     * @param actionEvent
     */
    public void clone(ActionEvent actionEvent)
    {
        // Supprime les éléments dans la troisieme colonne
        column3.getChildren().clear();

        // Insérer les buttons dans la liste suivante
        try
        {
            ControllerCloneUrl ccu = new ControllerCloneUrl(krakit,stage);

            // Charge la vue associé a ce modèle
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/cloneUrlMenu.fxml"));
            loader.setControllerFactory(ic->ccu);

            VBox vbox = loader.load();

            // Ajoute les différents éléments
            for(Object node : vbox.getChildren().toArray())
            {
                if(node instanceof HBox)
                {
                    HBox h = (HBox)node;
                    h.prefWidthProperty().bind(column3.prefWidthProperty());
                    h.setPrefHeight(25.0);
                    column3.getChildren().add(h);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //
}
