import java.util.HashMap;
import java.util.Stack;

/*
Author: Victor Alvarez
Class: Player.java

Description:
Represents the player in the game. Manages player stats, inventory using HashMap,
and movement history using Stack for undo functionality.
*/
public class Player {
    private String name;
    private String characterClass;
    private int health;
    private int score;
    private HashMap<String, Item> inventory;
    private Stack<Room> movementHistory;

    public Player(String name, String characterClass) {
        this.name = name;
        this.characterClass = characterClass;
        this.score = 0;
        this.inventory = new HashMap<>();
        this.movementHistory = new Stack<>();
        
        // Set starting stats based on class
        switch (characterClass.toLowerCase()) {
            case "warrior":
                this.health = 120; // Higher health
                break;
            case "mage":
                this.health = 80; // Lower health but maybe magic abilities later
                break;
            case "rogue":
                this.health = 100; // Balanced
                break;
            default:
                this.health = 100; // Default
                break;
        }
    }

    public String getName() {
        return name;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.put(item.getName().toLowerCase(), item);
        addScore(item.getValue());
    }

    public Item removeItem(String itemName) {
        return inventory.remove(itemName.toLowerCase());
    }

    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName.toLowerCase());
    }

    public void pushMovement(Room room) {
        movementHistory.push(room);
    }

    public Room popMovement() {
        if (!movementHistory.isEmpty()) {
            return movementHistory.pop();
        }
        return null;
    }

    public boolean canUndo() {
        return !movementHistory.isEmpty();
    }

    @Override
    public String toString() {
        return "Player: " + name + " (" + characterClass + ") | Health: " + health + " | Score: " + score;
    }
}