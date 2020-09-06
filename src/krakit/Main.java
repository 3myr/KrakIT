package krakit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import krakit.controllers.ControllerKrakit;
import krakit.controllers.ControllerMainMenu;
import krakit.controllers.ControllerSignature;
import krakit.controllers.ControllerTabMenu;
import krakit.modeles.Krakit;

import java.nio.file.Files;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Modeles
        Krakit krakit = new Krakit();

        // Controllers
        ControllerKrakit ck = new ControllerKrakit(krakit);
        ControllerMainMenu cmm = new ControllerMainMenu(krakit);
        ControllerTabMenu ctm = new ControllerTabMenu(krakit);
        ControllerSignature cs = new ControllerSignature(krakit);

        // Chargement du FXML
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("vues/krakit.fxml"));

        // Charge les controllers
        loader.setControllerFactory(ic -> {
            if (ic.equals(krakit.controllers.ControllerKrakit.class)) return ck;
            else if (ic.equals(krakit.controllers.ControllerMainMenu.class)) return cmm;
            else if (ic.equals(krakit.controllers.ControllerTabMenu.class)) return ctm;
            else if (ic.equals(krakit.controllers.ControllerSignature.class)) return cs;
            return null ;
        });

        // Fixe le fxml dans le root
        Parent root = loader.load();

        // Initialisation de la scene
        Scene scene = new Scene(root,1010,540);

        // Charge les feuilles de styles ( css )
        scene.getStylesheets().add(Main.class.getResource("css/dark.css").toURI().toString());

        primaryStage.setTitle("KrakIT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
