package Personnages;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import SAC.*;
import MAPS.*;

public class MenuConfig {
    private int demanderEntier(Scanner scanner, String message){
        int valeur = 0;
        boolean saisieValide = false;

        while (!saisieValide) {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                valeur = scanner.nextInt();
                if (valeur > 0) {
                    saisieValide = true;
                } else {
                    System.out.println("Veuillez entrer un nombre valide.");
                }
            } else {
                System.out.println("Erreur : vous devez saisir un nombre valide.");
                scanner.next();
            }
        }
        return valeur;
    }

    public void statutHeros(Heros heros, Salle salleCourante) {
        System.out.println("Position actuelle : " + salleCourante.getSalleNom() + " | (" + heros.getPosition().getX() + ", " + heros.getPosition().getY() + ")");
        System.out.println("PV : " + heros.getPv() + " / " + heros.getPvInit() + "| Sac : " + heros.getSac().getFullness() + " / " + heros.getSac().getCapacity());
    }

    public void descripSalle(Salle s){
        System.out.println("Objets visibles ici : ");
        List<Item> objects = s.getObjetsParSol();
        List<Personnage> personnages = s.getPersonnages();

        for (Item obj : objects) {
            System.out.println("- " + obj.getNom() + " ,sa taille = " + obj.getSize() + ", sa pos = (" + obj.getPosition().getX() + ", " + obj.getPosition().getY() + ")" );
        }

        System.out.println("Personnages dans la salle :");
        for (Personnage pers : personnages) {
            System.out.println("- " + pers.getNom() + "se trouve a (" + pers.getPosition().getX() + ", " + pers.getPosition().getY() + ")");
        }
    }

    public String radar(Salle salleCourante, Position p) {
        int x = p.getX();
        int y = p.getY();

        int x0 = salleCourante.getPosInit().getX();
        int y0 = salleCourante.getPosInit().getY();
        int l = salleCourante.getL();
        int h = salleCourante.getH();

        if (x0 < x && x < x0 + l && y0 < y && y < y0 + h) /* la position est au sein de la salle */ {
            String contenu = "";
            for (Item obj : salleCourante.getObjetsParSol()) {
                if (p.equals(obj.getPosition())) {
                    contenu += obj.getNom() + " ";
                }
            }
            for (Personnage pers : salleCourante.getPersonnages()) {
                if (p.equals(pers.getPosition())) {
                    contenu += pers.getNom() + " ";
                }
            }
            if (contenu.isEmpty()){
                return "LIBRE";
            }
            return contenu;
        } else {
            for (Porte porte : salleCourante.getPortes().values()) {
                if (p.equals(porte.getPosition())) {
                    if (porte.estFermee()) {
                        return "Porte verrouille";
                    }
                    return "Porte ouverte";
                }
            }
            return "MUR";
        }
    }

    public Heros demarageJeu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----Configuration du jeu----");

        int pvInit = demanderEntier(scanner, "Entrez les points de vie des personnages :");
        Personnage.initialiserPvInit(pvInit);
        int cap = demanderEntier(scanner, "Entrez la capacite du sac :");
        Heros heros = new Heros(cap);

        System.out.println("Configuration terminee. L'aventure commence !");
        scanner.close();

        return heros;
    }

    private void setPositionAllie(Heros heros) {
        int xCible = heros.getPosition().getX();
        int yCible = heros.getPosition().getY();
        for (Allie a : heros.getAllieHeros()) {
            a.setPosition(xCible, yCible);
        }
    }

    public void afficherSac(Sac sac) {
        if (sac.getFullness() != 0) {
            System.out.println("Votre sac contient :");
            for (Item obj : sac.getSAC()) {
                System.out.println("- " + obj.getNom() + " ,sa taille = " + obj.getSize() + ", son ID = " + obj.getId());
            }
        } else {
            System.out.println("Votre sac est vide !!");
        }
    } 

    public Salle deplacement(Scanner scanner, Salle salleCourante, Heros heros) {
        /*On capture la direction du heros */
    
        boolean saisieValide = false;
        Direction dir = null;

        while (!saisieValide) {
            System.out.println("Dans quelle direction ? (HAUT, BAS, DROITE, GAUCHE)");
            if (scanner.hasNext()) {
                String dirStr = scanner.next().trim().toUpperCase();
                try {
                    dir = Direction.valueOf(dirStr);
                    saisieValide = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Direction inconnue. Tapez HAUT, BAS, DROITE ou GAUCHE");
                }
            }
        }

        int xCible = heros.getPosition().getX();
        int yCible = heros.getPosition().getY();

        switch (dir) {
            case HAUT:
                yCible++;
                break;
            case BAS :
                yCible--;
                break;
            case GAUCHE :
                xCible--;
                break;
            case DROITE :
                xCible++;
                break;
        }

        int x0 = salleCourante.getPosInit().getX();
        int y0 = salleCourante.getPosInit().getY();
        int l = salleCourante.getL();
        int h = salleCourante.getH();

        if (x0 < xCible && xCible < x0 + l && y0 < yCible && yCible < y0 + h) {
            heros.setPosition(xCible, yCible);
            setPositionAllie(heros);
            return salleCourante;
        } else {
            for (Porte porte : salleCourante.getPortes().values()) {
                Position p = new Position(xCible, yCible);

                if (p.equals(porte.getPosition())) {
                    if (porte.estFermee()) {
                        for (Item obj : heros.getSac().getSAC()) {
                            if (obj instanceof Cle) {
                                Cle cle = (Cle) obj;
                                if (porte.ouvrirPorte(cle.getCleId())) {
                                    switch (dir){
                                        case HAUT :
                                            heros.setPosition(xCible, yCible++);
                                            break;
                                        case BAS :
                                            heros.setPosition(xCible, yCible--);
                                            break;
                                        case GAUCHE :
                                            heros.setPosition(xCible--, yCible);
                                            break;
                                        case DROITE :
                                            heros.setPosition(xCible++, yCible);
                                            break;
                                    }
                                    setPositionAllie(heros);
                                }
                            }
                        }
                    } else {
                        switch (dir){
                            case HAUT :
                                heros.setPosition(xCible, yCible++);
                                break;
                            case BAS :
                                heros.setPosition(xCible, yCible--);
                                break;
                            case GAUCHE :
                                heros.setPosition(xCible--, yCible);
                                break;
                            case DROITE :
                                heros.setPosition(xCible++, yCible);
                                break;
                        }
                        setPositionAllie(heros);
                    }

                    return porte.getAutreSalle(salleCourante);
                }
            }
            System.out.println("Tu veux entrer a travers le mur ??");
            return salleCourante;
        }
    }

    public void ramasser(Salle salleCourante, Heros heros) {
        List<Item> objSalle= salleCourante.getObjetsParSol();
        Item objCible = null;

        for (Item obj : objSalle) {
            if (heros.getPosition().equals(obj.getPosition())) {
                objCible = obj;
                break;
            }
        }

        if (objCible != null) {
            if (heros.getSac().collect(objCible)) {
                    salleCourante.prendreObjet(objCible);
                    System.out.println("Objet ajoute au Sac");
                } else {
                    System.out.println("Sac rempli");
                }
        }
    }

    public void manger(Scanner scanner, Heros heros) {
        Sac sac  = heros.getSac();

        while (true) {
            int id = demanderEntier(scanner, "Choisis l'ID de la nourriture souhaite (-1 pour finir)");
            if (id == -1) {
                return ;
            } else { 
                Item obj = sac.getSAC().get(id);
                if (obj instanceof Food) {
                    Food f = (Food) obj;
                    heros.consommer(f);
                    break;
                } else {
                    System.out.println("Choisis bien qlq chose de mangeable");
                }
            }
        }
    }

    public void bouclePrinc(){
        Heros heros = demarageJeu();
        LecteurMaison lecteur = new LecteurMaison();
        Maison maison = lecteur.chargerDepuisFichier("/home/reda/Bureau/java_game/GAME_JAVA/MAPS/MaisonTest.txt"); 
        heros.setPosition(maison.getPosReveil().getX(), maison.getPosReveil().getY());

        Salle salleCourante = maison.getSalleReveil();
        Scanner scanner = new Scanner(System.in);

        while (!heros.getEstMort()) {
            statutHeros(heros, salleCourante);
            descripSalle(salleCourante);

            System.out.println("RADAR : (Pour se deplacer)");
            System.out.println("HAUT : " + radar(salleCourante, new Position(heros.getPosition().getX(), heros.getPosition().getY() + 1)));
            System.out.println("BAS : " + radar(salleCourante, new Position(heros.getPosition().getX(), heros.getPosition().getY() - 1)));
            System.out.println("DROITE : " + radar(salleCourante, new Position(heros.getPosition().getX() + 1, heros.getPosition().getY())));
            System.out.println("GAUCHE : " + radar(salleCourante, new Position(heros.getPosition().getX() - 1, heros.getPosition().getY())));

            for (Personnage pers : salleCourante.getPersonnages()) {
                if (pers.getPosition().equals(heros.getPosition()) && !heros.getAllieHeros().contains(pers)) {
                    if (pers instanceof Allie) {
                        Allie a = (Allie) pers;
                        System.out.println(a.getNom() + "est mtn votre allie");
                        heros.addAllie(a, salleCourante);
                    } 
                    if (pers instanceof Monstre) {
                        Monstre m = (Monstre) pers;
                        List<Allie> vraiAllie = new ArrayList<Allie>();
                        List<Allie> allieTraitre = new ArrayList<Allie>();
                        
                        for (Allie a : heros.getAllieHeros()) {
                            if (a.getTrahison()) {
                                allieTraitre.add(a);
                                heros.removeAllie(a);
                            } else {
                                vraiAllie.add(a);
                            }
                        }

                        //Combat combat = new Combat(heros, m, allieTraitre, vraiAllie);

                        System.out.println("Fait attention !!" + m.getNom() + " vous attaque");

                        if (!allieTraitre.isEmpty()) {
                            System.out.print("Vous etes trahi par : ");
                            for (Allie a : allieTraitre) {
                                System.out.print(a.getNom() + " ");
                            }
                            System.out.println();
                        }
                        
                        // combat.Combattre();
                    }
                }

                
            }

            int maxChoix = 3;
            System.out.println("Actions possibles :");
            System.out.println("1/ Afficher Sac");
            System.out.println("2/ Se deplacer");
            System.out.println("3/ Manger");
            
            for (Item obj : salleCourante.getObjetsParSol()) {
                if (heros.getPosition().equals(obj.getPosition())) {
                    System.out.println("4/ Ramasser objet");
                    maxChoix = 4;
                    break;
                }
            }

            int choix = 0;
            boolean saisieValide = false;

            while (!saisieValide) {
                System.out.println("C'est quoi ta prochaine action ?");
                if (scanner.hasNextInt()) {
                    choix = scanner.nextInt();
                    if (1 <= choix && choix <= maxChoix) {
                        saisieValide = true;
                    } else {
                        System.out.println("Veuillez choisir une action valide.");
                    }
                } else {
                    System.out.println("Erreur : vous devez saisir un nombre valide.");
                    scanner.next();
                }
            }

            switch (choix) {
                case 1 : 
                    afficherSac(heros.getSac());
                    break;
                case 2 :
                    salleCourante = deplacement(scanner, salleCourante, heros);
                    break;
                case 3 :
                    manger(scanner, heros);
                    break;
                case 4 :
                    ramasser(salleCourante, heros);
                    break;
                default :
                    System.out.println("Pas d'action valide");
                    break;
            }

            /*On ne fait cette verification que si le heros a arrive a la salle de sortie */
            for (Item obj : heros.getSac().getSAC()){
                if (obj instanceof Tresor) {
                    Tresor tresor = (Tresor) obj;
                    tresor.toString();
                }
            }
        }
        scanner.close();
    }
}