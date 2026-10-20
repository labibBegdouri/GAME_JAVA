package Personnages;

import java.util.Scanner;

import MAPS.Affichage;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import SAC.Arme;
import SAC.Food ;

public class Combat {
    private List<Allie> allies;
    private List<Allie> alliesTraitre;
    private Heros hero;
    private Monstre monstre;

    public static final String RESET = "\u001B[0m";
    public static final String ROUGE = "\u001B[31m";
    public static final String VERT  = "\u001B[32m";

    public Combat(Heros hero, Monstre monstre, List<Allie> alliesTraitre, List<Allie> allies) {
        this.hero = hero;
        this.monstre = monstre;
        this.alliesTraitre = alliesTraitre;
        this.allies = allies;
    }

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

    public int choixScanner(Scanner scanner){
        System.out.println("1 : Attaquer");
        System.out.println("2 : Changer d'arme");
        System.out.println("3 : Manger et recuperer des PV");
        return lireEntier(scanner, "Ton action :", 1, 3);
    }

    public Personnage choixMonstre(Scanner scanner, List<Personnage> contre) {
        System.out.println("Choisis ta cible :");
        for (int i = 0; i < contre.size(); i++) {
            Personnage p = contre.get(i);
            System.out.println(i + " : " + p.getNom() + " (PV : " + p.getPv() + ")");
        }
        int index = lireEntier(scanner, "Cible :", 0, contre.size() - 1);
        return contre.get(index);
    }

    public Arme choixArme(Scanner scanner) {
        List<Arme> armes = this.hero.getArmesDuSac();
        if (armes.isEmpty()) {
            System.out.println("Aucune arme disponible.");
            return null;
        }
        System.out.println("Choisis une arme :");
        for (int i = 0; i < armes.size(); i++) {
            Arme arme = armes.get(i);
            System.out.println(i + " : " + arme.getNom() + " (Degats : " + arme.getDegat() + ")");
        }
        int index = lireEntier(scanner, "Arme :", 0, armes.size() - 1);
        return armes.get(index);
    }

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
        int index = lireEntier(scanner, "Nourriture :", 0, consommables.size() - 1);
        return consommables.get(index);
    }

    public Arme gestionChoix(Scanner scanner, int choix, Arme a, List<Personnage> contre) {
        switch (choix) {
            case 1:
                Personnage victime = choixMonstre(scanner, contre);
                this.hero.attaquer(victime, a);
                System.out.println("Tu as attaque " + victime.getNom() + " ! PV restant : " + victime.getPv());
                if (victime.getEstMort()) {
                    contre.remove(victime);
                    System.out.println(victime.getNom() + " est vaincu !");
                }
                return a;
            case 2:
                Arme nouvelleArme = choixArme(scanner);
                return (nouvelleArme != null) ? nouvelleArme : a;
            case 3:
                Food food = choixFood(scanner);
                if (food != null) {
                    this.hero.consommer(food);
                    System.out.println("Tu as consomme " + food.getNom() + ". Tes PV : " + this.hero.getPv());
                }
                return a;
        }
        return a;
    }

    public void lancerCombat() {
        System.out.println("Un combat commence : " + VERT + "Equipe HERO" + RESET + " VS " + ROUGE + "Equipe ENNEMIS" + RESET);
    }

    public void Combattre(Scanner scanner) {
        this.lancerCombat();
        Arme armeChoisie = this.choixArme(scanner);

        List<Personnage> avec = new ArrayList<>(this.allies);
        avec.add(hero);

        List<Personnage> contre = new ArrayList<>(this.alliesTraitre);
        contre.add(monstre);

        Affichage aff = new Affichage(hero);
        Random random = new Random();

        while (!this.hero.getEstMort() && !contre.isEmpty()) {
            aff.combatTour(avec, contre);

            // Tour du heros
            int choix = this.choixScanner(scanner);
            armeChoisie = this.gestionChoix(scanner, choix, armeChoisie, contre);

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

