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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import krakit.Main;
import krakit.modeles.Krakit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainMenuRepoManager extends Controller implements Initializable {

    // ATTRIBUT
    private Stage stage;

    // Composants graphique

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
    public ControllerMainMenuRepoManager(Krakit krakit, Stage stage, VBox column2, VBox column3)
    {
        super(krakit);

        this.stage = stage;
        this.column2 = column2;
        this.column3 = column3;

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
        boolean isFirstShow = false;
        if(column2.getChildren().isEmpty())
        {
            isFirstShow=true;
        }

        // Supprime tout les éléments dans la liste
        column2.getChildren().clear();

        // Insérer les buttons dans la liste suivante
        try
        {
            ControllerOpenExtendedMenu coem = new ControllerOpenExtendedMenu(krakit,stage,column3);

            // Charge la vue associé a ce modèle
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("vues/openExtendedMenu.fxml"));
            loader.setControllerFactory(ic->coem);

            VBox vbox = loader.load();

            for(Object node : vbox.getChildren().toArray())
            {
                Button b = (Button)node;
                b.prefWidthProperty().bind(column2.prefWidthProperty());
                b.getStylesheets().add(Main.class.getResource("css/dark.css").toExternalForm());
                b.getStyleClass().add("buttonRepoTab");
                column2.getChildren().add(b);
            }

            // Permet d'affiché le menu par défaut
            if(isFirstShow)
            {
                coem.recentlyOpen(actionEvent);
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
        boolean isFirstShow = false;
        if(column2.getChildren().isEmpty())
        {
            isFirstShow=true;
        }

        // Supprime tout les éléments dans la liste
        column2.getChildren().clear();

        // Insérer les buttons dans la liste suivante
        try
        {
            ControllerCloneExtendedMenu ccem = new ControllerCloneExtendedMenu(krakit,column3,stage);

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
                column2.getChildren().add(b);
            }

            // Permet d'affiché le menu par défaut
            if(isFirstShow)
            {
                ccem.clone(actionEvent);
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
    public void init(ActionEvent actionEvent) throws IOException, InterruptedException {
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
        if(selectedDirectory!=null)
        {
            this.krakit.gitInit(selectedDirectory.toPath());
            this.krakit.ajouterRepo(selectedDirectory.getName(),selectedDirectory.getAbsolutePath());
        }
    }

    //
}
