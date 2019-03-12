/**
 * This exception is thrown by the maze not containing
 * a start position or an end position
 *
 * @author Allen Pan
 */
public class MazeMissingStartOrEndPosition extends RuntimeException {

    /**
     * Tells the user which position is missing from the maze
     *
     * @param start true if maze contains a start position
     * @param end true if maze contains an end position
     */
    public MazeMissingStartOrEndPosition(boolean start, boolean end) {
        if (!start) {
            System.out.println("Missing: Start Position");
        }
        if (!end) {
            System.out.println("Missing: End Position");
        }
    }
}
