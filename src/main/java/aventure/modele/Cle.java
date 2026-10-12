package aventure.modele;

public class Cle extends Objet {

    private static final long serialVersionUID = 1L;

    public Cle(String nom, String description) {
        super(nom, description);
    }

    @Override
    public String utiliser(Joueur j) {
        Salle salle = j.getSalleCourante();
        for (Direction d : Direction.values()) {
            Sortie sortie = salle.getSortie(d);
            if (sortie != null && sortie.deverrouiller(this)) {
                j.getInventaire().retirer(this);
                return "La porte " + d.getLibelle()
                        + " est déverrouillée, la clé est consommée.";
            }
        }
        return "Cet objet n'a pas d'effet ici.";
    }
}
