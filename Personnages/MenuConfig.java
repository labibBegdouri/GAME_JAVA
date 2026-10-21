package Personnages;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import SAC.*;
import MAPS.*;
/**
 * Configure la partie et gère les actions de l’aventure dans la console.
 */

public class MenuConfig {
    //couleurs   
    public static final String RESET = "\u001B[0m";
    public static final String ROUGE = "\u001B[31m";
    public static final String VERT  = "\u001B[32m";


    /**
     * Demande et retourne un entier strictement positif.
     * @param scanner scanner utilisé pour lire la saisie
     * @param message message affiché à l’utilisateur
     * @return un entier strictement positif saisi par l’utilisateur.
     */
    private int demanderEntier(Scanner scanner, String message) {
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

    /**
     * Décrit le contenu de la position observée dans la salle.
     * @param salleCourante salle actuellement occupée
     * @param p position observée
     * @return une description de la position observée : son contenu, {@code LIBRE}, une porte ou un mur.
     */
    public String radar(Salle salleCourante, Position p) {
        int x = p.getX();
        int y = p.getY();

        int x0 = salleCourante.getPosInit().getX();
        int y0 = salleCourante.getPosInit().getY();
        int l = salleCourante.getL();
        int h = salleCourante.getH();

        if (x0 < x && (x <= x0 + l) && y0 < y && (y <= y0 + h)) /* la position est au sein de la salle */ {
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
            if (contenu.isEmpty()) {
                return VERT +"LIBRE"+ RESET;
            }
            return contenu;
        } else {
            for (Porte porte : salleCourante.getPortes().values()) {
                if (p.equals(porte.getPosition())) {
                    if (porte.estFermee()) {
                        return ROUGE +"Porte verrouille"+ RESET;
                    }
                    return VERT +"Porte ouverte"+ RESET ;
                }
            }
            return ROUGE +"MUR"+ RESET;
        }
    }

    /**
     * Demande les paramètres de la partie et crée le héros.
     * @param scanner scanner utilisé pour lire la saisie
     * @return le héros configuré pour la partie.
     */
    public Heros demarageJeu(Scanner scanner) {
        System.out.println("----Configuration du jeu----");

        int pvInit = demanderEntier(scanner, "Entrez les points de vie du heros :");

        int pvMonstres = demanderEntier(scanner, "Entrez les points de vie des monstres (communs a tous) :");
        Monstre.setPvCommuns(pvMonstres);

        int cap = demanderEntier(scanner, "Entrez la capacite du sac :");
        Heros heros = new Heros(pvInit,cap);

        System.out.println("Configuration terminee. L'aventure commence !");

        return heros;
    }

    /**
     * Place les alliés du héros à la position actuelle de celui-ci.
     * @param heros héros concerné
     */
    private void setPositionAllie(Heros heros) {
        int xCible = heros.getPosition().getX();
        int yCible = heros.getPosition().getY();
        for (Allie a : heros.getAllieHeros()) {
            a.setPosition(xCible, yCible);
        }
    }


    /**
     * Déplace le héros dans la direction indiquée et retourne la salle atteinte.
     * @param scanner scanner utilisé pour lire la saisie
     * @param salleCourante salle actuellement occupée
     * @param heros héros concerné
     * @param dir direction de la porte
     * @return la salle où se trouve le héros après le déplacement, ou la salle courante si le déplacement échoue.
     */
    public Salle deplacement(Scanner scanner, Salle salleCourante, Heros heros, Direction dir) {
        int xCible = heros.getPosition().getX();
        int yCible = heros.getPosition().getY();

        switch (dir) {
            case HAUT:
                yCible++;
                break;
            case BAS:
                yCible--;
                break;
            case GAUCHE:
                xCible--;
                break;
            case DROITE:
                xCible++;
                break;
        }

        int x0 = salleCourante.getPosInit().getX();
        int y0 = salleCourante.getPosInit().getY();
        int l = salleCourante.getL();
        int h = salleCourante.getH();

        if (x0 < xCible && xCible <= x0 + l && y0 < yCible && yCible <= y0 + h) {
            heros.setPosition(xCible, yCible);
            setPositionAllie(heros);
            return salleCourante;
        }

        // Verification de passage par une porte
        Position posCible = new Position(xCible, yCible);
        for (Porte porte : salleCourante.getPortes().values()) {
            if (posCible.equals(porte.getPosition())) {
                if (porte.estFermee()) {
                    if (porte.necessiteTresor()) {
                        if (heros.possedeTresor()) {
                            porte.ouvrirAvecTresor();
                            System.out.println(VERT + "Le Tresor scintille... La porte scellee s'ouvre devant toi !" + RESET);
                        } else {
                            System.out.println(ROUGE + "Cette porte est scellee par une magie ancienne. Seul le Tresor peut la deverrouiller !" + RESET);
                            return salleCourante;
                        }
                    }else {
                        Cle cleTrouvee = null;
                        for (Item obj : heros.getSac().getSAC()) {
                            if (obj instanceof Cle) {
                                Cle c = (Cle) obj;
                                if (porte.ouvrirPorte(c.getCleId())) {
                                    cleTrouvee = c;
                                    break;
                                }
                            }
                        }

                        if (cleTrouvee == null) {
                            System.out.println("La porte est verrouillee ! Tu n'as pas la bonne cle.");
                            return salleCourante;
                        }

                        heros.getSac().remove(cleTrouvee);
                        System.out.println("Porte ouverte avec succes avec la cle " + cleTrouvee.getNom() + " !");
                    }
                }

                switch (dir) {
                    case HAUT:
                        yCible++;
                        break;
                    case BAS:
                        yCible--;
                        break;
                    case GAUCHE:
                        xCible--;
                        break;
                    case DROITE:
                        xCible++;
                        break;
                }
                heros.setPosition(xCible, yCible);
                setPositionAllie(heros);
                return porte.getAutreSalle(salleCourante);
            }
        }

        System.out.println("Tu t'avances contre un mur !");
        return salleCourante;
    }

    /**
     * Ramasse l’objet situé sous le héros si le sac peut le contenir.
     * @param salleCourante salle actuellement occupée
     * @param heros héros concerné
     */
    public void ramasser(Salle salleCourante, Heros heros) {
        List<Item> objSalle = salleCourante.getObjetsParSol();
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
                if (objCible instanceof Tresor){
                    System.out.println(VERT + "Felicitation ! Tu as recupere le Tresor et maintenant tu dois chercher la sortie !" + RESET);
                }
                System.out.println("Objet ajoute au Sac");
            } else {
                System.out.println("Sac rempli");
            }
        }else {
            System.out.println("Il n'y a rien a ramasser sur cette case.");
        }
    }

    /**
     * Permet au héros de choisir et consommer un aliment de son sac.
     * @param scanner scanner utilisé pour lire la saisie
     * @param heros héros concerné
     */
    public void manger(Scanner scanner, Heros heros) {
        Sac sac = heros.getSac();
        List<Food> consommables = heros.getFoodDuSac();
        if (consommables.isEmpty()) {
            System.out.println("Tu n'as aucune nourriture dans ton sac.");
            return;
        }

        while (true) {
            Affichage aff = new Affichage(heros);
            aff.afficherSac();
            System.out.println("Choisis le numero de l'objet a consommer (ou -1 pour annuler) :");

            if (!scanner.hasNextInt()) {
                System.out.println("Saisie invalide, entre un nombre.");
                scanner.next();
                continue;
            }

            int choix = scanner.nextInt();
            if (choix < 1 || choix == -1) {
                return;
            }

            List<Item> items = sac.getSAC();
            if (choix > items.size()){
                System.out.println("Numero invalide. Choisis un indice affiche dans le sac.");
                continue;
            }

            Item obj = sac.getSAC().get(choix - 1);
            if (obj instanceof Food) {
                Food f = (Food) obj;
                heros.consommer(f);
                System.out.println("Tu as consomme " + f.getNom() + ". Tes PV : " + heros.getPv());
                break;
            } else {
                System.out.println("Choisis bien qlq chose de mangeable");
            }
            
        }
    }

    /**
     * Configure et exécute la boucle principale de l’aventure.
     */
    public void bouclePrinc() {
        Scanner scanner = new Scanner(System.in);
        Heros heros = demarageJeu(scanner);
        Affichage aff = new Affichage(heros);
        LecteurMaison lecteur = new LecteurMaison();
        Maison maison = lecteur.chargerDepuisFichier("./MAPS/MaisonTest.txt");

        heros.setPosition(maison.getPosReveil().getX(), maison.getPosReveil().getY());

        heros.getSac().collect(new Arme("mains", 0, 0, 1));

        ArrayList<String> options = new ArrayList<>(List.of("L", "K", "J", "H", "Z", "D", "S", "Q"));
        Salle salleCourante = maison.getSalleReveil();

        while (!heros.getEstMort()) {
            ArrayList<Personnage> personnages = new ArrayList<>(salleCourante.getPersonnages());
            for (Personnage pers : personnages) {
                if (pers.getPosition().equals(heros.getPosition()) && !heros.getAllieHeros().contains(pers)) {
                    if (pers instanceof Allie) {
                        Allie a = (Allie) pers;
                        System.out.println(a.getNom() + " rejoint vos rangs !");
                        heros.addAllie(a, salleCourante);
                    } else if (pers instanceof Monstre) {
                        Monstre m = (Monstre) pers;
                        ArrayList<Allie> vraiAllie = new ArrayList<>();
                        ArrayList<Allie> allieTraitre = new ArrayList<>();

                        for (Allie a : heros.getAllieHeros()) {
                            if (a.getTrahison()) {
                                allieTraitre.add(a);
                            } else {
                                vraiAllie.add(a);
                            }
                        }

                        heros.getAllieHeros().removeAll(allieTraitre);

                        Combat combat = new Combat(heros, m, allieTraitre, vraiAllie);
                        System.out.println("Attention ! " + m.getNom() + " vous attaque !");
                        if (!allieTraitre.isEmpty()) {
                            System.out.print("Vous etes trahi par : ");
                            for (Allie a : allieTraitre) {
                                System.out.print(a.getNom() + " ");
                            }
                            System.out.println();
                        }

                        combat.Combattre(scanner);

                        if (m.getEstMort()) {
                            salleCourante.supprimerPersonnage(m);
                        }
                    }
                }
            }

            if (heros.getEstMort()) {
                System.out.println(ROUGE + "GAME OVER TRY AGAIN" + RESET);
                break;
            }

            if (salleCourante.getSalleNom().equals("Sortie")) {
                System.out.println(VERT + "======================================================================" + RESET);
                System.out.println(VERT + " VICTOIRE ! Tu as ouvert la porte avec le Tresor et tu t'es echappe !" + RESET);
                System.out.println(VERT + "======================================================================" + RESET);
                break;
            }

            aff.afficherInfo(salleCourante);
            aff.decrireSalle(salleCourante);
            aff.afficherSalle(salleCourante);

            System.out.println("RADAR :");
            int haut = heros.getPosition().getY() + 1;
            int bas = heros.getPosition().getY() - 1;
            int droite = heros.getPosition().getX() + 1;
            int gauche = heros.getPosition().getX() - 1;

            System.out.println("HAUT : " + radar(salleCourante, new Position(heros.getPosition().getX(), haut)));
            System.out.println("BAS : " + radar(salleCourante, new Position(heros.getPosition().getX(), bas)));
            System.out.println("DROITE : " + radar(salleCourante, new Position(droite, heros.getPosition().getY())));
            System.out.println("GAUCHE : " + radar(salleCourante, new Position(gauche, heros.getPosition().getY())));

            System.out.println("Actions possibles :");
            System.out.println("Z/ HAUT | S/ BAS | Q/ GAUCHE | D/ DROITE");
            System.out.println("L/ Afficher Sac | K/ Manger");
            if (!heros.getAllieHeros().isEmpty()) {
                System.out.println("J/ Voir tes allies");
            }
            for (Item obj : salleCourante.getObjetsParSol()) {
                if (heros.getPosition().equals(obj.getPosition())) {
                    System.out.println("H/ Ramasser objet");
                    break;
                }
            }

            String choix = "";
            while (true) {
                System.out.println("Quelle action choisis-tu ?");
                choix = scanner.next().trim().toUpperCase();
                if (options.contains(choix)) {
                    break;
                }
                System.out.println("Action invalide, recommence.");
            }

            switch (choix) {
                case "L":
                    aff.afficherSac();
                    break;
                case "K":
                    manger(scanner, heros);
                    break;
                case "H":
                    ramasser(salleCourante, heros);
                    break;
                case "J":
                    if (heros.getAllieHeros().isEmpty()) {
                        System.out.println("Tu n'as aucun allie pour le moment.");
                    } else {
                        aff.alliesInfo();
                    }
                    break;
                case "Z":
                    salleCourante = deplacement(scanner, salleCourante, heros, Direction.HAUT);
                    break;
                case "Q":
                    salleCourante = deplacement(scanner, salleCourante, heros, Direction.GAUCHE);
                    break;
                case "S":
                    salleCourante = deplacement(scanner, salleCourante, heros, Direction.BAS);
                    break;
                case "D":
                    salleCourante = deplacement(scanner, salleCourante, heros, Direction.DROITE);
                    break;
            }
        }
        scanner.close();
    }
}