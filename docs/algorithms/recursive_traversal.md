# Recursive Room Traversal Algorithm

## Description
This algorithm explores all rooms connected to a starting room in the dungeon, marking each room as visited to prevent infinite loops.

## Pseudocode
```
function exploreRooms(currentRoom, visitedList):
    if currentRoom is in visitedList:
        return
    
    add currentRoom to visitedList
    mark currentRoom as visited
    
    for each connectedRoom in currentRoom.connections:
        exploreRooms(connectedRoom, visitedList)
```

## Flowchart
```
Start
  |
  v
Is currentRoom visited?
  | Yes -> Return
  | No
  v
Add to visited list
Mark as visited
  |
  v
For each connected room:
  |
  v
Call exploreRooms recursively
  |
  v
All connected rooms processed?
  | Yes -> Return
  | No -> Continue loop
```

## Time Complexity
O(V + E) where V = number of rooms, E = number of connections