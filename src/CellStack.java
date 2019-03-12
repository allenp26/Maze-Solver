import java.util.ArrayList;

/**
 * Custom made stack class to keep track of the current
 * cell in use
 *
 * @author Allen Pan
 */
public class CellStack<MazeCell> {
    private ArrayList<MazeCell> cellStack;
    private int size;

    /**
     * default constructor to set all value to zero or null
     */
    public CellStack() {
        cellStack = new ArrayList<>();
        size = 0;
    }

    /**
     * looks at the top of the stack and return the value
     *
     * @return the last added element in the stack
     */
    public MazeCell peek() {
        return cellStack.get(0);
    }

    /**
     * deletes the last added element from the stack
     *
     * @return the last added element in the stack
     */
    public MazeCell pop() {
        MazeCell cell = cellStack.get(0);
        cellStack.remove(0);
        size--;

        return cell;
    }

    /**
     * adds an element to the top of the stack
     *
     * @param cell the maze cell to be added to the stack
     */
    public void push(MazeCell cell) {
        cellStack.add(0, cell);
        size++;
    }

    /**
     * checks to see if the stack is empty
     *
     * @return true if stack is empty, vice versa
     */
    public boolean isEmpty() {
        if ( size > 0) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * size of the stack
     *
     * @return size of the stack
     */
    public int size() {
        return size;
    }
}
