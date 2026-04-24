/*
Author: Victor Alvarez
Class: Game.java

Description:
Main game logic class. Handles user input, room navigation, inventory management,
and game flow. Includes algorithms for sorting and searching.
*/
import java.util.*;

public class Game {
    private Scanner scanner = new Scanner(System.in);

    // ARRAY: Evidence item system
    private Item[] evidenceItems = {
            new Item("Trial Blade", 10),
            new Item("Stolen Verdict Seal", 20)
    };

    // HASH TABLE: Inventory and ending records
    private HashMap<String, Item> inventory = new HashMap<>();
    private HashMap<String, Boolean> endingRecords = new HashMap<>();

    // STACK: Undo movement
    private Stack<Room> undoStack = new Stack<>();

    // LINKED LIST: Path history
    private LinkedList<Room> pathHistory = new LinkedList<>();

    // QUEUE: Room event messages
    private Queue<String> eventQueue = new LinkedList<>();

    private Player player;
    private Dungeon dungeon;
    private Room currentRoom;

    private boolean oracleIntroComplete = false;
    private boolean paintingComplete = false;
    private boolean witnessComplete = false;
    private boolean runeComplete = false;
    private boolean evidenceCollected = false;
    private boolean judgeReady = false;
    private boolean gameEnded = false;

    private String paintingSuspicion = "Unknown";
    private String witnessSuspicion = "Unknown";
    private String runeSuspicion = "Unknown";
    private String finalAccusation = "Unknown";
    private String trialName = "The Trial of the Broken Verdict";
    private String ending = "None";

    private int deductionScore = 0;

    public Game() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Choose class:");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        System.out.println("3. Rogue");
        System.out.print("> ");

        String choice = scanner.nextLine();

        String characterClass = switch (choice) {
            case "1" -> "Warrior";
            case "2" -> "Mage";
            case "3" -> "Rogue";
            default -> "Rogue";
        };

        player = new Player(name, characterClass);
        dungeon = new Dungeon();
        currentRoom = dungeon.getStartRoom();

        endingRecords.put("True Ending - Rogue Exposed", false);
        endingRecords.put("Loop Ending - Warrior Accused", false);
        endingRecords.put("Mage Ending - The Deceived", false);
        endingRecords.put("Perfect Deduction Ending", false);
        endingRecords.put("Quit", false);

