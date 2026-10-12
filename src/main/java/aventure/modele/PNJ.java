package aventure.modele;

public class PNJ extends Personnage {

    private static final long serialVersionUID = 1L;

    private final String indice;

    public PNJ(String nom, String indice) {
        super(nom, 1, 0);
        this.indice = indice;
    }

    /** Réplique du personnage : un indice pour le joueur. */
    public String parler() {
        return indice;
    }
}
