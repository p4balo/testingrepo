import java.util.ArrayList;

public class CharacterInformation {
    private static ArrayList<String> inventory;
    private static String characterName;
    private CharacterInformation(){
        inventory = new ArrayList<>();
    }
    public static void enterInventoryItem(String item){
        inventory.add(item);
    }
    public static ArrayList<?> getInventory(){
        return inventory;
    }
    public static void setCharacterName(String name){
        characterName = name;
    }
    public static String getCharacterName(){
        return characterName;
    }
}
