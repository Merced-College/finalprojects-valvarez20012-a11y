public class Dungeon {
    private Room startRoom;

    public Dungeon() {
        Room oracle = new Room(
                "Oracle Room",
                "A candlelit chamber where the Oracle waits beside ancient carvings."
        );

        Room paintings = new Room(
                "Painting Room",
                "A gallery of glowing paintings showing the Warrior, the Rogue, and the stolen verdict."
        );

        Room witness = new Room(
                "Witness Hall",
                "A corridor where stone faces repeat fragments of what they saw."
        );

        Room runes = new Room(
                "Rune Chamber",
                "A chamber covered in runes about guilt, silence, and planted evidence."
        );

        Room evidence = new Room(
                "Evidence Room",
                "A final evidence chamber holding relics from the corrupted trial."
        );

        Room court = new Room(
                "Pantheon's Court",
                "A divine courtroom where Judge Pantheon waits for your final answer."
        );

        oracle.addRoom(paintings);
        paintings.addRoom(witness);
        witness.addRoom(runes);
        runes.addRoom(evidence);
        evidence.addRoom(court);

        startRoom = oracle;
    }

    public Room getStartRoom() {
        return startRoom;
    }
}