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
        // Create rooms with story-consistent descriptions
        Room oracleChamber = new Room("Oracle's Chamber", "A mystical chamber filled with ancient prophecies and glowing runes. The walls depict the tragic tale of a warrior who crossed realms and faced judgment.");
        Room pillarsWisdom = new Room("Pillars of Wisdom", "Tall stone pillars engraved with forgotten knowledge, casting long shadows. These pillars once witnessed the warrior's desperate search for answers before his trial.");
        Room pantheonGods = new Room("Pantheon of Gods", "A grand hall dedicated to divine beings, with statues of deities watching over. This was the court chamber where the warrior stood trial and was proclaimed GUILTY by the divine judges.");
        Room labyrinthTrials = new Room("Labyrinth of Trials", "A confusing maze of passages that once served as the warrior's escape route, pursued by kingdom guards through these twisting corridors of judgment.");
        Room gatewayEternity = new Room("Gateway to Eternity", "A shimmering portal leading to realms beyond, the same gateway the condemned warrior used to flee into this mystical realm, forever marked by his transgression.");

        // Add items to rooms with story-consistent descriptions
        oracleChamber.addItem(new Item("Oracle's Camping Supplies", "A weathered backpack containing essential supplies for mystical journeys - blankets, rations, and mystical herbs for safe passage", 10));
        pantheonGods.addItem(new Item("Divine Amulet", "A sacred amulet that once belonged to the warrior during his trial, bearing the mark of divine judgment", 25));
        pantheonGods.addItem(new Item("Crystal Shard", "A fragment of the crystal that shattered during the warrior's guilty verdict, still pulsing with the echoes of judgment", 15));
        labyrinthTrials.addItem(new Item("Elixir of Clarity", "A potion that clears the mind, much like the clarity the warrior sought during his frantic escape through these passages", 20));
        labyrinthTrials.addItem(new Item("Oracle's Key", "The ornate key that unlocked the warrior's fate, forged from mystical metals and bearing the weight of his condemnation", 30));

        // Add NPCs with story-consistent descriptions
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
        
        oracleChamber.addNpc(new NPC("Oracle", "An enigmatic oracle with glowing eyes, surrounded by mystical auras. The Oracle witnessed the warrior's trial and knows the full story of his condemnation.", oracleIntro, oraclePartial, oracleFull));
        pillarsWisdom.addNpc(new NPC("Sage", "A wise sage meditating among the ancient pillars, emanating calm wisdom. The Sage once counseled the condemned warrior during his search for redemption.", "Elixir of Clarity", "Ah, the Elixir! It can help one navigate the illusions of the labyrinth, much like it might have helped the warrior during his desperate flight."));

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