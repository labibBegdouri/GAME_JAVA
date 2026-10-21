package MAPS;

import java.util.List;

import Personnages.*;
import SAC.*;
/**
 * Affiche dans la console les informations du héros, des salles et des combats.
 */

public class Affichage {
    private Heros hero;

    public static final String RESET = "\u001B[0m";
    public static final String ROUGE = "\u001B[31m";
    public static final String VERT  = "\u001B[32m";

    /**
     * Crée un objet Affichage avec les paramètres fournis.
     * @param hero héros concerné
     */
    public Affichage(Heros hero){
        this.hero =  hero;
    }

    /**
     * Affiche les lignes fournies dans une boîte encadrée.
     * @param lignes lignes à afficher
     */
    private void afficherBoiteEgale(String[] lignes){
        int longueurMax = 0;
        for (String ligne : lignes){
            if (ligne.length()>longueurMax){
                longueurMax = ligne.length();
            }
        }

        int largeurInterieure = longueurMax + 4;
        String bordure = "=".repeat(largeurInterieure + 4);
        System.out.println(bordure);
        for (String ligne : lignes) {
            System.out.printf("||  %-" + longueurMax + "s  ||\n", ligne);
        }
        System.out.println(bordure);

    }

    /**
     * Affiche les informations de la salle et du héros.
     * @param salle salle concernée
     */
    public void afficherInfo(Salle salle){
        String [] info = new String[4];
        info[0] = "Salle : " + salle.getSalleNom();
        info[1] = "Ta position actuelle : " + "(" + hero.getPosition().getX()
        + "," + hero.getPosition().getY() + ")";
        info[2] = "PV : " + hero.getPv() + " / " + hero.getPvInit();
        info[3] = "Sac : "+ hero.getSac().getFullness() + " / " + hero.getSac().getCapacity();
        afficherBoiteEgale(info);
    }
    
    /**
     * Affiche les objets et les personnages présents dans la salle.
     * @param salle salle concernée
     */
    public void decrireSalle(Salle salle){
        List<Item> objects = salle.getObjetsParSol();
        List<Personnage> personnages = salle.getPersonnages();
        String [] description = new String[2 + objects.size() + personnages.size()];
        int i = 0;
        description[i] = "Objets visibles ici :";
        i++;
        for (Item obj : objects) {
            description[i] = "-" + obj.getNom() + " , sa taille = " + obj.getSize() + ", sa pos = ("
                    + obj.getPosition().getX() + "," + obj.getPosition().getY() + ")";
                    i++;
        }

        description[i] = "Personnages dans la salle :";
        i++;
        for (Personnage pers : personnages) {
            description[i] = "- " + pers.getNom() + " se trouve a (" + pers.getPosition().getX() + ","
                    + pers.getPosition().getY() + ")";
            i++;
        }
        afficherBoiteEgale(description);
    }

    /**
     * Affiche le contenu du sac du héros.
     */
    public void afficherSac() {
        Sac sac = this.hero.getSac();
        List<Item> items = sac.getSAC();
        if (!items.isEmpty()) {
            int i = 0;
            String[] sacInfo = new String[1 + items.size()];
            sacInfo[i] = "Votre sac contient :";
            i++;
            for (Item obj : items) {
                sacInfo[i] = i + "." + obj.toString();
                i++;
            }
            afficherBoiteEgale(sacInfo);
        } else {
            System.out.println("Votre sac est vide !!");
        }
    }

    /**
     * Affiche les points de vie et l’arme des alliés du héros.
     */
    public void alliesInfo(){
        List<Allie> allies = this.hero.getAllieHeros();
        String[] info = new String[1 + allies.size()];
        int i = 0;
        info[i] = "Vos allies";
        i++;
        for (Allie allie: allies){
            info[i] = i + "- " + allie.getNom() + ", PV: " + allie.getPv() + " / " + allie.getPvInit()
            + ", Arme: " + allie.getArme().getNom();
            i++;
        }
        afficherBoiteEgale(info);
    }

