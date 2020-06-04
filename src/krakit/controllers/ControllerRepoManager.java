package krakit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import krakit.Main;
import krakit.modeles.Krakit;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRepoManager extends Controller implements Initializable {

    // ATTRIBUT
    private ObservableList<Node> observableListColumn1;
    private ObservableList<Node> observableListColumn2;
    private ObservableList<Node> observableListColumn3;
    private Stage stage;


    // Composants graphique
    @FXML
    private ListView column1;
    @FXML
    private ListView column2;
    @FXML
    private ListView column3;


    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerRepoManager(Krakit krakit, Stage stage)
    {
        super(krakit);

        observableListColumn1 = FXCollections.<Node>observableArrayList();
        observableListColumn2 = FXCollections.<Node>observableArrayList();
        observableListColumn3 = FXCollections.<Node>observableArrayList();

        this.stage = stage;
    }


    //

    // GETTER / SETTER



    //

    // PROCEDURES

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            ControllerMainMenuRepoManager cmmr = new ControllerMainMenuRepoManager(krakit,stage,column2,observableListColumn2,column3,observableListColumn3);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/mainMenuRepoManager.fxml"));
            loader.setControllerFactory(ic->cmmr);

            VBox vbox = loader.load();

            for(Object node : vbox.getChildren().toArray())
            {
                Button b = (Button)node;
                b.prefWidthProperty().bind(column1.prefWidthProperty());
                b.getStylesheets().add(Main.class.getResource("css/dark.css").toExternalForm());
                b.getStyleClass().add("buttonRepoTab");
                observableListColumn1.add(b);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        column1.setItems(observableListColumn1);
        column2.setItems(observableListColumn2);
        column3.setItems(observableListColumn3);
    }

    /**
     *
     */
    public void mettreAJour()
    {

    }

    //
}
