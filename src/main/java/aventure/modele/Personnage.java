package aventure.modele;

import java.io.Serializable;

public abstract class Personnage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String nom;
    private int pointsDeVie;
    private final int pointsDeVieMax;
    private final int degats;

    protected Personnage(String nom, int pointsDeVieMax, int degats) {
        this.nom = nom;
        this.pointsDeVieMax = pointsDeVieMax;
        this.pointsDeVie = pointsDeVieMax;
        this.degats = degats;
    }

    public String getNom() {
        return nom;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public int getPointsDeVieMax() {
        return pointsDeVieMax;
    }

    /** Dégâts infligés par une attaque (redéfinie par le joueur pour inclure l'arme). */
    public int getDegats() {
        return degats;
    }

    public boolean estVivant() {
        return pointsDeVie > 0;
    }

    /** Retire des points de vie, sans descendre sous zéro. */
    public void subirDegats(int n) {
        pointsDeVie = Math.max(0, pointsDeVie - n);
    }

    /** Ajoute des points de vie, sans dépasser le maximum. */
    protected void ajouterPointsDeVie(int n) {
        pointsDeVie = Math.min(pointsDeVieMax, pointsDeVie + n);
    }

    /** Remet les points de vie au maximum. */
    protected void restaurerPointsDeVie() {
        pointsDeVie = pointsDeVieMax;
    }
}
