package aventure.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventaire implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Objet> objets = new ArrayList<>();

    /**
     * Ajoute un objet. Renvoie false (et n'ajoute rien) si l'objet est
     * une arme et que l'inventaire en contient déjà une.
     */
    public boolean ajouter(Objet o) {
        if (o instanceof Arme && getArme() != null) {
            return false;
        }
        objets.add(o);
        return true;
    }

    public void retirer(Objet o) {
        objets.remove(o);
    }

    /** Cherche un objet par son nom, sans tenir compte de la casse. */
    public Objet trouver(String nom) {
        if (nom == null) {
            return null;
        }
        for (Objet o : objets) {
            if (o.getNom().equalsIgnoreCase(nom.trim())) {
                return o;
            }
        }
        return null;
    }

    /** Renvoie l'arme portée, ou null s'il n'y en a pas. */
    public Arme getArme() {
        for (Objet o : objets) {
            if (o instanceof Arme) {
                return (Arme) o;
            }
        }
        return null;
    }

    public List<Objet> getObjets() {
        return Collections.unmodifiableList(objets);
    }

    public boolean estVide() {
        return objets.isEmpty();
    }
}
