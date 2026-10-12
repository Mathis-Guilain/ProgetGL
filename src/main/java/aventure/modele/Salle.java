package aventure.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nom;
    private final String description;
    private final Map<Direction, Sortie> sorties = new EnumMap<>(Direction.class);
    private final List<Objet> objets = new ArrayList<>();
    private final List<Personnage> personnages = new ArrayList<>();
    private Tresor tresor;

    public Salle(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    // ---- sorties ----

    public void ajouterSortie(Direction d, Sortie s) {
        sorties.put(d, s);
    }

    /** Renvoie la sortie dans cette direction, ou null s'il n'y en a pas. */
    public Sortie getSortie(Direction d) {
        return sorties.get(d);
    }

    // ---- objets ----

    public void ajouterObjet(Objet o) {
        objets.add(o);
    }

    public void retirerObjet(Objet o) {
        objets.remove(o);
    }

    public Objet trouverObjet(String nomObjet) {
        if (nomObjet == null) {
            return null;
        }
        for (Objet o : objets) {
            if (o.getNom().equalsIgnoreCase(nomObjet.trim())) {
                return o;
            }
        }
        return null;
    }

    public List<Objet> getObjets() {
        return Collections.unmodifiableList(objets);
    }

    // ---- personnages ----

    public void ajouterPersonnage(Personnage p) {
        personnages.add(p);
    }

    public List<Personnage> getPersonnages() {
        return Collections.unmodifiableList(personnages);
    }

    /** Renvoie un monstre vivant présent dans la salle, ou null. */
    public Monstre getMonstreVivant() {
        for (Personnage p : personnages) {
            if (p instanceof Monstre && p.estVivant()) {
                return (Monstre) p;
            }
        }
        return null;
    }

    /** Renvoie le PNJ portant ce nom dans la salle, ou null. */
    public PNJ trouverPNJ(String nomPnj) {
        if (nomPnj == null) {
            return null;
        }
        for (Personnage p : personnages) {
            if (p instanceof PNJ && p.getNom().equalsIgnoreCase(nomPnj.trim())) {
                return (PNJ) p;
            }
        }
        return null;
    }

    // ---- trésor ----

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }

    public Tresor getTresor() {
        return tresor;
    }

    public boolean contientTresor() {
        return tresor != null;
    }
}
