/*
Author: Victor Alvarez
Class: NPC.java

Description:
Represents a non-player character in the game. NPCs can provide hints based on items the player has.
*/
public class NPC {
    private String name;
    private String description;
    private String hintItem;
    private String hintMessage;

    public NPC(String name, String description, String hintItem, String hintMessage) {
        this.name = name;
        this.description = description;
        this.hintItem = hintItem;
        this.hintMessage = hintMessage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasHintForItem(String itemName) {
        return hintItem != null && hintItem.equalsIgnoreCase(itemName);
    }

    public String getHintMessage() {
        return hintMessage;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}