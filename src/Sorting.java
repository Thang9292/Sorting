import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Implementation of various sorting algorithms.
 *
 * @author Thang Huynh
 * @version 1.0
 *
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        //The Exceptions
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array entered or the comparator was null");
        }

        //Insertion Sort
        for (int n = 1; n <= arr.length; n++) {
            int i = n - 1;
            while (i != 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                T item = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = item;
                i--;
            }
        }
    }




    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        //The Exceptions
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array entered or the comparator was null");
        }

        //Cocktail Sort
        boolean swapsMade = true;
        int startInd = 0;
        int endInd = arr.length - 1;
        boolean a1;
        int a = arr.length - 1; //Represents the index of where the last swap was made
        int b = 0; //Represents the index of where the last swap was made

        while (swapsMade) {
            swapsMade = false;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T item = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = item;
                    swapsMade = true;
                    a = i;
                }
            }
            endInd = a;

            if (swapsMade) {
                swapsMade = false;
                for (int i = endInd; i > startInd; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T item = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = item;
                        swapsMade = true;
                        b = i;
                    }
                }
                startInd = b;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Creates more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, we will put the
     * extra data on the right side.
     *
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        //The Exceptions
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array entered or the comparator was null");
        }

        ///////////////////////////////
        //        Merge Sort         //
        //////////////////////////////
        if (arr.length <= 1) {
            return;
        }

        int length = arr.length;
        int midIndex = length / 2;
        T[] left = (T[]) new Object[midIndex];
        T[] right = (T[]) new Object[length - midIndex];

        //Copy Left Array (From 0 to midIndex - 1)
        for (int i = 0; i <= midIndex - 1; i++) {
            left[i] = arr[i];
        }
        //Copy Right Array (From midIndex to Length - 1)
        for (int i = midIndex; i <= length - 1; i++) {
            right[i - midIndex] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int i = 0;
        int j = 0;
        while (i != left.length && j != right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * Makes an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        //The Exception
        if (arr == null) {
            throw new IllegalArgumentException("The array entered was null");
        }

        //LSD Radix Sort
        LinkedList<Integer>[] buckets = new LinkedList[19];
        //An Array of LinkedLists

        //Finding the longest number in the array
        int largest = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > largest) {
                largest = arr[i];
            }
        }

        //Digits of Largest Number
        int digitOfLargest = 0;
        while (largest != 0) {
            largest = largest / 10;
            digitOfLargest++;
        }

        //The Sort
        for (int iterations = 0; iterations < digitOfLargest; iterations++) {
            //For Calculating Exact Digits
            int x = 1;
            int y = iterations;

            while (y != 0) {
                x = x * 10;
                y--;
            }

            for (int i = 0; i < arr.length; i++) {
                int curDigit = arr[i] / x % 10;
                if (buckets[curDigit + 9] == null) {
                    buckets[curDigit + 9] = new LinkedList<>();
                }
                buckets[curDigit + 9].add(arr[i]);
            }
            int idx = 0;
            for (int bucket = 0; bucket < 19; bucket++) {
                if (buckets[bucket] != null) {
                    while (!buckets[bucket].isEmpty()) {
                        arr[idx] = buckets[bucket].removeFirst();
                        idx++;
                    }
                }
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Uses the provided random object to select our pivots. 
     *
     * If recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        //The Exceptions
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array entered or the comparator was null");
        }

        rQuick(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * A recursive helper method for quicksort.
     *
     * @param <T>           data type to sort
     * @param arr           the array that must be sorted after the method runs
     * @param start         the starting index
     * @param end           the ending index
     * @param comparator    the Comparator used to compare the data in arr
     * @param rand          the Random object used to select pivots
     */
    private static <T> void rQuick(T[] arr, int start, int end, Comparator<T> comparator,
                                   Random rand) {
        if (end - start < 1) {
            return;
        }

        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIdx];
        T item3 = arr[start];
        arr[pivotIdx] = item3;
        arr[start] = pivotVal;

        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (i < j) {
                T item = arr[i];
                arr[i] = arr[j];
                arr[j] = item;
                i++;
                j--;
            }
        }
        T item2 = arr[start];
        arr[start] = arr[j];
        arr[j] = item2;
        rQuick(arr, start, j - 1, comparator, rand);
        rQuick(arr, j + 1, end, comparator, rand);
    }
}
