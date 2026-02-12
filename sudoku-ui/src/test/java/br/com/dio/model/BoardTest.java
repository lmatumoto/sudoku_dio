package br.com.dio.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private List<List<Space>> spaces;

    @Before
    public void setUp() {
        spaces = createEmptyBoard();
        board = new Board(spaces);
    }

    private List<List<Space>> createEmptyBoard() {
        List<List<Space>> result = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Space> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(new Space(i * 9 + j + 1, false));
            }
            result.add(row);
        }
        return result;
    }

    private List<List<Space>> createBoardWithFixedSpaces() {
        List<List<Space>> result = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Space> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                // Create alternating fixed and non-fixed spaces
                boolean isFixed = (i + j) % 2 == 0;
                row.add(new Space((i + j) % 9 + 1, isFixed));
            }
            result.add(row);
        }
        return result;
    }

    @Test
    public void testBoardInitialization() {
        assertNotNull(board.getSpaces());
        assertEquals(9, board.getSpaces().size());
        assertEquals(9, board.getSpaces().get(0).size());
    }

    @Test
    public void testGameStatusNonStarted() {
        assertEquals(GameStatusEnum.NON_STARTED, board.getStatus());
    }

    @Test
    public void testGameStatusIncomplete() {
        board.changeValue(0, 0, 5);
        assertEquals(GameStatusEnum.INCOMPLETE, board.getStatus());
    }

    @Test
    public void testGameStatusComplete() {
        // Fill all spaces with correct values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.changeValue(i, j, spaces.get(i).get(j).getExpected());
            }
        }
        assertEquals(GameStatusEnum.COMPLETE, board.getStatus());
    }

    @Test
    public void testHasErrorsWhenNonStarted() {
        assertFalse(board.hasErrors());
    }

    @Test
    public void testHasErrorsWithCorrectValue() {
        board.changeValue(0, 0, spaces.get(0).get(0).getExpected());
        assertFalse(board.hasErrors());
    }

    @Test
    public void testHasErrorsWithIncorrectValue() {
        int expectedValue = spaces.get(0).get(0).getExpected();
        int wrongValue = expectedValue == 1 ? 2 : 1;
        board.changeValue(0, 0, wrongValue);
        assertTrue(board.hasErrors());
    }

    @Test
    public void testChangeValueOnNonFixedSpace() {
        assertTrue(board.changeValue(0, 0, 5));
        assertEquals(Integer.valueOf(5), spaces.get(0).get(0).getActual());
    }

    @Test
    public void testChangeValueOnFixedSpace() {
        List<List<Space>> boardWithFixed = createBoardWithFixedSpaces();
        Board testBoard = new Board(boardWithFixed);
        
        // Find a fixed space
        Space fixedSpace = boardWithFixed.get(0).get(0);
        if (fixedSpace.isFixed()) {
            assertFalse(testBoard.changeValue(0, 0, 99));
        }
    }

    @Test
    public void testClearValue() {
        board.changeValue(0, 0, 5);
        assertTrue(board.clearValue(0, 0));
        assertNull(spaces.get(0).get(0).getActual());
    }

    @Test
    public void testClearValueOnFixedSpace() {
        List<List<Space>> boardWithFixed = createBoardWithFixedSpaces();
        Board testBoard = new Board(boardWithFixed);
        
        Space fixedSpace = boardWithFixed.get(0).get(0);
        if (fixedSpace.isFixed()) {
            assertFalse(testBoard.clearValue(0, 0));
            assertEquals(fixedSpace.getExpected(), fixedSpace.getActual().intValue());
        }
    }

    @Test
    public void testResetClearsAllValues() {
        // Fill some values
        board.changeValue(0, 0, 1);
        board.changeValue(1, 1, 2);
        board.changeValue(2, 2, 3);
        
        board.reset();
        
        // Check all non-fixed spaces are cleared
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Space space = spaces.get(i).get(j);
                if (!space.isFixed()) {
                    assertNull(space.getActual());
                }
            }
        }
    }

    @Test
    public void testGameIsFinishedWithCorrectSolution() {
        // Fill all spaces with correct values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.changeValue(i, j, spaces.get(i).get(j).getExpected());
            }
        }
        assertTrue(board.gameIsFinished());
    }

    @Test
    public void testGameIsNotFinishedWithIncompleteBoard() {
        board.changeValue(0, 0, spaces.get(0).get(0).getExpected());
        assertFalse(board.gameIsFinished());
    }

    @Test
    public void testGameIsNotFinishedWithErrors() {
        // Fill all spaces with incorrect values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int wrongValue = spaces.get(i).get(j).getExpected() == 1 ? 2 : 1;
                board.changeValue(i, j, wrongValue);
            }
        }
        assertFalse(board.gameIsFinished());
    }

    @Test
    public void testGameIsNotFinishedWhenNonStarted() {
        assertFalse(board.gameIsFinished());
    }

    @Test
    public void testChangeValueMultipleTimes() {
        board.changeValue(5, 5, 3);
        assertEquals(Integer.valueOf(3), spaces.get(5).get(5).getActual());
        
        board.changeValue(5, 5, 7);
        assertEquals(Integer.valueOf(7), spaces.get(5).get(5).getActual());
    }
}
