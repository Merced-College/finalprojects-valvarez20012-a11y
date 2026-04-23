# Bubble Sort Algorithm

## Description
Sorts the player's inventory items by their value in ascending order.

## Pseudocode
```
function bubbleSort(items):
    n = length of items
    for i from 0 to n-2:
        for j from 0 to n-i-2:
            if items[j].value > items[j+1].value:
                swap items[j] and items[j+1]
```

## Flowchart
```
Start
  |
  v
n = items.length
  |
  v
i = 0
  |
  v
i < n-1?
  | No -> End
  | Yes
  v
j = 0
  |
  v
j < n-i-1?
  | No -> i++
  | Yes
  v
items[j].value > items[j+1].value?
  | Yes -> Swap
  | No
  v
j++
  |
  ^ Loop back
```

## Time Complexity
O(n²) worst case, where n = number of items