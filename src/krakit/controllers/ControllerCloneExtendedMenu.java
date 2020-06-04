package krakit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import krakit.Main;
import krakit.modeles.Krakit;

public class ControllerCloneExtendedMenu extends Controller {

    // ATTRIBUT
    private ObservableList<Node> observableListColumn3;

    // Composants graphique
    @FXML
    private ListView column3;

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerCloneExtendedMenu(Krakit krakit, ListView column3, ObservableList<Node> observableListColum3)
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

    }

    //
}
