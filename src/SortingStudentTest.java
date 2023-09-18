import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

/**
 * This is a basic set of unit tests for Sorting.
 */
public class SortingStudentTest {

    private static final int TIMEOUT = 200;
    private People[] P;
    private People[] PByName;
    private ComparatorPlus<People> comp;

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Alvin
                index 1: Lisa
                index 2: Sera
                index 3: Tahsin
                index 4: Hannah
                index 5: Avery
                index 6: Wendy
                index 7: Sheza
                index 8: Hanrui
                index 9: Carolyn
         */

        /*
            Sorted Names:
                index 0: Alvin
                index 1: Avery
                index 2: Carolyn
                index 3: Hannah
                index 4: Hanrui
                index 5: Lisa
                index 6: Sera
                index 7: Sheza
                index 8: Tahsin
                index 9: Wendy
         */

        P = new People[10];
        P[0] = new People("Alvin"); //0
        P[1] = new People("Lisa"); //5
        P[2] = new People("Sera"); //6
        P[3] = new People("Tahsin"); //8
        P[4] = new People("Hannah"); //3
        P[5] = new People("Avery"); //1
        P[6] = new People("Wendy"); //9
        P[7] = new People("Sheza"); //7
        P[8] = new People("Hanrui"); //4
        P[9] = new People("Carolyn"); //2
        PByName = new People[10];
        PByName[0] = P[0];
        PByName[1] = P[5];
        PByName[2] = P[9];
        PByName[3] = P[4];
        PByName[4] = P[8];
        PByName[5] = P[1];
        PByName[6] = P[2];
        PByName[7] = P[7];
        PByName[8] = P[3];
        PByName[9] = P[6];

        comp = People.getNameComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(P, comp);
        assertArrayEquals(PByName, P);
        System.out.println(comp.getCount());
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 30 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(P, comp);
        assertArrayEquals(PByName, P);
        System.out.println(comp.getCount());
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 33 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(P, comp);
        assertArrayEquals(PByName, P);
        System.out.println(comp.getCount());
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 24 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Sorting.quickSort(P, comp, new Random(234));
        assertArrayEquals(PByName, P);
        System.out.println(comp.getCount());
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 30 && comp.getCount() != 0);
    }

    /**
     * Class for testing proper sorting.
     */
    private static class People {
        private String name;

        /**
         * Create a person.
         *
         * @param name name of the person
         */
        public People(String name) {
            this.name = name;
        }

        /**
         * Get the name of the person.
         *
         * @return name of person
         */
        public String getName() {
            return name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the persons
         */
        public static ComparatorPlus<People> getNameComparator() {
            return new ComparatorPlus<People>() {
                @Override
                public int compare(People ta1,
                                   People ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof People
                && ((People) other).name.equals(this.name);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}