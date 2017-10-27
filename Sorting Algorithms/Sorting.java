import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author John Blasco jblasco6
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array can not be null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Input comparator can not "
                    + "be null.");
        }

        int compIndex;
        T tempSwap;

        for (int i = 1; i < arr.length; i++) {
            compIndex = i;
            while (compIndex > 0
                    && comparator.compare(arr[compIndex - 1],
                    arr[compIndex]) > 0) {
                //swap
                tempSwap = arr[compIndex - 1];
                arr[compIndex - 1] = arr[compIndex];
                arr[compIndex] = tempSwap;

                --compIndex;
            }
        }

    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null or k is not in the range of 1 to arr.length
     * @param <T> data type to sort
     * @param k the index + 1 (due to 0-indexing) to retrieve the data 
     * from as if the array were sorted; the 'k' in "kth select"
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array can not be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator can not be null.");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random can not be null.");
        }
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("K is out of the array range.");
        }

        return kthSelectHelper(k, arr, comparator, rand, 0,
                arr.length - 1);
    }

    /**
     *  Helper method for Kth selection
     * @param k the index + 1 (due to 0-indexing) to retrieve the data
     * from as if the array were sorted; the 'k' in "kth select"
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param leftBound The left most bounds of the array to sort/search
     * @param rightBound the right most bounds of the array to sort/search
     * @param <T> data type to sort
     * @return the kth smallest element
     */
    private static <T> T kthSelectHelper(int k,
                                         T[] arr, Comparator<T> comparator,
                                         Random rand, int leftBound,
                                         int rightBound) {
        if (leftBound == rightBound) {
            return arr[leftBound];
        }

        int pivot = rand.nextInt(rightBound + 1 - leftBound) + leftBound;
        T tempSwap;

        // swap pivot with the first element
        tempSwap = arr[leftBound];
        arr[leftBound] = arr[pivot];
        arr[pivot] = tempSwap;


        int i = leftBound + 1;
        int j = rightBound;
        while (j > i) {
            while (i < rightBound && j > i && comparator.compare(arr[i],
                    arr[leftBound]) < 0) {
                ++i;
            }
            while (j >= leftBound && j > i && comparator.compare(arr[j],
                    arr[leftBound]) > 0) {
                --j;
            }
            if (j > i) {
                tempSwap = arr[i];
                arr[i] = arr[j];
                arr[j] = tempSwap;
            }
        }

        if (rightBound - leftBound == 1) {
            if (comparator.compare(arr[j], arr[leftBound]) < 0) {
                // swap
                tempSwap = arr[leftBound];
                arr[leftBound] = arr[rightBound];
                arr[rightBound] = tempSwap;
                --j;
            }
        } else if (i == rightBound) {
            tempSwap = arr[leftBound];
            arr[leftBound] = arr[rightBound];
            arr[rightBound] = tempSwap;
        } else {
            tempSwap = arr[leftBound];
            arr[leftBound] = arr[--j];
            arr[j] = tempSwap;
        }


        if (j == k - 1) {
            return arr[j];
        } else if (j < k - 1) {
            // return recursive call to right side of the array
            return kthSelectHelper(k, arr, comparator, rand, j + 1, rightBound);
        } else {
            // return recursive call to left side of the array
            return kthSelectHelper(k, arr, comparator, rand, leftBound, j - 1);
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * 
     * When necessary due to an odd number of elements, the 
     * excess element MUST go on the right side!
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    @SuppressWarnings("unchecked")
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array can not be null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Input comparator can not "
                    + "be null.");
        }

        if (arr.length <= 1) {
            return;
        }

        if (arr.length > 1) {
            if (arr.length == 2) {
                // compare and swap
                T tempSwap;
                if (comparator.compare(arr[0], arr[1]) > 0) {
                    tempSwap = arr[0];
                    arr[0] = arr[1];
                    arr[1] = tempSwap;
                }
            } else {
                int rightSize = arr.length / 2;
                if (arr.length % 2 == 1) {
                    ++rightSize;
                }

                // split array
                T[] arrLeft = (T[]) new Object[arr.length / 2];
                T[] arrRight = (T[]) new Object[rightSize];
                for (int i = 0; i < arr.length / 2; i++) {
                    arrLeft[i] = arr[i];
                }
                for (int i = 0; i < rightSize; i++) {
                    arrRight[i] = arr[i + (arr.length / 2)];
                }

                // recursive call
                mergeSort(arrLeft, comparator);
                mergeSort(arrRight, comparator);

                // merge
                int index = 0;
                int lIndex = 0;
                int rIndex = 0;
                while (index < arr.length) {
                    if (lIndex >= arrLeft.length) {
                        arr[index++] = arrRight[rIndex++];
                    } else if (rIndex >= arrRight.length) {
                        arr[index++] = arrLeft[lIndex++];
                    } else if (comparator.compare(arrLeft[lIndex],
                           arrRight[rIndex]) > 0) {
                        arr[index++] = arrRight[rIndex++];
                    } else {
                        arr[index++] = arrLeft[lIndex++];
                    }
                }

            }
        } else {
            return;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Input array can not be null");
        }

        if (arr.length <= 1) {
            return arr;
        }

        // Get the max absolute value
        int longestValue = Math.abs(arr[0]);
        for (int i = 1; i < arr.length; ++i) {
            if (arr[i] == Integer.MIN_VALUE) {
                longestValue = Integer.MAX_VALUE;
                i = arr.length;
            } else if (Math.abs(arr[i]) > longestValue) {
                longestValue = Math.abs(arr[i]);
            }
        }

        // get length of max absolute value
        int numDigits = 1;
        int divisor = 10;
        while (longestValue / divisor != 0) {
            divisor *= 10;
            ++numDigits;
        }

        ArrayList<Queue<Integer>> listTemp = new ArrayList<>(9);
        int index;
        for (int i = 0; i < numDigits; ++i) {
            // Populate arrayList with empty queues
            for (int j = 0; j < 19; ++j) {
                listTemp.add(new LinkedList<>());
            }
            // add to arrayList
            for (int j = 0; j < arr.length; ++j) {
                index = (Math.abs(arr[j]) / pow(10, i)) % 10;

                if (arr[j] == Integer.MIN_VALUE) {
                    listTemp.get(0).add(arr[j]);
                } else if (arr[j] < 0) {
                    listTemp.get(9 - index).add(arr[j]);
                } else {
                    listTemp.get(9 + index).add(arr[j]);
                }
            }
            // remove from arrayList back into array
            int count = 0;

            for (int j = 0; j < listTemp.size(); ++j) {
                if (listTemp.get(j) != null) {
                    while (!listTemp.get(j).isEmpty()) {
                        arr[count++] = listTemp.get(j).remove();
                    }
                }
            }

            listTemp.clear();
        }

        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }
}