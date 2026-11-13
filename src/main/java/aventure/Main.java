package aventure;

import aventure.controleur.ControleurPrincipal;
import aventure.modele.Partie;
import aventure.vue.Vue;
import aventure.vue.VueConsole;

public class Main {

    public static void main(String[] args) {
        Partie partie = new Partie("Aventurier");
        Vue vue = new VueConsole();
        new ControleurPrincipal(vue, partie).lancer();
    }
}
