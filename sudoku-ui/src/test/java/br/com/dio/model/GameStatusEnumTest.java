package br.com.dio.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameStatusEnumTest {

    @Test
    public void testNonStartedLabel() {
        assertEquals("não iniciado", GameStatusEnum.NON_STARTED.getLabel());
    }

    @Test
    public void testIncompleteLabel() {
        assertEquals("incompleto", GameStatusEnum.INCOMPLETE.getLabel());
    }

    @Test
    public void testCompleteLabel() {
        assertEquals("completo", GameStatusEnum.COMPLETE.getLabel());
    }

    @Test
    public void testEnumValues() {
        GameStatusEnum[] values = GameStatusEnum.values();
        assertEquals(3, values.length);
    }

    @Test
    public void testEnumValuesContainAllStatuses() {
        GameStatusEnum[] values = GameStatusEnum.values();
        assertTrue(contains(values, GameStatusEnum.NON_STARTED));
        assertTrue(contains(values, GameStatusEnum.INCOMPLETE));
        assertTrue(contains(values, GameStatusEnum.COMPLETE));
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(GameStatusEnum.NON_STARTED, GameStatusEnum.valueOf("NON_STARTED"));
        assertEquals(GameStatusEnum.INCOMPLETE, GameStatusEnum.valueOf("INCOMPLETE"));
        assertEquals(GameStatusEnum.COMPLETE, GameStatusEnum.valueOf("COMPLETE"));
    }

    private boolean contains(GameStatusEnum[] values, GameStatusEnum target) {
        for (GameStatusEnum value : values) {
            if (value.equals(target)) {
                return true;
            }
        }
        return false;
    }
}
