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

        if (player.hasMetOracle() && !currentRoom.getName().equals("Oracle's Chamber")) {
            System.out.println(getOracleRoomInsight(currentRoom));
        }

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
        System.out.println("- look: Examine the room more closely with detailed item and NPC descriptions");
        System.out.println("- brief: Get a brief description of this room");

        if (!currentRoom.getConnectedRooms().isEmpty()) {
            System.out.println("- go <room>: Move to a connected room (e.g., go Pillars of Wisdom)");
        }

        if (!currentRoom.getItems().isEmpty()) {
            System.out.println("- take <item>: Pick up an item from the room (e.g., take Oracle's Key)");
        }

        if (!currentRoom.getNpcs().isEmpty()) {
            System.out.println("- talk <person>: Speak with someone in the room (e.g., talk Oracle)");
        }

        System.out.println("- inventory: Check your inventory with item descriptions and values");
        System.out.println("- status: View your current status (health, score, inventory count)");
        System.out.println("- undo: Go back to the previous room (if available)");
        System.out.println("- help: Show detailed command list with descriptions");
        System.out.println("- quit: Exit the game");
    }

    private String getCommand() {
        return scanner.nextLine().toLowerCase().trim();
    }

    private void showHelp() {
        System.out.println("=== Dungeon Adventure Game Commands ===");
        System.out.println("Navigation & Exploration:");
        System.out.println("  go <room>     - Move to a connected room (e.g., go Pillars of Wisdom)");
        System.out.println("  look          - Examine the room more closely with detailed item and NPC descriptions");
        System.out.println("  brief         - Get a brief description of this room");
        System.out.println("  undo          - Go back to the previous room (if available)");
        System.out.println();
        System.out.println("Interaction:");
        System.out.println("  take <item>   - Pick up an item from the room (e.g., take Oracle's Key)");
        System.out.println("  talk <person> - Speak with someone in the room (e.g., talk Oracle)");
        System.out.println();
        System.out.println("Information:");
        System.out.println("  inventory     - Check your inventory with item descriptions and values");
        System.out.println("  status        - View your current status (health, score, inventory count)");
        System.out.println("  help          - Show this detailed command list");
        System.out.println();
        System.out.println("Game Control:");
        System.out.println("  quit          - Exit the game");
        System.out.println();
        System.out.println("Note: After meeting the Oracle, you'll unlock special story insights for each room!");
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
                    player.setOracleMet(true);
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

    private String getOracleRoomInsight(Room room) {
        String roomName = room.getName();
        String characterClass = player.getCharacterClass();

        switch (roomName) {
            case "Pillars of Wisdom":
                if (characterClass.equalsIgnoreCase("Mage")) {
                    return "The Oracle's visions reveal that the pillars hold hidden sigils of power that only a magical scholar can interpret.";
                } else if (characterClass.equalsIgnoreCase("Rogue")) {
                    return "The Oracle's tale makes the shadows feel alive here, as if secret passages and unseen eyes watch every step.";
                } else {
                    return "The Oracle reminds you that this hall once hosted the warrior's desperate search for answers before his trial.";
                }
            case "Pantheon of Gods":
                if (characterClass.equalsIgnoreCase("Mage")) {
                    return "The Oracle's vision uncovers the divine energy trapped in the statues, a clue to the warrior's fate in this court.";
                } else if (characterClass.equalsIgnoreCase("Rogue")) {
                    return "The Oracle's story shows you how the court's judges were both protector and prison, perfect for someone who reads people well.";
                } else {
                    return "The Oracle tells you this is the very hall where the condemned warrior was judged and found GUILTY.";
                }
            case "Labyrinth of Trials":
                if (characterClass.equalsIgnoreCase("Mage")) {
                    return "The Oracle's insight reveals the maze's illusions and the magical traps set to keep the warrior from escaping.";
                } else if (characterClass.equalsIgnoreCase("Rogue")) {
                    return "The Oracle warns that this maze is designed for those who move unseen, with hidden shortcuts waiting to be found.";
                } else {
                    return "The Oracle's story makes it clear: this labyrinth was the warrior's final escape route from his pursuers.";
                }
            case "Gateway to Eternity":
                if (characterClass.equalsIgnoreCase("Mage")) {
                    return "The Oracle shares the portal's true nature: a threshold between worlds that hums with raw cosmic energy.";
                } else if (characterClass.equalsIgnoreCase("Rogue")) {
                    return "The Oracle hints that this gate can be slipped through by those who move quickly and quietly.";
                } else {
                    return "The Oracle reveals that this portal is the same one the condemned warrior used to flee into another realm.";
                }
            default:
                return "The Oracle's knowledge now helps you see the deeper meaning of this place.";
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