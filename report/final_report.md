# Final Project Report: Dungeon Adventure Game

**Student Name:** Victor Alvarez  
**Date:** April 23, 2026  
**Course:** CPSC 39  

## Program Overview

The Dungeon Adventure Game is a text-based adventure game written in Java. Players explore a dungeon by navigating through interconnected rooms, collecting items, and ultimately reaching the exit. The game demonstrates the use of various data structures and algorithms as required for the course.

The program is useful for entertainment and educational purposes, showing how data structures can be applied in game development. It provides an interactive experience where players make decisions that affect their progress and score.

## Algorithms

### 1. Recursive Room Traversal
**Purpose:** Explores all connected rooms from a starting point to map the dungeon.  
**How it works:** The function takes a current room and a list of visited rooms. It marks the current room as visited and recursively calls itself for each unvisited connected room.  
**Big-O Complexity:** O(V + E), where V is the number of rooms (vertices) and E is the number of connections (edges).  
**Usage:** Used in the Dungeon class to initialize room exploration and could be extended for pathfinding.

### 2. Bubble Sort
**Purpose:** Sorts the player's inventory items by their value in ascending order.  
**How it works:** Compares adjacent items and swaps them if they are in the wrong order, repeating until no swaps are needed.  
**Big-O Complexity:** O(n²) in the worst case, where n is the number of items.  
**Usage:** Called when displaying the inventory to show items in order of value.

### 3. Linear Search
**Purpose:** Searches for a specific item in the player's inventory.  
**How it works:** Iterates through each item in the inventory HashMap values and checks if the name matches.  
**Big-O Complexity:** O(n), where n is the number of items in inventory.  
**Usage:** Used internally in inventory management, such as checking if a player has a specific item.

## Data Structures

### 1. LinkedList
**What it stores:** Rooms and their connections in the dungeon.  
**Why chosen:** Allows dynamic addition/removal of rooms and efficient traversal of connections. Better than arrays for this graph-like structure.  
**How used:** In Room class for connected rooms, in Dungeon class for the list of all rooms.

### 2. HashMap
**What it stores:** Player's inventory items, with item name as key and Item object as value.  
**Why chosen:** Provides fast lookup, insertion, and deletion by key. Ideal for inventory where items are accessed by name.  
**How used:** In Player class for managing collected items.

### 3. ArrayList
**What it stores:** Items present in each room.  
**Why chosen:** Dynamic array that can grow as needed, easy to add/remove items.  
**How used:** In Room class to hold items that can be collected.

### 4. Stack
**What it stores:** History of player's movements (rooms visited).  
**Why chosen:** LIFO structure perfect for undo functionality.  
**How used:** In Player class to track movement history for undo moves.

## Development Reflection

### Problem Encountered and Solved
One problem I encountered was managing the room connections properly. Initially, I had issues with bidirectional connections, but I solved it by ensuring that when adding a connection from room A to room B, I also add from B to A where appropriate.

### Design Improvement Discovered
I discovered that using a HashMap for inventory made item management much more efficient than searching through a list each time. This improved both code readability and performance.

### Future Improvements
For version 2, I would add:
- Save/load game functionality using file I/O
- More complex puzzles requiring specific items
- Multiple dungeon levels
- Enemy encounters with combat mechanics

## Conclusion

This project successfully demonstrates the required data structures (LinkedList, HashMap, ArrayList, Stack) and algorithms (recursive traversal, bubble sort, linear search). The game is fully functional, well-documented, and provides an engaging user experience. All code is original and properly commented.