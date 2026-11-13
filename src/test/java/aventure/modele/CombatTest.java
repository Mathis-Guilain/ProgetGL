package aventure.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

class CombatTest {

    /** Générateur dont le tirage est toujours égal à la valeur donnée. */
    private static Random tirageFixe(double valeur) {
        return new Random() {
            @Override
            public double nextDouble() {
                return valeur;
            }
        };
    }

    private static Joueur creerJoueur() {
        return new Joueur("test", 20, 2, new Salle("salle", "description"));
    }

    @Test
    void attaqueEnleveDesPointsDeVieAuMonstre() {
        Monstre monstre = new Monstre("gobelin", 8, 2, null);
        Combat combat = new Combat(creerJoueur(), monstre, tirageFixe(0.0));

        assertEquals(2, combat.attaquerJoueur());
        assertEquals(6, monstre.getPointsDeVie());
    }

    @Test
    void armeAugmenteLesDegatsDuJoueur() {
        Joueur joueur = creerJoueur();
        joueur.getInventaire().ajouter(new Arme("epee", "d", 3));
        Monstre monstre = new Monstre("gobelin", 8, 2, null);
        Combat combat = new Combat(joueur, monstre, tirageFixe(0.0));

        assertEquals(5, combat.attaquerJoueur());
        assertEquals(3, monstre.getPointsDeVie());
    }

    @Test
    void tourDuMonstreEnleveDesPointsDeVieAuJoueur() {
        Joueur joueur = creerJoueur();
        Monstre monstre = new Monstre("squelette", 14, 3, null);
        Combat combat = new Combat(joueur, monstre, tirageFixe(0.0));

        assertEquals(3, combat.tourMonstre());
        assertEquals(17, joueur.getPointsDeVie());
    }

    @Test
    void lesPointsDeVieNeDescendentJamaisSousZero() {
        Monstre monstre = new Monstre("rat", 1, 1, null);
        new Combat(creerJoueur(), monstre, tirageFixe(0.0)).attaquerJoueur();

        assertEquals(0, monstre.getPointsDeVie());
        assertFalse(monstre.estVivant());
    }

    @Test
    void leCombatSeTermineQuandLeMonstreEstVaincu() {
        Monstre monstre = new Monstre("gobelin", 8, 2, null);
        Combat combat = new Combat(creerJoueur(), monstre, tirageFixe(0.0));

        for (int i = 0; i < 4; i++) {
            assertFalse(combat.estTermine());
            combat.attaquerJoueur();
        }
        assertTrue(combat.estTermine());
    }

    @Test
    void fuiteReussieOuRateeSelonLeTirage() {
        Monstre monstre = new Monstre("gobelin", 8, 2, null);

        assertTrue(new Combat(creerJoueur(), monstre, tirageFixe(0.0)).tenterFuite());
        assertFalse(new Combat(creerJoueur(), monstre, tirageFixe(0.99)).tenterFuite());
    }

    @Test
    void reinitialiserRendTousLesPointsDeVieAuMonstre() {
        Monstre monstre = new Monstre("gobelin", 8, 2, null);
        monstre.subirDegats(5);
        monstre.reinitialiser();

        assertEquals(8, monstre.getPointsDeVie());
    }
}
