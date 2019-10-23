package ru.otus;


import java.util.LinkedList;
import java.util.List;

public class Benchmark implements BenchmarkMBean{

    private int size = 0;
    private int loop = 10;
    List<Integer []> list = new LinkedList<>();

    void run() throws InterruptedException {
        while (true){
            for(int i = 0; i < loop; i++){
                list.add(new Integer[500]);
                list.add(new Integer[500]);
                list.add(new Integer[500]);
                list.add(new Integer[500]);
                list.remove(0);
                list.remove(0);
            }
            Thread.sleep(20);
            System.out.println("сделал");
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }
}
