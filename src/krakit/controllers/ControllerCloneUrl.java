package krakit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import krakit.modeles.Krakit;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerCloneUrl extends Controller implements Initializable{

    // ATTRIBUT
    private ObservableList<Node> observableListColumn3;
    private Stage stage;

    // Composants graphique
    @FXML
    private TextField path;
    @FXML
    private TextField url;
    @FXML
    private Label fullPath;
    @FXML
    private TextField namePath;
    @FXML
    private HBox containerFullPath;
    @FXML
    private Button cloneRepo;


    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerCloneUrl(Krakit krakit, Stage stage)
    {
        super(krakit);

        this.stage = stage;
    }

    //

    // GETTER / SETTER



    //

    // PROCEDURES

    /**
     * Permet de choisir un emplacement
     * @param actionEvent
     */
    public void browse(ActionEvent actionEvent)
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

        // Fixe le chemin dans le TextField
        if(!selectedDirectory.getAbsolutePath().isEmpty())
        {
            path.setText(selectedDirectory.getAbsolutePath());
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
        // Evenements quand l'utilisateur ecrit dans le TextField pour rentrer une url
        this.url.textProperty().addListener((obs, oldText, newText) -> {

            // Si l'utilisateur a écrit dans les deux TextField, fait apparaitre un TextField récapitulatif et rend accessible le boutton pour créer le projet
            if(!this.url.getText().isEmpty() && !this.path.getText().isEmpty())
            {
                // Si l'url contient un /, le nom du projet par défaut sera celui du string apres le dernier /
                if(this.url.getText().contains("/"))
                {
                    String text = this.url.getText().substring(this.url.getText().lastIndexOf('/')+1,this.url.getText().length());

                    // Supprime le string .git du nom du projet par défaut
                    if(text.contains(".git"))
                    {
                        namePath.setText(text.substring(0,text.indexOf(".git")));
                    }
                    else
                    {
                        namePath.setText(text);
                    }
                }
                else
                {
                    // Supprime le string .git du nom du projet par défaut
                    if(this.url.getText().contains(".git"))
                    {
                        namePath.setText(this.url.getText().substring(0,this.url.getText().indexOf(".git")));
                    }
                    else
                    {
                        namePath.setText(this.url.getText());
                    }
                }

                // Affiche le chemin où sera créer le repertoire contenant le projet et active le boutton pour créer le projet
                fullPath.setText(path.getText().replace("\\","/")+"/");
                containerFullPath.setVisible(true);
                cloneRepo.setDisable(false);
            }
            else
            {
                // Nettoye les informations et desactive le boutton pour creer le projet
                namePath.clear();
                containerFullPath.setVisible(false);
                cloneRepo.setDisable(true);
            }
        });

        // Evenements quand l'utilisateur ecrit dans le TextField pour rentrer une url
        this.path.textProperty().addListener((obs, oldText, newText) -> {
            // Si l'utilisateur a écrit dans les deux TextField, fait apparaitre un TextField récapitulatif et rend accessible le boutton pour créer le projet
            if(!this.path.getText().isEmpty() && !this.url.getText().isEmpty())
            {
                // Affiche le chemin où sera créer le repertoire contenant le projet et active le boutton pour créer le projet
                fullPath.setText(path.getText().replace("\\","/")+"/");
                containerFullPath.setVisible(true);
                cloneRepo.setDisable(false);
            }
            else
            {
                // Nettoye les informations et desactive le boutton pour creer le projet
                namePath.clear();
                containerFullPath.setVisible(false);
                cloneRepo.setDisable(true);
            }
        });
    }

    /**
     * Permet de clone un projet
     * @param actionEvent
     */
    public void clone(ActionEvent actionEvent)
    {
        Path path = Paths.get(fullPath.getText()+namePath.getText());
        try
        {
            krakit.gitClone(path,url.getText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    //
}
