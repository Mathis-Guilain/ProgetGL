package aventure.vue;

import java.util.Scanner;

public class VueConsole implements Vue {

    private final Scanner clavier = new Scanner(System.in);

    @Override
    public String lireSaisie() {
        System.out.print("> ");
        System.out.flush();
        if (clavier.hasNextLine()) {
            return clavier.nextLine();
        }
        return null;
    }

    @Override
    public void afficherMessage(String message) {
        System.out.println(message);
        System.out.println();
    }

    /** Appelée par la Partie : on affiche simplement le texte reçu. */
    @Override
    public void partieModifiee(String evenement) {
        afficherMessage(evenement);
    }
}
