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
import java.util.ArrayList;
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
    @FXML
    private PasswordField password;
    @FXML
    private TextField login;


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
     *
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
     *
     * @param actionEvent
     */
    public void clone(ActionEvent actionEvent)
    {
        try
        {
            // Faire des verifications + git clone
            // Recupere le nombre de commit
            // VERIFIER SI GIT EST INSTALLE
            ProcessBuilder builder = new ProcessBuilder();

            if(!password.getText().isEmpty() && !login.getText().isEmpty())
            {
                // Commandes a executer ( && permet de lancer plusieurs commandes d'affilée )
                //builder.command("cmd.exe", "/c", "git clone "+this.url.getText()+" "+this.namePath.getText());
                //builder.command("D:\\Logiciel\\Git\\git-bash.exe", "git clone "+ "http://" + login.getText()+":"+password.getText() +this.url.getText().substring(0,this.url.getText().indexOf("@"))+" "+this.namePath.getText());
            }
            else
            {
                // Commandes a executer ( && permet de lancer plusieurs commandes d'affilée )
                if(this.url.getText().contains("https"))
                {
                    //builder.command("cmd.exe", "/c", "git config --global --unset http.proxy&&git config --global --unset https.proxy&&git clone "+ "http://" + login.getText()+":"+password.getText() +this.url.getText().substring(0,this.url.getText().indexOf("@"))+" "+this.namePath.getText());
                    //builder.command("D:\\Logiciel\\Git\\git-bash.exe", "git clone "+ "http://" + login.getText()+":"+password.getText() +this.url.getText().substring(0,this.url.getText().indexOf("@"))+" "+this.namePath.getText());
                }
                else
                {
                    //builder.command("cmd.exe", "/c", "git config --global --unset http.proxy&&git config --global --unset https.proxy&&git clone "+ "https://" + login.getText()+":"+password.getText() +this.url.getText().substring(0,this.url.getText().indexOf("@"))+" "+this.namePath.getText());
                    //builder.command("D:\\Logiciel\\Git\\git-bash.exe", "git clone "+ "https://" + login.getText()+":"+password.getText() +this.url.getText().substring(0,this.url.getText().indexOf("@"))+" "+this.namePath.getText());
                }
            }

            builder.command("D:\\Logiciel\\Git\\git-bash.exe", "git clone https://demazier1u@redmine.fst.univ-lorraine.fr/redmine-atelis/twisk/demazieres/twisk24.git "+this.namePath.getText(), "&&ls");
            //System.out.println("git clone https://demazier1u@redmine.fst.univ-lorraine.fr/redmine-atelis/twisk/demazieres/twisk24.git "+this.namePath.getText());
            //builder.command("D:\\Logiciel\\Git\\git-bash.exe", "ls");

            // TEST -----------------------
            //System.out.println("https://" + login.getText()+":"+password.getText() +this.url.getText().substring(this.url.getText().indexOf("@"),this.url.getText().length())+" "+this.namePath.getText());
            // ----------------------------

            // Fixe le repertoire a partir duquel lancer la CMD
            builder.directory(new File(fullPath.getText()));

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
                System.out.println("OK : "+s);
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

            // Ferme les buffers
            stdError.close();
            stdInput.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        /*
        System.out.println(login.getText());
        //System.out.println(password.getText());
        System.out.println(fullPath.getText()+""+namePath.getText());
        System.out.println("Done !");
         */
    }

    //
}
