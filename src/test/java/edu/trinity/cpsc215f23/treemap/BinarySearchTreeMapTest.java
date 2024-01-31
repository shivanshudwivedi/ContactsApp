package edu.trinity.cpsc215f23.treemap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for BinarySearchTreeMap.
 */
class BinarySearchTreeMapTest {

    /**
     * Test data.
     */
    private final String[] samples;
    /**
     * Test data.
     */
    private final int[] values;

    private final BinarySearchTreeMap<String, Integer> searchables = new BinarySearchTreeMap<>();

    BinarySearchTreeMapTest() {
        samples = new String[]{"gamma", "phi", "beta", "alpha", "delta", "lambda", "epsilon", "zeta"};
        values = new Random().ints(samples.length, 0, 999).toArray();

        for (int i = 0; i < samples.length; i++) {
            searchables.put(samples[i], values[i]);
        }
    }

    @Test
    public void itemsPresent() {
        assertEquals(searchables.size(), 8, String.format("All items (including sentinels) have been added to tree as count is %d.", searchables.size()));
        String phiToken = "(" + samples[1] + ", " + values[1] + ")";
        assertTrue(searchables.toString().contains(phiToken), String.format("Text output contains token: %s", phiToken));
    }

    @Test
    public void printEntries() {
        String allEntriesDump = searchables.toString();
        System.out.format("All Entries: %n%s%n", allEntriesDump);
        assertTrue(allEntriesDump.length() > 100, String.format("The text output of the tree has an expected length: %d.", allEntriesDump.length()));
    }


    @Test
    public void verifyGetPutRemove() {
        for (int i = 0; i < samples.length; i++) {
            assertEquals((int) searchables.get(samples[i]), values[i], String.format("The key '%s' has the value '%s'.",
                    samples[i], values[i]));
        }
        searchables.put("life", 42);
        assertEquals(42, (int) searchables.get("life"), String.format("The key 'life' has the value %s.", searchables.get("life")));

        String lifeToken = "(life, 42)";
        assertTrue(searchables.toString().contains(lifeToken), String.format("Before remove collection contains token: '%s'.", lifeToken));
        searchables.remove("life");
        assertFalse(searchables.toString().contains(lifeToken), String.format("After remove collection does not contain token: '%s'.", lifeToken));

        assertNull(searchables.get(lifeToken), String.format("Search for '%s' after removal was null.", lifeToken));
        assertNull(searchables.get(""), "Test 3: Search for blank key after removal was null.");
    }

    @Test
    public void verifyValues() {
        for (Integer value : searchables.values()) {
            assertTrue(Arrays.stream(values).anyMatch(i -> i == value), String.format("Verify value(), the value '%d' was expected.", value));
        }
    }

    @Test
    public void verifyKeys() {
        // Test 5: Print and verify all keys using the method keySet().
        for (String key : searchables.keySet()) {
            assertTrue(Arrays.asList(samples).contains(key), String.format("Verify keySet(), the key '%s' was expected.", key));
        }
    }
}


