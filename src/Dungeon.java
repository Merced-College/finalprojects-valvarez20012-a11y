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
        Room oracleChamber = new Room("Oracle's Chamber", "A mystical chamber filled with ancient prophecies and glowing runes.");
        Room pillarsWisdom = new Room("Pillars of Wisdom", "Tall stone pillars engraved with forgotten knowledge, casting long shadows.");
        Room pantheonGods = new Room("Pantheon of Gods", "A grand hall dedicated to divine beings, with statues of deities watching over.");
        Room labyrinthTrials = new Room("Labyrinth of Trials", "A confusing maze of passages, testing the worthy with illusions and traps.");
        Room gatewayEternity = new Room("Gateway to Eternity", "A shimmering portal leading to realms beyond, the ultimate escape.");

        // Add items to rooms
        pantheonGods.addItem(new Item("Divine Amulet", "A sacred amulet blessed by the gods, radiating holy power", 25));
        pantheonGods.addItem(new Item("Crystal Shard", "A fragment of ancient crystal, pulsing with magical energy", 15));
        labyrinthTrials.addItem(new Item("Elixir of Clarity", "A potion that clears the mind and reveals hidden truths", 20));
        labyrinthTrials.addItem(new Item("Oracle's Key", "A ornate key forged from mystical metals, said to unlock divine secrets", 30));

        // Add NPCs
        oracleChamber.addNpc(new NPC("Oracle", "A enigmatic oracle with glowing eyes, surrounded by mystical auras.", "Oracle's Key", "That key... it holds the power to open the Gateway to Eternity in the labyrinth."));
        pillarsWisdom.addNpc(new NPC("Sage", "A wise sage meditating among the ancient pillars, emanating calm wisdom.", "Elixir of Clarity", "Ah, the Elixir! It can help one navigate the illusions of the labyrinth."));

        // Connect rooms
        oracleChamber.addConnection(pillarsWisdom);
        pillarsWisdom.addConnection(oracleChamber);
        pillarsWisdom.addConnection(pantheonGods);
        pillarsWisdom.addConnection(labyrinthTrials);
        pantheonGods.addConnection(pillarsWisdom);
        labyrinthTrials.addConnection(pillarsWisdom);
        labyrinthTrials.addConnection(gatewayEternity);
        gatewayEternity.addConnection(labyrinthTrials);

        // Add to room list
        rooms.add(oracleChamber);
        rooms.add(pillarsWisdom);
        rooms.add(pantheonGods);
        rooms.add(labyrinthTrials);
        rooms.add(gatewayEternity);

        startRoom = oracleChamber;
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