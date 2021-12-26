package com.company;


import java.util.Random;
import static java.lang.Math.abs;

public class Main {

    static int [] randArray(int size){
        int [] array = new int[size];
        Random rd = new Random();
        for(int i = 0; i < size; i++)
            array[i] = rd.nextInt();
        return array;

    }

    static void printArrayOfInt(int[] arr, int size){
        for(int i = 0; i<size ; i++)
            System.out.println(arr[i]);
    }

    static int randomizedPivotPos(int startPos, int lastPos){
        Random rd = new Random();
        int size = lastPos - startPos + 1;
        return startPos + abs(rd.nextInt() % size);
    }

    static void swapElem(int [] arr, int locationA, int locationB){
        int temp;
        temp = arr[locationA];
        arr[locationA] = arr[locationB];
        arr[locationB] = temp;
    }

    static int randPartition(int [] arr, int locA, int locB){
        int i, pivotPoint = locA;
        int randPos = randomizedPivotPos(locA , locB);

        swapElem(arr, randPos, locB);

        for( i =locA; i < locB; i++){
            if(arr[i] < arr[locB])
            {
                swapElem(arr, i, pivotPoint);
                pivotPoint += 1;
            }
        }
        swapElem(arr, pivotPoint, locB);
        //printArrayOfInt(arr, locB - locA + 1);
        return pivotPoint;
    }

    static int medianValueWithRandPivot(int [] arr, int firstPos, int lastPos, int ith) {
        if(firstPos == lastPos)
            return arr[firstPos];
        int r = randPartition(arr, firstPos, lastPos);
        int k = r - firstPos + 1;
        if (ith == k) return arr[r];
        if (ith < k) return medianValueWithRandPivot(arr, firstPos, r-1, ith);
                else return medianValueWithRandPivot(arr, r+1, lastPos, ith-k);
    }


    public static void main(String[] args) {
        int size = 10;
        int [] arr = randArray(size);



        //printArrayOfInt(arr, size);

    }
}
