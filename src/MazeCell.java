/**
 * This class stores the necessary cell types
 * that are crucial for the maze to be made and solved.
 *
 * @author Allen Pan
 */
public class MazeCell {
    //private char type;
    private boolean end;
    private MazeCell[] nghbCell = new MazeCell[6];
    private CellType cellType;

    enum CellType{
        WALL("W"), START("M"), END("C"), OPEN("O"), CURRENT_PATH(" "), END_FOUND("S"), REJECTED("X");

        private String cellType;

        //default constructor to set the cell type
        CellType(String cellType)
        {
            this.cellType = cellType;
        }

        @Override
        public String toString() {
            return cellType;
        }
    }

    /**
     * default constructor to set all values to null
     */
    public MazeCell() {
        end = false;
        for (MazeCell mc : nghbCell) {
            mc = null;
        }
        cellType = CellType.WALL;

    }

    /**
     * constructor to set the cell type
     *
     * @param cellType cell's type
     */
    public MazeCell(CellType cellType) {
        this.cellType = cellType;
    }

    //setters
    public void setCellType(String cellType) {
        this.cellType = CellType.valueOf(cellType);
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public void setNghbCell(MazeCell neighbour, int i) {
        this.nghbCell[i] = neighbour;
    }

    //getters
    public CellType getCellType() {
        return cellType;
    }

    public MazeCell getNghbCell(int i) {
        return nghbCell[i];
    }

    public boolean getEnd() {
        return end;
    }

    @Override
    public String toString() {
        String info;
        info = ""+cellType;
        return info;
    }
}
