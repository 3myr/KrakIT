package krakit.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
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
    private SplitPane splitPane;

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

            // Recupere le nombre de commit
            // VERIFIER SI GIT EST INSTALLE
            ProcessBuilder builder = new ProcessBuilder();

            // Commandes a executer ( && permet de lancer plusieurs commandes d'affilée )
            builder.command("cmd.exe", "/c", "git branch&&echo $&&git rev-list --all --count&&echo $&&git rev-list --all --pretty=format:\"%an | %ar | %s\"");

            // Fixe le repertoire a partir duquel lancer la CMD
            builder.directory(new File("C:\\Users\\remyd\\Documents\\Projets\\Informatique\\Personnel\\KrakIT\\KrakIT"));
            //builder.directory(new File(repo.getPath()));

            // Execute les commandes
            Process p = builder.start();

            // Buffer pour lire les prints / erreurs
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // Récupère les sorties des différentes commandes
            String s = null;
            ArrayList<ArrayList<String>> stdout = new ArrayList<ArrayList<String>>();
            boolean error=false;
            ArrayList<String> tmp = new ArrayList<String>();

            while ((s = stdInput.readLine()) != null)
            {
                // Caractère de séparation de sortie de commandes
                if(!s.equals("$"))
                {
                    tmp.add(s);
                }
                else // Cas ou on se trouve dans une nouvelle commande
                {
                    stdout.add(tmp);
                    tmp = new ArrayList<String>();
                }
            }

            // Ajoute la dernière ligne si elle ne se trouve pas deja dans la liste
            if(!stdout.contains(tmp))
            {
                stdout.add(tmp);
            }

            // Inutile pour le moment
            while ((s = stdError.readLine()) != null) {
                System.out.println("Error : "+s);
                error=true;
            }

            /*
            // Affiche les sorties des commandes
            for(ArrayList<String> sortie: stdout)
            {
                System.out.println(sortie);
            }
             */

            // Traitement des informations récupéré

            // Si il y a une erreur, executer une action ( A FAIRE )
            if(error)
            {
                throw new Exception("pas de git");
            }

            ArrayList<String> commits = new ArrayList<String>(Integer.parseInt(stdout.get(1).get(0)));
            int cmp=0;
            for(String info : stdout.get(2))
            {
                info.replace("^[^,]*,","");
                if(cmp%2!=0)
                {
                    commits.add(info);
                }
                cmp++;
            }

            /*
            // Affichage des commits
            for(String info : commits)
            {
                System.out.println(info);
            }
             */

            // Ajouter des HBox dans la ListView du milieu
            for(int i=0;i<Integer.parseInt(stdout.get(1).get(0));i++)
            {
                // Recupère les informations pour chaque liste
                String author = commits.get(i).substring(0,commits.get(i).indexOf('|'));
                String hour = commits.get(i).substring(commits.get(i).indexOf('|')+1,commits.get(i).lastIndexOf('|'));
                String comment = commits.get(i).substring(commits.get(i).lastIndexOf('|')+1,commits.get(i).length());

                // Liste contenant les noms des auteurs des commits
                HBox authorContainer = new HBox();
                // Position
                authorContainer.setPadding(new Insets(20, 0, 0, 20));
                Label name = new Label(author);
                name.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                authorContainer.getChildren().add(name);

                authorObservableList.addAll(authorContainer);

                // Liste contenant la date des commits
                HBox hourContainer = new HBox();
                // Position
                hourContainer.setPadding(new Insets(20, 0, 0, 20));
                Label hourLabel = new Label(hour);
                hourLabel.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                hourContainer.getChildren().add(hourLabel);

                hourObservableList.addAll(hourContainer);

                // Liste contenant la premières lignes des commentaires des commits
                HBox commentContainer = new HBox();
                // Position
                commentContainer.setPadding(new Insets(20, 0, 0, 20));
                Label commentLabel = new Label(comment);
                commentLabel.setStyle("-fx-text-fill: black; -fx-font-size: 12px; -fx-font-weight: bold");
                commentContainer.getChildren().add(commentLabel);

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

                    // SplitPane
                    // Affiche les Split Pane Divider quand l'utilisateur passe la souris dessus

                }
            });


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //
}
