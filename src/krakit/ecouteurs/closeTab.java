package krakit.ecouteurs;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import krakit.modeles.Krakit;
import krakit.modeles.Repo;

import java.util.HashMap;

public class closeTab implements EventHandler<Event> {

    // ATTRIBUT

    private Krakit krakit;
    private TabPane tabPane;
    private HashMap<Repo, Tab> tabs;
    private Repo repo;

    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public closeTab(Krakit krakit, Repo repo, TabPane tabPane, HashMap<Repo, Tab> tabs)
    {
        this.krakit = krakit;
        this.tabPane = tabPane;
        this.tabs = tabs;
        this.repo = repo;
    }

    //

    // GETTER / SETTER



    //

    // PROCEDURES

    @Override
    public void handle(Event event)
    {
        // Retire l'onglet du modele et de la liste d'onglet
        tabs.remove(repo,tabs.get(repo));
        krakit.supprimerRepo(repo);

        // Selectionne l'onglet suivant ( ne surtout pas selectionné le premier onglet )
        tabPane.getSelectionModel().selectNext();

        // Si après suppression il n'y a plus d'onglets dans la liste, en rajouter un
        if(tabs.size()<1)
        {
            krakit.ajouterRepo();
        }
    }

    //
}
