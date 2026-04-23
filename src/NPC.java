/*
Author: Victor Alvarez
Class: NPC.java

Description:
Represents a non-player character in the game. NPCs can provide hints based on items the player has.
Also supports class-specific dialogue and storytelling features.
*/
public class NPC {
    private String name;
    private String description;
    private String hintItem;
    private String hintMessage;
    private String storyIntroduction;
    private String storyPartial;
    private String storyFull;
    private boolean isStorytellingNpc;

    public NPC(String name, String description, String hintItem, String hintMessage) {
        this.name = name;
        this.description = description;
        this.hintItem = hintItem;
        this.hintMessage = hintMessage;
        this.isStorytellingNpc = false;
    }

    public NPC(String name, String description, String storyIntro, String storyPartial, String storyFull) {
        this.name = name;
        this.description = description;
        this.storyIntroduction = storyIntro;
        this.storyPartial = storyPartial;
        this.storyFull = storyFull;
        this.isStorytellingNpc = true;
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

    public boolean isStorytellingNpc() {
        return isStorytellingNpc;
    }

    public String getStoryIntroduction() {
        return storyIntroduction;
    }

    public String getStoryPartial() {
        return storyPartial;
    }

    public String getStoryFull() {
        return storyFull;
    }

    public String getHintMessage() {
        return hintMessage;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}