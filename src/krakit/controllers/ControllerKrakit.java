package krakit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import krakit.modeles.Krakit;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerKrakit extends Controller implements Initializable
{

    // ATTRIBUT

    // Composants graphique
    @FXML
    private HBox mainMenu;
    @FXML
    private TabPane tabMenu;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane signature;


    //

    // CONSTRUCTEUR

    /**
     *
     * @param krakit
     */
    public ControllerKrakit(Krakit krakit)
    {
        super(krakit);
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
        AnchorPane.setTopAnchor(tabMenu,mainMenu.getPrefHeight());
    }

    //
}
