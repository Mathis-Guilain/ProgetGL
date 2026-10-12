package aventure.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monde implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Salle> salles = new ArrayList<>();
    private Salle salleDepart;

    private Monde() {
    }

    public Salle getSalleDepart() {
        return salleDepart;
    }

    public List<Salle> getSalles() {
        return Collections.unmodifiableList(salles);
    }

    /** Construit le donjon du jeu : six salles, deux monstres, un trésor. */
    public static Monde creerDonjon() {
        Monde monde = new Monde();

        // Objets
        Cle cle = new Cle("cle", "Une vieille clé rouillée.");
        Arme epee = new Arme("epee", "Une épée en fer, solide et bien équilibrée.", 3);
        Potion potion = new Potion("potion", "Un flacon rouge qui sent la cerise.", 10);

        // Salles
        Salle hall = new Salle("Hall d'entrée",
                "Un grand hall en pierre, éclairé par des torches. Trois portes s'offrent à vous.");
        Salle armurerie = new Salle("Armurerie",
                "Des râteliers d'armes rouillées couvrent les murs.");
        Salle cachot = new Salle("Cachot",
                "Une cellule humide qui sent la moisissure.");
        Salle bibliotheque = new Salle("Bibliothèque",
                "Des étagères branlantes croulent sous les vieux livres.");
        Salle trone = new Salle("Salle du trône",
                "Un trône de pierre fissuré domine la pièce.");
        Salle salleTresor = new Salle("Salle du trésor",
                "Une petite salle voûtée, étrangement lumineuse.");

        // Contenu des salles
        armurerie.ajouterObjet(epee);
        armurerie.ajouterObjet(potion);
        cachot.ajouterPersonnage(new Monstre("gobelin", 8, 2, cle));
        bibliotheque.ajouterPersonnage(new PNJ("sage",
                "Le gobelin du cachot garde une clé. Elle ouvre la porte du nord."));
        trone.ajouterPersonnage(new Monstre("squelette", 14, 3, null));
        salleTresor.setTresor(new Tresor("Un coffre rempli d'or et de gemmes scintille devant vous !"));

        // Sorties
        relier(hall, Direction.NORD, bibliotheque, Direction.SUD);
        relier(hall, Direction.EST, armurerie, Direction.OUEST);
        relier(hall, Direction.OUEST, cachot, Direction.EST);
        relier(trone, Direction.NORD, salleTresor, Direction.SUD);

        // Porte verrouillée : bibliothèque -> salle du trône
        bibliotheque.ajouterSortie(Direction.NORD, new Sortie(trone, cle));
        trone.ajouterSortie(Direction.SUD, new Sortie(bibliotheque));

        monde.salles.add(hall);
        monde.salles.add(armurerie);
        monde.salles.add(cachot);
        monde.salles.add(bibliotheque);
        monde.salles.add(trone);
        monde.salles.add(salleTresor);
        monde.salleDepart = hall;
        return monde;
    }

    /** Crée deux sorties libres, une dans chaque sens, entre deux salles. */
    private static void relier(Salle a, Direction deAversB, Salle b, Direction deBversA) {
        a.ajouterSortie(deAversB, new Sortie(b));
        b.ajouterSortie(deBversA, new Sortie(a));
    }
}
