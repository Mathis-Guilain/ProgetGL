package aventure.modele;

public class Potion extends Objet {

    private static final long serialVersionUID = 1L;

    private final int pointsRendus;

    public Potion(String nom, String description, int pointsRendus) {
        super(nom, description);
        this.pointsRendus = pointsRendus;
    }

    public int getPointsRendus() {
        return pointsRendus;
    }

    @Override
    public String utiliser(Joueur j) {
        j.soigner(pointsRendus);
        j.getInventaire().retirer(this);
        return "Vous buvez " + getNom() + " : vous récupérez jusqu'à "
                + pointsRendus + " points de vie ("
                + j.getPointsDeVie() + "/" + j.getPointsDeVieMax() + ").";
    }
}
