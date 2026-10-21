package Personnages;

import java.util.Scanner;

import MAPS.Affichage;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import SAC.Arme;
import SAC.Food ;
/**
 * Déroule un combat entre le héros, ses alliés et les adversaires.
 */

public class Combat {
    private List<Allie> allies;
    private List<Allie> alliesTraitre;
    private Heros hero;
    private Monstre monstre;

    public static final String RESET = "\u001B[0m";
    public static final String ROUGE = "\u001B[31m";
    public static final String VERT  = "\u001B[32m";

    /**
     * Crée un objet Combat avec les paramètres fournis.
     * @param hero héros concerné
     * @param monstre monstre affronté
     * @param alliesTraitre alliés traîtres qui combattent les héros
     * @param allies alliés du héros
     */
    public Combat(Heros hero, Monstre monstre, List<Allie> alliesTraitre, List<Allie> allies) {
        this.hero = hero;
        this.monstre = monstre;
        this.alliesTraitre = alliesTraitre;
        this.allies = allies;
    }

    /**
     * Lit un entier compris entre les bornes fournies.
     * @param scanner scanner utilisé pour lire la saisie
     * @param message message affiché à l’utilisateur
     * @param min borne minimale autorisée
     * @param max borne maximale autorisée
     * @return un entier compris entre les bornes minimale et maximale incluses.
     */
    private int lireEntier(Scanner scanner, String message, int min, int max) {
        int valeur;
        while (true) {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                valeur = scanner.nextInt();
                if (valeur >= min && valeur <= max) {
                    return valeur;
                }
                System.out.println("Choix invalide. Entre une valeur entre " + min + " et " + max + ".");
            } else {
                System.out.println("Saisie invalide, entre un nombre entier.");
                scanner.next();
            }
        }
    }

    /**
     * Demande au joueur quelle action effectuer pendant son tour.
     * @param scanner scanner utilisé pour lire la saisie
     * @return le numéro de l’action sélectionnée par le joueur.
     */
    public int choixScanner(Scanner scanner){
        System.out.println("1 : Attaquer");
        System.out.println("2 : Changer d'arme");
        System.out.println("3 : Manger et recuperer des PV");
        return lireEntier(scanner, "Ton action :", 1, 3);
    }

    /**
     * Demande au joueur de choisir un personnage adverse à attaquer.
     * @param scanner scanner utilisé pour lire la saisie
     * @param contre personnages du camp adverse
     * @return le personnage adverse choisi comme cible.
     */
    public Personnage choixMonstre(Scanner scanner, List<Personnage> contre) {
        System.out.println("Choisis ta cible :");
        for (int i = 0; i < contre.size(); i++) {
            Personnage p = contre.get(i);
            System.out.println(i + " : " + p.getNom() + " (PV : " + p.getPv() + ")");
        }
        int index = lireEntier(scanner, "Cible :", 0, contre.size() - 1);
        return contre.get(index);
    }

    /**
     * Demande au joueur de choisir une arme dans son sac.
     * @param scanner scanner utilisé pour lire la saisie
     * @return l’arme choisie, ou {@code null} si le sac ne contient aucune arme.
     */
    public Arme choixArme(Scanner scanner) {
        List<Arme> armes = this.hero.getArmesDuSac();
        if (armes.isEmpty()) {
            System.out.println("Aucune arme disponible.");
            return null;
        }
        System.out.println("Choisis une arme (ou entre -1 pour annuler) :");
        for (int i = 0; i < armes.size(); i++) {
            Arme arme = armes.get(i);
            System.out.println(i + " : " + arme.getNom() + " (Degats : " + arme.getDegat() + ")");
        }
        int index = lireEntier(scanner, "Arme :", -1, armes.size() - 1);
        return (index == -1) ? null : armes.get(index);
    }

    /**
     * Demande au joueur de choisir un aliment dans son sac.
     * @param scanner scanner utilisé pour lire la saisie
     * @return l’aliment choisi, ou {@code null} si le sac ne contient aucun aliment.
     */
    public Food choixFood(Scanner scanner) {
        List<Food> consommables = this.hero.getFoodDuSac();
        if (consommables.isEmpty()) {
            System.out.println("Tu n'as aucune nourriture dans ton sac !");
            return null;
        }
        System.out.println("Choisis ce que tu veux consommer :");
        for (int j = 0; j < consommables.size(); j++) {
            Food food = consommables.get(j);
            System.out.println(j + " : " + food.getNom() + " (Soin : +" + food.getBonus() + " PV)");
        }
        int index = lireEntier(scanner, "Choisis ce que tu veux consommer (ou -1 pour annuler) :", -1, consommables.size() - 1);
        return (index == -1) ? null : consommables.get(index);
    }

    /**
     * Annonce le début du combat.
     */
    public void lancerCombat() {
        System.out.println("Un combat commence : " + VERT + "Equipe HERO" + RESET + " VS " + ROUGE + "Equipe ENNEMIS" + RESET);
    }

    /**
     * Fait jouer les participants jusqu’à la défaite du héros ou l’élimination des ennemis.
     * @param scanner scanner utilisé pour lire la saisie
     */
    public void Combattre(Scanner scanner) {
        this.lancerCombat();

        List<Arme> armesSac = this.hero.getArmesDuSac();
        Arme armeCourante = !armesSac.isEmpty() ? armesSac.get(0) : null;

        List<Personnage> avec = new ArrayList<>(this.allies);
        avec.add(hero);

        List<Personnage> contre = new ArrayList<>(this.alliesTraitre);
        contre.add(monstre);

        Affichage aff = new Affichage(hero);
        Random random = new Random();

        while (!this.hero.getEstMort() && !contre.isEmpty()) {
            aff.combatTour(avec, contre);

            // Tour du héros : recommence tant qu'une action finale (attaquer ou manger) n'a pas été exécutée
            boolean actionValidee = false;
            while (!actionValidee) {
                int choix = this.choixScanner(scanner);

                switch (choix) {
                    case 1: // Attaquer
                        Personnage victime = choixMonstre(scanner, contre);
                        this.hero.attaquer(victime, armeCourante);
                        String nomArme = (armeCourante != null) ? armeCourante.getNom() : "mains nues";
                        System.out.println("Tu as attaque " + victime.getNom() + " avec " + nomArme + " ! PV restant : " + victime.getPv());

                        if (victime.getEstMort()) {
                            contre.remove(victime);
                            System.out.println(victime.getNom() + " est vaincu !");
                        }
                        actionValidee = true;
                        break;

                    case 2: // Changer d'arme (ne consomme pas le tour d'attaque)
                        Arme nouvelleArme = choixArme(scanner);
                        if (nouvelleArme != null) {
                            armeCourante = nouvelleArme;
                            System.out.println("Tu t'equipes de : " + armeCourante.getNom());
                        }
                        break;

                    case 3: // Manger
                        Food food = choixFood(scanner);
                        if (food != null) {
                            this.hero.consommer(food);
                            System.out.println("Tu as consomme " + food.getNom() + ". Tes PV : " + this.hero.getPv());
                            actionValidee = true;
                        }
                        break;
                }
            }
            // Arret immediat si le camp adverse est elimine
            if (contre.isEmpty()) {
                break;
            }

            // Tour des allies joueurs
            for (Allie allie : this.allies) {
                if (allie.getEstMort()) {
                    continue;
                }
                if (contre.isEmpty()) {
                    break;
                }
                Personnage cible = contre.get(random.nextInt(contre.size()));
                allie.attaquer(cible, allie.getArme());
                System.out.println("Ton allie " + allie.getNom() + " a attaque " + cible.getNom());
                if (cible.getEstMort()) {
                    contre.remove(cible);
                    System.out.println(cible.getNom() + " est mort !");
                }
            }

            if (contre.isEmpty()) {
                break;
            }

            // Tour des ennemis (monstre + traîtres)
            for (Personnage ennemi : contre) {
                if (ennemi.getEstMort() || avec.isEmpty()) {
                    continue;
                }
                Personnage victime = avec.get(random.nextInt(avec.size()));
                Arme armeAttaque = (ennemi instanceof Monstre) ? ((Monstre) ennemi).getArme() : ((Allie) ennemi).getArme();
                ennemi.attaquer(victime, armeAttaque);
                System.out.println(ennemi.getNom() + " a attaque " + victime.getNom() + " (PV : " + victime.getPv() + ")");

                if (victime.getEstMort()) {
                    avec.remove(victime);
                    System.out.println(victime.getNom() + " est tombe au combat !");

                    // Arrêt immédiat de la boucle ennemie si le héros meurt
                    if (victime == this.hero) {
                        break;
                    }
                }
            }
        }

        // Nettoyage des allies tues du groupe du heros
        if (!this.hero.getEstMort()) {
            this.hero.getAllieHeros().removeIf(Personnage::getEstMort);
            System.out.println(VERT + "Victoire !" + RESET);
        }
    }
}

