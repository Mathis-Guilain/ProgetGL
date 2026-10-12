package aventure.modele;

import java.util.Random;

public class Combat {

    /** Probabilité de réussir une fuite. */
    private static final double CHANCE_FUITE = 0.5;

    private final Joueur joueur;
    private final Monstre monstre;
    private final Random hasard;

    public Combat(Joueur joueur, Monstre monstre) {
        this(joueur, monstre, new Random());
    }

    /** Constructeur avec générateur imposé : permet des tests reproductibles. */
    public Combat(Joueur joueur, Monstre monstre, Random hasard) {
        this.joueur = joueur;
        this.monstre = monstre;
        this.hasard = hasard;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Monstre getMonstre() {
        return monstre;
    }

    /** Le joueur attaque le monstre. Renvoie les dégâts infligés. */
    public int attaquerJoueur() {
        int degats = joueur.getDegats();
        monstre.subirDegats(degats);
        return degats;
    }

    /** Le monstre attaque le joueur. Renvoie les dégâts subis. */
    public int tourMonstre() {
        int degats = monstre.getDegats();
        joueur.subirDegats(degats);
        return degats;
    }

    /** Tire au sort le résultat d'une tentative de fuite : true si elle réussit. */
    public boolean tenterFuite() {
        return hasard.nextDouble() < CHANCE_FUITE;
    }

    public boolean estTermine() {
        return !joueur.estVivant() || !monstre.estVivant();
    }
}
