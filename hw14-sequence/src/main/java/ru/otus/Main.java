package ru.otus;

public class Main {
    public static void main(String[] args) {
        SequencePrint sequencePrint = new SequencePrint();
        Thread thread1 = new Thread(sequencePrint, "odin");
        Thread thread2 = new Thread(sequencePrint, "vtor");
        thread1.start();
        thread2.start();
    }

}
