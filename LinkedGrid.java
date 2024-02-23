import java.util.Arrays;

public class LinkedGrid<T> {
	int width;
	int height;
	LinearNode<T>[] grid;

	/*
	 * Constructor uses outer for-loop to fill the grid array with LinearNode
	 * objects that will act as the 'front' of a linked list. The inner for-loop
	 * creates a linked list propagating downwards out of the array.
	 */
	public LinkedGrid(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new LinearNode[width];
		for (int w = 0; w < width; w++) {
			LinearNode<T> node = new LinearNode<T>();
			grid[w] = node;
			for (int h = 0; h < height; h++) {
				node.setNext(new LinearNode<T>());
				node = node.getNext();
			}
		}
	}

	/*
	 * setElement() uses the col parameter to access the 'front' of the linked list
	 * containing the target, then traverses the list with the aid of a temporary
	 * variable & for-loop until it arrives at the target. Sets the node with the
	 * element that got passed in through the parameter "data".
	 */
	public void setElement(int col, int row, T data) throws LinkedListException {
		if (col >= width || row >= height || col < 0 || row < 0) {
			throw new LinkedListException("");
		}
		LinearNode<T> temp = grid[col];
		for (int i = 0; i < row; i++) {
			temp = temp.getNext();
		}
		temp.setElement(data);
	}

	/*
	 * getElement() uses the same algorithm as the method above to find the target,
	 * then it retrieves the data stored in the target.
	 */
	public T getElement(int col, int row) {
		if (col >= width || row >= height || col < 0 || row < 0) {
			throw new LinkedListException("");
		}
		LinearNode<T> temp = grid[col];
		for (int i = 0; i < row; i++) {
			temp = temp.getNext();
		}

		return temp.getElement();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/*
	 * toString() creates an array of Strings where each index stores a string
	 * representation of one row of the grid. The rows array is initialized with
	 * empty strings. The grid is traversed like in setElement() & getElement(), the
	 * string representation of each cell is added to its corresponding index in the
	 * rows array. The last for-loop is used to concatenate the elements of the rows
	 * array.
	 */
	public String toString() {
		String text = "";
		String[] rows = new String[height];
		Arrays.fill(rows, "");
		for (int w = 0; w < width; w++) {
			LinearNode<T> temp = grid[w];
			for (int r = 0; r < rows.length; r++) {
				rows[r] += temp.getElement() + "  ";
				temp = temp.getNext();
			}
		}
		for (int r = 0; r < rows.length; r++) {
			text += rows[r] + "\n";
		}
		return text;
	}
}
