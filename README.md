# Game Class

This Java class represents a game board for a Minesweeper-like game. It includes functionality for initializing the game board, handling user clicks, and determining the numbers of neighboring bombs.

## Constructors

### Game(int width, int height, boolean fixedRandom, int seed)

- Initializes a new game with the specified width and height.
- Uses two nested for-loops to initialize the board and cells with underscores and GUICell objects respectively.
- Randomly places bombs on the board using `BombRandomizer.placeBombs()` method.
- Initializes GUI for the game.

### Game(LinkedGrid<Character> board)

- Initializes a new game using an existing board.
- Sets the width and height of the game board.
- Initializes the GUI for the game.

## Public Methods

### int getWidth()

- Returns the width of the game board.

### int getHeight()

- Returns the height of the game board.

### LinkedGrid<GUICell> getCells()

- Returns the grid of GUI cells.

### void determineNumbers()

- Traverses the game board and calculates the number of neighboring bombs for each cell using `checkForBombs()` method.

### int processClick(int col, int row)

- Processes the user click on a cell at the specified coordinates.
- Returns an integer value based on the result of the click:
  - `-10`: Game over.
  - `-1`: Bomb clicked, game over.
  - `0`: Cell already revealed.
  - `1`: Cell revealed successfully.

## Private Methods

### void checkForBombs(int col, int row)

- Counts the number of bombs surrounding a single cell on the game board.
- Modifies neighboring cell's number or reveals bomb cells accordingly.

### int recClear(int col, int row)

- Recursively clears neighboring cells when a cell with no neighboring bombs is clicked.
- Reveals cells and returns the count of cleared cells.

