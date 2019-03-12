import java.util.*;
import java.io.*;

/**
 * Main processing class for creating the maze
 * and solving the maze. Reads in the structure
 * of the maze and create a maze accordingly.
 *
 * @author Allen Pan
 */
public class Maze {
    private File dataFile;
    private MazeCell maze[][];
    private CellStack<MazeCell> cellStack = new CellStack<>();
    private int row;
    private int col;

    /**
     * Default constructor to set all values
     * to zero or null
     */
    public Maze() {
        dataFile = null;
        row = 0;
        col = 0;
    }

    /**
     * Reads in file and call methods to create the maze
     * and solve the maze.
     *
     * @param dataFile file containing the maze
     * @throws IOException when file not found
     * @throws MazeMissingStartOrEndPosition when start or end is not found in the maze
     */
    public Maze (File dataFile) throws IOException {
        this.dataFile = dataFile;
        loadMaze();
        if (checkForStart() && checkForEnd()) {
            addNeighbour();
            printMaze();
            solveMaze();
        }
        else {
            throw new MazeMissingStartOrEndPosition(checkForStart(), checkForEnd());
        }

    }

    /**
     * Creates the maze array based on readings of the file.
     * First line of the file should indicate how many rows and cols the maze should have.
     * Assign different cell types to each index of the maze array.
     *
     * @throws IOException when data file is not found
     * @throws UnknownMazeCharacterException when the maze contains unknown character
     * @throws InputMismatchException when the first line does not contain row/col number
     */
    private void loadMaze() throws IOException, UnknownMazeCharacterException, InputMismatchException
    {
        BufferedReader inp;
        String line;
        int totalLines = 0;
        inp = new BufferedReader(new FileReader(dataFile));
        line = inp.readLine();
        System.out.println("Reading maze from: " + dataFile.getName());

        //Tokenize the first line of file to get row and column

        StringTokenizer lineTk = new StringTokenizer(line);
        row = Integer.parseInt(lineTk.nextToken());
        col = Integer.parseInt(lineTk.nextToken());

        maze = new MazeCell[row+2][col+20];

        for (int r = 1; r < row +1; r++) {
            line = inp.readLine();
            totalLines++;
            lineTk = new StringTokenizer(line);
            for (int c = 1; c < col+1; c++) {
                char token = lineTk.nextToken().charAt(0);
                switch (token) {
                    case ' ':
                        maze[r][c] = new MazeCell(MazeCell.CellType.CURRENT_PATH);
                        break;
                    case 'W':
                        maze[r][c] = new MazeCell(MazeCell.CellType.WALL);
                        break;
                    case 'M':
                        maze[r][c] = new MazeCell(MazeCell.CellType.START);
                        cellStack.push(maze[r][c]);
                        break;
                    case 'C':
                        maze[r][c] = new MazeCell(MazeCell.CellType.END);
                        maze[r][c].setEnd(true);
                        break;
                    case 'O':
                        maze[r][c] = new MazeCell(MazeCell.CellType.OPEN);
                        break;
                    case 'S':
                        maze[r][c] = new MazeCell(MazeCell.CellType.END_FOUND);
                        break;
                    case 'X':
                        maze[r][c] = new MazeCell(MazeCell.CellType.REJECTED);
                        break;
                    default:
                        throw new UnknownMazeCharacterException(token);

                }

            }
        }
        if (totalLines < row || totalLines < col) {
            throw new InvalidMazeRowOrColumns(totalLines);
        }
    }

    /**
     * Add neighbour cell to each maze character
     * apply offset value to suit the case where each
     * maze cell has 6 neighbours.
     */
    private void addNeighbour() {
        int offset = 0;
        for (int r = 1; r < row+1; r++) {
            for (int c = 1; c < col+1; c++) {
                offset = 1 - r%2;

                maze[r][c].setNghbCell(maze[r - 1][c + offset], 0);
                maze[r][c].setNghbCell(maze[r][c + 1], 1);
                maze[r][c].setNghbCell(maze[r + 1][c + offset], 2);
                maze[r][c].setNghbCell(maze[r + 1][c - 1 + offset], 3);
                maze[r][c].setNghbCell(maze[r][c - 1], 4);
                maze[r][c].setNghbCell(maze[r - 1][c - 1 + offset], 5);
            }
        }
    }

