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
            treeCreation<Integer> t1 = new treeCreation<Integer>(0, this.array);
            Thread th1 = new Thread(t1);
            th1.start();
            th1.join();
            this.tree.setRoot(t1.getRoot());
            this.tree.setDepth(t1.getDepth());
            this.treeCreationTime = t1.getEndTime() - t1.getStartTime();
        }else if(this.thread_cnt == 2){
            treeCreation<Integer> t1 = new treeCreation<Integer>(1, this.array);
            treeCreation<Integer> t2 = new treeCreation<Integer>(2, this.array);
            Thread th1 = new Thread(t1);
            Thread th2 = new Thread(t2);
            th1.start();
            th2.start();
            th1.join();
            th2.join();
            treeNode<Integer> root = new treeNode<Integer>(this.array[0]);
            root.setLeft(t1.getRoot());
            root.setRight(t2.getRoot());
            this.tree.setRoot(root);
            this.tree.setDepth(Math.max(t1.getDepth(), t2.getDepth()) + 1);
//            this.treeCreationTime = Math.max(t1.getRunTime(), t2.getRunTime());
            this.treeCreationTime = Math.max(t1.getEndTime(), t2.getEndTime()) - Math.min(t1.getStartTime(), t2.getStartTime());
        }else{
            treeCreation<Integer> t1 = new treeCreation<Integer>(3, this.array);
            treeCreation<Integer> t2 = new treeCreation<Integer>(4, this.array);
            treeCreation<Integer> t3 = new treeCreation<Integer>(5, this.array);
            treeCreation<Integer> t4 = new treeCreation<Integer>(6, this.array);
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
            treeNode<Integer> root = new treeNode<Integer>(this.array[0]);
            treeNode<Integer> left = new treeNode<Integer>(this.array[1]);
            treeNode<Integer> right = new treeNode<Integer>(this.array[2]);
            root.setLeft(left);
            root.setRight(right);
            left.setLeft(t1.getRoot());
            left.setRight(t2.getRoot());
            right.setLeft(t3.getRoot());
            right.setRight(t4.getRoot());
            this.tree.setRoot(root);
            this.tree.setDepth(Math.max(Math.max(t1.getDepth(), t2.getDepth()), Math.max(t3.getDepth(), t4.getDepth())) + 2);
//            this.treeCreationTime = Math.max(Math.max(t1.getRunTime(), t2.getRunTime()), Math.max(t3.getRunTime(), t4.getRunTime()));
            this.treeCreationTime = Math.max(Math.max(t1.getEndTime(), t2.getEndTime()), Math.max(t3.getEndTime(), t4.getEndTime())) - Math.min(Math.min(t1.getStartTime(), t2.getStartTime()), Math.min(t3.getStartTime(), t4.getStartTime()));
        }

        this.treeHeight = this.tree.getDepth();

    }

    private void searchTree() throws InterruptedException {

        if(this.thread_cnt == 1){
            treeSearch<Integer> t1 = new treeSearch<Integer>(this.tree.getRoot(), this.searchElement);
            Thread th1 = new Thread(t1);
            th1.start();
            th1.join();
            this.treeSearchTime = t1.getEndTime() - t1.getStartTime();
        }else if(this.thread_cnt == 2){
            if(this.tree.getRoot().getEle() == this.searchElement){
                this.treeSearchTime = 0;
            }else {
                treeSearch<Integer> t1 = new treeSearch<Integer>(this.tree.getRoot().getLeft(), this.searchElement);
                treeSearch<Integer> t2 = new treeSearch<Integer>(this.tree.getRoot().getRight(), this.searchElement);
                Thread th1 = new Thread(t1);
                Thread th2 = new Thread(t2);
                th1.start();
                th2.start();
                th1.join();
                th2.join();
                this.treeSearchTime = Math.max(t1.getEndTime(), t2.getEndTime()) - Math.min(t1.getStartTime(), t2.getStartTime());
            }
        }else{
            if((this.tree.getRoot().getEle() == this.searchElement) || (this.tree.getRoot().getLeft().getEle() == this.searchElement) || (this.tree.getRoot().getRight().getEle() == this.searchElement)){
                this.treeSearchTime = 0;
            }else {
                treeSearch<Integer> t1 = new treeSearch<Integer>(this.tree.getRoot().getLeft().getLeft(), this.searchElement);
                treeSearch<Integer> t2 = new treeSearch<Integer>(this.tree.getRoot().getLeft().getRight(), this.searchElement);
                treeSearch<Integer> t3 = new treeSearch<Integer>(this.tree.getRoot().getRight().getLeft(), this.searchElement);
                treeSearch<Integer> t4 = new treeSearch<Integer>(this.tree.getRoot().getRight().getRight(), this.searchElement);
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
//                this.treeSearchTime = Math.min(Math.min(t1.getRunTime(), t2.getRunTime()), Math.min(t3.getRunTime(), t4.getRunTime()));
                this.treeSearchTime = Math.max(Math.max(t1.getEndTime(), t2.getEndTime()), Math.max(t3.getEndTime(), t4.getEndTime())) - Math.min(Math.min(t1.getStartTime(), t2.getStartTime()), Math.min(t3.getStartTime(), t4.getStartTime()));
            }
        }

    }

}

