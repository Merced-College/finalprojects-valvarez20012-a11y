[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23693900)
# cpsc39-finalProjects

Upload your final project to this github repo.

# Dungeon Adventure Game

## Project Description
A mystical text-based adventure game where players explore divine chambers, collect sacred artifacts, and seek enlightenment through the Oracle's Pillars and Pantheon. The game demonstrates various data structures and algorithms in Java, featuring character classes, NPC interactions, and atmospheric storytelling.

## How to Run the Program
1. Navigate to the `/src` directory
2. Compile the Java files: `javac *.java`
3. Run the main class: `java Main`

## Features
- Room navigation using linked list for room connections
- Inventory management with HashMap
- Item collection and usage
- Simple scoring system
- Undo functionality with Stack
- Recursive room exploration algorithm
- Character class selection (Warrior/Mage/Rogue)
- NPC interactions with item-based hints
- Brief room descriptions and atmospheric NPC dialogue
- **NEW**: Available actions list displayed in every room for better player guidance
- **NEW**: Story-consistent descriptions throughout all rooms, items, and NPCs
- **NEW**: Enhanced storytelling with class-specific Oracle interactions
- **NEW**: Perception-based narrative reveals for Mage characters

## Class Overview
- **Main**: Entry point, starts the game
- **Game**: Handles user input, commands, and game logic
- **Player**: Manages character stats, inventory, and movement
- **Room**: Represents dungeon rooms with items and connections
- **Dungeon**: Creates and connects rooms, explores via recursion
- **Item**: Defines collectible items with name, description, value
- **NPC**: Represents non-player characters with hints and storytelling capabilities

## Data Structures Used
- **LinkedList**: For room sequences and player movement history
- **HashMap**: For player inventory (item name -> Item object)
- **ArrayList**: For storing items in rooms and player stats
- **Stack**: For undo moves functionality
- **Queue**: For action/event queue (optional)

## Algorithms Used
- **Recursive Room Traversal**: For exploring connected rooms and finding paths
- **Sorting Algorithm**: Bubble sort for sorting inventory items by value
- **Searching Algorithm**: Linear search for finding items in inventory
- **Simple Recursive Function**: For calculating player score based on items collected
- **NEW**: Perception Check Algorithm: Random probability system for Mage class narrative reveals

## Recent Updates

### April 23, 2026 - Enhanced Game Progression and Story Consistency
- **Added available actions list**: Every room now displays a comprehensive list of possible actions to guide player progression
- **Story-consistent descriptions**: All rooms, items, and NPCs now follow the narrative established in the Oracle's Chamber about the condemned warrior
- **Enhanced room descriptions**: Each room references the warrior's journey (Oracle's Chamber → Pillars of Wisdom → Pantheon of Gods → Labyrinth of Trials → Gateway to Eternity)
- **Updated item lore**: Items now connect to the warrior's story (Divine Amulet from trial, Crystal Shard from verdict, etc.)
- **Improved NPC interactions**: Sage and Oracle descriptions maintain narrative coherence

### April 23, 2026 - Storytelling Features Implementation
- **Fixed compilation error**: Resolved syntax error in NPC.java (extra closing brace)
- **Added class-specific storytelling**: Oracle provides different narrative reveals based on player class
- **Implemented perception checks**: Mage characters have 60% chance to see full story through magical perception
- **Enhanced NPC system**: Added storytelling NPCs with introduction, partial, and full story capabilities
- **Updated dungeon generation**: Integrated storytelling elements into world creation

## Contributors
- Victor Alvarez (Individual Project)

## Development Notes
This project was developed over multiple commits to demonstrate progressive development. All code is original with proper documentation and comments. The game features a cohesive narrative about a condemned warrior who crossed realms, with all elements (rooms, items, NPCs) maintaining story consistency throughout the player's journey.
