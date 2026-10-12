package aventure.modele;

public class Monstre extends Personnage {

    private static final long serialVersionUID = 1L;

    private final Objet butin;

    /** Le butin peut être null (monstre sans récompense). */
    public Monstre(String nom, int pointsDeVieMax, int degats, Objet butin) {
        super(nom, pointsDeVieMax, degats);
        this.butin = butin;
    }

    public Objet getButin() {
        return butin;
    }

    /** Rend tous ses points de vie au monstre (après une fuite du joueur). */
    public void reinitialiser() {
        restaurerPointsDeVie();
    }
}
