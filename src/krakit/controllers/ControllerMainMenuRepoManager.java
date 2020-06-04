package krakit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class ControllerMainMenuRepoManager extends Controller implements Initializable {

    // ATTRIBUT
    private ObservableList<Node> observableListColumn2;
    private ObservableList<Node> observableListColumn3;
    private Stage stage;

    // Composants graphique
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
    public ControllerMainMenuRepoManager(Krakit krakit)
    {
        super(krakit);

    }

    /**
     *
     * @param krakit
     */
    public ControllerMainMenuRepoManager(Krakit krakit, Stage stage, ListView column2, ObservableList<Node> observableListColum2, ListView column3, ObservableList<Node> observableListColum3)
    {
        super(krakit);

        this.column2 = column2;
        this.observableListColumn2 = observableListColum2;

        this.column3 = column3;
        this.observableListColumn3 = observableListColum3;

        this.stage = stage;

    }


    //

    // GETTER / SETTER



    //

    // PROCEDURES

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    /**
     *
     */
    public void mettreAJour()
    {

    }

    /**$
     *
     * @param actionEvent
     */
    public void open(ActionEvent actionEvent)
    {
        // Supprime tout les éléments dans la liste
        observableListColumn2.clear();

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
                b.prefWidthProperty().bind(column2.prefWidthProperty());
                b.getStylesheets().add(Main.class.getResource("css/dark.css").toExternalForm());
                b.getStyleClass().add("buttonRepoTab");
                observableListColumn2.add(b);
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
    public void clone(ActionEvent actionEvent)
    {
        // Supprime tout les éléments dans la liste
        observableListColumn2.clear();

        // Insérer les buttons dans la liste suivante
        try
        {
            ControllerCloneExtendedMenu ccem = new ControllerCloneExtendedMenu(krakit,column3,observableListColumn3);

            // Charge la vue associé a ce modèle
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/cloneExtendedMenu.fxml"));
            loader.setControllerFactory(ic->ccem);

            VBox vbox = loader.load();

            for(Object node : vbox.getChildren().toArray())
            {
                Button b = (Button)node;
                b.prefWidthProperty().bind(column2.prefWidthProperty());
                b.getStylesheets().add(Main.class.getResource("css/dark.css").toExternalForm());
                b.getStyleClass().add("buttonRepoTab");
                observableListColumn2.add(b);
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
    public void init(ActionEvent actionEvent)
    {
        // Supprime tout les éléments dans la liste
        observableListColumn2.clear();

        // Insérer les buttons dans la liste suivante
        try
        {
            ControllerInitExtendedMenu ciem = new ControllerInitExtendedMenu(krakit,column3,observableListColumn3);

            // Charge la vue associé a ce modèle
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/initExtendedMenu.fxml"));
            loader.setControllerFactory(ic->ciem);

            VBox vbox = loader.load();

            for(Object node : vbox.getChildren().toArray())
            {
                Button b = (Button)node;
                b.prefWidthProperty().bind(column2.prefWidthProperty());
                b.getStylesheets().add(Main.class.getResource("css/dark.css").toExternalForm());
                b.getStyleClass().add("buttonRepoTab");
                observableListColumn2.add(b);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //
}
