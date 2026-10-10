package MAPS;

import java.util.*;


public class JsonReader {
    
    public Maison chargerDepuisFichier(String chemin){ // charger la map depuis le chemin
        
        Maison maison = new Maison();
        // extraire la data utile
        ArrayList<String> dataUtile = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))){
        } catch (IOException e) {
        }

        if (dataUtile.size() < 2 ){
            return maison;
        }
        return maison;

        }

        // analyse de la map pour relier les classes




        
        

}
