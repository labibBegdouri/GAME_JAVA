# JEU D'AVENTURE (IMAG DUNGEON)

## Présentation du projet

Ce projet est un jeu d'aventure textuel développé en Java orienté objet. Le joueur se réveille dans une maison inconnue et hostile. Son objectif est d'explorer les différentes salles, de gérer son inventaire, d'affronter les monstres, de recruter des alliés, de récupérer le trésor et de s'échapper par la sortie.

## Compilation et exécution

Le projet se compile et s'exécute à l'aide du `Makefile` fourni à la racine :

* **Compiler le projet :**

  ```
  make
  ```

* **Lancer le jeu :**

  ```
  make run
  ```

* **Nettoyer les fichiers compilés :**

  ```
  make clean
  ```

## Documentation utilisateur

Pour le détail complet des règles du jeu, le fonctionnement du radar, le système de combat et d'alliance, ainsi que le guide détaillé des commandes, veuillez consulter le document PDF inclus :

* `documentation_jeu.pdf`

## Structure de l'archive

* `EntryPoint.java` : Point d'entrée principal du programme (`main`).

* `Makefile` : Fichier d'automatisation de la compilation et du lancement.

* `README.md` : Présentation générale et architecture du projet.

* `documentation_utilisateur.pdf` : Guide utilisateur et manuel de jeu complet.

* `sujetTPLAventure.pdf` : Énoncé et spécifications du TP.

### MAPS / (Module de gestion de l'environnement et de la carte)

* `Affichage.java` : Rendu console (carte, radar, informations de combat et de salle).

* `Direction.java` : Énumération des directions cardinales (HAUT, BAS, GAUCHE, DROITE).

* `LecteurMaison.java` : Chargement et instanciation de la carte depuis un fichier texte.

* `Maison.java` : Représentation globale de la maison et de ses salles.

* `MaisonTest.txt` : Fichier de description de la carte de test.

* `Porte.java` : Modélisation des portes (état ouvert/fermé, serrures à clés et trésor).

* `Position.java` : Modélisation des coordonnées géométriques bidimensionnelles (x, y).

* `Salle.java` : Modélisation d'une salle (dimensions, portes, objets et personnages).

### Position / 
* `Position.java` : Modélisation des coordonnées géométriques bidimensionnelles (x, y).
### Personnages / (Module des entités vivantes et du combat)


* `Allie.java` : Gestion des personnages secondaires et de leur statut de trahison.

* `Combat.java` : Moteur de combat au tour par tour.

* `Heros.java` : Personnage principal incarné (points de vie, sac, gestion des alliés).


* `Monstre.java` : Gestion des ennemis, de leur arme et de leurs points de vie communs.

* `Personnage.java` : Classe mère abstraite commune à tous les personnages.

### SAC / (Module de gestion des objets et de l'inventaire)

* `Arme.java` : Objets de type arme (dégâts d'attaque).

* `Cle.java` : Objets de type clé associés à un identifiant de serrure.

* `Food.java` : Objets consommables pour restaurer des points de vie.

* `Item.java` : Classe mère abstraite commune pour tous les objets.

* `Sac.java` : Gestionnaire de l'inventaire du héros (capacité et encombrement).

* `Tresor.java` : Objet unique nécessaire pour déverrouiller la porte de sortie.

  
### Menu / (L'interface interactif)

* `MenuConfig.java` : Boucle principale du jeu, radar et gestion des interactions joueur.
