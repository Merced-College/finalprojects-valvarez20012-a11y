import java.util.LinkedList;

/*
Author: Victor Alvarez
Class: Dungeon.java

Description:
Manages the dungeon structure using a linked list of rooms.
Creates and connects rooms, provides navigation functionality.
*/
public class Dungeon {
    private LinkedList<Room> rooms;
    private Room startRoom;

    public Dungeon() {
        rooms = new LinkedList<>();
        createDungeon();
    }

    private void createDungeon() {
        // Create rooms
        Room entrance = new Room("Entrance", "The entrance to the ancient dungeon. A cold draft blows from deeper within.");
        Room hallway = new Room("Hallway", "A long hallway with torches flickering on the walls.");
        Room treasureRoom = new Room("Treasure Room", "A room filled with glittering treasures!");
        Room trapRoom = new Room("Trap Room", "Watch your step! This room is filled with traps.");
        Room exit = new Room("Exit", "The exit to freedom! Congratulations!");

        // Add items to rooms
        treasureRoom.addItem(new Item("Gold Coin", "A shiny gold coin", 10));
        treasureRoom.addItem(new Item("Silver Ring", "A beautiful silver ring", 20));
        trapRoom.addItem(new Item("Magic Potion", "A potion that restores health", 15));
        trapRoom.addItem(new Item("Ancient Key", "A rusty key that might open something", 5));

        // Add NPCs
        entrance.addNpc(new NPC("Old Man", "A wise old man with a long beard.", "Ancient Key", "That key looks familiar... I think it opens the way to the exit in the trap room."));
        hallway.addNpc(new NPC("Guard", "A stern-looking guard blocking the way.", "Magic Potion", "Ah, a potion! These are useful for healing wounds from traps."));

        // Connect rooms
        entrance.addConnection(hallway);
        hallway.addConnection(entrance);
        hallway.addConnection(treasureRoom);
        hallway.addConnection(trapRoom);
        treasureRoom.addConnection(hallway);
        trapRoom.addConnection(hallway);
        trapRoom.addConnection(exit);
        exit.addConnection(trapRoom);

        // Add to room list
        rooms.add(entrance);
        rooms.add(hallway);
        rooms.add(treasureRoom);
        rooms.add(trapRoom);
        rooms.add(exit);

        startRoom = entrance;
    }

    public Room getStartRoom() {
        return startRoom;
    }

    public LinkedList<Room> getRooms() {
        return rooms;
    }

    /*
    Algorithm: Recursive Room Traversal
    This recursive function explores all connected rooms from a starting point.
    Time Complexity: O(V + E) where V is vertices (rooms) and E is edges (connections)
    */
    public void exploreRooms(Room currentRoom, LinkedList<Room> visited) {
        if (visited.contains(currentRoom)) {
            return;
        }
        visited.add(currentRoom);
        currentRoom.setVisited(true);

        for (Room connected : currentRoom.getConnectedRooms()) {
            exploreRooms(connected, visited);
        }
    }
}