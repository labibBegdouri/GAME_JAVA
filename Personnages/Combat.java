package Personnages;

import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import SAC.Arme;
import SAC.Food ;

public class Combat {
    private Set<Creature> heros;
    private Creature monstre;
    private Heros hero;

    public Combat(Heros hero, Creature monstre) {
        this.hero = hero;
        this.monstre = monstre;
        this.heros = new HashSet<>();
    }
    public int choix_scanner(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("choisis 1 pour attaquer");
        System.out.println("choisis 2 pour changer d'arme");
        System.out.println("choisis 3 pour manger et gagner des pv");
        int choix = scanner.nextInt();
        scanner.close();
        System.out.println("tu as choisi l'option "+ choix);
        return choix ;
    }


    public Arme gestion_choix(int choix , Arme a){
        switch (choix){
            case 1:
                this.hero.attaquer(monstre,a);
                return a ;
            case 2 :
                List<Arme> armes = this.hero.getArmesDuSac();
                Scanner arme_scanner = new Scanner(System.in);
                System.out.println("choisi une arme");
                int i = 1 ;
                for (Arme arme : armes){
                    System.out.println(i +" pour l'arme "+ arme.getNom()+" avec un degat de "+arme.getDegat());
                    i++;
                }
                Arme arme = armes.get(arme_scanner.nextInt());
                arme_scanner.close();
                return arme;
            case 3 :
                List<Food> consommables = this.hero.getFoodDuSac();
                Scanner food_scanner = new Scanner(System.in);
                System.out.println("choisi ce que tu veux consommer :");
                int j = 1 ;
                for (Food food : consommables){
                    System.out.println(j +" pour "+ food.getNom()+" avec un pv de "+food.getBonus());
                    j++;
                }
                Food food = consommables.get(food_scanner.nextInt());
                this.hero.consommer(food);
                return a ;
        }
        return a ;
    }

    public void lancerCombat() {
        String message = "tu es entré dans une salle contenant un monstre qui a une arme qui cause "
                + this.monstre.getPv() + ". Le monstre t'attaque";
        System.out.println(message);
        this.monstre.attaquer(this.hero);
    }

    public Arme choix_arme(){
        List<Arme> armes = this.hero.getArmesDuSac();
        Scanner arme_scanner = new Scanner(System.in);
        System.out.println("choisi une arme");
        int i = 1 ;
        for (Arme arme : armes){
            System.out.println(i +" pour l'arme "+ arme.getNom()+" avec un degat de "+arme.getDegat());
            i++;
        }
        Arme arme = armes.get(arme_scanner.nextInt());
        arme_scanner.close();
        return arme ;
    }

    public void Combattre() {
        this.lancerCombat();
        Arme arme_choisie = this.choix_arme();
        ArrayList<Personnage> perso = new ArrayList<>(this.heros);
        perso.add(this.hero);
        Random random = new Random();
        while (!this.hero.getEstMort() && !this.monstre.getEstMort()) {
            int choix = this.choix_scanner();
            Arme armeChoisie = this.gestion_choix(choix, arme_choisie);
            for(Creature allié : this.heros){
                allié.attaquer(this.monstre);
            }
            Personnage victime = perso.get(random.nextInt(perso.size()));
            this.monstre.attaquer(victime);
        }
    }
}

