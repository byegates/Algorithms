import org.junit.jupiter.api.Test;
import resources.SudokuBoards;

import static org.junit.jupiter.api.Assertions.*;
import static resources.SudokuBoards.*;

class SudokuTest {

    private boolean core(char[][] board) {
        Sudoku sudoku = new Sudoku();
        sudoku.solveSudoku(board);
        return sudoku.isValidSudoku(board);
    }

    @Test
    void Maker() {
        assertTrue(core(MAKER));
    }

    @Test
    void Leet1() {
        assertTrue(core(Leet1));
    }

    @Test
    void Leet2() {
        assertTrue(core(Leet2));
    }

    @Test
    void Empty() {
        assertTrue(core(EMPTY));
    }

    @Test
    void Easy1() {
        assertTrue(core(Easy1));
    }

    @Test
    void Medium1() {
        assertTrue(core(Medium1));
    }

    @Test
    void Hard1() {
        assertTrue(core(Hard1));
    }

    @Test
    void Expert1() {
        assertTrue(core(Expert1));
    }

    @Test
    void Evil1() {
        assertTrue(core(Evil1));
    }

    @Test
    void Easy2() {
        assertTrue(core(Easy2));
    }

    @Test
    void Medium2() {
        assertTrue(core(Medium2));
    }

    @Test
    void Hard2() {
        assertTrue(core(Hard2));
    }

    @Test
    void Expert2() {
        assertTrue(core(Expert2));
    }

    @Test
    void Evil2() {
        assertTrue(core(Evil2));
    }
    
    // below are Sudoku validation method tests
    @Test
    void Case1Maker() {assertTrue(new Sudoku().isValidSudoku(SudokuBoards.Results.MAKER));}

    @Test
    void LeetBoard() {assertTrue(new Sudoku().isValidSudoku(Leet1));}

    @Test
    void EmptyBoard() {assertTrue(new Sudoku().isValidSudoku(EMPTY));}

    @Test
    void Case4LeetSample() {assertTrue(new Sudoku().isValidSudoku(SudokuBoards.Results.Leet1));}

    @Test
    void Case5EMPTY_RESULT() {assertTrue(new Sudoku().isValidSudoku(SudokuBoards.Results.EMPTY));}

    @Test
    void Case6Easy1Result() {assertTrue(new Sudoku().isValidSudoku(SudokuBoards.Results.Easy1));}

}