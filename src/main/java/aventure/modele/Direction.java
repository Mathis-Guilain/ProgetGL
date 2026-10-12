package aventure.modele;

public enum Direction {
    NORD("nord"),
    SUD("sud"),
    EST("est"),
    OUEST("ouest");

    private final String libelle;

    Direction(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    /** Renvoie la direction correspondant au texte saisi, ou null si inconnue. */
    public static Direction depuisTexte(String texte) {
        if (texte == null) {
            return null;
        }
        for (Direction d : values()) {
            if (d.libelle.equalsIgnoreCase(texte.trim())) {
                return d;
            }
        }
        return null;
    }
}
