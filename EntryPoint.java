import Personnages.MenuConfig;
/**
 * Point d’entrée du jeu : affiche le message d’accueil et démarre le menu.
 */

public class EntryPoint {

    /**
     * Lance le menu principal du jeu.
     * @param args arguments transmis au programme
     */
    public static void main(String[] args){
        System.out.println("WELCOME TO THE IMAG DUNGEON ");
        

        MenuConfig menu = new MenuConfig();
        menu.bouclePrinc();
    }


    
}
