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

    public Personnage choixMonstre(){
        Scanner scanner = new Scanner(System.in);
        int choix = 0;
        
        for (Allie p : this.alliesTraitre){
            System.out.println("choisis " + choix + "pour attaquer" + p.getNom() +" qui a un pv = " + p.getPv());
            choix++;
        }
        System.out.println("choisis " + choix + "pour attaquer" + monstre.getNom() +" qui a un pv = " + monstre.getPv());
        choix = scanner.nextInt();
        scanner.close();
        if (choix < this.alliesTraitre.size()){
            return this.alliesTraitre.get(choix);
        }
        return this.monstre;
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

    public Arme gestion_choix(int choix , Arme a){
        switch (choix){
            case 1:
                Personnage monstreAattaquer = choixMonstre(); 
                this.hero.attaquer(monstreAattaquer,a);
                return a ;
            case 2 :
                return choix_arme();
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

    public void Combattre() {
        this.lancerCombat();
        Arme arme_choisie = this.choix_arme();
        List<Personnage> avec = new ArrayList<>(this.allies);
        avec.add(hero);

        List<Personnage> contre = new ArrayList<>(this.alliesTraitre);
        contre.add(monstre);

        Random random = new Random();
        while (!this.hero.getEstMort() || (!this.monstre.getEstMort() && !alliesTraitreMort())) {
            int choix = this.choix_scanner();
            arme_choisie = this.gestion_choix(choix, arme_choisie);

            for(Allie allié : this.allies){
                Personnage victime = contre.get(random.nextInt(contre.size()));
                allié.attaquer(victime,allié.getArme());
                
                if(victime.getEstMort()){
                    contre.remove(victime);
                }
            }

            for(Personnage monstre : contre){
                Personnage victime = avec.get(random.nextInt(avec.size()));
                if (monstre instanceof Monstre){
                    this.monstre.attaquer(victime,this.monstre.getArme());
                }else{
                    Allie allieTraitre = (Allie) monstre;
                    allieTraitre.attaquer(victime, allieTraitre.getArme());
                }

                if(victime.getEstMort()){
                    avec.remove(victime);
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

