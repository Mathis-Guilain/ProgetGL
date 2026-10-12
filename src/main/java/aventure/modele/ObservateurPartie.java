package aventure.modele;

public interface ObservateurPartie {
    /** Appelée par la Partie à chaque changement à afficher. */
    void partieModifiee(String evenement);
}
