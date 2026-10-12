package aventure.modele;

public class Joueur extends Personnage {

    private static final long serialVersionUID = 1L;

    private final Inventaire inventaire = new Inventaire();
    private Salle salleCourante;
    private Salle sallePrecedente;

    public Joueur(String nom, int pointsDeVieMax, int degatsDeBase, Salle salleDepart) {
        super(nom, pointsDeVieMax, degatsDeBase);
        this.salleCourante = salleDepart;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public Salle getSalleCourante() {
        return salleCourante;
    }

    public Salle getSallePrecedente() {
        return sallePrecedente;
    }

    /** Déplace le joueur et mémorise la salle quittée (utile pour la fuite). */
    public void deplacerVers(Salle s) {
        sallePrecedente = salleCourante;
        salleCourante = s;
    }

    public void soigner(int n) {
        ajouterPointsDeVie(n);
    }

    /** Dégâts de base plus bonus de l'arme portée, s'il y en a une. */
    @Override
    public int getDegats() {
        Arme arme = inventaire.getArme();
        return super.getDegats() + (arme != null ? arme.getBonusDegats() : 0);
    }
}
