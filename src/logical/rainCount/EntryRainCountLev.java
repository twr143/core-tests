package logical.rainCount;

import static logical.rainCount.DataGenerator.concurrArrayFillFixedFP;
import static logical.rainCount.DataGenerator.nCores;

/**
 * Created by ilya on 04.12.2016.
 */
public class EntryRainCountLev {

    static long puddleVolume(long[] pound) {
        long startTime = System.currentTimeMillis();

        int poundLength = pound.length;
        long glMax = 0;
        int glMaxIndex = 0;
        for (int i = 0; i < poundLength; i++)
            if (pound[i] > glMax) {
                glMaxIndex = i;
                glMax = pound[i];
            }

        int j = 0;
        long curMax;
        long vol = 0;

        while (j < glMaxIndex) {
            while ((pound[j + 1] >= pound[j]) && (j < glMaxIndex))
                j++;

            curMax = pound[j];

            while ((j < glMaxIndex) && (pound[j] <= curMax)) {
                vol += curMax - pound[j];
                j++;
            }

        }

        j = poundLength - 1;

        while (j > glMaxIndex) {
            while ((pound[j - 1] >= pound[j]) && (j > glMaxIndex))
                j--;

            curMax = pound[j];

            while ((j > glMaxIndex) && (pound[j] <= curMax)) {
                vol += curMax - pound[j];
                j--;
            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("rain count Lev ms:" + (endTime - startTime));

        return vol;
    }

    public static void main(String[] args) {
        System.out.println("# cores:" + nCores);
        System.out.println("vol lin parall:" + puddleVolume(concurrArrayFillFixedFP(100000000, 100, 16)));

    }


}
