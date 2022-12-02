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

    private static final int[] thread_cnt = {1, 2, 4};
    private static final int[] lengths = {10, 1000, 1000000};
    private Integer[] array;
    private int searchElement;

    public runSimulations() throws IOException, InterruptedException {
        FileWriter fileOut = null;
        try{
            fileOut = new FileWriter("Runtimes_partB.txt");
            for (int length : lengths) {
                this.generateRandomArray(length);
                for (int threads : thread_cnt) {
                    fileOut.write("Length of array: " + length + " Number of threads: " + threads + "\n");
                    Simulate sim = new Simulate(length, threads, this.array, this.searchElement);
                    double runTime = ((double)sim.getCreationTime())/1000000.0;
//                    long runTime = sim.getCreationTime();
                    int treeHeight = sim.getTreeHeight();
                    double searchTime = ((double)sim.getSearchTime())/1000000.0;
//                    long searchTime = sim.getSearchTime();
                    fileOut.write("Time taken: " + runTime + " ms\nTree height: " + treeHeight + "\nTime to search for an element: " + searchTime + " ms\n\n");
                }
            }
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    private void generateRandomArray(int length){
        this.array = new Integer[length];
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            this.array[i] = (int)(rand.nextInt((int)2e9) - 1e9);
        }
        this.searchElement = this.array[rand.nextInt(length)];
    }

}

class Simulate{

    private final int length;
    private final int thread_cnt;
    private final Integer[] array;
    private final int searchElement;

    private Tree<Integer> tree;
    private int treeHeight;
    private long treeCreationTime;
    private long treeSearchTime;

    public Simulate(int length, int thread_cnt, Integer[] array, int searchElement) throws InterruptedException {

        this.length = length;
        this.thread_cnt = thread_cnt;
        this.array = array;
        this.searchElement = searchElement;

        this.createTree();
        this.searchTree();

    }

    public int getTreeHeight(){
        return this.treeHeight;
    }

    public long getCreationTime(){
        return this.treeCreationTime;
    }

    public long getSearchTime(){
        return this.treeSearchTime;
    }

    private void createTree() throws InterruptedException {

        this.tree = new Tree<Integer>(this.length);

        if(this.thread_cnt == 1){
            treeCreation<Integer> t1 = new treeCreation<Integer>(this.tree, 0, this.array);
            Thread th1 = new Thread(t1);
            th1.start();
            th1.join();
            this.treeCreationTime = t1.getRunTime();
        }else if(this.thread_cnt == 2){
            tree.setEle(0, this.array[0]);
            treeCreation<Integer> t1 = new treeCreation<Integer>(this.tree, 1, this.array);
            treeCreation<Integer> t2 = new treeCreation<Integer>(this.tree, 2, this.array);
            Thread th1 = new Thread(t1);
            Thread th2 = new Thread(t2);
            th1.start();
            th2.start();
            th1.join();
            th2.join();
            this.treeCreationTime = Math.max(t1.getRunTime(), t2.getRunTime());
        }else{
            tree.setEle(0, this.array[0]);
            tree.setEle(1, this.array[1]);
            tree.setEle(2, this.array[2]);
            treeCreation<Integer> t1 = new treeCreation<Integer>(this.tree, 3, this.array);
            treeCreation<Integer> t2 = new treeCreation<Integer>(this.tree, 4, this.array);
            treeCreation<Integer> t3 = new treeCreation<Integer>(this.tree, 5, this.array);
            treeCreation<Integer> t4 = new treeCreation<Integer>(this.tree, 6, this.array);
            Thread th1 = new Thread(t1);
            Thread th2 = new Thread(t2);
            Thread th3 = new Thread(t3);
            Thread th4 = new Thread(t4);
            th1.start();
            th2.start();
            th3.start();
            th4.start();
            th1.join();
            th2.join();
            th3.join();
            th4.join();
            this.treeCreationTime = Math.max(Math.max(t1.getRunTime(), t2.getRunTime()), Math.max(t3.getRunTime(), t4.getRunTime()));
        }

        this.treeHeight = this.tree.getDepth();

    }

