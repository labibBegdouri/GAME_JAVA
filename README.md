### JEU D'AVENTURE

# Presentation du projet :
Ce projet est un jeu d'aventure textuel développé en Java. Le joueur se réveille dans une maison inconnue et son objectif est d´explorer la maison, tuer les monstres rencontrés, trouver le trésor et s´échapper avec.

# Structure de l´Archive :
EntryPoint.java : Point d´entrée principale du programme (Main)
Makefile
README.md

MAPS /  (Module de gestion de la carte et de l´environnement)
    Direction.java       : Enum des directions (HAUT, BAS, GAUCHE, DROITE)
    LecteurMaison.java   : Chargeur de maison á partir des fichiers textes
    Maison.java          : Representation globale de la maison et des salles
    MaisonTest.java      : Fichier texte pour la maison de test
    Porte.java           : Gestion des portes (ouvert/ fermée) et des clés
    Position.java        : Modélisation des coordonnées géométriques
    Salle.java           : Modélisation d´une salle
    
Personnages / (Module de gestion des entités de jeu)
    Allie.java           : Gestion des personnages secondaires
    Combat.java          : Stratègie et règles de combat
    Heros.java           : Classe du personnage principale
    MenuConfig.java      : Moteur du jeu principale (boucle)
    Monstre.java         : Gestion des ennemies
    Personnage.java      : Classe mère abstraite pour tous les personnages
    
SAC / (Module de gestion des objets et de l´inventaire)
    Arme.java            : Objets de type Arme
    Cle.java             : Objets de type Cle
    Food.java            : Objets consommables 
    Item.java            : Classe mère abstraite pour tous les objets (items)
    Sac.java             : Gestion du sac á dos du héros
    Tresor.java          : Objet trésor
