package com.company;


import java.util.Arrays;
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

    static int medianOfFive(int a, int b, int c, int d, int e){
        int [] arr = {a, b, c, d, e};
        Arrays.sort(arr);
        return arr[2];
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

    static void swapCol (int [][] arr, int col1, int col2){
        int temp;
        for(int i=0; i<5 ; i++)
        {
            temp = arr[col1][i];
            arr[col1][i] = arr[col2][i];
            arr[col2][i] = temp;
        }
    }

    static void medianOfGroups(int [] arrOfMedian, int [][] groups, int size){

    }

    static int medianValueGrouping(int []arr, int size, int ith) {
        if(size <= 15) { // if array has 15 or less elements sort and return ith
            Arrays.sort(arr);
            return arr[ith];
        }
        int i;
        int noOfGroups = size / 5;
        int[] mediansOfGroups = new int [noOfGroups];
        int [][] groups = size % 5 == 0 ? new int [noOfGroups][5] : new int [noOfGroups + 1][5] ;
        for (i = 0; i < size; i++)
            groups[i/5][i%5] = arr[i];


        for(i = 0; i<noOfGroups; i++ ){
            Arrays.sort(groups[i]);
            mediansOfGroups [i] = groups[i][2];
        }



    }



    public static void main(String[] args) {
        int size = 10;
        int [] arr = randArray(size);



        //printArrayOfInt(arr, size);

    }
}
