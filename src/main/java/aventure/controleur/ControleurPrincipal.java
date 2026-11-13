package aventure.controleur;

import aventure.modele.EtatPartie;
import aventure.modele.Partie;
import aventure.persistance.GestionnaireSauvegarde;
import aventure.vue.Vue;

public class ControleurPrincipal {

    private final Vue vue;
    private Partie partie;
    private final ControleurExploration exploration;
    private final ControleurCombat combat;
    private Controleur controleurCourant;

    public ControleurPrincipal(Vue vue, Partie partie) {
        this.vue = vue;
        this.partie = partie;
        this.partie.ajouterObservateur(vue);
        this.exploration = new ControleurExploration(partie, vue, new GestionnaireSauvegarde(), this);
        this.combat = new ControleurCombat(partie, vue);
        this.controleurCourant = exploration;
    }

    /** Boucle principale : lire une saisie, la faire traiter, recommencer. */
    public void lancer() {
        vue.afficherMessage("=== Le Donjon oublié ===\nTapez « aide » pour voir les commandes.");
        partie.observer();

        while (partie.getEtat() == EtatPartie.EN_COURS) {
            String saisie = vue.lireSaisie();
            if (saisie == null) {
                return;
            }
            Commande commande = Commande.analyser(saisie);
            if (commande.getVerbe().isEmpty()) {
                continue;
            }
            if (commande.getVerbe().equals("quitter")) {
                vue.afficherMessage("À bientôt, aventurier !");
                return;
            }
            controleurCourant.traiter(commande);
            choisirControleur();
        }
        vue.afficherMessage("Fin de la partie.");
    }

    /** Remplace la partie courante (après un chargement) et reconnecte la vue. */
    public void remplacerPartie(Partie nouvelle) {
        this.partie = nouvelle;
        nouvelle.ajouterObservateur(vue);
        exploration.setPartie(nouvelle);
        combat.setPartie(nouvelle);
        choisirControleur();
    }

    /** Choisit le contrôleur selon le mode de jeu : combat ou exploration. */
    private void choisirControleur() {
        controleurCourant = (partie.getCombatEnCours() != null) ? combat : exploration;
    }
}
