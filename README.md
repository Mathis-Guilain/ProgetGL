
  JEU D'AVENTURES MEDIEVAL-FANTASTIQUE
  Projet de Génie Logiciel - Polytech Grenoble (INFO5)


1. PRESENTATION
---------------------------------------------------------------------
Jeu d'aventures en mode texte (ligne de commande), en Java, selon une
architecture Modèle-Vue-Contrôleur. Le joueur explore un donjon,
ramasse des objets, affronte des monstres au tour par tour et doit
atteindre le trésor.

Organisation du code (src/main/java/aventure) :
  - modele/       règles et données du jeu
  - vue/          affichage et lecture de la saisie (console)
  - controleur/   interprétation des commandes
  - persistance/  sauvegarde et chargement de la partie


2. PREREQUIS
---------------------------------------------------------------------
  - JDK 17 ou supérieur   (vérification : java -version)
  - Apache Maven 3.8 ou supérieur   (vérification : mvn -version)
  - Un accès à Internet lors de la première compilation, pour que
    Maven télécharge ses plugins et JUnit.

Aucune base de données, aucun identifiant, aucun mot de passe.


3. COMPILATION
---------------------------------------------------------------------
Depuis le dossier racine du projet (celui qui contient pom.xml) :

    mvn clean package

Cette commande compile le code, exécute les tests unitaires et
produit l'archive :

    target/aventure-1.0.jar

Pour compiler sans lancer les tests :

    mvn clean package -DskipTests


4. LANCEMENT
---------------------------------------------------------------------
Depuis le dossier racine du projet :

    java -jar target/aventure-1.0.jar

Le jeu doit être lancé dans un terminal (il lit la saisie au clavier).
Si les accents s'affichent mal sous Windows, exécuter d'abord :

    chcp 65001


5. DONNEES ET CONFIGURATION
---------------------------------------------------------------------
  - Le monde du jeu (salles, objets, monstres) est créé directement
    par le programme : aucun fichier de données à fournir.
  - La sauvegarde est écrite dans le fichier "sauvegarde.dat", dans le
    dossier depuis lequel le jeu est lancé. Ce fichier est créé par la
    commande "sauvegarder" et lu par la commande "charger".
  - Aucune autre configuration n'est nécessaire.


6. COMMANDES DU JEU (résumé)
---------------------------------------------------------------------
En exploration :
    aller <direction>      direction : nord, sud, est, ouest
    observer               décrit la salle, ses objets, ses sorties
    prendre <objet>        ramasse un objet (une seule arme possible)
    utiliser <objet>       utilise un objet (clé, potion...)
    parler <personnage>    parle à un personnage de la salle
    inventaire             affiche l'inventaire et les points de vie
    sauvegarder            enregistre la partie
    charger                recharge la partie sauvegardée
    quitter                quitte le jeu

En combat (rencontre d'un monstre) :
    attaquer               attaque le monstre
    utiliser <objet>       utilise un objet (par exemple une potion)
    fuir                   tente de fuir (peut échouer)

Le manuel utilisateur complet est fourni dans le rapport (rapport.pdf).


7. TESTS
---------------------------------------------------------------------
Les tests unitaires (JUnit 5) sont lancés automatiquement par
"mvn clean package". Pour les lancer seuls :

    mvn test


8. AUTEURS
---------------------------------------------------------------------
  GUILAIN Mathis, ALBALAT Nathan
  Polytech Grenoble - INFO5 - Génie Logiciel - 2026-2027