    private void searchTree() throws InterruptedException {

        if(this.thread_cnt == 1){
            treeSearch<Integer> t1 = new treeSearch<Integer>(this.tree, 0, this.searchElement);
            Thread th1 = new Thread(t1);
            th1.start();
            th1.join();
            this.treeSearchTime = t1.getRunTime();
        }else if(this.thread_cnt == 2){
            if(this.tree.getEle(0) == this.searchElement){
                this.treeSearchTime = 0;
            }else {
                treeSearch<Integer> t1 = new treeSearch<Integer>(this.tree, 1, this.searchElement);
                treeSearch<Integer> t2 = new treeSearch<Integer>(this.tree, 2, this.searchElement);
                Thread th1 = new Thread(t1);
                Thread th2 = new Thread(t2);
                th1.start();
                th2.start();
                th1.join();
                th2.join();
                this.treeSearchTime = Math.min(t1.getRunTime(), t2.getRunTime());
            }
        }else{
            if((this.tree.getEle(0) == this.searchElement) || (this.tree.getEle(1) == this.searchElement) || (this.tree.getEle(2) == this.searchElement)){
                this.treeSearchTime = 0;
            }else {
                treeSearch<Integer> t1 = new treeSearch<Integer>(this.tree, 3, this.searchElement);
                treeSearch<Integer> t2 = new treeSearch<Integer>(this.tree, 4, this.searchElement);
                treeSearch<Integer> t3 = new treeSearch<Integer>(this.tree, 5, this.searchElement);
                treeSearch<Integer> t4 = new treeSearch<Integer>(this.tree, 6, this.searchElement);
                Thread th1 = new Thread(t1);
                Thread th2 = new Thread(t2);
                Thread th3 = new Thread(t3);
                Thread th4 = new Thread(t4);
                th1.start();
                th2.start();
                th3.start();
                th4.start();
                th1.join();
                th2.join();
                th3.join();
                th4.join();
                this.treeSearchTime = Math.min(Math.min(t1.getRunTime(), t2.getRunTime()), Math.min(t3.getRunTime(), t4.getRunTime()));
            }
        }

    }

}

class Tree<T>{

    private final int size;
    private int depth;
    private T[] ele;

    public Tree(int size){
        this.size = size;
        this.ele = (T[]) new Object[size];
        this.depth = 1;
    }

    public int getSize(){
        return this.size;
    }

    public int getDepth(){
        this.calculateDepth();
        return this.depth;
    }

    public T getEle(int index){
        return this.ele[index];
    }

    public void setEle(int index, T ele){
        this.ele[index] = ele;
    }

    private void calculateDepth(){
        while (this.size > (1<<this.depth)) { this.depth++; }
    }

}

class treeCreation<T> implements Runnable{

    private final int init_idx;
    private final Tree<T> tree;
    private final T[] array;
    private long startTime;
    private long endTime;

    public treeCreation(Tree<T> tree, int init_idx, T[] array){
        this.tree = tree;
        this.init_idx = init_idx;
        this.array = array;
    }

    @Override
    public void run() {
        this.startTime = System.nanoTime();
        this.createTree(init_idx);
        this.endTime = System.nanoTime();
    }

    private void createTree(int idx){
        if(idx >= this.tree.getSize()) return;
        this.tree.setEle(idx, this.array[idx]);
        createTree(2*idx + 1);
        createTree(2*idx + 2);
    }

    public long getRunTime(){
        return this.endTime - this.startTime;
    }

}

class treeSearch<T> implements Runnable{

    private final int init_idx;
    private final Tree<T> tree;
    private final T searchElement;
    private long startTime = 0;
    private long endTime = 0;
    private boolean found = false;

    public treeSearch(Tree<T> tree, int init_idx, T searchElement){
        this.tree = tree;
        this.init_idx = init_idx;
        this.searchElement = searchElement;
    }

    @Override
    public void run() {
        this.startTime = System.nanoTime();
        if(!this.searchTree(init_idx)) this.endTime = System.nanoTime();
    }

    private boolean searchTree(int idx){
        if(idx >= this.tree.getSize()) return Boolean.FALSE;
        if(this.tree.getEle(idx) == this.searchElement){
            this.elementFound();
            return Boolean.TRUE;
        }
        if(searchTree(2*idx + 1)) return Boolean.TRUE;
        if(searchTree(2*idx + 2)) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    private void elementFound(){
        this.found = Boolean.TRUE;
        this.endTime = System.nanoTime();
    }

    public long getRunTime(){
        return this.endTime - this.startTime;
    }

    public boolean wasFound(){
        return this.found;
    }

}