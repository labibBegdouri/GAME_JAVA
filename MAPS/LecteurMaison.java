package MAPS;

import java.util.ArrayList;


import java.util.List;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import SAC.*;
import Personnages.*;

public class LecteurMaison{
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
                    int x = Integer.parseInt(ligneSplit[-2]);
                    int y = Integer.parseInt(ligneSplit[-1]);
                    int h = Integer.parseInt(ligneSplit[-4]);
                    int l = Integer.parseInt(ligneSplit[-2]);
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
            e.printStackTrace();
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
                int x = Integer.parseInt(lignePorteSplit[-2]);
                int y = Integer.parseInt(lignePorteSplit[-1]);
                porte.setPosition(x,y);
            }

        }
        
        return maison;
    }

    private Direction directionOpposee(Direction dir) {
        switch (dir) {
            case HAUT: return Direction.BAS;
            case BAS: return Direction.HAUT;
            case GAUCHE: return Direction.DROITE;
            case DROITE: return Direction.GAUCHE;
            default: return null;
        }
    }

    private void traiterContenuCase(String contenu, Salle salle, Maison maison){
        String[] contenuSplit = contenu.split(":");
        String type = contenuSplit[0];
        int x = Integer.parseInt(contenuSplit[-2]);
        int y = Integer.parseInt(contenuSplit[-1]);

        switch (type) {
            case "REVEIL":
                maison.setSalleReveil(salle.getSalleNom());
                maison.setPosReveil(x,y);
                break;
            case "TRESOR":
                maison.setSalleTresor(salle.getSalleNom());
                maison.setPosTresor(x, y);

                break;
            case "ARME":
                if (contenuSplit.length >= 5) {
                     Arme a = new Arme(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), Integer.parseInt(contenuSplit[4]));
                     a.setPosition(x, y);
                     salle.deposerObjet(a);
                }
                break;
            case "FOOD":
                // Syntaxe attendue -> FOOD:Nom:Id:Size:Bonus (ex: FOOD:POMME:1:1:15)
                if (contenuSplit.length >= 5) {
                    Food f = new Food(contenuSplit[1], Integer.parseInt(contenuSplit[2]), Integer.parseInt(contenuSplit[3]), Integer.parseInt(contenuSplit[4]));
                    f.setPosition(x, y);
                    salle.deposerObjet(f);
                }
                break;
            case "MONSTRE":
                if (contenuSplit.length >= 4) {
                        int pvInit = Integer.parseInt(contenuSplit[1]);
                        String nomArme = contenuSplit[2];
                        int degatArme = Integer.parseInt(contenuSplit[3]);

                        Arme armeMonstre = new Arme(nomArme, 0, 1, degatArme);
                        Monstre m = new Monstre (armeMonstre);
                        m.setPosition(x, y);
                        salle.ajouterPersonnage(m);
                    }
                    break;
            case "ALLIE":
                if (contenuSplit.length >= 5) {
                    int pvInit = Integer.parseInt(contenuSplit[1]);
                    String nomArme = contenuSplit[3];
                    int degatArme = Integer.parseInt(contenuSplit[4]);
                    
                    Arme armeAllie = new Arme(nomArme, 0, 1, degatArme);
                    boolean trahison = Boolean.parseBoolean(contenuSplit[2]);
                    Allie allie = new Allie(trahison, armeAllie);
                    allie.setPosition(x, y);
                    salle.ajouterPersonnage(allie);
                }
                break;
            case "CLE":
                if (contenuSplit.length >= 5) {
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




