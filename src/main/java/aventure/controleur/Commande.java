package aventure.controleur;

import java.util.Locale;

public final class Commande {

    private final String verbe;
    private final String argument;

    public Commande(String verbe, String argument) {
        this.verbe = verbe;
        this.argument = argument;
    }

    /** Découpe une saisie : le premier mot est le verbe, le reste l'argument. */
    public static Commande analyser(String saisie) {
        String texte = (saisie == null) ? "" : saisie.trim();
        int espace = texte.indexOf(' ');
        if (espace < 0) {
            return new Commande(texte.toLowerCase(Locale.ROOT), "");
        }
        return new Commande(texte.substring(0, espace).toLowerCase(Locale.ROOT),
                texte.substring(espace + 1).trim());
    }

    public String getVerbe() {
        return verbe;
    }

    public String getArgument() {
        return argument;
    }
}
