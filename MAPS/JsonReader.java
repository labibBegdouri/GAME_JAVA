
import java.util.*;
import java.io.*;
import SAC.*;
import com.google.gson.*;


public class JsonReader {
    
    public Maison chargerDepuisFichier(String chemin){ // charger la map depuis le chemin
        Gson gson = new Gson();

        
        Maison maison = new Maison();
        // extraire la data utile
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
