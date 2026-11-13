package aventure.vue;

import aventure.modele.ObservateurPartie;

public interface Vue extends ObservateurPartie {

    /** Lit une saisie du joueur. Renvoie null si l'entrée est terminée. */
    String lireSaisie();

    void afficherMessage(String message);
}
