package krakit.controllers;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import krakit.Main;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;
import krakit.vues.ControllerDefaultTab;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerTabMenu extends Controller implements Initializable
{
    // ATTRIBUT

    // Composants Graphiques
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab openRepo;

    // Propriete
    private int nbTab;
    private HashMap<Repo,Tab> tabs;

    // Propiété

    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerTabMenu(Krakit krakit)
    {
        super(krakit);

        tabs = new HashMap<Repo,Tab>(10); // Taille a changer

        this.krakit.ajouterControllers(this);
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
        // Ajoute des onglets
        for(Repo r : krakit.getRepos())
        {
            // Si l'onglet n'est pas présent dans la liste d'onglet présent, on le crée et le rajoute
            if(!tabs.containsKey(r))
            {
                // Crée un nouvel onglet
                Tab tab = new Tab(r.getNom());
                tab.setClosable(true);

                // Charge la page d'un onglet avec un projet vide
                if(r.getPath()==null)
                {
                    // Fixe un Node associé a l'onglet
                    try
                    {
                        ControllerDefaultTab cdt = new ControllerDefaultTab(krakit);

                        // FXML loader
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("vues/defaultTab.fxml"));
                        loader.setControllerFactory(ic->cdt);

                        AnchorPane anchorPane = loader.load();
                        anchorPane.setPrefSize(500,180);
                        anchorPane.setStyle("-fx-background-color: green");
                        tab.setContent(anchorPane);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else // Charger la page pour un onglet qui possède un projet
                {
                    try
                    {

                    }
                    catch (Exception e)
                    {

                    }
                }

                // Evenement quand l'onglet se ferme
                tab.setOnCloseRequest(event ->
                {
                    // Retire l'onglet du modele et de la liste d'onglet
                    tabs.remove(r,tab);
                    krakit.supprimerRepo(r);

                    // Selectionne l'onglet suivant ( ne surtout pas selectionné le premier onglet )
                    tabPane.getSelectionModel().selectNext();

                    // Si après suppression il ne reste qu'un onglet, rajoute un,
                    if(tabs.size()<1)
                    {
                        krakit.ajouterRepo();
                    }
                });

                // Ajoute l'onglet dans le menu d'onglet
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab); // Selectionne l'onglet créé

                // Ajoute l'onglet dans la liste des onglets
                tabs.put(r,tab);
            }
            else
            {
                // Si l'onglet ne donne pas sur un projet, raffraichi la liste des projets ouvert
                if(r.getPath()==null)
                {
                    // Fixe un Node associé a l'onglet
                    try
                    {
                        ControllerDefaultTab cdt = new ControllerDefaultTab(krakit);

                        // FXML loader
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("vues/defaultTab.fxml"));
                        loader.setControllerFactory(ic->cdt);

                        AnchorPane anchorPane = loader.load();
                        anchorPane.setPrefSize(500,180);
                        anchorPane.setStyle("-fx-background-color: green");
                        tabs.get(r).setContent(anchorPane);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Selection de l'onglet par default
        if(tabPane.getTabs().size()<3 && tabPane.getTabs().size()>1)
        {
            tabPane.getSelectionModel().select(1);
        }

        // Garde un onglet ouvert quand l'utilisateur supprime tout les onglets
        if(tabPane.getTabs().size()<2)
        {
            this.krakit.ajouterRepo();
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
        // Initialise une image sur l'onglet ouvrant les projets
        Image img = new Image(Main.class.getResourceAsStream("/logo/folder.png"));
        ImageView imgview = new ImageView(img);
        imgview.setFitWidth(15);
        imgview.setFitHeight(13);
        openRepo.setGraphic(imgview);

        // Ajoute au minimum un onglet
        if(tabs.size()<2)
        {
            this.krakit.ajouterRepo();
        }

        // Empeche de selectionné l'onglet permettant d'ouvrir un projet
        tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {

            // Si le nouvel onglet selectionné est l'onglet permettant d'ouvrir un projet, se remet sur le précédent onglet et ouvre le menu pour ouvrir un projet
            if(t1.getGraphic()!=null && t1.getGraphic().equals(imgview))
            {
                System.out.println("Menu ouvrir un projet");

                tabPane.getSelectionModel().select(tab);
            }

        });

    }

    //
}
