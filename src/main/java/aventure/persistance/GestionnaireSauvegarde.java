package aventure.persistance;

import aventure.modele.Partie;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GestionnaireSauvegarde {

    public void sauvegarder(Partie partie, String chemin) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chemin))) {
            out.writeObject(partie);
        }
    }

    public Partie charger(String chemin) throws IOException {
        if (!new File(chemin).exists()) {
            throw new FileNotFoundException("aucune sauvegarde trouvée");
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(chemin))) {
            return (Partie) in.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new IOException("fichier de sauvegarde invalide", e);
        }
    }
}
