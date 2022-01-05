package com.company;
import java.util.*;

// This Code is NOT property of Hassan Elsawy and is used ONLY for comparison's sake <-------------------------
// Link for source : https://stackoverflow.com/questions/1790360/median-of-medians-in-java

public class externalMOM {
    private void externalMOM(){

    }
    /**
     * Returns median of list in linear time.
     *
     * @param list list to search, which may be reordered on return
     * @return median of array in linear time.
     */
    public static int getMedian(ArrayList<Integer> list) {
        int s = list.size();
        if (s < 1)
            throw new IllegalArgumentException();
        int pos = select(list, 0, s, s / 2);
        return list.get(pos);
    }

    /**
     * Returns position of k'th largest element of sub-list.
     *
     * @param list list to search, whose sub-list may be shuffled before
     *            returning
     * @param lo first element of sub-list in list
     * @param hi just after last element of sub-list in list
     * @param k
     * @return position of k'th largest element of (possibly shuffled) sub-list.
     */
    public static int select(ArrayList<Integer> list, int lo, int hi, int k) {
        if (lo >= hi || k < 0 || lo + k >= hi)
            throw new IllegalArgumentException();
        if (hi - lo < 10) {
            Collections.sort(list.subList(lo, hi));
            return lo + k;
        }
        int s = hi - lo;
        int np = s / 5; // Number of partitions
        for (int i = 0; i < np; i++) {
            // For each partition, move its median to front of our sublist
            int lo2 = lo + i * 5;
            int hi2 = (i + 1 == np) ? hi : (lo2 + 5);
            int pos = select(list, lo2, hi2, 2);
            Collections.swap(list, pos, lo + i);
        }

        // Partition medians were moved to front, so we can recurse without making another list.
        int pos = select(list, lo, lo + np, np / 2);

        // Re-partition list to [<pivot][pivot][>pivot]
        int m = triage(list, lo, hi, pos);
        int cmp = lo + k - m;
        if (cmp > 0)
            return select(list, m + 1, hi, k - (m - lo) - 1);
        else if (cmp < 0)
            return select(list, lo, m, k);
        return lo + k;
    }

    /**
     * Partition sub-list into 3 parts [<pivot][pivot][>pivot].
     *
     * @param list
     * @param lo
     * @param hi
     * @param pos input position of pivot value
     * @return output position of pivot value
     */
    private static int triage(ArrayList<Integer> list, int lo, int hi, int pos) {
        Integer pivot = list.get(pos);
        int lo3 = lo;
        int hi3 = hi;
        while (lo3 < hi3) {
            Integer e = list.get(lo3);
            int cmp = e.compareTo(pivot);
            if (cmp < 0)
                lo3++;
            else if (cmp > 0)
                Collections.swap(list, lo3, --hi3);
            else {
                while (hi3 > lo3 + 1) {
                    assert (list.get(lo3).compareTo(pivot) == 0);
                    e = list.get(--hi3);
                    cmp = e.compareTo(pivot);
                    if (cmp <= 0) {
                        if (lo3 + 1 == hi3) {
                            Collections.swap(list, lo3, lo3 + 1);
                            lo3++;
                            break;
                        }
                        Collections.swap(list, lo3, lo3 + 1);
                        assert (list.get(lo3 + 1).compareTo(pivot) == 0);
                        Collections.swap(list, lo3, hi3);
                        lo3++;
                        hi3++;
                    }
                }
                break;
            }
        }
        assert (list.get(lo3).compareTo(pivot) == 0);
        return lo3;
    }
}
