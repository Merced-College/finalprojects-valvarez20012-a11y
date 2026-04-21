[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23693900)
# cpsc39-finalProjects

Upload your final project to this github repo.

# Dungeon Adventure Game

## Project Description
A text-based adventure game where players explore a dungeon, navigate through interconnected rooms using a linked list structure, collect items stored in a hash map inventory, and solve simple puzzles. The game demonstrates various data structures and algorithms in Java.

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

## Class Overview
- **Main**: Entry point, starts the game
- **Game**: Handles user input, commands, and game logic
- **Player**: Manages character stats, inventory, and movement
- **Room**: Represents dungeon rooms with items and connections
- **Dungeon**: Creates and connects rooms, explores via recursion
- **Item**: Defines collectible items with name, description, value
- **NPC**: Represents non-player characters with hints

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

## Contributors
- Victor Alvarez (Individual Project)

## Development Notes
This project was developed over multiple commits to demonstrate progressive development. All code is original with proper documentation and comments.
