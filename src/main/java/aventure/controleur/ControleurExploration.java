package aventure.controleur;

import aventure.modele.Direction;
import aventure.modele.Partie;
import aventure.persistance.GestionnaireSauvegarde;
import aventure.vue.Vue;
import java.io.IOException;

public class ControleurExploration implements Controleur {

    private static final String FICHIER_SAUVEGARDE = "sauvegarde.dat";

    private Partie partie;
    private final Vue vue;
    private final GestionnaireSauvegarde gestionnaire;
    private final ControleurPrincipal principal;

    public ControleurExploration(Partie partie, Vue vue,
            GestionnaireSauvegarde gestionnaire, ControleurPrincipal principal) {
        this.partie = partie;
        this.vue = vue;
        this.gestionnaire = gestionnaire;
        this.principal = principal;
    }

    @Override
    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    @Override
    public void traiter(Commande c) {
        switch (c.getVerbe()) {
            case "aller" -> aller(c);
            case "observer" -> partie.observer();
            case "prendre", "ramasser" -> {
                if (!manqueArgument(c, "prendre epee")) {
                    partie.ramasser(c.getArgument());
                }
            }
            case "utiliser" -> {
                if (!manqueArgument(c, "utiliser potion")) {
                    partie.utiliser(c.getArgument());
                }
            }
            case "parler" -> {
                if (!manqueArgument(c, "parler sage")) {
                    partie.parler(c.getArgument());
                }
            }
            case "inventaire" -> partie.consulterInventaire();
            case "sauvegarder" -> sauvegarder();
            case "charger" -> charger();
            case "aide" -> vue.afficherMessage(
                    "Commandes : aller <nord|sud|est|ouest>, observer, prendre <objet>, "
                    + "utiliser <objet>, parler <personnage>, inventaire, "
                    + "sauvegarder, charger, aide, quitter");
            default -> vue.afficherMessage(
                    "Commande inconnue ou impossible ici : « " + c.getVerbe()
                    + " ». Tapez « aide » pour la liste des commandes.");
        }
    }

    private void aller(Commande c) {
        Direction direction = Direction.depuisTexte(c.getArgument());
        if (direction == null) {
            vue.afficherMessage("Direction inconnue. Utilisez : nord, sud, est ou ouest.");
        } else {
            partie.deplacer(direction);
        }
    }

    private boolean manqueArgument(Commande c, String exemple) {
        if (c.getArgument().isEmpty()) {
            vue.afficherMessage("Précisez une cible, par exemple : " + exemple);
            return true;
        }
        return false;
    }

    private void sauvegarder() {
        try {
            gestionnaire.sauvegarder(partie, FICHIER_SAUVEGARDE);
            vue.afficherMessage("Partie sauvegardée dans " + FICHIER_SAUVEGARDE + ".");
        } catch (IOException e) {
            vue.afficherMessage("Échec de la sauvegarde : " + e.getMessage());
        }
    }

    private void charger() {
        try {
            Partie chargee = gestionnaire.charger(FICHIER_SAUVEGARDE);
            principal.remplacerPartie(chargee);
            vue.afficherMessage("Partie chargée.");
            partie.observer();
        } catch (IOException e) {
            vue.afficherMessage("Impossible de charger la partie : " + e.getMessage());
        }
    }
}
