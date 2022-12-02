package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            runSimulations runSims = new runSimulations();
        }catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}

class runSimulations{

    private final int[] thread_cnt = {1, 2};
    private final int[] lengths = {1, 10, 100, 1000, 10000};
    private Double[] array;

    public runSimulations() throws IOException, InterruptedException {
        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter("Runtimes_partA.txt");
            for (int length : lengths) {
                this.generateRandomArray(length);
                for(int thread: thread_cnt){
                    fileOut.write("Length of array: " + length + " Number of threads: " + thread + "\n");
                    Simulate sim = new Simulate(length, thread, this.array);
                    double runTime = ((double)sim.getRunTime())/1000000.0;
                    fileOut.write("Time taken: " + runTime + " ms\n");
                }
            }
        }finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    private void generateRandomArray(int length){
        this.array = new Double[length];
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            this.array[i] = rand.nextDouble()*10;
        }
    }

}

class Simulate{

    private final int length;
    private final int thread_cnt;
    private final Double[] array;
    long runTime;

    public Simulate(int length, int thread_cnt, Double[] array) throws InterruptedException {
        this.length = length;
        this.thread_cnt = thread_cnt;
        this.array = array;

        this.sort();

    }

    public long getRunTime(){
        return this.runTime;
    }

    public int getLength(){
        return this.length;
    }

    public int getThreadCnt(){
        return this.thread_cnt;
    }

    public Double[] getArray(){
        return this.array;
    }

    private void sort() throws InterruptedException {

        sortedArray<Double> Arr = new sortedArray<>(length, this.array);

        if(this.thread_cnt == 1){
            sortArray<Double> t1 = new sortArray<Double>(0, this.length, Arr);
            Thread th1 = new Thread(t1);
            th1.start();
            th1.join();
            this.runTime += t1.getRunTime();
        }else{
            sortArray<Double> t1 = new sortArray<Double>(0, this.length/2, Arr);
            sortArray<Double> t2 = new sortArray<Double>(this.length/2, this.length, Arr);
            Thread th1 = new Thread(t1);
            Thread th2 = new Thread(t2);
            th1.start();
            th2.start();
            th1.join();
            th2.join();
            this.runTime += Math.max(t1.getEndTime(), t2.getEndTime()) - Math.min(t1.getStartTime(), t2.getStartTime());
            mergeArray<Double> t3 = new mergeArray<Double>(0, this.length/2, this.length, Arr.getArray());
            Thread th3 = new Thread(t3);
            th3.start();
            th3.join();
            this.runTime += t3.getRunTime();
            Arr.setArray(t3.getArray());
        }

//        for(Double i:Arr.getArray()){
//            System.out.print(i + " ");
//        }
//        System.out.println();

    }

}

class sortedArray<T>{
    private int length;
    private T[] array;

    public sortedArray(int length, T[] array){
        this.length = length;
        this.array = array;
    }

    public int getLength(){
        return this.length;
    }

    public T[] getArray(){
        return this.array;
    }

    public void setArray(T[] array){
        this.array = array;
        this.length = array.length;
    }

    public T getElement(int index){
        return this.array[index];
    }

    public void swapElements(int idx){
        if(idx >= (this.length - 1)) return;
        T temp = this.array[idx];
        this.array[idx] = this.array[idx+1];
        this.array[idx+1] = temp;
    }
}

class sortArray<T> implements Runnable{

    private final int startIdx;
    private final int endIdx;
    private final sortedArray<T> array;
    private long startTime;
    private long endTime;

    public sortArray(int startIdx, int endIdx, sortedArray<T> array){
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.array = array;
    }

    @Override
    public void run() {
        this.startTime = System.nanoTime();
        for(int i = this.startIdx; i < this.endIdx; i += 2){
            int j = this.startIdx;
            if((j&1) != (i&1)) j++;
            for(; j < this.endIdx; j += 2){
                if((j + 1) >= this.endIdx) break;
                if(((Comparable)this.array.getElement(j)).compareTo(this.array.getElement(j+1)) < 0){
                    this.array.swapElements(j);
                }
            }
        }
        this.endTime = System.nanoTime();
    }

    public long getRunTime(){
        return this.endTime - this.startTime;
    }

    public long getStartTime(){
        return this.startTime;
    }

    public long getEndTime(){
        return this.endTime;
    }

}

class mergeArray<T> implements Runnable{

    private final int startIdx;
    private final int midIdx;
    private final int endIdx;
    private T[] array;
    private long startTime;
    private long endTime;

    public mergeArray(int startIdx, int midIdx, int endIdx, T[] array){
        this.startIdx = startIdx;
        this.midIdx = midIdx;
        this.endIdx = endIdx;
        this.array = array;
    }

    @Override
    public void run(){
        this.startTime = System.nanoTime();

        T[] temp_a = Arrays.copyOfRange(this.array, this.startIdx, this.midIdx);
        T[] temp_b = Arrays.copyOfRange(this.array, this.midIdx, this.endIdx);
        int a = this.midIdx - this.startIdx, b = this.endIdx - this.midIdx;

        int i = 0, j = 0, k = this.startIdx;
        while((i < a) || (j < b)){
            if(i == a){
                this.array[k++] = temp_b[j++];
            }else if(j == b) {
                this.array[k++] = temp_a[i++];
            }else if(((Comparable)temp_a[i]).compareTo(temp_b[j]) > 0){
                this.array[k++] = temp_a[i++];
            }else{
                this.array[k++] = temp_b[j++];
            }
        }

        this.endTime = System.nanoTime();
    }

    public long getRunTime(){
        return this.endTime - this.startTime;
    }

    public T[] getArray(){
        return this.array;
    }

}