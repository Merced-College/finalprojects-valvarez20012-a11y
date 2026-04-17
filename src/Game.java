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
        player = new Player(name);
        dungeon = new Dungeon();
        currentRoom = dungeon.getStartRoom();
        player.pushMovement(currentRoom);
    }

    public void start() {
        System.out.println("Welcome to the Dungeon Adventure Game, " + player.getName() + "!");
        System.out.println("Type 'help' for commands.\n");

        while (true) {
            displayRoom();
            String command = getCommand();

            if (command.equals("quit")) {
                break;
            } else if (command.equals("help")) {
                showHelp();
            } else if (command.startsWith("go ")) {
                move(command.substring(3));
            } else if (command.startsWith("take ")) {
                takeItem(command.substring(5));
            } else if (command.equals("inventory")) {
                showInventory();
            } else if (command.equals("look")) {
                displayRoom();
            } else if (command.equals("undo")) {
                undoMove();
            } else if (command.equals("score")) {
                System.out.println("Your score: " + player.getScore());
            } else {
                System.out.println("Unknown command. Type 'help' for commands.");
            }

            // Check win condition
            if (currentRoom.getName().equals("Exit")) {
                System.out.println("Congratulations! You escaped the dungeon!");
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

        System.out.println("Connected rooms:");
        for (Room room : currentRoom.getConnectedRooms()) {
            System.out.println("- " + room.getName());
        }
        System.out.print("> ");
    }

    private String getCommand() {
        return scanner.nextLine().toLowerCase().trim();
    }

    private void showHelp() {
        System.out.println("Commands:");
        System.out.println("go <room> - Move to a connected room");
        System.out.println("take <item> - Take an item from the current room");
        System.out.println("inventory - Show your inventory");
        System.out.println("look - Look around the current room");
        System.out.println("undo - Undo last move");
        System.out.println("score - Show current score");
        System.out.println("quit - Quit the game");
    }

    private void move(String roomName) {
        for (Room room : currentRoom.getConnectedRooms()) {
            if (room.getName().toLowerCase().equals(roomName.toLowerCase())) {
                currentRoom = room;
                player.pushMovement(currentRoom);
                return;
            }
        }
        System.out.println("You can't go there.");
    }

    private void takeItem(String itemName) {
        Item item = currentRoom.removeItem(itemName);
        if (item != null) {
            player.addItem(item);
            System.out.println("You took the " + item.getName());
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
            ArrayList<Item> items = new ArrayList<>(player.getInventory());
            bubbleSort(items);
            for (Item item : items) {
                System.out.println("- " + item);
            }
        }
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
        for (Item item : player.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }
}