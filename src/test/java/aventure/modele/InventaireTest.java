package aventure.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InventaireTest {

    @Test
    void uneSeuleArmeEstAutorisee() {
        Inventaire inventaire = new Inventaire();

        assertTrue(inventaire.ajouter(new Arme("epee", "d", 3)));
        assertFalse(inventaire.ajouter(new Arme("hache", "d", 4)));
        assertEquals(1, inventaire.getObjets().size());
    }

    @Test
    void plusieursPotionsSontAutorisees() {
        Inventaire inventaire = new Inventaire();

        assertTrue(inventaire.ajouter(new Potion("potion", "d", 10)));
        assertTrue(inventaire.ajouter(new Potion("potion", "d", 10)));
        assertEquals(2, inventaire.getObjets().size());
    }

    @Test
    void trouverIgnoreLaCasseEtLesEspaces() {
        Inventaire inventaire = new Inventaire();
        inventaire.ajouter(new Arme("epee", "d", 3));

        assertNotNull(inventaire.trouver("  EPEE "));
        assertNull(inventaire.trouver("hache"));
    }

    @Test
    void retirerEnleveLObjet() {
        Inventaire inventaire = new Inventaire();
        Potion potion = new Potion("potion", "d", 10);
        inventaire.ajouter(potion);
        inventaire.retirer(potion);

        assertTrue(inventaire.estVide());
    }

    @Test
    void uneSortieNeSeDeverrouillePasAvecUneMauvaiseCle() {
        Cle bonne = new Cle("cle", "d");
        Cle mauvaise = new Cle("cle", "d");
        Sortie sortie = new Sortie(new Salle("s", "d"), bonne);

        assertTrue(sortie.estVerrouillee());
        assertFalse(sortie.deverrouiller(mauvaise));
        assertTrue(sortie.estVerrouillee());
        assertTrue(sortie.deverrouiller(bonne));
        assertFalse(sortie.estVerrouillee());
    }
}
