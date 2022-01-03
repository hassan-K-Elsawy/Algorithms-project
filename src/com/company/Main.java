package com.company;


import java.util.Arrays;
import java.util.Random;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;

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

    static int randPartitionMOM(int [][] arr,int[] medians, int locA, int locB){
        int i, pivotPoint = locA;
        int randPos = randomizedPivotPos(locA , locB);

        swapElem(medians, randPos, locB);
        swapCol(arr, randPos, locB);

        for( i =locA; i < locB; i++){
            if(medians[i] < medians[locB])
            {
                swapElem(medians, i, pivotPoint);
                swapCol(arr, i, pivotPoint);
                pivotPoint += 1;
            }
        }
        swapElem(medians, pivotPoint, locB);
        swapCol(arr, pivotPoint, locB);
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

    static int medianValueWithRandPivotMedianOfMedians(int [][] groups, int [] arr, int firstPos, int lastPos, int ith) {
        if(firstPos == lastPos)
            return arr[firstPos];
        int r = randPartitionMOM(groups, arr, firstPos, lastPos);
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


    static int medianValueGrouping(int []arr, int size, int ith) {

        if(size <= 15) { // if array has 15 or less elements sort and return ith
            Arrays.sort(arr);
            return arr[ith];
        }
        int i;
        int noOfGroups = size / 5;
        int extraElem = size % 5;
        boolean is5Div = extraElem == 0 ;
        int[] mediansOfGroups = new int [noOfGroups];
        int [][] groups = is5Div? new int [noOfGroups][5] : new int [noOfGroups + 1][5] ;
        for (i = 0; i < size; i++)
            groups[i/5][i%5] = arr[i];


        for(i = 0; i<noOfGroups; i++ ){
            Arrays.sort(groups[i]);
            mediansOfGroups [i] = groups[i][2];
        }

        int x = medianValueWithRandPivotMedianOfMedians(groups, mediansOfGroups, 0 , noOfGroups-1, noOfGroups/2 );
        int k = (int) ((5*(ceil(noOfGroups/2)-1)) + 3);
        int nextPoint = 0;
        if((ith) == k)
            return x;
        else {
            int newSize = (int) ( size - (ceil(noOfGroups/2)) * 3 - 1 + extraElem);
            int[] newArr = new int [size];
            int itter = (size / 5) * 5;
            if(!is5Div){
                for(i=0; i<extraElem ; i++)
                {
                    newArr[nextPoint] = groups[noOfGroups][nextPoint];
                    nextPoint += 1;
                }
            }
            if ((ith) < k)
            {

                for(i=0; i < (ceil(noOfGroups/2)-1); i++)
                {
                    newArr[nextPoint] = groups[i][0];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][1];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][2];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][3];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][4];
                    nextPoint += 1;

                }
                for(; i < noOfGroups ; i++){
                    newArr[nextPoint] = groups[i][0];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][1];
                    nextPoint += 1;
                }
                int [] sentArr = new int[nextPoint];
                for(i=0; i<nextPoint; i++)
                    sentArr[i] = newArr[i];
                return medianValueGrouping(sentArr, nextPoint, ith - 1);
            }
            else
            {
                /*for(i=0 ; i < itter-(noOfGroups*3); i++) {
                    if (groups[i/5][i%5] > x) {
                        newArr[nextPoint] = groups[i/5][i%5];
                        nextPoint += 1;
                    }
                }*/
                for(i=0; i < ceil(noOfGroups/2); i++)
                {
                    newArr[nextPoint] = groups[i][3];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][4];
                    nextPoint += 1;

                }
                for(; i < noOfGroups ; i++){
                    newArr[nextPoint] = groups[i][0];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][1];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][2];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][3];
                    nextPoint += 1;
                    newArr[nextPoint] = groups[i][4];
                    nextPoint += 1;
                }
                int [] sentArr = new int[nextPoint];
                for(i=0; i<nextPoint; i++)
                    sentArr[i] = newArr[i];
                return medianValueGrouping(sentArr, nextPoint, ith - k + 1);
            }
        }
    }



    public static void main(String[] args) {
        int size = 10;
        int [] arr = randArray(size);

        int [] arr1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
        int temp = medianValueGrouping(arr1, 40, 30);


        System.out.println(temp);


        //printArrayOfInt(arr, size);

    }
}
