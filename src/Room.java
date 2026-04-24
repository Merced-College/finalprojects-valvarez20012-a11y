import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private ArrayList<Room> connectedRooms = new ArrayList<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addRoom(Room room) {
        connectedRooms.add(room);
    }

    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}