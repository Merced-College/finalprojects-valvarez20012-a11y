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
        String oracleIntro = "The Oracle gestures to three images on the chamber walls:\n" +
                            "  Image 1: A figure passing through a shimmering portal\n" +
                            "  Image 2: A man being pursued through palace corridors\n" +
                            "  Image 3: A court chamber where a verdict of 'GUILTY' is proclaimed\n\n" +
                            "The Oracle speaks: 'These images tell a story, but the connection between them is obscured. What do you seek?'";
        
        String oraclePartial = "The Oracle translates the runes connecting the images:\n\n" +
                              "The first image shows a warrior who passed through a gateway to this realm.\n" +
                              "The second image shows this same warrior fleeing from kingdom guards.\n" +
                              "The third image shows a trial with a guilty verdict pronounced.\n\n" +
                              "[The Oracle keeps certain details hidden from you...]";
        
        String oracleFull = "The Oracle reveals the full story:\n\n" +
                           "This is the tale of a warrior who passed through the Gateway before you.\n" +
                           "He fled through the kingdom, pursued by those who sought him.\n" +
                           "He stood trial in this very court... and was found GUILTY.\n\n" +
                           "The warrior you see was judged harshly for crossing between worlds.";
        
        oracleChamber.addNpc(new NPC("Oracle", "An enigmatic oracle with glowing eyes, surrounded by mystical auras.", oracleIntro, oraclePartial, oracleFull));
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