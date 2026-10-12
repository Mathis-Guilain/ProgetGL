package aventure.modele;

import java.io.Serializable;

public abstract class Objet implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nom;
    private final String description;

    protected Objet(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Utilise l'objet pour le joueur et renvoie le message à afficher.
     * Un objet consommable se retire lui-même de l'inventaire.
     */
    public abstract String utiliser(Joueur j);
}
