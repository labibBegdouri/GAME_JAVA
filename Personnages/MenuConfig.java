package Personnages;
import java.util.ArrayList;
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
        System.out.println("Position : " + salleCourante.getSalleNom());
        System.out.println("PV : " + heros.getPv() + " / " + heros.getPvInit() + "| Sac : " + heros.getSac().getFullness() + " / " + heros.getSac().getCapacity());

        String directions = "";
        for (Direction dir : salleCourante.getPortes().keySet()){
            directions += " " + dir;
        }

        System.out.print("Sorties possibles :" + directions);
    }

    public void descripSalle(Salle s){
        System.out.println("Objets visibles ici : ");
        List<Item> objects = s.getObjetsParSol();
        List<Personnage> personnages = s.getPersonnages();

        for (Item obj : objects) {
            System.out.println("- " + obj.getNom() + " ,sa taille = " + obj.getSize() + ", son id = " + obj.getId() + ", son pos = " + obj.getPosition() );
        }

        System.out.println("Personnages dans la salle :");
        for (Personnage pers : personnages) {
            System.out.println("- " + pers.getNom());
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

        /*On recupere la porte et on essaie de l'ouvrir */
        Porte porte = salleCourante.getPorte(dir);

        if (porte == null) {
            System.out.println("Vous voulez entrer dans un mur ?");
            return salleCourante;
        }
        if (porte.estFermee()){
            for (Item obj : heros.getSac().getSAC()){
                if (obj instanceof Cle) {
                    Cle cle = (Cle) obj;
                    if (porte.ouvrirPorte(cle.getCleId())){
                        return porte.getAutreSalle(salleCourante);
                    }
                }
            }
            if (porte.estFermee()) {
                System.out.println("La porte est ferme, tu n'as pas la cle !!");
                return salleCourante;
            }
        }
        return porte.getAutreSalle(salleCourante);
    }

    public void ramasser(Scanner scanner, Salle salleCourante, Heros heros) {
        List<Item> objSalle= salleCourante.getObjetsParSol();

        while (true) {
            int id = demanderEntier(scanner, "Choisis l'ID de l'objet souhaite (-1 pour finir)");
            if (id == -1) {
                return ;
            } else { 
                if (id >= 0 && id < objSalle.size()) {
                    Item obj = objSalle.get(id);
                    if (heros.getSac().collect(obj)) {
                        salleCourante.prendreObjet(obj);
                        System.out.println("Objet ajoute !!");
                    } else {
                        System.out.println("Sac rempli !!");
                    }
                } else {
                    System.out.println("ID inexistant !!");
                }
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
                
            }
        }
    }

    public void bouclePrinc(){
        Heros heros = demarageJeu();
        LecteurMaison lecteur = new LecteurMaison();
        Maison maison = lecteur.chargerDepuisFichier("/MAPS/MaisonText.txt"); 
        
        Salle salleCourante = maison.getSalleReveil();

        while (!heros.getEstMort()) {
            statutHeros(heros, salleCourante);
            descripSalle(salleCourante);

            


        }
    }
}