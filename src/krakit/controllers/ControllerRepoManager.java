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
    private boolean showOpen;
    private boolean showClone;
    private boolean showInit;

    // Composants graphique
    @FXML
    private VBox column1;
    @FXML
    private VBox column2;
    @FXML
    private VBox column3;


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
        this.stage.setResizable(false);
    }


    //

    // GETTER / SETTER

    /**
     *
     * @return
     */
    public boolean isShowOpen() {
        return showOpen;
    }

    /**
     *
     * @return
     */
    public boolean isShowClone() {
        return showClone;
    }

    /**
     *
     * @return
     */
    public boolean isShowInit() {
        return showInit;
    }

    /**
     *
     * @param showOpen
     */
    public void setShowOpen(boolean showOpen) {
        this.showOpen = showOpen;
        this.showClone = false;
        this.showInit = false;
    }

    /**
     *
     * @param showClone
     */
    public void setShowClone(boolean showClone) {
        this.showOpen = false;
        this.showClone = showClone;
        this.showInit = false;
    }

    /**
     *
     * @param showInit
     */
    public void setShowInit(boolean showInit) {
        this.showOpen = false;
        this.showClone = false;
        this.showInit = showInit;
    }

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
            ControllerMainMenuRepoManager cmmr = new ControllerMainMenuRepoManager(krakit,stage,column2,column3);

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
                column1.getChildren().add(b);
            }

            // Indique quel menu mettre par defaut
            if(showClone)
            {
                // Permet d'affiché le menu par défaut
                cmmr.clone(null);
            }
            else
            {
                if(showOpen)
                {
                    // Permet d'affiché le menu par défaut
                    cmmr.open(null);
                }
                else
                {
                    if(showInit)
                    {
                        // Permet d'affiché le menu par défaut
                        cmmr.init(null);
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
     */
    public void mettreAJour()
    {
        column1.getChildren().clear();

        try
        {
            ControllerMainMenuRepoManager cmmr = new ControllerMainMenuRepoManager(krakit,stage,column2,column3);

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
                column1.getChildren().add(b);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //
}
