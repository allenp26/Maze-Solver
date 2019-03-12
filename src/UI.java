import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

/**
 * This class asks the user for the input file of the maze, which
 * handles the main interaction with user.
 *
 * @author Allen Pan
 *
 */
public class UI {

    /**
     * This method starts the program by asking the user to
     * input the file name for the maze. If nothing is entered
     * default input is maze0.txt.
     *
     * Catches different exceptions and notify the user to correct
     * the file or enter a different file.
     *
     * @throws IOException if file path not found or not accessible
     */
    public static void main(String args[]) throws IOException  {

        //define necessary variables
        String fileName = "";
        Scanner in = new Scanner(System.in);
        boolean fileCheck = false;
        Maze maze = new Maze();


        do {
            try{
                System.out.print("Please enter the data filename: ");
                fileName = in.nextLine();

                if (fileName.isEmpty()) {
                    fileName = "maze0.txt";
                }

                maze = new Maze(new File(fileName));
                fileCheck = true;

            }
            catch (FileNotFoundException e) {
                if (fileName.equalsIgnoreCase("q")) {
                    break;
                }
                System.out.println("Not able to open file " + fileName);
                System.out.println("Please try again or enter \"Q\" to exit the program \n");
            }
            catch (UnknownMazeCharacterException c) {
                System.out.println("This maze encountered a character during Maze building that it cannot recognize:\n\t" + c.getMessage());
            }
            catch (InvalidMazeRowOrColumns rowOrColumns) {
                System.out.println("The maze contains less rows or columns than expected:\n\t" + rowOrColumns.getMessage());
            }
            catch (InputMismatchException p) {
                System.out.println("The maze doesn't specify rows or columns for the maze.");
            }
            catch (MazeMissingStartOrEndPosition m) {
                System.out.println();
            }
        }while (!fileCheck);
    }
}
