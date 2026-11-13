package aventure.controleur;

import aventure.modele.Partie;
import aventure.vue.Vue;

public class ControleurCombat implements Controleur {

    private Partie partie;
    private final Vue vue;

    public ControleurCombat(Partie partie, Vue vue) {
        this.partie = partie;
        this.vue = vue;
    }

    @Override
    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    @Override
    public void traiter(Commande c) {
        switch (c.getVerbe()) {
            case "attaquer" -> partie.attaquer();
            case "fuir" -> partie.fuir();
            case "utiliser" -> {
                if (c.getArgument().isEmpty()) {
                    vue.afficherMessage("Précisez l'objet, par exemple : utiliser potion");
                } else {
                    partie.utiliser(c.getArgument());
                }
            }
            default -> vue.afficherMessage(
                    "En combat, vous pouvez seulement : attaquer, utiliser <objet>, fuir.");
        }
    }
}
