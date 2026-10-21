package MAPS;

import java.util.ArrayList;


import java.util.List;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import SAC.*;
import Personnages.*;
/**
 * Lit un fichier de carte pour construire une maison, ses salles et leur contenu.
 */

public class LecteurMaison{
    /**
     * Charge la maison et ses éléments depuis le fichier indiqué.
     * @param chemin chemin du fichier de carte
     * @return la maison décrite dans le fichier.
     */
    public Maison chargerDepuisFichier(String chemin){ // charger la map depuis le chemin
        
        Maison maison = new Maison();
    
        List<String[]> memoirePortes = new ArrayList<String[]>(); //memoriser toutes les portes.

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))){
            String ligne;
            String salleName = "";
            Salle salleCourante = null;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")){
                    continue;
                }
                
                String[] ligneSplit = ligne.split(":");
                String objetType = ligneSplit[0];
                if (objetType.equals("Salle")){
                    salleName = ligneSplit[1];
                    salleCourante = new Salle(salleName);
                    int x = Integer.parseInt(ligneSplit[ligneSplit.length-2]);
                    int y = Integer.parseInt(ligneSplit[ligneSplit.length-1]);
                    int h = Integer.parseInt(ligneSplit[ligneSplit.length-4]);
                    int l = Integer.parseInt(ligneSplit[ligneSplit.length-3]);
                    salleCourante.setPosInit(x, y);
                    salleCourante.setH(h);
                    salleCourante.setL(l);
                    maison.ajouterSalle(salleCourante);
                }else if (objetType.equals("PORTE")) {
                    memoirePortes.add(new String[]{salleName,ligne});
                }else{
                    traiterContenuCase(ligne, salleCourante, maison);
                }
            }
        } catch (IOException e) {
            System.err.println("Impossible de charger la carte : " + chemin);
            throw new RuntimeException("Erreur de chargement du fichier de carte", e);
        }

        //Connecter les portes.

        for (String[] infoPorte : memoirePortes){
            String salleName = infoPorte[0];
            String lignePorte = infoPorte[1];
            String[] lignePorteSplit = lignePorte.split(":");

            String etat = lignePorteSplit[1];
            String serrureId = lignePorteSplit[2];  //L'ID ou "aucune"
            Direction dir = Direction.valueOf(lignePorteSplit[3]);
            String nomSalleB = lignePorteSplit[4];

            Salle salleA = maison.getSalle(salleName);
            Salle salleB = maison.getSalle(nomSalleB);

            if (salleA != null && salleB != null && salleA.getPorte(dir) == null) {
                    
                Porte porte = new Porte(etat, serrureId);
                
                porte.setSalleA(salleA);
                porte.setSalleB(salleB);
                
                salleA.addPorte(dir, porte);
                salleB.addPorte(directionOpposee(dir), porte);
                int x = Integer.parseInt(lignePorteSplit[lignePorteSplit.length-2]);
                int y = Integer.parseInt(lignePorteSplit[lignePorteSplit.length-1]);
                porte.setPosition(x,y);
            }

        }
        
        return maison;
    }

    /**
     * Retourne la direction opposée à celle fournie.
     * @param dir direction de la porte
     * @return la direction opposée à celle fournie.
     */
    private Direction directionOpposee(Direction dir) {
        switch (dir) {
            case HAUT: return Direction.BAS;
            case BAS: return Direction.HAUT;
            case GAUCHE: return Direction.DROITE;
            case DROITE: return Direction.GAUCHE;
            default: return null;
        }
    }

    /**
     * Interprète une ligne de contenu et ajoute l’élément correspondant à la salle ou à la maison.
     * @param contenu ligne décrivant l’élément à traiter
     * @param salle salle concernée
     * @param maison maison à compléter
     */
    private void traiterContenuCase(String contenu, Salle salle, Maison maison){
        String[] contenuSplit = contenu.split(":");
        String type = contenuSplit[0];
        int x = Integer.parseInt(contenuSplit[contenuSplit.length-2]);
        int y = Integer.parseInt(contenuSplit[contenuSplit.length-1]);

        switch (type) {
            case "REVEIL":
                maison.setSalleReveil(salle.getSalleNom());
                maison.setPosReveil(x,y);
                break;
            case "TRESOR":
                Tresor tresor=new Tresor("tresor",  Integer.parseInt(contenuSplit[1]));
                tresor.setPosition(x,y);
                salle.deposerObjet(tresor);
                maison.setSalleTresor(salle.getSalleNom());
                maison.setPosTresor(x, y);

                break;
            case "ARME":
                if (contenuSplit.length >= 7) {
                     Arme a = new Arme(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), Integer.parseInt(contenuSplit[4]));
                     a.setPosition(x, y);
                     salle.deposerObjet(a);
                }
                break;
            case "FOOD":
                if (contenuSplit.length >= 7) {
                    Food f = new Food(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), Integer.parseInt(contenuSplit[4]));
                    f.setPosition(x, y);
                    salle.deposerObjet(f);
                }
                break;
            case "MONSTRE":
                if (contenuSplit.length >= 6) {
                        //int pvInit = Integer.parseInt(contenuSplit[1]);
                        String nomArme = contenuSplit[2];
                        int degatArme = Integer.parseInt(contenuSplit[3]);

                        Arme armeMonstre = new Arme(nomArme, 0, 1, degatArme);
                        Monstre m = new Monstre (armeMonstre);
                        m.setPosition(x, y);
                        salle.ajouterPersonnage(m);
                    }
                    break;
            case "ALLIE":
                if (contenuSplit.length >= 7) {
                    int pvInit = Integer.parseInt(contenuSplit[1]);
                    String nomArme = contenuSplit[3];
                    int degatArme = Integer.parseInt(contenuSplit[4]);
                    
                    Arme armeAllie = new Arme(nomArme, 0, 1, degatArme);
                    String trahisonStr = contenuSplit[2].trim().toLowerCase();
                    boolean trahison = trahisonStr.equals("oui");
                    Allie allie = new Allie(pvInit,trahison, armeAllie);
                    allie.setPosition(x, y);
                    salle.ajouterPersonnage(allie);
                }
                break;
            case "CLE":
                if (contenuSplit.length >= 7) {
                    Cle c = new Cle(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), contenuSplit[4]);
                    c.setPosition(x, y);
                    salle.deposerObjet(c);
                }
                break;
            default:
                break;
        }
    }

}




