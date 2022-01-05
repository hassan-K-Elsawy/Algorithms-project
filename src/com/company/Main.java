package com.company;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.System.*;

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

    static void sortgroup(int [] arr, int ithGroup){
        int startPos = ithGroup*5;
        int [] temp = new int [5];

        //for(int i =0; i<5 ; i++)
        //    temp[i] = arr[startPos+i];

        arraycopy(arr,startPos,temp,0,5);
        Arrays.sort(temp);
        arraycopy(temp,0,arr,startPos,5);
        //for(int i =0; i<5 ; i++)
        //    arr[startPos+i] = temp[i];
    }

    static int getMOM(int[] arr, int size){

        int i;

        int noOfGroups = size / 5;
        int [] medians = new int [noOfGroups];
        int midMedian = (int) ceil(noOfGroups/2.0);

        for(i=0; i<noOfGroups; i++)
        {
            sortgroup(arr, i);
            medians[i] = arr[(i*5)+2];
        }

        return medianValueWithRandPivot(medians, 0, noOfGroups-1, midMedian);
    }

    static int partition(int [] arr, int size, int x){
        int i,pivotPoint=0;
        for(i=0; i<size; i++) {
            if (x == arr[i]) {
                swapElem(arr, i, size - 1);
                break;
            }
        }

        for( i =0; i < size-1; i++){
            if(arr[i] < arr[size-1])
            {
                swapElem(arr, i, pivotPoint);
                pivotPoint += 1;
            }
        }
        swapElem(arr, pivotPoint, size-1);
        //printArrayOfInt(arr, locB - locA + 1);
        return pivotPoint;
    }

    static int medianValueWithMOM(int[] arr, int size, int ith){

        if(size <= 15){
            Arrays.sort(arr);
            return arr[ith-1];
        }

        int x = getMOM(arr, size);
        int r = partition(arr, size, x);
        if (ith == r + 1) return arr[r];
        int [] newArr ;
        if (ith < r + 1) {
            newArr = new int [r];
            arraycopy(arr,0, newArr, 0, r);
            return medianValueWithMOM(newArr, r, ith);
        }
        else {
            newArr = new int [size-r-1];
            arraycopy(arr,r+1,newArr,0,size-r-1);
            return medianValueWithMOM(newArr, size-r-1, ith - r - 1);
        }
    }


    ///////////////////////////////////////////////////
    /*
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


    static int medianValueWithRandPivotMedianOfMedians(int [][] groups, int [] arr, int firstPos, int lastPos, int ith) {
        if(firstPos == lastPos)
            return arr[firstPos];
        int r = randPartitionMOM(groups, arr, firstPos, lastPos);
        int k = r - firstPos + 1;
        if (ith == k) return arr[r];
        if (ith < k) return medianValueWithRandPivotMedianOfMedians(groups, arr, firstPos, r-1, ith);
        else return medianValueWithRandPivotMedianOfMedians(groups, arr, r+1, lastPos, ith-k);
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
            return arr[ith - 1];
        }
        int i;
        int noOfGroups = size / 5;
        int extraElem = size % 5;
        boolean is5Div = extraElem == 0 ;
        int[] mediansOfGroups = new int [noOfGroups];
        int [][] groups = is5Div? new int [noOfGroups][5] : new int [noOfGroups + 1][5] ;
        int nextPoint = 0;
        for (i = 0; i < size; i++) {
            groups[i/5][i%5] = arr[i];
        }
        for(i = 0; i<noOfGroups; i++ ){
            Arrays.sort(groups[i]);
            mediansOfGroups [i] = groups[i][2];
        }
        int midGroup = (int) ceil(noOfGroups/2.0);
        medianValueWithRandPivotMedianOfMedians(groups, mediansOfGroups, 0 , noOfGroups-1, noOfGroups/2 );

        int x = groups[midGroup-1][2];
        int k = (int) ((5*(ceil(noOfGroups/2.0)-1)) + 2);


        nextPoint = 0;
        if((ith) == k+1)
            return groups[midGroup-1][2];
        else {
            int[] newArr = new int [size];
            if ((ith) < k+1)
            {
                for(i=0; i < (midGroup-1); i++)
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
                if(!is5Div){
                    for(i=0; i<extraElem ; i++)
                    {
                        newArr[nextPoint] = groups[noOfGroups][i];
                        nextPoint += 1;
                    }
                }
                int [] sentArr = new int[nextPoint];
                for(i=0; i<nextPoint; i++)
                    sentArr[i] = newArr[i];
                return medianValueGrouping(sentArr, nextPoint, ith);
            }
            else
            {
                for(i=0; i < midGroup; i++)
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
                if(!is5Div){
                    for(i=0; i<extraElem ; i++)
                    {
                        newArr[nextPoint] = groups[noOfGroups][i];
                        nextPoint += 1;
                    }
                }
                int [] sentArr = new int[nextPoint];
                for(i=0; i<nextPoint; i++)
                    sentArr[i] = newArr[i];
                return medianValueGrouping(sentArr, nextPoint, ith - (size - nextPoint));
            }
        }
    }

    */

    public static void main(String[] args) {
        int size =  10000000;
        int [] arr = randArray(size);
        int [] arrClone1;
        int [] arrClone1_1;
        int [] arrClone2;
        int [] arrClone3;
        long startTime, MOMElapsed, RandElapsed, classicElapsed;

        arrClone1 = arr.clone();
        arrClone1_1 = arr.clone();
        arrClone2 = arr.clone();
        arrClone3 = arr.clone();

        //int [] arr1 = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40};
        //int [] arr1 = {24,35,1,2,36,17,26,23,12,34,30,27,11,13,14,3,18,19,20,28,29,7,8,9,10,25,4,5,6,21,22,37,38,39,40,15,16,31,32,33};

        //for (int i = 0; i< 40; i++) {
        //    int temp = medianValueWithMOM(arr1, 40, i+1);
        //    System.out.println(temp);
        //}
        externalMOM extMOM = new externalMOM();

        ArrayList<Integer> arrList = new ArrayList<>();
        for(int num:arrClone1_1)
            arrList.add(num);

        startTime = nanoTime();
        System.out.println(extMOM.getMedian(arrList));
        MOMElapsed = nanoTime() - startTime;
        System.out.println("elapsed time for median of medians external implementation " +  MOMElapsed/1000000);

        startTime = nanoTime();
        System.out.println(medianValueWithMOM(arrClone1, size, (size/2)+1));
        MOMElapsed = nanoTime() - startTime;
        System.out.println("elapsed time for median of medians " +  MOMElapsed/1000000);

        startTime = nanoTime();
        System.out.println(medianValueWithRandPivot(arrClone2,0, size-1,(size/2)+1));
        RandElapsed = nanoTime() - startTime;
        System.out.println("elapsed time for Random pivot " +  RandElapsed/1000000);

        startTime = currentTimeMillis();
        Arrays.sort(arrClone3);
        System.out.println(arrClone3[(size/2)]);
        classicElapsed = startTime - currentTimeMillis();
        System.out.println("elapsed time for Sort method " + classicElapsed);
    }
}
