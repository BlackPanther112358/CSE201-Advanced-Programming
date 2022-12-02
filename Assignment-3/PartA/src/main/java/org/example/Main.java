package org.example;

import java.io.FileWriter;
import java.io.IOException;
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

        for(int i = 0; i < this.length; i++){
            if(this.thread_cnt == 1){
                sortArray<Double> t1 = new sortArray<Double>(0, this.length, Arr, i&1);
                Thread th1 = new Thread(t1);
                th1.start();
                th1.join();
                this.runTime += t1.getRunTime();
            }else{
                sortArray<Double> t1 = new sortArray<Double>(0, this.length/2, Arr, i&1);
                sortArray<Double> t2 = new sortArray<Double>(this.length/2, this.length, Arr, i&1);
                Thread th1 = new Thread(t1);
                Thread th2 = new Thread(t2);
                th1.start();
                th2.start();
                th1.join();
                th2.join();
                this.runTime += Math.max(t1.getRunTime(), t2.getRunTime());
            }
        }

//        for(Double i:Arr.getArray()){
//            System.out.print(i + " ");
//        }
//        System.out.println();

    }

}

class sortedArray<T>{
    private final int length;
    private final T[] array;

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

    private int startIdx;
    private final int endIdx;
    private final sortedArray<T> array;
    private long startTime;
    private long endTime;

    public sortArray(int startIdx, int endIdx, sortedArray<T> array, int parity){
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.array = array;
        if(this.startIdx%2 != parity) this.startIdx++;
    }

    @Override
    public void run() {
        this.startTime = System.nanoTime();
        for(int i = this.startIdx; i < this.endIdx; i += 2){
            if((i+1) >= this.endIdx) break;
            if(((Comparable)this.array.getElement(i)).compareTo(this.array.getElement(i+1)) < 0){
                this.array.swapElements(i);
            }
        }
        this.endTime = System.nanoTime();
    }

    public long getRunTime(){
        return this.endTime - this.startTime;
    }

}