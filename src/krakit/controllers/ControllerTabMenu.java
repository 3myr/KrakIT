package krakit.controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import krakit.Main;
import krakit.ecouteurs.closeTab;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;

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
    @FXML
    private Tab addRepo;

    // Propriete
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
        for(Repo r : krakit.getReposInTabPane())
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

                        BorderPane hBox = loader.load();
                        hBox.prefWidthProperty().bind(tabPane.widthProperty());
                        hBox.prefHeightProperty().bind(tabPane.heightProperty());
                        hBox.setStyle("-fx-background-color: #171717");
                        tab.setContent(hBox);
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

                        BorderPane hBox = loader.load();
                        hBox.prefWidthProperty().bind(tabPane.widthProperty());
                        hBox.prefHeightProperty().bind(tabPane.heightProperty());
                        hBox.setStyle("-fx-background-color: #171717");
                        tabs.get(r).setContent(hBox);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            // Evenement quand l'onglet se ferme
            tabs.get(r).setOnCloseRequest(new closeTab(krakit,r,tabPane,tabs));
        }

        // Selection de l'onglet par default
        if(tabPane.getTabs().size()<3 && tabPane.getTabs().size()>1)
        {
            tabPane.getSelectionModel().select(2);
        }

        // Garde un onglet ouvert quand l'utilisateur supprime tout les onglets
        if(tabPane.getTabs().size()<3)
        {
            this.krakit.ajouterRepo();
        }

        // Deplace l'onglet pour ajouter des pages a la fin des onglets


        // Se place sur l'onglet choisit
        // Si l'onglet courant est dans la liste d'onglet
        if(this.tabs.get(this.krakit.getCurrentTab())!=null)
        {
            this.tabPane.getSelectionModel().select(this.tabs.get(this.krakit.getCurrentTab()));
        }
        else // L'onglet n'est pas dans la liste d'onglet
        {
            // Ajouter l'onglet dans la liste
            if(this.krakit.getCurrentTab()!=null)
            {
                Tab tab = new Tab();
                tab.setText(this.krakit.getCurrentTab().getNom());

                tabs.put(this.krakit.getCurrentTab(),tab);
                tabPane.getTabs().add(tab);

                // Evenement quand l'onglet se ferme
                tab.setOnCloseRequest(event ->
                {
                    // Retire l'onglet du modele et de la liste d'onglets et reinitialise l'onglet courant
                    for(Repo r : krakit.getRepos())
                    {
                        if(r.getNom().equals(tab.getText()))
                        {
                            tabs.remove(r,tab);
                            krakit.supprimerRepo(r);
                            krakit.currentTab(null);
                        }
                    }

                    // Selectionne l'onglet suivant ( ne surtout pas selectionné le premier onglet )
                    tabPane.getSelectionModel().selectNext();

                    // Si après suppression il n'y a plus d'onglets dans la liste, en rajouter un
                    if(tabs.size()<1)
                    {
                        krakit.ajouterRepo();
                    }
                });

                this.tabPane.getSelectionModel().select(tab);
            }
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
        if(tabs.size()<1)
        {
            this.krakit.ajouterRepo();
        }

        // Empeche de selectionné l'onglet permettant d'ouvrir un projet
        tabPane.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {

            // Si le nouvel onglet selectionné est l'onglet permettant d'ouvrir un projet, se remet sur le précédent onglet et ouvre le menu pour ouvrir un projet
            if((t1.getGraphic()!=null && t1.getGraphic().equals(imgview)) || (t1.getText().equals("+")) )
            {
                if(t1.getText()!=null && t1.getText().equals("+"))
                {
                    this.krakit.ajouterRepo();
                    tabPane.getSelectionModel().selectPrevious();
                }
                else
                {
                    System.out.println("Menu ouvrir un projet");
                    tabPane.getSelectionModel().select(tab);
                }

            }

        });

    }

    //
}
