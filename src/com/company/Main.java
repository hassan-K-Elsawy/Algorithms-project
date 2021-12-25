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

    static int randomizedPivotPos(int size){
        Random rd = new Random();
        return abs(rd.nextInt() % size);
    }

    public static void main(String[] args) {
        int size = 10;
        int [] arr = randArray(size);
        //printArrayOfInt(arr, size);
        //System.out.println(randomizedPivotPos(size));
    }
}
