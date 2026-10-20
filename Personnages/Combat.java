package Personnages;

import java.util.Scanner;

import MAPS.Affichage;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import SAC.Arme;
import SAC.Food ;

/**
 * Gère le déroulement d'un combat entre un héros, ses alliés, un monstre et
 * d'éventuels alliés traîtres.
 * <p>
 * Les choix du joueur sont lus depuis le scanner fourni aux méthodes de
 * combat.
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
     * Crée un combat avec les personnages participants.
     *
     * @param hero héros contrôlé par le joueur
     * @param monstre monstre affronté
     * @param alliesTraitre alliés qui combattent du côté adverse
     * @param allies alliés qui combattent aux côtés du héros
     */
    public Combat(Heros hero, Monstre monstre, List<Allie> alliesTraitre, List<Allie> allies) {
        this.hero = hero;
        this.monstre = monstre;
        this.alliesTraitre = alliesTraitre;
        this.allies = allies;
    }
    /**
     * Affiche les actions disponibles et lit le choix du joueur.
     *
     * @param scanner scanner utilisé pour lire la saisie
     * @return numéro de l'action choisie
     */
    public int choixScanner(Scanner scanner){
        System.out.println("choisis 1 pour attaquer");
        System.out.println("choisis 2 pour changer d'arme");
        System.out.println("choisis 3 pour manger et gagner des pv");
        int choix = scanner.nextInt();
        return choix ;
    }

    /**
     * Demande au joueur de choisir une cible parmi les personnages adverses.
     *
     * @param scanner scanner utilisé pour lire la saisie
     * @param contre personnages pouvant être attaqués
     * @return personnage choisi comme cible
     */
    public Personnage choixMonstre(Scanner scanner, List<Personnage> contre){
        int choix = 0;
        for (Personnage p : contre){
            System.out.println("choisis " + choix + " pour attaquer " + p.getNom() +" qui a un pv = " + p.getPv());
            choix++;
        }
        choix = scanner.nextInt();
        Personnage victime = contre.get(choix) ;
        return victime;
        
    }

    /**
     * Demande au joueur de choisir une arme dans le sac du héros.
     *
     * @param scanner scanner utilisé pour lire la saisie
     * @return arme choisie
     */
    public Arme choixArme(Scanner scanner){
        List<Arme> armes = this.hero.getArmesDuSac();
        System.out.println("choisis une arme");
        int i = 0 ;
        for (Arme arme : armes){
            System.out.println(i +" pour l'arme "+ arme.getNom()+" avec un degat de "+arme.getDegat());
            i++;
        }
        Arme arme = armes.get(scanner.nextInt());
        return arme ;
    }

    /**
     * Demande au joueur de choisir un consommable dans le sac du héros.
     *
     * @param scanner scanner utilisé pour lire la saisie
     * @return consommable choisi
     */
    public Food choixFood(Scanner scanner){
        List<Food> consommables = this.hero.getFoodDuSac();
        System.out.println("choisis ce que tu veux consommer :");
        int j = 1 ;
        for (Food food : consommables){
            System.out.println(j +" pour "+ food.getNom()+" avec un bonus de "+food.getBonus());
            j++;
        }

        
        Food food = consommables.get(scanner.nextInt());
        return food;
    }

    /**
     * Applique l'action choisie par le joueur : attaquer, changer d'arme ou
     * consommer un aliment.
     *
     * @param scanner scanner utilisé pour lire les choix de cible ou d'objet
     * @param choix numéro de l'action
     * @param a arme actuellement sélectionnée
     * @param contre personnages adverses pouvant être ciblés
     * @return arme à conserver pour la prochaine attaque
     */
    public Arme gestionChoix(Scanner scanner, int choix , Arme a, List<Personnage> contre){
        switch (choix){
            case 1:
                Personnage victime = choixMonstre(scanner,contre); 
                this.hero.attaquer(victime,a);
                if (victime.getEstMort()){
                    contre.remove(victime);
                }
                System.out.println("tu as attaque " + victime.getNom() + ", son PV actuel: " + victime.getPv());
                return a ;
            case 2 :
                Arme arme = choixArme(scanner);
                return arme;
            case 3 :
                Food food = choixFood(scanner);
                this.hero.consommer(food);
                System.out.println("tu as mange " + food.getNom() + "ton pv actuel est " + this.hero.getPv());
                return a ;
        }
        return a ;
    }

    /**
     * Affiche le début du combat et fait attaquer le monstre une première fois.
     */
    public void lancerCombat() {
        String message = "tu es devant un monstre qui a un pv =  "
                + this.monstre.getPv() + ", son arme " + this.monstre.getArme().getNom() +" avec un degat de "
                + this.monstre.getArme().getDegat();
        System.out.println(message);
        System.out.println("Le combat commence " + VERT + "Equipe HERO" + RESET + " VS " + ROUGE + "Equipe MONSTRE" + RESET);
    }

    /**
     * Indique si tous les alliés traîtres sont morts.
     *
     * @return {@code true} si aucun allié traître n'est encore vivant
     */
    private boolean alliesTraitreMort(){
        for (Allie a : this.alliesTraitre){
            if (!a.getEstMort()){
                return false;
            }
        }
        return true;
    }

    /**
     * Lance le combat et le poursuit jusqu'à la défaite du héros ou la
     * disparition des adversaires. Les participants jouent leurs tours
     * automatiquement, tandis que le héros suit les choix saisis.
     *
     * @param scanner scanner utilisé pour lire les choix du joueur
     */
    public void Combattre(Scanner scanner) {
        this.lancerCombat();
        Arme arme_choisie = this.choixArme(scanner);
        List<Personnage> avec = new ArrayList<>(this.allies);
        avec.add(hero);

        List<Personnage> contre = new ArrayList<>(this.alliesTraitre);
        contre.add(monstre);
        Affichage aff = new Affichage(hero);
        Random random = new Random();
        while (!this.hero.getEstMort() && (!this.monstre.getEstMort() || !alliesTraitreMort())) {
            aff.combatTour(avec, contre);
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

