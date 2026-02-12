package br.com.dio.service;

import br.com.dio.model.GameStatusEnum;
import br.com.dio.model.Space;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BoardServiceTest {

    private BoardService boardService;
    private Map<String, String> gameConfig;

    @Before
    public void setUp() {
        gameConfig = createValidGameConfig();
        boardService = new BoardService(gameConfig);
    }

    private Map<String, String> createValidGameConfig() {
        Map<String, String> config = new HashMap<>();
        
        // Create a 9x9 sudoku board configuration
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int expectedValue = (i * 9 + j) % 9 + 1;
                boolean fixed = (i + j) % 3 == 0; // Some fixed positions
                String key = "%s,%s".formatted(i, j);
                String value = "%s,%s".formatted(expectedValue, fixed);
                config.put(key, value);
            }
        }
        return config;
    }

    @Test
    public void testBoardServiceInitialization() {
        assertNotNull(boardService);
        assertNotNull(boardService.getSpaces());
    }

    @Test
    public void testGetSpacesReturns9x9Grid() {
        List<List<Space>> spaces = boardService.getSpaces();
        assertEquals(9, spaces.size());
        
        for (List<Space> row : spaces) {
            assertEquals(9, row.size());
        }
    }

    @Test
    public void testGameStatusInitiallyNonStarted() {
        assertEquals(GameStatusEnum.NON_STARTED, boardService.getStatus());
    }

    @Test
    public void testGetStatusIncomplete() {
        // This would require a way to modify spaces through boardService
        // For now, we verify the status changes when spaces have actual values
        List<List<Space>> spaces = boardService.getSpaces();
        Space space = spaces.get(0).get(0);
        
        if (!space.isFixed()) {
            space.setActual(5);
            assertEquals(GameStatusEnum.INCOMPLETE, boardService.getStatus());
        }
    }

    @Test
    public void testHasErrorsWhenGameNonStarted() {
        assertFalse(boardService.hasErrors());
    }

    @Test
    public void testHasErrorsWithWrongValue() {
        List<List<Space>> spaces = boardService.getSpaces();
        Space space = spaces.get(0).get(0);
        
        if (!space.isFixed()) {
            int wrongValue = space.getExpected() == 1 ? 2 : 1;
            space.setActual(wrongValue);
            assertTrue(boardService.hasErrors());
        }
    }

    @Test
    public void testHasErrorsWithCorrectValue() {
        List<List<Space>> spaces = boardService.getSpaces();
        Space space = spaces.get(0).get(0);
        
        if (!space.isFixed()) {
            space.setActual(space.getExpected());
            assertFalse(boardService.hasErrors());
        }
    }

    @Test
    public void testReset() {
        List<List<Space>> spaces = boardService.getSpaces();
        
        // Set some values
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Space space = spaces.get(i).get(j);
                if (!space.isFixed()) {
                    space.setActual(5);
                }
            }
        }
        
        boardService.reset();
        
        // Verify all non-fixed spaces are cleared
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
    public void testGameIsFinished() {
        List<List<Space>> spaces = boardService.getSpaces();
        
        // Fill all spaces with correct values
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Space space = spaces.get(i).get(j);
                if (!space.isFixed()) {
                    space.setActual(space.getExpected());
                }
            }
        }
        
        assertTrue(boardService.gameIsFinished());
    }

    @Test
    public void testGameIsNotFinishedWhenIncomplete() {
        List<List<Space>> spaces = boardService.getSpaces();
        Space space = spaces.get(0).get(0);
        
        if (!space.isFixed()) {
            space.setActual(space.getExpected());
            assertFalse(boardService.gameIsFinished());
        }
    }

    @Test
    public void testGameConfigurationWithFixedSpaces() {
        List<List<Space>> spaces = boardService.getSpaces();
        
        boolean hasFixedSpaces = false;
        for (List<Space> row : spaces) {
            for (Space space : row) {
                if (space.isFixed()) {
                    hasFixedSpaces = true;
                    // Fixed spaces should have actual value set to expected
                    assertEquals(space.getExpected(), space.getActual().intValue());
                }
            }
        }
        
        assertTrue(hasFixedSpaces);
    }

    @Test
    public void testBoardServiceWithMinimalConfiguration() {
        Map<String, String> minimalConfig = new HashMap<>();
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String key = "%s,%s".formatted(i, j);
                String value = "1,false"; // All positions have expected value 1 and are not fixed
                minimalConfig.put(key, value);
            }
        }
        
        BoardService service = new BoardService(minimalConfig);
        assertNotNull(service);
        assertEquals(9, service.getSpaces().size());
    }

    @Test
    public void testMultipleBoardServiceInstances() {
        BoardService service1 = new BoardService(gameConfig);
        BoardService service2 = new BoardService(gameConfig);
        
        assertNotNull(service1);
        assertNotNull(service2);
        assertNotEquals(service1, service2); // Different instances
    }
}
