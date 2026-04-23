import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/*
Author: Victor Alvarez
Class: Game.java

Description:
Main game logic class. Handles user input, room navigation, inventory management,
and game flow. Includes algorithms for sorting and searching.
*/
public class Game {
    private Player player;
    private Dungeon dungeon;
    private Room currentRoom;
    private Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.println("Choose your character class:");
        System.out.println("1. Warrior (High health, strong in combat)");
        System.out.println("2. Mage (Low health, magical abilities)");
        System.out.println("3. Rogue (Balanced, stealthy)");
        System.out.print("Enter 1, 2, or 3: ");
        
        String classChoice = scanner.nextLine();
        String characterClass;
        switch (classChoice) {
            case "1":
                characterClass = "Warrior";
                break;
            case "2":
                characterClass = "Mage";
                break;
            case "3":
                characterClass = "Rogue";
                break;
            default:
                characterClass = "Adventurer";
                break;
        }
        
        player = new Player(name, characterClass);
        dungeon = new Dungeon();
        currentRoom = dungeon.getStartRoom();
        player.pushMovement(currentRoom);
    }

    public void start() {
        System.out.println("Welcome to the Dungeon Adventure Game, " + player.getName() + " the " + player.getCharacterClass() + "!");
        System.out.println("Your goal is to explore the mystical chambers, collect sacred artifacts, and reach the Gateway to Eternity.");
        System.out.println("Type 'help' for commands.\n");

        while (true) {
            displayRoom();
            String command = getCommand();

            if (command.equals("quit")) {
                break;
            } else if (command.equals("help")) {
                showHelp();
            } else if (command.equals("go")) {
                System.out.println("Usage: go <room>. Example: go Pillars of Wisdom");
            } else if (command.startsWith("go ")) {
                move(command.substring(3));
            } else if (command.equals("take")) {
                System.out.println("Usage: take <item>. Example: take Oracle's Key");
            } else if (command.startsWith("take ")) {
                takeItem(command.substring(5));
            } else if (command.equals("inventory")) {
                showInventory();
            } else if (command.equals("look")) {
                lookAround();
            } else if (command.equals("undo")) {
                undoMove();
            } else if (command.equals("talk")) {
                System.out.println("Usage: talk <person>. Example: talk Oracle");
            } else if (command.startsWith("talk ")) {
                talkToNpc(command.substring(5));
            } else if (command.equals("brief")) {
                showBrief();
            } else if (command.equals("status")) {
                showStatus();
            } else {
                System.out.println("Unknown command. Type 'help' for commands.");
            }

            // Check win condition
            if (currentRoom.getName().equals("Gateway to Eternity")) {
                System.out.println("Congratulations! You have transcended to eternity!");
                System.out.println("Final Score: " + player.getScore());
                break;
            }
        }

        scanner.close();
    }

    private void displayRoom() {
        System.out.println("\n" + currentRoom.getName());
        System.out.println(currentRoom.getDescription());

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("Items here:");
            for (Item item : currentRoom.getItems()) {
                System.out.println("- " + item.getName());
            }
        }

        if (!currentRoom.getNpcs().isEmpty()) {
            System.out.println("People here:");
            for (NPC npc : currentRoom.getNpcs()) {
                System.out.println("- " + npc.getName());
            }
        }

        System.out.println("Connected rooms:");
        for (Room room : currentRoom.getConnectedRooms()) {
            System.out.println("- " + room.getName());
        }

        // Show available actions
        showAvailableActions();
        System.out.print("> ");
    }

    private void showAvailableActions() {
        System.out.println("\nAvailable actions:");
        System.out.println("- look: Examine the room more closely");
        System.out.println("- brief: Get a brief description of this room");

        if (!currentRoom.getConnectedRooms().isEmpty()) {
            System.out.println("- go <room>: Move to a connected room");
        }

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("- take <item>: Pick up an item from the room");
        }

        if (!currentRoom.getNpcs().isEmpty()) {
            System.out.println("- talk <person>: Speak with someone in the room");
        }

        System.out.println("- inventory: Check your inventory");
        System.out.println("- status: View your current status");
        System.out.println("- undo: Go back to the previous room");
        System.out.println("- help: Show all available commands");
        System.out.println("- quit: Exit the game");
    }

    private String getCommand() {
        return scanner.nextLine().toLowerCase().trim();
    }

    private void showHelp() {
        System.out.println("Commands:");
        System.out.println("go <room> - Move to a connected room");
        System.out.println("take <item> - Take an item from the current room");
        System.out.println("talk <npc> - Talk to a person in the room");
        System.out.println("brief - Get a brief description of the room");
        System.out.println("inventory - Show your inventory");
        System.out.println("look - Look around the current room with item and NPC details");
        System.out.println("undo - Undo last move");
        System.out.println("status - Show player status");
        System.out.println("quit - Quit the game");
    }

    private void lookAround() {
        System.out.println("\n" + currentRoom.getName());
        System.out.println(currentRoom.getDescription());

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("Items here:");
            for (Item item : currentRoom.getItems()) {
                System.out.println("- " + item.getName() + ": " + item.getDescription());
            }
        } else {
            System.out.println("No items are visible right now.");
        }

        if (!currentRoom.getNpcs().isEmpty()) {
            System.out.println("People here:");
            for (NPC npc : currentRoom.getNpcs()) {
                System.out.println("- " + npc.getName() + ": " + npc.getDescription());
            }
        } else {
            System.out.println("No one else is present in this room.");
        }

        System.out.println("Connected rooms:");
        for (Room room : currentRoom.getConnectedRooms()) {
            System.out.println("- " + room.getName());
        }
    }

    private void move(String roomName) {
        for (Room room : currentRoom.getConnectedRooms()) {
            if (room.getName().toLowerCase().equals(roomName.toLowerCase())) {
                currentRoom = room;
                player.pushMovement(currentRoom);
                System.out.println("You move to " + currentRoom.getName() + ".");
                return;
            }
        }
        System.out.println("You can't go there.");
    }

    private void takeItem(String itemName) {
        Item item = currentRoom.removeItem(itemName);
        if (item != null) {
            player.addItem(item);
            System.out.println("You took the " + item.getName() + ": " + item.getDescription());
        } else {
            System.out.println("No such item here.");
        }
    }

    private void showInventory() {
        if (player.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            // Sort inventory by value using bubble sort
            ArrayList<Item> items = new ArrayList<>(player.getInventory().values());
            bubbleSort(items);
            for (Item item : items) {
                System.out.println("- " + item.getName() + ": " + item.getDescription() + " (Value: " + item.getValue() + ")");
            }
        }
    }

    private void showStatus() {
        System.out.println("Player Status:");
        System.out.println("Name: " + player.getName());
        System.out.println("Health: " + player.getHealth());
        System.out.println("Score: " + player.getScore());
        System.out.println("Inventory items: " + player.getInventory().size());
    }

    private void undoMove() {
        if (player.canUndo()) {
            Room previous = player.popMovement();
            if (previous != null) {
                currentRoom = previous;
                System.out.println("Moved back to " + currentRoom.getName());
            }
        } else {
            System.out.println("Can't undo further.");
        }
    }

    private void talkToNpc(String npcName) {
        for (NPC npc : currentRoom.getNpcs()) {
            if (npc.getName().equalsIgnoreCase(npcName)) {
                // Handle storytelling NPCs (like Oracle)
                if (npc.isStorytellingNpc()) {
                    System.out.println(npc.getStoryIntroduction());
                    
                    // Class-specific handling
                    if (player.getCharacterClass().equalsIgnoreCase("Mage")) {
                        // Mage gets a perception check
                        if (attemptPerceptionCheck()) {
                            System.out.println("\n[Perception Check: SUCCESS]");
                            System.out.println(npc.getStoryFull());
                        } else {
                            System.out.println("\n[Perception Check: FAILED]");
                            System.out.println(npc.getStoryPartial());
                        }
                    } else if (player.getCharacterClass().equalsIgnoreCase("Warrior") || 
                               player.getCharacterClass().equalsIgnoreCase("Rogue")) {
                        // Warriors and Rogues get the partial story from translated runes
                        System.out.println("\n" + player.getCharacterClass() + ": 'Can you translate these runes?'");
                        System.out.println(npc.getStoryPartial());
                    }
                    return;
                }
                
                // Handle regular hint-based NPCs
                System.out.println(npc.getDescription());
                boolean gaveHint = false;
                for (Item item : player.getInventory().values()) {
                    if (npc.hasHintForItem(item.getName())) {
                        System.out.println("Hint: " + npc.getHintMessage());
                        gaveHint = true;
                        break;
                    }
                }
                if (!gaveHint) {
                    System.out.println("The " + npc.getName() + " doesn't have anything special to say right now.");
                }
                return;
            }
        }
        System.out.println("No one by that name here.");
    }

    /*
    Algorithm: Perception Check (Random probability for Mage class)
    Determines if the Mage can successfully read the full story from the runes.
    Gives a 60% chance of success with randomness.
    Time Complexity: O(1)
    */
    private boolean attemptPerceptionCheck() {
        int roll = (int) (Math.random() * 100);
        return roll < 60; // 60% success rate
    }

    private void showBrief() {
        System.out.println("Brief: " + currentRoom.getName() + " - " + getBriefDescription(currentRoom.getName()));
    }

    private String getBriefDescription(String roomName) {
        switch (roomName) {
            case "Oracle's Chamber":
                return "Mystical chamber with glowing runes depicting the condemned warrior's tale.";
            case "Pillars of Wisdom":
                return "Ancient pillars that witnessed the warrior's desperate search for answers.";
            case "Pantheon of Gods":
                return "Divine court chamber where the warrior was judged and found GUILTY.";
            case "Labyrinth of Trials":
                return "Twisting maze that served as the warrior's escape route from pursuers.";
            case "Gateway to Eternity":
                return "Shimmering portal the condemned warrior used to flee between worlds.";
            default:
                return "An unknown mystical chamber.";
        }
    }

    /*
    Algorithm: Bubble Sort
    Sorts inventory items by value in ascending order.
    Time Complexity: O(n^2)
    */
    private void bubbleSort(ArrayList<Item> items) {
        int n = items.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (items.get(j).getValue() > items.get(j + 1).getValue()) {
                    Collections.swap(items, j, j + 1);
                }
            }
        }
    }

    /*
    Algorithm: Linear Search
    Searches for an item in the player's inventory.
    Time Complexity: O(n)
    */
    public boolean searchInventory(String itemName) {
        for (Item item : player.getInventory().values()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }
}