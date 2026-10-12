package aventure.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Partie implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Monde monde;
    private final Joueur joueur;
    private EtatPartie etat = EtatPartie.EN_COURS;

    // Non sauvegardés : la vue est ré-enregistrée après un chargement,
    // et on ne sauvegarde jamais pendant un combat.
    private transient Combat combatEnCours;
    private transient List<ObservateurPartie> observateurs;

    public Partie(String nomJoueur) {
        this.monde = Monde.creerDonjon();
        this.joueur = new Joueur(nomJoueur, 20, 2, monde.getSalleDepart());
    }

    // ------------------------------------------------------------------
    // Accès en lecture
    // ------------------------------------------------------------------

    public EtatPartie getEtat() {
        return etat;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Monde getMonde() {
        return monde;
    }

    /** Renvoie le combat en cours, ou null si le joueur explore. */
    public Combat getCombatEnCours() {
        return combatEnCours;
    }

    // ------------------------------------------------------------------
    // Observateurs
    // ------------------------------------------------------------------

    public void ajouterObservateur(ObservateurPartie o) {
        if (observateurs == null) {
            observateurs = new ArrayList<>();
        }
        observateurs.add(o);
    }

    private void notifier(String evenement) {
        if (observateurs == null) {
            return;
        }
        for (ObservateurPartie o : observateurs) {
            o.partieModifiee(evenement);
        }
    }

    // ------------------------------------------------------------------
    // Actions d'exploration
    // ------------------------------------------------------------------

    public void deplacer(Direction d) {
        if (partieTerminee()) {
            return;
        }
        if (combatEnCours != null) {
            notifier("Impossible de se déplacer pendant un combat : "
                    + "attaquez, utilisez un objet ou fuyez !");
            return;
        }
        Sortie sortie = joueur.getSalleCourante().getSortie(d);
        if (sortie == null) {
            notifier("Il n'y a pas de sortie vers le " + d.getLibelle() + ".");
            return;
        }
        if (sortie.estVerrouillee()) {
            notifier("La porte vers le " + d.getLibelle() + " est verrouillée.");
            return;
        }

        joueur.deplacerVers(sortie.getDestination());
        Salle salle = joueur.getSalleCourante();
        StringBuilder message = new StringBuilder(decrire(salle));

        Monstre monstre = salle.getMonstreVivant();
        if (monstre != null) {
            combatEnCours = new Combat(joueur, monstre);
            message.append("\n\nUn ").append(monstre.getNom())
                    .append(" vous barre la route ! Le combat commence (vous : ")
                    .append(joueur.getPointsDeVie()).append("/").append(joueur.getPointsDeVieMax())
                    .append(" PV, ").append(monstre.getNom()).append(" : ")
                    .append(monstre.getPointsDeVie()).append("/").append(monstre.getPointsDeVieMax())
                    .append(" PV).");
        } else if (salle.contientTresor()) {
            etat = EtatPartie.GAGNEE;
            message.append("\n\n").append(salle.getTresor().getDescription())
                    .append("\nVictoire ! Vous avez trouvé le trésor.");
        }
        notifier(message.toString());
    }

    public void observer() {
        if (partieTerminee()) {
            return;
        }
        notifier(decrire(joueur.getSalleCourante()));
    }

    public void ramasser(String nomObjet) {
        if (partieTerminee()) {
            return;
        }
        if (combatEnCours != null) {
            notifier("Impossible de ramasser un objet pendant un combat.");
            return;
        }
        Salle salle = joueur.getSalleCourante();
        Objet objet = salle.trouverObjet(nomObjet);
        if (objet == null) {
            notifier("Il n'y a pas de « " + nomObjet + " » ici.");
            return;
        }
        if (!joueur.getInventaire().ajouter(objet)) {
            notifier("Vous portez déjà une arme : vous ne pouvez pas en prendre une seconde.");
            return;
        }
        salle.retirerObjet(objet);
        notifier("Vous ramassez : " + objet.getNom() + ".");
    }

    public void consulterInventaire() {
        if (partieTerminee()) {
            return;
        }
        Inventaire inventaire = joueur.getInventaire();
        StringBuilder message = new StringBuilder();
        message.append("Points de vie : ").append(joueur.getPointsDeVie())
                .append("/").append(joueur.getPointsDeVieMax())
                .append(" - dégâts par attaque : ").append(joueur.getDegats());
        if (inventaire.estVide()) {
            message.append("\nVotre inventaire est vide.");
        } else {
            message.append("\nInventaire :");
            for (Objet o : inventaire.getObjets()) {
                message.append("\n - ").append(o.getNom()).append(" : ").append(o.getDescription());
            }
        }
        notifier(message.toString());
    }

    public void parler(String nomPnj) {
        if (partieTerminee()) {
            return;
        }
        if (combatEnCours != null) {
            notifier("Impossible de parler pendant un combat.");
            return;
        }
        PNJ pnj = joueur.getSalleCourante().trouverPNJ(nomPnj);
        if (pnj == null) {
            notifier("Personne ne s'appelle « " + nomPnj + " » ici.");
            return;
        }
        notifier(pnj.getNom() + " : « " + pnj.parler() + " »");
    }

    // ------------------------------------------------------------------
    // Actions utilisables en exploration et en combat
    // ------------------------------------------------------------------

    public void utiliser(String nomObjet) {
        if (partieTerminee()) {
            return;
        }
        Objet objet = joueur.getInventaire().trouver(nomObjet);
        if (objet == null) {
            notifier("Vous n'avez pas de « " + nomObjet + " ».");
            return;
        }
        StringBuilder message = new StringBuilder(objet.utiliser(joueur));
        if (combatEnCours != null) {
            message.append("\n").append(tourDuMonstre());
        }
        notifier(message.toString());
    }

    // ------------------------------------------------------------------
    // Actions de combat
    // ------------------------------------------------------------------

    public void attaquer() {
        if (partieTerminee()) {
            return;
        }
        if (combatEnCours == null) {
            notifier("Il n'y a pas de combat en cours.");
            return;
        }
        Monstre monstre = combatEnCours.getMonstre();
        int degats = combatEnCours.attaquerJoueur();
        StringBuilder message = new StringBuilder();
        message.append("Vous attaquez le ").append(monstre.getNom())
                .append(" : -").append(degats).append(" PV (")
                .append(monstre.getPointsDeVie()).append("/")
                .append(monstre.getPointsDeVieMax()).append(").");

        if (monstre.estVivant()) {
            message.append("\n").append(tourDuMonstre());
        } else {
            message.append("\nLe ").append(monstre.getNom()).append(" est vaincu !");
            Objet butin = monstre.getButin();
            Salle salle = joueur.getSalleCourante();
            if (butin != null) {
                salle.ajouterObjet(butin);
                message.append(" Il laisse tomber : ").append(butin.getNom()).append(".");
            }
            combatEnCours = null;
            if (salle.contientTresor()) {
                etat = EtatPartie.GAGNEE;
                message.append("\n\n").append(salle.getTresor().getDescription())
                        .append("\nVictoire ! Vous avez trouvé le trésor.");
            }
        }
        notifier(message.toString());
    }

    public void fuir() {
        if (partieTerminee()) {
            return;
        }
        if (combatEnCours == null) {
            notifier("Vous n'êtes pas en combat.");
            return;
        }
        if (combatEnCours.tenterFuite()) {
            combatEnCours.getMonstre().reinitialiser();
            combatEnCours = null;
            joueur.deplacerVers(joueur.getSallePrecedente());
            notifier("Vous réussissez à fuir !\n\n" + decrire(joueur.getSalleCourante()));
        } else {
            notifier("La fuite échoue !\n" + tourDuMonstre());
        }
    }

    // ------------------------------------------------------------------
    // Méthodes internes
    // ------------------------------------------------------------------

    /** Le monstre attaque ; si le joueur tombe à zéro, la partie est perdue. */
    private String tourDuMonstre() {
        Monstre monstre = combatEnCours.getMonstre();
        int degats = combatEnCours.tourMonstre();
        String message = "Le " + monstre.getNom() + " vous attaque : -" + degats
                + " PV (" + joueur.getPointsDeVie() + "/" + joueur.getPointsDeVieMax() + ").";
        if (!joueur.estVivant()) {
            etat = EtatPartie.PERDUE;
            combatEnCours = null;
            message += "\nVous êtes à terre... La partie est perdue.";
        }
        return message;
    }

    /** Si la partie est finie, prévient la vue et renvoie true. */
    private boolean partieTerminee() {
        if (etat != EtatPartie.EN_COURS) {
            notifier("La partie est terminée.");
            return true;
        }
        return false;
    }

    private String decrire(Salle salle) {
        StringBuilder sb = new StringBuilder();
        sb.append("== ").append(salle.getNom()).append(" ==\n").append(salle.getDescription());

        if (!salle.getObjets().isEmpty()) {
            StringJoiner objets = new StringJoiner(", ");
            for (Objet o : salle.getObjets()) {
                objets.add(o.getNom());
            }
            sb.append("\nObjets ici : ").append(objets);
        }
        for (Personnage p : salle.getPersonnages()) {
            if (p instanceof Monstre) {
                if (p.estVivant()) {
                    sb.append("\nMonstre : ").append(p.getNom()).append(" (")
                            .append(p.getPointsDeVie()).append("/")
                            .append(p.getPointsDeVieMax()).append(" PV)");
                }
            } else {
                sb.append("\nPersonnage : ").append(p.getNom());
            }
        }
        StringJoiner sorties = new StringJoiner(", ");
        for (Direction d : Direction.values()) {
            Sortie s = salle.getSortie(d);
            if (s != null) {
                sorties.add(d.getLibelle() + (s.estVerrouillee() ? " (verrouillée)" : ""));
            }
        }
        sb.append("\nSorties : ").append(sorties);
        return sb.toString();
    }
}
