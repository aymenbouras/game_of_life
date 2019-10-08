package model;

import java.util.*;

import static model.CellState.ALIVE;
import static model.CellState.DEAD;


/**
 * {@link Grid} instances represent the grid in <i>The Game of Life</i>.
 */
public class Grid implements Iterable<Cell> {

    private final int numberOfRows;
    private final int numberOfColumns;
    private final Cell[][] cells;

    /**
     * Creates a new {@code Grid} instance given the number of rows and columns.
     *
     * @param numberOfRows    the number of rows
     * @param numberOfColumns the number of columns
     * @throws IllegalArgumentException if {@code numberOfRows} or {@code numberOfColumns} are
     *                                  less than or equal to 0
     */
    public Grid(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.cells = createCells();
    }

    /**
     * Returns an iterator over the cells in this {@code Grid}.
     *
     * @return an iterator over the cells in this {@code Grid}
     */

    @Override
    public Iterator<Cell> iterator() {
        return new GridIterator(this);
    }

    private Cell[][] createCells() {
        Cell[][] cells = new Cell[getNumberOfRows()][getNumberOfColumns()];
        for (int rowIndex = 0; rowIndex < getNumberOfRows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < getNumberOfColumns(); columnIndex++) {
                cells[rowIndex][columnIndex] = new Cell();
            }
        }
        return cells;
    }

    /**
     * Returns the {@link Cell} at the given index.
     *
     * <p>Note that the index is wrapped around so that a {@link Cell} is always returned.
     *
     * @param rowIndex    the row index of the {@link Cell}
     * @param columnIndex the column index of the {@link Cell}
     * @return the {@link Cell} at the given row and column index
     */
    public Cell getCell(int rowIndex, int columnIndex) {
        return cells[getWrappedRowIndex(rowIndex)][getWrappedColumnIndex(columnIndex)];
    }

    private int getWrappedRowIndex(int rowIndex) {
        return (rowIndex + getNumberOfRows()) % getNumberOfRows();
    }

    private int getWrappedColumnIndex(int columnIndex) {
        return (columnIndex + getNumberOfColumns()) % getNumberOfColumns();
    }

    /**
     * Returns the number of rows in this {@code Grid}.
     *
     * @return the number of rows in this {@code Grid}
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Returns the number of columns in this {@code Grid}.
     *
     * @return the number of columns in this {@code Grid}
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }


    // TODO: Écrire une version correcte de cette méthode.
    private List<Cell> getNeighbours(int rowIndex, int columnIndex) {

        List<Cell> neighbours = new ArrayList<>();


        neighbours.add(getCell(rowIndex + 1, columnIndex - 1));
        neighbours.add(getCell(rowIndex, columnIndex - 1));
        neighbours.add(getCell(rowIndex - 1, columnIndex - 1));

        neighbours.add(getCell(rowIndex + 1, columnIndex));
        neighbours.add(getCell(rowIndex - 1, columnIndex));


        neighbours.add(getCell(rowIndex + 1, columnIndex + 1));
        neighbours.add(getCell(rowIndex, columnIndex + 1));
        neighbours.add(getCell(rowIndex - 1, columnIndex + 1));

        return neighbours;
    }


    // TODO: Écrire une version correcte de cette méthode.
    private int countAliveNeighbours(int rowIndex, int columnIndex) {

        int count = 0;
        for (Cell cell : getNeighbours(rowIndex, columnIndex)) {
            if (cell.isAlive())
                count = count + 1;
            else
                return count;
        }
        return count;
    }

    // TODO: Écrire une version correcte de cette méthode.
    private CellState calculateNextState(int rowIndex, int columnIndex) {
        Cell cell = getCell(rowIndex, columnIndex);

        if (!cell.isAlive()){
          if (countAliveNeighbours(rowIndex, columnIndex) == 3)
            return ALIVE;
        }
         if (cell.isAlive())
                 if (countAliveNeighbours(rowIndex, columnIndex) == 2)
                     return ALIVE;
                  if (countAliveNeighbours(rowIndex, columnIndex) == 3)
            return ALIVE;
        else
        return DEAD;

    }


    // TODO: Écrire une version correcte de cette méthode.
    private CellState[][] calculateNextStates() {
        CellState[][] nextCellState = new CellState[getNumberOfRows()][getNumberOfColumns()];
        for (int r = 0; r < getNumberOfRows(); r++) {
            for (int c = 0; c < getNumberOfColumns(); c++) {

                nextCellState[r][c] = calculateNextState(r, c);
            }

        }
        return nextCellState;
    }

    // TODO: Écrire une version correcte de cette méthode.
    private void updateStates(CellState[][] nextState) {

        for (int r = 0; r < getNumberOfRows(); r = r + 1) {
            for (int c = 0; c < getNumberOfColumns(); c = c + 1) {
                Cell cell = getCell(r, c);
                if (calculateNextState(r, c) == ALIVE && cell.getState() == DEAD)
                    cell.setState(ALIVE);
                else if (calculateNextState(r, c) == DEAD && cell.getState() == ALIVE)
                    cell.setState(DEAD);
            }

        }

    }

    /**
     * Transitions all {@link Cell}s in this {@code Grid} to the next generation.
     *
     * <p>The following rules are applied:
     * <ul>
     * <li>Any live {@link Cell} with fewer than two live neighbours dies, i.e. underpopulation.</li>
     * <li>Any live {@link Cell} with two or three live neighbours lives on to the next
     * generation.</li>
     * <li>Any live {@link Cell} with more than three live neighbours dies, i.e. overpopulation.</li>
     * <li>Any dead {@link Cell} with exactly three live neighbours becomes a live cell, i.e.
     * reproduction.</li>
     * </ul>
     */
    // TODO: Écrire une version correcte de cette méthode.
    void updateToNextGeneration() {
        updateStates(calculateNextStates());

    }

    /**
     * Sets all {@link Cell}s in this {@code Grid} as dead.
     */
    // TODO: Écrire une version correcte de cette méthode.
    void clear() {
        for (int r = 0; r < getNumberOfRows(); r = r + 1) {
            for (int c = 0; c < getNumberOfColumns(); c = c + 1) {
                Cell cell = getCell(r, c);
                cell.setState(DEAD);


            }
        }
    }

    /**
     * Goes through each {@link Cell} in this {@code Grid} and randomly sets its state as ALIVE or DEAD.
     *
     * @param random {@link Random} instance used to decide if each {@link Cell} is ALIVE or DEAD.
     * @throws NullPointerException if {@code random} is {@code null}.
     */
    // TODO: Écrire une version correcte de cette méthode.
    void randomGeneration(Random random) {
        for (int r = 0; r < getNumberOfRows(); r = r + 1) {
            for (int c = 0; c < getNumberOfColumns(); c = c + 1) {
                Cell cell = getCell(r, c);

                if (random.nextBoolean() == true) {
                    cell.setState(ALIVE);
                } else cell.setState(DEAD);

            }
        }
    }
}
