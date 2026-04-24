public class Player {
    private String name;
    private String characterClass;

    public Player(String name, String characterClass) {
        this.name = name;
        this.characterClass = characterClass;
    }

    public String getName() {
        return name;
    }

    public String getCharacterClass() {
        return characterClass;
    }
}