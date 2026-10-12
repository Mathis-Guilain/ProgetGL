package aventure.modele;

import java.io.Serializable;

public class Sortie implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Salle destination;
    private final Cle cleRequise;
    private boolean verrouillee;

    /** Sortie libre. */
    public Sortie(Salle destination) {
        this.destination = destination;
        this.cleRequise = null;
        this.verrouillee = false;
    }

    /** Sortie verrouillée, ouverte par la clé donnée. */
    public Sortie(Salle destination, Cle cleRequise) {
        this.destination = destination;
        this.cleRequise = cleRequise;
        this.verrouillee = true;
    }

    public Salle getDestination() {
        return destination;
    }

    public boolean estVerrouillee() {
        return verrouillee;
    }

    /** Déverrouille la sortie si la clé est la bonne. Renvoie true en cas de succès. */
    public boolean deverrouiller(Cle c) {
        if (verrouillee && cleRequise == c) {
            verrouillee = false;
            return true;
        }
        return false;
    }
}