        eventQueue.add("A cold whisper moves through the dungeon...");
        eventQueue.add("The walls remember an old verdict...");
        eventQueue.add("Somewhere ahead, a judge waits...");
    }

    public void start() {
        System.out.println("\n=== DUNGEON OF TRUTH ===");

        while (!gameEnded) {
            showRoom();
            showMenu();
            handleChoice(scanner.nextLine());
        }

        System.out.println("\n=== GAME OVER ===");

        GameRecord record = new GameRecord(
                player.getName(),
                player.getCharacterClass(),
                ending,
                pathHistory.size()
        );

        System.out.println(record);
    }

    private void showRoom() {
        System.out.println("\nROOM: " + currentRoom.getName());
        System.out.println(currentRoom.getDescription());
        System.out.println("Current Trial Name: " + trialName);

        if (!eventQueue.isEmpty()) {
            System.out.println("Event: " + eventQueue.poll());
        }
    }

    private void showMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Move Forward");
        System.out.println("2. Talk / Interact");
        System.out.println("3. Inspect Room");
        System.out.println("4. Status");
        System.out.println("5. Undo");
        System.out.println("6. Ending Records");
        System.out.println("7. Quit");
        System.out.print("> ");
    }

    private void handleChoice(String input) {
        switch (input) {
            case "1" -> moveForward();
            case "2" -> talk();
            case "3" -> inspectRoom();
            case "4" -> showStatus();
            case "5" -> undo();
            case "6" -> showEndingRecords();
            case "7" -> {
                ending = "Quit";
                endingRecords.put("Quit", true);
                gameEnded = true;
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void moveForward() {
        if (currentRoom.getName().equals("Oracle Room") && !oracleIntroComplete) {
            System.out.println("The Oracle stops you. Talk to the Oracle first.");
            return;
        }

        if (currentRoom.getName().equals("Painting Room") && !paintingComplete) {
            System.out.println("The paintings block the path. Inspect them first.");
            return;
        }

        if (currentRoom.getName().equals("Witness Hall") && !witnessComplete) {
            System.out.println("The witnesses refuse to let you pass. Inspect the room first.");
            return;
        }

        if (currentRoom.getName().equals("Rune Chamber") && !runeComplete) {
            System.out.println("The runes seal the exit. Inspect them first.");
            return;
        }

        if (currentRoom.getName().equals("Evidence Room") && !evidenceCollected) {
            System.out.println("You cannot leave without collecting the evidence.");
            return;
        }

        if (currentRoom.getConnectedRooms().isEmpty()) {
            System.out.println("No path forward.");
            return;
        }

        undoStack.push(currentRoom);
        pathHistory.add(currentRoom);
        currentRoom = currentRoom.getConnectedRooms().get(0);

        System.out.println("You move forward into " + currentRoom.getName() + ".");
    }

    private void talk() {
        if (currentRoom.getName().equals("Oracle Room")) {
            oracleOpeningScene();
        } else if (currentRoom.getName().equals("Pantheon's Court")) {
            pantheonJudgment();
        } else {
            System.out.println("There is no one here who answers you.");
        }
    }

    private void oracleOpeningScene() {
        if (oracleIntroComplete) {
            System.out.println("Oracle: Continue forward. Each chamber will test your judgment.");
            return;
        }

        System.out.println("\nOracle: A sacred verdict was stolen from the divine court.");
        System.out.println("Oracle: Without it, judgment became corrupted.");
        System.out.println("Oracle: The Warrior was imprisoned, but the Rogue moved through the same trial in shadow.");

        if (player.getCharacterClass().equalsIgnoreCase("Warrior")) {
            System.out.println("\nOracle: Since you are a Warrior, beware the Rogue.");
            System.out.println("Oracle: A Rogue could slip past guards, forge blame, and vanish.");
            System.out.println("Oracle: But hatred of shadows can blind even an honorable warrior.");
        } else if (player.getCharacterClass().equalsIgnoreCase("Rogue")) {
            System.out.println("\nOracle: Since you are a Rogue, beware the Warrior.");
            System.out.println("Oracle: The Warrior looked guilty because his blade was found near judgment.");
            System.out.println("Oracle: But obvious guilt is sometimes planted.");
        } else {
            mageTrialQuestions();
        }

        oracleIntroComplete = true;
        System.out.println("\nOracle: Go now. The paintings will test your memory first.");
    }

    private void mageTrialQuestions() {
        System.out.println("\nOracle: Mage, answer carefully. Knowledge can deceive.");

        System.out.println("\nQuestion 1: Who could enter the court vault?");
        System.out.println("1. Warrior by force");
        System.out.println("2. Rogue by stealth");
        System.out.print("> ");
        scanner.nextLine();

        System.out.println("\nQuestion 2: Which clue seems more suspicious?");
        System.out.println("1. A blade near the court");
        System.out.println("2. A missing seal");
        System.out.print("> ");
        scanner.nextLine();

        System.out.println("\nQuestion 3: Who benefits from confusion?");
        System.out.println("1. Warrior, if fear protects him");
        System.out.println("2. Rogue, if blame falls elsewhere");
        System.out.print("> ");
        scanner.nextLine();

        System.out.println("\nOracle: Both suspects can be argued against.");
        System.out.println("Oracle: Evidence matters more than cleverness.");
    }

    private void inspectRoom() {
        if (currentRoom.getName().equals("Painting Room")) {
            paintingEncounter();
        } else if (currentRoom.getName().equals("Witness Hall")) {
            witnessEncounter();
        } else if (currentRoom.getName().equals("Rune Chamber")) {
            runeEncounter();
        } else if (currentRoom.getName().equals("Evidence Room")) {
            evidenceEncounter();
        } else {
            System.out.println("There is nothing special to inspect here.");
        }
    }

    private void paintingEncounter() {
        if (paintingComplete) {
            System.out.println("You already studied the paintings.");
            return;
        }

        System.out.println("\nThe paintings glow.");
        System.out.println("Painting 1: The Warrior stands chained before divine judges.");
        System.out.println("Painting 2: The Rogue flees through a doorway of shadow.");
        System.out.println("Painting 3: A stolen verdict seal disappears from the court altar.");

        System.out.println("\nWho appears most guilty from the paintings?");
        System.out.println("1. Warrior");
        System.out.println("2. Rogue");
        System.out.print("> ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            paintingSuspicion = "Warrior";
            trialName = "The Trial of the Chained Warrior";
            System.out.println("You suspect the Warrior, but the scene feels staged.");
        } else if (choice.equals("2")) {
            paintingSuspicion = "Rogue";
            deductionScore++;
            trialName = "The Trial of the Shadowed Rogue";
            System.out.println("You suspect the Rogue. The shadows feel deliberate.");
        } else {
            System.out.println("The paintings reject your unclear answer.");
            return;
        }

        paintingComplete = true;
        System.out.println("The paintings fade. The next chamber opens.");
    }

    private void witnessEncounter() {
        if (witnessComplete) {
            System.out.println("You already questioned the witnesses.");
            return;
        }

        System.out.println("\nSilent witnesses appear as stone faces in the walls.");
        System.out.println("One says: 'I heard armor near the court.'");
        System.out.println("Another says: 'I saw a shadow near the seal.'");

        System.out.println("\nWhich witness do you believe?");
        System.out.println("1. The one who heard armor");
        System.out.println("2. The one who saw a shadow");
        System.out.print("> ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            witnessSuspicion = "Warrior";
            trialName = "The Trial of Iron Footsteps";
            System.out.println("You believe the armor witness. Suspicion turns toward the Warrior.");
        } else if (choice.equals("2")) {
            witnessSuspicion = "Rogue";
            deductionScore++;
            trialName = "The Trial of the Moving Shadow";
            System.out.println("You believe the shadow witness. Suspicion turns toward the Rogue.");
        } else {
            System.out.println("The witnesses fall silent.");
            return;
        }

        witnessComplete = true;
        System.out.println("The Witness Hall opens its far door.");
    }

    private void runeEncounter() {
        if (runeComplete) {
            System.out.println("You already interpreted the runes.");
            return;
        }

        System.out.println("\nAncient runes burn into the floor.");
        System.out.println("Rune 1: 'The loudest guilt is often planted.'");
        System.out.println("Rune 2: 'The quietest hand may move the court.'");

        System.out.println("\nWhich rune do you trust?");
        System.out.println("1. The loud guilt rune");
        System.out.println("2. The quiet hand rune");
        System.out.print("> ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            runeSuspicion = "Warrior";
            trialName = "The Trial of Planted Guilt";
            System.out.println("You focus on the loud evidence surrounding the Warrior.");
        } else if (choice.equals("2")) {
            runeSuspicion = "Rogue";
            deductionScore++;
            trialName = "The Trial of the Quiet Hand";
            System.out.println("You focus on hidden manipulation pointing to the Rogue.");
        } else {
            System.out.println("The runes dim without accepting your answer.");
            return;
        }

        runeComplete = true;
        System.out.println("The Rune Chamber releases its seal.");
    }

    private void evidenceEncounter() {
        if (!paintingComplete || !witnessComplete || !runeComplete) {
            System.out.println("The evidence refuses to reveal itself until earlier chambers are complete.");
            return;
        }

        if (evidenceCollected) {
            System.out.println("You already collected the evidence.");
            return;
        }

        System.out.println("\nYou find two physical pieces of evidence:");

        for (int i = 0; i < evidenceItems.length; i++) {
            System.out.println((i + 1) + ". " + evidenceItems[i].getName());
        }

        System.out.println("\nYou collect both so Pantheon can judge your final answer.");
        inventory.put(evidenceItems[0].getName(), evidenceItems[0]);
        inventory.put(evidenceItems[1].getName(), evidenceItems[1]);

        evidenceCollected = true;
        judgeReady = true;

        System.out.println("You obtained:");
        System.out.println("- Trial Blade");
        System.out.println("- Stolen Verdict Seal");
        System.out.println("The path to Pantheon's Court opens.");
    }

    private void pantheonJudgment() {
        if (!judgeReady) {
            System.out.println("Pantheon: You come without enough proof. Return when the trial is complete.");
            return;
        }

        System.out.println("\nPantheon waits above a broken judgment altar.");
        System.out.println("Pantheon: I have watched your choices.");
        System.out.println("Painting suspicion: " + paintingSuspicion);
        System.out.println("Witness suspicion: " + witnessSuspicion);
        System.out.println("Rune suspicion: " + runeSuspicion);
        System.out.println("Current trial name: " + trialName);
        System.out.println("Deduction score: " + deductionScore + " / 3");

        System.out.println("\nPantheon: Choose the final evidence you trust.");
        System.out.println("1. Trial Blade - accuse the Warrior");
        System.out.println("2. Stolen Verdict Seal - accuse the Rogue");
        System.out.print("> ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            finalAccusation = "Warrior";
            trialName = "The Trial of the Condemned Warrior";

            if (player.getCharacterClass().equalsIgnoreCase("Mage")) {
                mageDeceivedEnding();
            } else {
                warriorEnding();
            }

        } else if (choice.equals("2")) {
            finalAccusation = "Rogue";
            trialName = "The Trial of the Stolen Verdict";

            if (deductionScore == 3) {
                perfectDeductionEnding();
            } else {
                rogueEnding();
            }

        } else {
            System.out.println("Pantheon: Your answer is unclear. Speak plainly.");
        }
    }

    private void warriorEnding() {
        ending = "Loop Ending - Warrior Accused";
        endingRecords.put(ending, true);

        System.out.println("\n=== LOOP ENDING ===");
        System.out.println("Pantheon: You present the Trial Blade.");
        System.out.println("The Warrior is condemned again.");
        System.out.println("The dungeon stretches downward.");
        System.out.println("By Floor 106, the halls still have not ended.");

        gameEnded = true;
    }

    private void rogueEnding() {
        ending = "True Ending - Rogue Exposed";
        endingRecords.put(ending, true);

        System.out.println("\n=== TRUE ENDING ===");
        System.out.println("Pantheon: You present the Stolen Verdict Seal.");
        System.out.println("The Rogue stole the seal and framed the Warrior.");
        System.out.println("The Warrior was imprisoned for a crime he did not commit.");
        System.out.println("The dungeon opens, and the truth is restored.");

        gameEnded = true;
    }

    private void mageDeceivedEnding() {
        ending = "Mage Ending - The Deceived";
        endingRecords.put(ending, true);

        System.out.println("\n=== MAGE ENDING: THE DECEIVED ===");
        System.out.println("Pantheon: You relied on logic, yet chose incorrectly.");
        System.out.println("Every conclusion you made begins to contradict itself.");
        System.out.println("You are not trapped in a loop...");
        System.out.println("You are trapped in a false truth.");

        gameEnded = true;
    }

    private void perfectDeductionEnding() {
        ending = "Perfect Deduction Ending";
        endingRecords.put(ending, true);

        System.out.println("\n=== PERFECT DEDUCTION ENDING ===");
        System.out.println("Pantheon: Every answer you gave pointed toward the same hidden truth.");
        System.out.println("The paintings, witnesses, runes, and seal all agree.");
        System.out.println("The Rogue stole the divine verdict and framed the Warrior.");
        System.out.println("The Warrior is freed, and the dungeon collapses into light.");
        System.out.println("You solved the trial without being deceived.");

        gameEnded = true;
    }

    private void showStatus() {
        System.out.println("\nPlayer: " + player.getName());
        System.out.println("Class: " + player.getCharacterClass());
        System.out.println("Current Trial Name: " + trialName);
        System.out.println("Painting choice: " + paintingSuspicion);
        System.out.println("Witness choice: " + witnessSuspicion);
        System.out.println("Rune choice: " + runeSuspicion);
        System.out.println("Final accusation: " + finalAccusation);
        System.out.println("Deduction score: " + deductionScore + " / 3");
        System.out.println("Inventory: " + inventory.keySet());
        System.out.println("Rooms visited: " + pathHistory.size());
        System.out.println("Recursive path depth: " + recursivePathDepth(0));
    }

    private void showEndingRecords() {
        int found = 0;

        System.out.println("\n=== ENDING RECORDS ===");

        for (String endingName : endingRecords.keySet()) {
            boolean discovered = endingRecords.get(endingName);

            if (discovered) {
                found++;
                System.out.println("- " + endingName + " [FOUND]");
            } else {
                System.out.println("- " + endingName + " [LOCKED]");
            }
        }

        System.out.println("\nEndings found: " + found + " / " + endingRecords.size());
    }

    private void undo() {
        if (!undoStack.isEmpty()) {
            currentRoom = undoStack.pop();
            System.out.println("You step back to " + currentRoom.getName() + ".");
        } else {
            System.out.println("No previous room.");
        }
    }

    private int recursivePathDepth(int index) {
        if (index >= pathHistory.size()) {
            return 0;
        }
        return 1 + recursivePathDepth(index + 1);
    }
}