    /**
     * print the maze to the screen with hexagon formation
     */
    private void printMaze() {
        int off = 0;
        for (int i = 1; i < row+1; i++) {
            for (int j = 1; j < col+1; j++) {
                off = i%2;
                if (off == 0) {
                    System.out.print(" " + maze[i][j].toString());
                }
                else {
                    System.out.print(maze[i][j].toString()+ " ");
                }


            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * check to see if the maze contains a start position
     *
     * @return startFound if there's a start to the maze
     */
    private boolean checkForStart() {
        boolean startFound = false;

        for (int i = 1; i < row+1; i++) {
            for (int j = 1; j < col + 1; j++) {
                if (maze[i][j].toString().equals("M")) {
                    startFound = true;
                }
            }
        }


        return startFound;
    }

    /**
     * check to see if the maze contains an end position
     *
     * @return endFound if there's an end to the maze
     */
    private boolean checkForEnd() {
        boolean endFound = false;

        for (int i = 1; i < row+1; i++) {
            for (int j = 1; j < col + 1; j++) {
                if (maze[i][j].getEnd()) {
                    endFound = true;
                }
            }
        }

        return endFound;
    }

    /**
     * Solve the maze based on the user request i.e press enter to continue.
     * Look through the maze to see if the current cell is the end, if not
     * call helper method to look for available neighbours.
     *
     * @return foundCheese true if end has been found, false if not
     */
    private boolean solveMaze() {
        int totalSteps = 0;
        boolean foundCheese = false;
        MazeCell currentCell;



        while (!foundCheese && !cellStack.isEmpty() ) {
            currentCell = cellStack.peek();
            System.out.println("Press \"ENTER\" to continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            if (currentCell.getEnd()) {
                currentCell.setCellType("END_FOUND");
                foundCheese = true;
            }
            else {
                findNext(currentCell);
                totalSteps++;
            }
            printMaze();
        }
        showTotalSteps(totalSteps);

        return false;
    }

    /**
     * Checks the neighbours of the current cell to find an open path.
     * If the open path continues to have open path on the way keep going,
     * if the open path has C as one of the neighbours the maze is solved
     *
     * @param current current mze cell that we are using to check for neighbours
     */
    private void findNext(MazeCell current) {
        int maxNeighbours = 6;
        MazeCell neighbourCell;
        boolean availNeighbour = false;

        for (int i = 0; i < maxNeighbours; i++) {
            try {
                neighbourCell = current.getNghbCell(i);
                if(!neighbourCell.getCellType().toString().equals("W") && !neighbourCell.getCellType().toString().equals("M") &&
                        !neighbourCell.getCellType().toString().equals("X") && !neighbourCell.getCellType().toString().equals(" ")) {
                    cellStack.push(neighbourCell);

                    if (neighbourCell.getCellType().toString().equals("C")) {
                        neighbourCell.setCellType("END_FOUND");
                    }

                    if (!neighbourCell.getCellType().toString().equals("C")) {
                        neighbourCell.setCellType("CURRENT_PATH");
                    }
                    availNeighbour = true;
                    i = maxNeighbours;
                }

            }catch (NullPointerException n) {
                //Catch statement which does nothing to the exception
            }
        }

        if (!availNeighbour) {
            current = cellStack.pop();
            current.setCellType("REJECTED");
        }
    }

    /**
     * prints the total steps the program has taken to solve the maze
     *
     * @param totalSteps steps taken to solve the maze
     */
    private void showTotalSteps(int totalSteps) {
        System.out.println("Maze has been solved in " +totalSteps+ " steps.");
    }


}
