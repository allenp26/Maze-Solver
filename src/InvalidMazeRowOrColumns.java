/**
 * This exception is thrown by the maze not having enough rows
 * or columns to create a usable maze
 *
 * @author Allen Pan
 */
public class InvalidMazeRowOrColumns extends RuntimeException {

    /**
     * tell the user the total number of rows or columns the file has
     *
     * @param i number of lines in the file
     */
    public InvalidMazeRowOrColumns (int i) {
        super("Total rows or columns: " + i);
    }
}
