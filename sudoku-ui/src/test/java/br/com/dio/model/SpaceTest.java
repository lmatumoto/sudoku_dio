package br.com.dio.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

    private Space space;

    @Before
    public void setUp() {
        space = new Space(5, false);
    }

    @Test
    public void testSpaceCreationWithExpectedValue() {
        assertEquals(5, space.getExpected());
        assertFalse(space.isFixed());
        assertNull(space.getActual());
    }

    @Test
    public void testFixedSpaceInitializesWithExpectedValue() {
        Space fixedSpace = new Space(7, true);
        assertEquals(7, fixedSpace.getExpected());
        assertTrue(fixedSpace.isFixed());
        assertEquals(Integer.valueOf(7), fixedSpace.getActual());
    }

    @Test
    public void testSetActualValue() {
        space.setActual(5);
        assertEquals(Integer.valueOf(5), space.getActual());
    }

    @Test
    public void testSetActualValueMultipleTimes() {
        space.setActual(3);
        assertEquals(Integer.valueOf(3), space.getActual());
        
        space.setActual(8);
        assertEquals(Integer.valueOf(8), space.getActual());
    }

    @Test
    public void testCannotSetActualValueOnFixedSpace() {
        Space fixedSpace = new Space(9, true);
        fixedSpace.setActual(1);
        
        // Value should remain as expected (9)
        assertEquals(Integer.valueOf(9), fixedSpace.getActual());
    }

    @Test
    public void testClearSpaceRemovesActualValue() {
        space.setActual(5);
        assertEquals(Integer.valueOf(5), space.getActual());
        
        space.clearSpace();
        assertNull(space.getActual());
    }

    @Test
    public void testClearSpaceOnFixedSpaceDoesNothing() {
        Space fixedSpace = new Space(4, true);
        fixedSpace.clearSpace();
        
        assertEquals(Integer.valueOf(4), fixedSpace.getActual());
    }

    @Test
    public void testSetActualToNull() {
        space.setActual(3);
        space.setActual(null);
        assertNull(space.getActual());
    }

    @Test
    public void testSpaceWithDifferentValues() {
        for (int i = 1; i <= 9; i++) {
            Space testSpace = new Space(i, false);
            assertEquals(i, testSpace.getExpected());
            testSpace.setActual(i);
            assertEquals(Integer.valueOf(i), testSpace.getActual());
        }
    }
}
