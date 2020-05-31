package krakit.outils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;

public class SimpleFileTreeItem extends TreeItem<File> {

    // ATTRIBUT

    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf;

    //

    // CONSTRUCTEUR

    /**
     *
     * @param f
     */
    public SimpleFileTreeItem(File f) {
        super(f);
    }

    //

    // GETTER / SETTER

    /**
     * Verifie si l'item est un fichier
     * @return
     */
    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf)
        {
            isFirstTimeLeaf = false;
            File f = (File) getValue();
            isLeaf = f.isFile();
        }

        return isLeaf;
    }

    //

    // PROCEDURES

    /**
     * Retourne tout les fils a partir du fichier père ( rentrer en parametre du constructeur )
     * @return
     */
    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren)
        {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }

    /**
     * Retourne une collection d'item a partir du fichier fourni en parametre du constructeur
     * @param TreeItem
     * @return si l'item n'est pas un dossier ou un fichier, retourne une collection vide
     */
    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f != null && f.isDirectory())
        {
            File[] files = f.listFiles();
            if (files != null)
            {
                ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                for (File childFile : files)
                {
                    children.add(new SimpleFileTreeItem(childFile));
                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }

    //
}
