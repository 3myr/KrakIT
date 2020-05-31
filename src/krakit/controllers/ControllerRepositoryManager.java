package krakit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import krakit.modeles.Krakit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

public class ControllerRepositoryManager extends Controller {


    @FXML
    private ListView extendedMenuList; // Contient le menu détaillé a droite des boutons Open, Clone et Init

    private VBox selectedMenuList;


    // CONSTRUCTEURS

    public ControllerRepositoryManager(){
        super(null);
    }

    /**
     *
     * @param krakit
     */
    public ControllerRepositoryManager(Krakit krakit)
    {
        super(krakit);
    }

    // FONCTION D'INTIALISATION
    public void initialize(){
        extendedMenuList.getItems().add(new Button("A"));
        openRepository();
    }


    public void openRepository(){
        manageButtonClick("../vues/openExtendedMenu.fxml");
    }

    public void cloneRepository(){
        manageButtonClick("../vues/cloneExtendedMenu.fxml");
    }

    public void initRepository(){
        manageButtonClick("../vues/initExtendedMenu.fxml");
    }


    public void manageButtonClick(String viewName){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(viewName)); // On récupère la vue du menu étendu
        try {
            selectedMenuList = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mettreAJour();
    }

    public void mettreAJour(){
        extendedMenuList.getItems().clear();
        for(Object node : selectedMenuList.getChildren().toArray()){
            extendedMenuList.getItems().add((Node)node);
        }
    }


}