class treeNode<T>{

    private final T ele;
    private treeNode<T> left;
    private treeNode<T> right;

    public treeNode(T ele){
        this.ele = ele;
        this.left = null;
        this.right = null;
    }

    public void setLeft(treeNode<T> left){
        this.left = left;
    }

    public void setRight(treeNode<T> right){
        this.right = right;
    }

    public T getEle(){
        return this.ele;
    }

    public treeNode<T> getLeft(){
        return this.left;
    }

    public treeNode<T> getRight(){
        return this.right;
    }

}

class Tree<T>{

    private final int size;
    private int depth;
    private treeNode<T> root;

    public Tree(int size){
        this.size = size;
        this.root = null;
        this.depth = 1;
    }

    public int getSize(){
        return this.size;
    }

    public int getDepth(){
        return this.depth;
    }

    public void setDepth(int depth){
        this.depth = depth;
    }

    public treeNode<T> getRoot(){
        return this.root;
    }

    public void setRoot(treeNode<T> root){
        this.root = root;
    }

}

class treeCreation<T> implements Runnable{

    private final int init_idx;
    private final T[] array;
    private long startTime;
    private long endTime;
    private treeNode<T> root;
    private int depth;

    public treeCreation(int init_idx, T[] array){
        this.init_idx = init_idx;
        this.array = array;
    }

    @Override
    public void run() {
        this.startTime = System.nanoTime();
        this.root = this.createTree(init_idx);
        this.depth = this.calculateDepth(this.root);
        this.endTime = System.nanoTime();
    }

    private treeNode<T> createTree(int idx){
        if(idx >= this.array.length) return null;
        treeNode<T> node = new treeNode<T>(this.array[idx]);
        node.setLeft(createTree(2*idx + 1));
        node.setRight(createTree(2*idx + 2));
        return node;
    }

    private int calculateDepth(treeNode<T> node){
        if(node == null) return 0;
        return 1 + Math.max(calculateDepth(node.getLeft()), calculateDepth(node.getRight()));
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

    public int getDepth(){
        return this.depth;
    }

    public treeNode<T> getRoot(){
        return this.root;
    }

}

class treeSearch<T> implements Runnable{

    private final treeNode<T> root;
    private final T searchElement;
    private long startTime = 0;
    private long endTime = 0;
    private boolean found = false;

    public treeSearch(treeNode<T> root, T searchElement){
        this.root = root;
        this.searchElement = searchElement;
    }

    @Override
    public void run() {
        this.startTime = System.nanoTime();
        if(!this.searchTree(this.root)) this.endTime = System.nanoTime();
    }

    private boolean searchTree(treeNode<T> node){
        if(node == null) return Boolean.FALSE;
        if(node.getEle() == this.searchElement){
            this.elementFound();
            return Boolean.TRUE;
        }
        if(searchTree(node.getLeft())) return Boolean.TRUE;
        if(searchTree(node.getRight())) return Boolean.TRUE;
        return Boolean.FALSE;
    }

    private void elementFound(){
        this.found = Boolean.TRUE;
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

    public boolean wasFound(){
        return this.found;
    }

}