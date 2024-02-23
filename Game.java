import java.awt.Color;

public class Game {
	public LinkedGrid<Character> board;
	private LinkedGrid<GUICell> cells;
	public static int width;
	public static int height;
	boolean isPlaying;
	private GUI gui;

	/*
	 * Constructor uses two pairs of nested for-loops to initialize the board &
	 * cells with underscores & GUICell objects respectively.
	 */
	public Game(int width, int height, boolean fixedRandom, int seed) {
		Game.width = width;
		Game.height = height;
		board = new LinkedGrid<Character>(width, height);
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				board.setElement(w, h, '_');
			}
		}
		cells = new LinkedGrid<GUICell>(width, height);
		for (int w = 0; w < width; w++) {
			for (int h = 0; h < height; h++) {
				cells.setElement(w, h, new GUICell(w, h));
			}
		}
		BombRandomizer.placeBombs(board, fixedRandom, seed);
		determineNumbers();
		isPlaying = true;
		gui = new GUI(this, cells);

	}

	/*
	 * Second constructor uses board parameter to initialize the board instance
	 * variable then uses board.width & board.height to set width & height
	 * respectively. The rest of the method is unchanged from the first constructor.
	 */
	public Game(LinkedGrid<Character> board) {
		this.board = board;
		Game.width = board.width;
		Game.height = board.height;
		cells = new LinkedGrid<GUICell>(board.width, board.height);
		for (int w = 0; w < cells.width; w++) {
			for (int h = 0; h < cells.height; h++) {
				cells.setElement(w, h, new GUICell(w, h));
			}
		}
		determineNumbers();
		isPlaying = true;
		gui = new GUI(this, cells);
	}

	/* getWidth() returns the board's width. */
	public int getWidth() {
		return board.width;
	}

	/* getHeight() returns the board's height. */
	public int getHeight() {
		return board.height;
	}

	public LinkedGrid<GUICell> getCells() {
		return cells;
	}

	/*
	 * checkForBombs() is a private helper method that counts the number of bombs
	 * surrounding a single cell in the board grid. If the cell in the board grid
	 * contains a bomb, then its corresponding cell in the cells grid is given the
	 * number -1. Otherwise, the method counts the number of bombs in the 3x3
	 * sub-grid around the cell (including the cell itself) & counts the number of
	 * bombs surrounding the cell. The integers top, bottom, left, & right are start
	 * and end points for the nested for-loops. The series of if statements modify
	 * the integers as necessary to ensure that they remain within the bounds of the
	 * board.
	 */
	private void checkForBombs(int col, int row) {
		int top = row - 1;
		int bottom = row + 2;
		int left = col - 1;
		int right = col + 2;
		if (top < 0)
			top = 0;
		if (bottom > board.height)
			bottom = board.height;
		if (left < 0)
			left = 0;
		if (right > board.width)
			right = board.width;
		if (board.getElement(col, row) == 'x') {
			cells.getElement(col, row).setNumber(-1);
		} else {
			int bombs = 0;
			for (int w = left; w < right; w++) {
				for (int h = top; h < bottom; h++) {
					if (board.getElement(w, h) == 'x') {
						bombs++;
					}
				}
			}
			cells.getElement(col, row).setNumber(bombs);
		}

	}

	/*
	 * determineNumbers() traverses the board grid and invokes the checkForBombs()
	 * method on each cell.
	 */
	public void determineNumbers() {
		for (int w = 0; w < board.width; w++) {
			for (int h = 0; h < board.height; h++) {
				checkForBombs(w, h);
			}
		}
	}

	/*
	 * processClick() uses a series of if, else if, & else statements to return
	 * integers exactly as described in the assignment 4 hand-out.
	 */
	public int processClick(int col, int row) {
		if (!isPlaying)
			return -10;
		if (cells.getElement(col, row).getNumber() == -1) {

			cells.getElement(col, row).setBackground(Color.red);
			cells.getElement(col, row).reveal();
			isPlaying = false;/*
								 * sets isPlaying to false, because the game has ended, since a bomb was
								 * clicked.
								 */
			return -1;

		} else if (cells.getElement(col, row).getNumber() == 0) {
			return recClear(col, row);
		} else {
			if (cells.getElement(col, row).isRevealed()) {
				return 0;
			} else {
				cells.getElement(col, row).reveal();
				cells.getElement(col, row).setBackground(Color.white);
				return 1;
			}
		}
	}

	/*
	 * recClear() translates the pseudo-code from the assignment 4 hand-out into
	 * Java code.
	 */
	private int recClear(int col, int row) {
		if (col >= cells.width || row >= cells.height || col < 0 || row < 0)
			return 0;
		if (cells.getElement(col, row).isRevealed())
			return 0;
		if (cells.getElement(col, row).getNumber() == -1)
			return 0;
		else if (cells.getElement(col, row).getNumber() > 0) {
			cells.getElement(col, row).reveal();
			if (!(gui == null))
				cells.getElement(col, row).setBackground(Color.white);
			return 1;
		} else {
			int result;
			cells.getElement(col, row).reveal();
			if (!(gui == null))
				cells.getElement(col, row).setBackground(Color.white);
			result = 1;
			result += recClear(col - 1, row - 1) + recClear(col, row - 1) + recClear(col + 1, row - 1)
					+ recClear(col - 1, row) + recClear(col + 1, row) + recClear(col - 1, row + 1)
					+ recClear(col, row + 1) + recClear(col + 1, row + 1);
			return result;
		}
	}

}
