package aventure.controleur;

import aventure.modele.Partie;

public interface Controleur {

    void traiter(Commande commande);

    /** Appelée quand la partie est remplacée (chargement d'une sauvegarde). */
    void setPartie(Partie partie);
}
