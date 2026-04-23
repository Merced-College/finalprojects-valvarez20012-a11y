# Linear Search Algorithm

## Description
Searches for an item by name in the player's inventory.

## Pseudocode
```
function searchInventory(itemName):
    for each item in inventory.values():
        if item.name equals itemName (case insensitive):
            return true
    return false
```

## Flowchart
```
Start
  |
  v
For each item in inventory:
  |
  v
Item name matches search?
  | Yes -> Return true
  | No
  v
Next item
  |
  v
All items checked?
  | Yes -> Return false
  | No -> Continue
```

## Time Complexity
O(n) where n = number of items in inventory