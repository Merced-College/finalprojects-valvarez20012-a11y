import java.util.ArrayList;
import java.util.LinkedList;

/*
Author: Victor Alvarez
Class: Room.java

Description:
Represents a room in the dungeon. Each room has a description, may contain items,
and has connections to other rooms via a linked list structure.
Uses ArrayList for items in the room and LinkedList for navigation history.
*/
public class Room {
    private String name;
    private String description;
    private ArrayList<Item> items;
    private LinkedList<Room> connectedRooms;
    private boolean visited;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
        this.connectedRooms = new LinkedList<>();
        this.visited = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public LinkedList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public void addConnection(Room room) {
        if (!connectedRooms.contains(room)) {
            connectedRooms.add(room);
        }
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}