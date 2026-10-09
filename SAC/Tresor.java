package SAC;

import java.util.ArrayList;

public class Tresor {
        ArrayList<Item> items = new ArrayList<Item>();

        public Tresor(ArrayList<Item> tresor){
            for (Item item:tresor){
                items.add(item);
            }

        }

        Item take(Item item){
            items.remove(item);
            return item;
        }

        
        public ArrayList<Item> getItems() {
            return items;
        }

    
}
