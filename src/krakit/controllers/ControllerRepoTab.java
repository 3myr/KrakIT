package krakit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import krakit.Main;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;
import krakit.outils.SimpleFileTreeItem;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRepoTab extends Controller implements Initializable
{


    // ATTRIBUT

    // Composants graphiques
    @FXML
    private TreeView treeView;

    // Propriétés
    private Repo repo;

    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerRepoTab(Krakit krakit, Repo repo)
    {
        super(krakit);
        this.repo = repo;
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

    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Initialise
        treeView.setRoot(new SimpleFileTreeItem(new File(System.getProperty("user.dir")))); // CHANGER LE DOSSIER D'EMPLACEMENT PAR CELUI DU PROJET CHARGER
        //treeView.setRoot(new SimpleFileTreeItem(new File(repo.getPath())));

        // Fixe l'affichage des données représenté dans la TreeView
        treeView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>()
        {
            public TreeCell<File> call(TreeView<File> tv) {
                return new TreeCell<File>() {

                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty || item!=null)
                        {
                            //  Cas ou l'item est un dossier
                            if(item.isDirectory())
                            {
                                // Premiere facon de fixer le texte ( + une image de dossier )
                                HBox container = new HBox();

                                // Alignement des éléments
                                container.setAlignment(Pos.CENTER_LEFT);

                                // Icone du dossier
                                ImageView imgView = new ImageView(new Image(Main.class.getResourceAsStream("/logo/folder.png")));
                                imgView.setFitHeight(10);
                                imgView.setFitWidth(10);

                                // Nom du fichier / dossier
                                Label l = new Label(item.getName());

                                // Ajoute les éléments dans la HBox
                                container.getChildren().addAll(imgView, l);

                                // Fixe les items
                                setGraphic(container);
                            }
                            else // Cas ou l'item est un fichier
                            {
                                setGraphic(null);

                                // Deuxieme facon de fixer le texte
                                setText(item.getName());
                            }
                        }
                        else
                        {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });

    }

    //
}