    /**
     * Affiche les deux équipes participant au combat.
     * @param avec personnages du camp du héros
     * @param contre personnages du camp adverse
     */
    public void combatTour(List<Personnage> avec, List<Personnage> contre){
        System.out.println(VERT + "Equipe HERO" + RESET);
        String[] avecInfo = combatTourAux(avec);
        afficherBoiteEgale(avecInfo);
        System.out.println(ROUGE + "Equipe MONSTRE" + RESET);
        String[] contreInfo = combatTourAux(contre);
        afficherBoiteEgale(contreInfo);
    }

    /**
     * Construit les lignes d’information d’une équipe.
     * @param pers personnages concernés
     * @return un tableau de lignes décrivant le nom et les points de vie de chaque personnage.
     */
    public String[] combatTourAux(List<Personnage> pers){
        String[] infoPers = new String[pers.size()];
        int i = 0 ;
        for (Personnage per: pers){
            infoPers[i] = i + "- " + per.getNom() + ", PV : " + per.getPv() +"/"+ per.getPvInit();
            i++;
        }
        return infoPers;
    }

    /**
     * Affiche la carte de la salle et sa légende.
     * @param salle salle concernée
     */
    public void afficherSalle(Salle salle){
        int h = salle.getH();
        int l = salle.getL();
        int x0 = salle.getPosInit().getX();
        int y0 = salle.getPosInit().getY();

        String[][] grille = new String[h + 2][l + 2];
        
        for (int ly = 0; ly <= h + 1; ly++) {
            for (int lx = 0; lx <= l + 1; lx++) {
                boolean estMur = (lx == 0 || lx == l + 1 || ly == 0 || ly == h + 1);
                grille[ly][lx] = estMur ? "#" : ".";
            }
        }

        for (Porte porte : salle.getPortes()) {
            int lx = porte.getPosition().getX() - x0;
            int ly = porte.getPosition().getY() - y0;
            if (lx >= 0 && lx <= l + 1 && ly >= 0 && ly <= h + 1) {
                grille[ly][lx] = porte.estFermee() ? (ROUGE + "P" + RESET) : (VERT + "P" + RESET);
            }
        }

        for (Item item : salle.getObjetsParSol()) {
            int lx = item.getPosition().getX() - x0;
            int ly = item.getPosition().getY() - y0;
            if (lx >= 1 && lx <= l && ly >= 1 && ly <= h) {
                if (item instanceof Cle) {
                    grille[ly][lx] = "K";
                } else if (item instanceof Arme) {
                    grille[ly][lx] = "A";
                } else if (item instanceof Food) {
                    grille[ly][lx] = "F";
                } else if (item instanceof Tresor) {
                    grille[ly][lx] = "T";
                } else {
                    grille[ly][lx] = "I";
                }
            }
        }

        for (Personnage pers : salle.getPersonnages()) {
            int lx = pers.getPosition().getX() - x0;
            int ly = pers.getPosition().getY() - y0;
            if (lx >= 1 && lx <= l && ly >= 1 && ly <= h) {
                if (pers instanceof Monstre) {
                    grille[ly][lx] = ROUGE + "M" + RESET;
                } else if (pers instanceof Allie) {
                    grille[ly][lx] = VERT + "L" + RESET;
                }
            }
        }

        if (this.hero != null && this.hero.getPosition() != null) {
            int hx = this.hero.getPosition().getX() - x0;
            int hy = this.hero.getPosition().getY() - y0;
            if (hx >= 1 && hx <= l && hy >= 1 && hy <= h) {
                grille[hy][hx] = VERT + "H" + RESET;
            }
        }
        System.out.print("  y \\ x ");
        for (int lx = 0; lx <= l + 1; lx++) {
            System.out.printf("%2d ", x0 + lx);
        }
        System.out.println();
        for (int ly = h + 1; ly >= 0; ly--) {
            System.out.printf("  %3d | ", y0 + ly);
            for (int lx = 0; lx <= l + 1; lx++) {
                System.out.print(" " + grille[ly][lx] + " ");
            }
            System.out.println();
        }
        System.out.println("Legende : [H] Heros | [M] Monstre | [L] Allie | [P] Porte | [K] Cle | [A] Arme | [F] Nourriture | [T] Tresor\n");    }
}
