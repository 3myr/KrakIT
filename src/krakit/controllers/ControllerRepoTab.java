package krakit.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerRepoTab extends Controller implements Initializable
{

    // ATTRIBUT

    // Composants graphiques
    @FXML
    private TreeView treeView;
    @FXML
    private ListView listCommit;
    @FXML
    private ListView listHour;
    @FXML
    private ListView listComment;
    @FXML
    private TextArea commitMessage;
    @FXML
    private Button sendCommit;


    // Propriétés
    private Repo repo;
    private ObservableList<HBox> authorObservableList;
    private ObservableList<HBox> hourObservableList;
    private ObservableList<HBox> commentObservableList;


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

        authorObservableList = FXCollections.<HBox>observableArrayList();
        hourObservableList = FXCollections.<HBox>observableArrayList();
        commentObservableList = FXCollections.<HBox>observableArrayList();

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
        try
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

            // Recupere tout les commits
            ArrayList<String> commits = this.krakit.getCommits();

            // Ajouter des HBox dans la ListView du milieu
            for(int i=0;i<commits.size();i++)
            {
                // Recupère les informations pour chaque liste
                String id = commits.get(i);
                i++;
                String author = commits.get(i).substring(0,commits.get(i).indexOf('|'));
                String hour = commits.get(i).substring(commits.get(i).indexOf('|')+1,commits.get(i).lastIndexOf('|'));
                String comment = commits.get(i).substring(commits.get(i).lastIndexOf('|')+1,commits.get(i).length());

                // Liste contenant les noms des auteurs des commits
                HBox authorContainer = new HBox();
                // Position
                authorContainer.setPadding(new Insets(20, 10, 0, 20));
                authorContainer.setAlignment(Pos.CENTER_RIGHT);
                Label name = new Label(author);
                name.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                authorContainer.getChildren().add(name);
                authorContainer.setId(id);

                authorObservableList.addAll(authorContainer);

                // Liste contenant la date des commits
                HBox hourContainer = new HBox();
                // Position
                hourContainer.setPadding(new Insets(20, 0, 0, 20));
                hourContainer.setAlignment(Pos.CENTER);
                Label hourLabel = new Label(hour);
                hourLabel.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                hourContainer.getChildren().add(hourLabel);
                hourContainer.setId(id);

                hourObservableList.addAll(hourContainer);

                // Liste contenant la premières lignes des commentaires des commits
                HBox commentContainer = new HBox();
                // Position
                commentContainer.setPadding(new Insets(20, 0, 0, 20));
                commentContainer.setAlignment(Pos.CENTER_LEFT);
                Label commentLabel = new Label(comment);
                commentLabel.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                commentContainer.getChildren().add(commentLabel);
                commentContainer.setId(id);

                commentObservableList.addAll(commentContainer);
            }

            // Ajoute les item dans la ListView
            listCommit.setItems(authorObservableList);
            listHour.setItems(hourObservableList);
            listComment.setItems(commentObservableList);

            // Bind les property des ScrollBar
            Platform.runLater(new Runnable()
            {
                @Override
                public void run() {
                    // ScrollBar
                    ScrollBar s1 = (ScrollBar)listCommit.lookup(".scroll-bar");
                    if (s1!=null)
                    {
                        ScrollBar s2 = (ScrollBar)listHour.lookup(".scroll-bar");
                        if (s2!=null)
                        {
                            ScrollBar s3 = (ScrollBar)listComment.lookup(".scroll-bar");
                            if (s3!=null)
                            {
                                s1.valueProperty().bindBidirectional(s2.valueProperty());
                                s1.valueProperty().bindBidirectional(s3.valueProperty());
                                s2.valueProperty().bindBidirectional(s3.valueProperty());
                            }
                        }
                    }
                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Realise un commit sur le git
     * @param actionEvent
     */
    public void commit(ActionEvent actionEvent)
    {
        // Tests ( supprimer les lignes d'en dessous pour les tests)
        if(!commitMessage.getText().contains("\n"))
        {
            krakit.setCommits("Commit de Test | 15 | "+commitMessage.getText());
        }
        else
        {
            krakit.setCommits("Commit de Test | 15 | "+commitMessage.getText().substring(0,commitMessage.getText().indexOf('\n')));
        }
        krakit.setCommits(Math.random()+"");
        //krakit.setCommits("123456789");
        // -------------------------------------------------------

        // Recupère les commits
        try
        {
            krakit.getCommits();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        // Verifie que le commit n'est pas déjà présent dans la liste
        for(int i=0;i<krakit.commits().size();i++)
        {
            boolean contains=false;

            // Récupère le numéro du commit
            String id = krakit.commits().get(i);
            i++;

            // Verification commit dans liste
            for(HBox h : authorObservableList)
            {
                if(h.getId().equals(id))
                {
                    contains=true;
                }
            }

            // Si le commit n'est pas affiché, on l'affiche
            if(!contains)
            {
                // Recupère les informations pour chaque liste
                String author = krakit.commits().get(i).substring(0,krakit.commits().get(i).indexOf('|'));
                String hour = krakit.commits().get(i).substring(krakit.commits().get(i).indexOf('|')+1,krakit.commits().get(i).lastIndexOf('|'));
                String comment = krakit.commits().get(i).substring(krakit.commits().get(i).lastIndexOf('|')+1,krakit.commits().get(i).length());

                // Liste contenant les noms des auteurs des commits
                HBox authorContainer = new HBox();
                // Position
                authorContainer.setPadding(new Insets(20, 10, 0, 20));
                authorContainer.setAlignment(Pos.CENTER_RIGHT);
                Label name = new Label(author);
                name.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                authorContainer.getChildren().add(name);
                authorContainer.setId(id);

                // Ajoute le details du commit au début de la liste
                authorObservableList.add(0,authorContainer);

                // Liste contenant la date des commits
                HBox hourContainer = new HBox();
                // Position
                hourContainer.setPadding(new Insets(20, 0, 0, 20));
                hourContainer.setAlignment(Pos.CENTER);
                Label hourLabel = new Label(hour);
                hourLabel.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                hourContainer.getChildren().add(hourLabel);
                hourContainer.setId(id);

                // Ajoute le details du commit au début de la liste
                hourObservableList.add(0,hourContainer);

                // Liste contenant la premières lignes des commentaires des commits
                HBox commentContainer = new HBox();
                // Position
                commentContainer.setPadding(new Insets(20, 0, 0, 20));
                commentContainer.setAlignment(Pos.CENTER_LEFT);
                Label commentLabel = new Label(comment);
                commentLabel.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                commentContainer.getChildren().add(commentLabel);
                commentContainer.setId(id);

                // Ajoute le details du commit au début de la liste
                commentObservableList.add(0,commentContainer);
            }
        }



        // Met a jour les controllers
        this.krakit.reagir();
    }

    //
}
