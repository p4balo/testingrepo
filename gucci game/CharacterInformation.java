import java.util.ArrayList;

public class CharacterInformation {
    private static ArrayList<String> inventory;
    private static String characterName;
    private static String characterRace;
    private static String characterHair;
    private static String characterStrength;
    private static String characterGender;
    private static double moneyCount;
    private static int cloutCount;
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
    public static void setCharacterRace(String race){
        characterRace = race;
    }
    public static String getCharacterRace(){
        return characterRace;
    }
    public static void setCharacterHair(String hair){
        characterHair = hair;
    }
    public static String getCharacterHair(){
        return characterHair;
    }
    public static void setCharacterStrength(String strength){
        characterStrength = strength;
    }
    public static String getCharacterStrength(){
        return characterStrength;
    }
    public static void setCharacterGender(String gender){
        characterGender = gender;
    }
    public static String getCharacterGender(){
        return characterGender;
    }
    public static void addMoneyCount(int amount){
        moneyCount+=amount;
    }
    public static void removeMoneyCount(int amount){
        moneyCount-=amount;
    }
    public static double getMoneyCount(){
        return moneyCount;
    }
    public static void addCloutCount(int amount){
        cloutCount+=amount;
    }
    public static void removeCloutCount(int amount){
        cloutCount-=amount;
    }
    public static int getCloutCount(){
        return cloutCount;
    }
}
