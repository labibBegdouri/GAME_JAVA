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

        // Connecter les portes
        for (String[] infoPorte : memoirePortes) {
            // PORTE:<Etat:O/F>:<CleId/TRESOR>:<SalleCible>:<x>:<y>
            String salleName = infoPorte[0];
            String lignePorte = infoPorte[1];
            String[] lignePorteSplit = lignePorte.split(":");
            String etat = lignePorteSplit[1];
            String serrureId = lignePorteSplit[2];
            String nomSalleB = lignePorteSplit[3];

            Salle salleA = maison.getSalle(salleName);
            Salle salleB = maison.getSalle(nomSalleB);

            if (salleA != null && salleB != null) {
                Porte porte = new Porte(etat, serrureId);
                porte.setSalleA(salleA);
                porte.setSalleB(salleB);

                int x = Integer.parseInt(lignePorteSplit[lignePorteSplit.length - 2]);
                int y = Integer.parseInt(lignePorteSplit[lignePorteSplit.length - 1]);
                porte.setPosition(x, y);

                salleA.addPorte(porte);
                salleB.addPorte(porte);
            }
        }
        
        return maison;
    }

    /**
     * Interprète une ligne de contenu et ajoute l’élément correspondant à la salle ou à la maison.
     * @param contenu ligne décrivant l’élément à traiter
     * @param salle salle concernée
     * @param maison maison à compléter
     */
    private void traiterContenuCase(String contenu, Salle salle, Maison maison){
        String[] split = contenu.split(":");
        String type = split[0];
        int x = Integer.parseInt(split[split.length-2]);
        int y = Integer.parseInt(split[split.length-1]);

        switch (type) {
            case "REVEIL":
                maison.setSalleReveil(salle.getSalleNom());
                maison.setPosReveil(x,y);
                break;
            case "TRESOR":
                Tresor tresor = new Tresor(1);
                tresor.setPosition(x, y);
                salle.deposerObjet(tresor);
                maison.setSalleTresor(salle.getSalleNom());
                maison.setPosTresor(x, y);
                break;

            case "ARME":
                // ARME:<Nom>:<Taille>:<Degats>:<x>:<y>
                Arme a = new Arme(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                a.setPosition(x, y);
                salle.deposerObjet(a);
                break;

            case "FOOD":
                // FOOD:<Nom>:<Taille>:<SoinPV>:<x>:<y>
                Food f = new Food(split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                f.setPosition(x, y);
                salle.deposerObjet(f);
                break;

            case "CLE":
                // CLE:<Nom>:<Taille>:<CleId>:<x>:<y>
                Cle c = new Cle(split[1], Integer.parseInt(split[2]), split[3]);
                c.setPosition(x, y);
                salle.deposerObjet(c);
                break;

            case "MONSTRE":
                // MONSTRE:<NomArme>:<DegatArme>:<x>:<y>
                Arme armeM = new Arme(split[1], 0, Integer.parseInt(split[2]));
                Monstre m = new Monstre(armeM);
                m.setPosition(x, y);
                salle.ajouterPersonnage(m);
                break;

            case "ALLIE":
                // ALLIE:<PV>:<Trahison>:<NomArme>:<DegatArme>:<x>:<y>
                int pvAllie = Integer.parseInt(split[1]);
                boolean trahison = split[2].trim().equalsIgnoreCase("OUI");
                Arme armeA = new Arme(split[3], 0, Integer.parseInt(split[4]));
                Allie allie = new Allie(pvAllie, trahison, armeA);
                allie.setPosition(x, y);
                salle.ajouterPersonnage(allie);
                break;
            default:
                break;
        }
    }

}




