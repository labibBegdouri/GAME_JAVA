package Personnages;

import java.util.Scanner;
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

    public Combat(Heros hero, Monstre monstre, List<Allie> alliesTraitre, List<Allie> allies) {
        this.hero = hero;
        this.monstre = monstre;
        this.alliesTraitre = alliesTraitre;
        this.allies = allies;
    }
    public int choixScanner(Scanner scanner){
        System.out.println("choisis 1 pour attaquer");
        System.out.println("choisis 2 pour changer d'arme");
        System.out.println("choisis 3 pour manger et gagner des pv");
        int choix = scanner.nextInt();
        System.out.println("tu as choisi l'option "+ choix);
        return choix ;
    }

    public Personnage choixMonstre(Scanner scanner, List<Personnage> contre){
        int choix = 0;
        for (Personnage p : contre){
            System.out.println("choisis " + choix + "pour attaquer" + p.getNom() +" qui a un pv = " + p.getPv());
            choix++;
        }
        choix = scanner.nextInt();
        Personnage victime = contre.get(choix) ;
        return victime;
        
    }

    public Arme choixArme(Scanner scanner){
        List<Arme> armes = this.hero.getArmesDuSac();
        System.out.println("choisi une arme");
        int i = 0 ;
        for (Arme arme : armes){
            System.out.println(i +" pour l'arme "+ arme.getNom()+" avec un degat de "+arme.getDegat());
            i++;
        }
        Arme arme = armes.get(scanner.nextInt());
        return arme ;
    }

    public Food choixFood(Scanner scanner){
        List<Food> consommables = this.hero.getFoodDuSac();
        System.out.println("choisi ce que tu veux consommer :");
        int j = 1 ;
        for (Food food : consommables){
            System.out.println(j +" pour "+ food.getNom()+" avec un pv de "+food.getBonus());
            j++;
        }
        Food food = consommables.get(scanner.nextInt());
        return food;
    }

    public Arme gestionChoix(Scanner scanner, int choix , Arme a, List<Personnage> contre){
        switch (choix){
            case 1:
                Personnage victime = choixMonstre(scanner,contre); 
                this.hero.attaquer(victime,a);
                if (victime.getEstMort()){
                    contre.remove(victime);
                }
                System.out.println("tu as attaque " + victime.getNom());
                return a ;
            case 2 :
                Arme arme = choixArme(scanner);
                System.out.println("tu as choisis l'arme " + arme.getNom() + "qui a pour degat" + arme.getDegat());
                return arme;
            case 3 :
                Food food = choixFood(scanner);
                this.hero.consommer(food);
                System.out.println("tu as mange " + food.getNom() + "ton pv actuel est " + this.hero.getPv());

                return a ;
        }
        return a ;
    }

    public void lancerCombat() {
        String message = "tu es entré dans une salle contenant un monstre qui a un pv =  "
                + this.monstre.getPv() + ". Le monstre t'attaque";
        System.out.println(message);
        this.monstre.attaquer(this.hero,this.monstre.getArme());
    }

    private boolean alliesTraitreMort(){
        for (Allie a : this.alliesTraitre){
            if (!a.getEstMort()){
                return false;
            }
        }
        return true;
    }

    public void Combattre(Scanner scanner) {
        this.lancerCombat();
        Arme arme_choisie = this.choixArme(scanner);
        List<Personnage> avec = new ArrayList<>(this.allies);
        avec.add(hero);

        List<Personnage> contre = new ArrayList<>(this.alliesTraitre);
        contre.add(monstre);

        Random random = new Random();
        while (!this.hero.getEstMort() && (!this.monstre.getEstMort() || !alliesTraitreMort())) {
            int choix = this.choixScanner(scanner);
            arme_choisie = this.gestionChoix(scanner,choix, arme_choisie,contre);
            for(Allie allie : this.allies){
                Personnage victime = contre.get(random.nextInt(contre.size()));
                allie.attaquer(victime,allie.getArme());
                System.out.println("ton allie " + allie.getNom()+ " a attaque " +  victime.getNom());

                if(victime.getEstMort()){
                    contre.remove(victime);
                    System.out.println(victime.getNom() + " est mort");
                }
            }

            for(Personnage monstre : contre){
                Personnage victime = avec.get(random.nextInt(avec.size()));
                if (monstre instanceof Monstre){
                    this.monstre.attaquer(victime,this.monstre.getArme());
                    System.out.println("le monstre " + this.monstre.getNom()+ " a attaque " +  victime.getNom());

                }else{
                    Allie allieTraitre = (Allie) monstre;
                    allieTraitre.attaquer(victime, allieTraitre.getArme());
                    System.out.println("le monstre " + allieTraitre.getNom()+ " a attaque " +  victime.getNom());

                }

                if(victime.getEstMort()){
                    avec.remove(victime);
                    System.out.println(victime.getNom() + " est mort");
                }
             
            }
        }
        
        if (!this.hero.getEstMort()){
            for (Allie a : allies){
                if (a.getEstMort()){
                    this.hero.removeAllie(a);
                }
            }
        }
    }
}

