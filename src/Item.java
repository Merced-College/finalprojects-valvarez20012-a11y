/*
Author: Victor Alvarez
Class: Item.java

Description:
Represents an item in the game that can be collected by the player.
Uses basic data structures like strings for name and description.
*/
public class Item {
    private String name;
    private String description;
    private int value;

    public Item(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
}