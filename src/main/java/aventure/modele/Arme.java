package aventure.modele;

public class Arme extends Objet {

    private static final long serialVersionUID = 1L;

    private final int bonusDegats;

    public Arme(String nom, String description, int bonusDegats) {
        super(nom, description);
        this.bonusDegats = bonusDegats;
    }

    public int getBonusDegats() {
        return bonusDegats;
    }

    @Override
    public String utiliser(Joueur j) {
        return "Inutile de l'utiliser : " + getNom() + " augmente vos dégâts de "
                + bonusDegats + " tant que vous la portez.";
    }
}
