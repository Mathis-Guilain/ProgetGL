package aventure.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartieTest {

    private Partie partie;

    @BeforeEach
    void initialiser() {
        partie = new Partie("Test");
    }

    private String nomSalle(Partie p) {
        return p.getJoueur().getSalleCourante().getNom();
    }

    /** Attaque jusqu'à la fin du combat (avec une limite de sécurité). */
    private void vaincreLeMonstre() {
        int garde = 0;
        while (partie.getCombatEnCours() != null && garde++ < 30) {
            partie.attaquer();
        }
    }

    private Partie sauvegarderPuisCharger(Partie p) throws Exception {
        ByteArrayOutputStream octets = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(octets)) {
            out.writeObject(p);
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(octets.toByteArray()))) {
            return (Partie) in.readObject();
        }
    }

    /** Amène le joueur dans la bibliothèque avec la clé en poche. */
    private void aller_chercherLaCle() {
        partie.deplacer(Direction.EST);
        partie.ramasser("epee");
        partie.deplacer(Direction.OUEST);
        partie.deplacer(Direction.OUEST);
        vaincreLeMonstre();
        partie.ramasser("cle");
        partie.deplacer(Direction.EST);
        partie.deplacer(Direction.NORD);
    }

    @Test
    void deplacementVersUneSortieInexistanteNeChangeRien() {
        partie.deplacer(Direction.SUD);

        assertEquals("Hall d'entrée", nomSalle(partie));
    }

    @Test
    void ramasserUneEpeeAugmenteLesDegats() {
        partie.deplacer(Direction.EST);
        partie.ramasser("epee");

        assertEquals(5, partie.getJoueur().getDegats());
    }

    @Test
    void entrerChezLeGobelinDeclencheUnCombat() {
        partie.deplacer(Direction.OUEST);

        assertNotNull(partie.getCombatEnCours());
    }

    @Test
    void onNePeutPasSeDeplacerPendantUnCombat() {
        partie.deplacer(Direction.OUEST);
        partie.deplacer(Direction.EST);

        assertEquals("Cachot", nomSalle(partie));
    }

    @Test
    void laPorteVerrouilleeResteFermeeSansCle() {
        partie.deplacer(Direction.NORD);
        partie.deplacer(Direction.NORD);

        assertEquals("Bibliothèque", nomSalle(partie));
    }

    @Test
    void vaincreLeGobelinLaisseTomberLaCle() {
        partie.deplacer(Direction.EST);
        partie.ramasser("epee");
        partie.deplacer(Direction.OUEST);
        partie.deplacer(Direction.OUEST);
        vaincreLeMonstre();

        assertNull(partie.getCombatEnCours());
        assertNotNull(partie.getJoueur().getSalleCourante().trouverObjet("cle"));
    }

    @Test
    void parcoursCompletJusquAuTresor() {
        aller_chercherLaCle();
        partie.utiliser("cle");
        partie.deplacer(Direction.NORD);
        assertNotNull(partie.getCombatEnCours());

        vaincreLeMonstre();
        assertEquals(EtatPartie.EN_COURS, partie.getEtat());

        partie.deplacer(Direction.NORD);
        assertEquals(EtatPartie.GAGNEE, partie.getEtat());
    }

    @Test
    void lAvancementSurvitALaSerialisation() throws Exception {
        partie.deplacer(Direction.EST);
        partie.ramasser("epee");

        Partie rechargee = sauvegarderPuisCharger(partie);

        assertEquals("Armurerie", nomSalle(rechargee));
        assertNotNull(rechargee.getJoueur().getInventaire().getArme());
        assertNull(rechargee.getCombatEnCours());
        rechargee.ajouterObservateur(evenement -> { });
    }

    @Test
    void laCleOuvreToujoursLaPorteApresUnChargement() throws Exception {
        aller_chercherLaCle();

        Partie rechargee = sauvegarderPuisCharger(partie);
        rechargee.utiliser("cle");
        rechargee.deplacer(Direction.NORD);

        assertEquals("Salle du trône", nomSalle(rechargee));
    }
}
