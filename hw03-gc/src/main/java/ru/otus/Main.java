package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

/*
VM options:

-Xms512m
-Xmx512m
-XX:+UseConcMarkSweepGC

or  -XX:+UseSerialGC
    -XX:+UseParallelGC
    -XX:+UseConcMarkSweepGC
    -XX:+UseG1GC
 */


public class Main
{
    static int second = 60;

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();

        long beginTime = System.currentTimeMillis();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);

        Timer timer = new Timer();
        timer.schedule(new MessageTask(), 0, second*1000);

        System.out.println("__________________");
        try{
            try{
                mbean.run();
            }
            finally {
                mbean.list.clear();
                timer.cancel();
            }
        }catch (Throwable e){
            System.out.println("\n\nOOM exception was thrown");
            System.out.println("Time: " + (System.currentTimeMillis() - beginTime) / 60000 + "min "+ (System.currentTimeMillis() - beginTime) % 60000/1000 + "s ");
            infAboutWork.forEach((a, b) -> System.out.println("GC : " + a + ", Count : " + b[0] + ", Time : " + b[1]));
        }
    }

    private static Map<String, long[]> infPerMinute = new HashMap<>();
    private static Map<String, long[]> infAboutWork = new HashMap<>();
    private static String[] gcName = new String[2];

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for(int i = 0; i < gcbeans.size(); i++){
            infPerMinute.put(gcbeans.get(i).getName(), new long[] {0, 0});
            infAboutWork.put(gcbeans.get(i).getName(), new long[] {0, 0});
            gcName[i] = gcbeans.get(i).getName();
        }

        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    long duration = info.getGcInfo().getDuration();

                    newStatistic(gcName, duration);
                    //System.out.println("Name:" + gcName + ", time:" + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    public static void Message(){
        infPerMinute.forEach((a,b)-> System.out.println("GC : " + a + ", Count : " + b[0] + ", Time : " + b[1]));
        System.out.println("__________________");
        //обновление мапы каждую минуту
        infPerMinute.clear();
        for(String name:gcName){
            infPerMinute.put(name, new long[] {0, 0});
        }
    }

    private static void newStatistic(String gcName, long duration){
        long[] newInfotmation = new long[2];
        newInfotmation = infPerMinute.get(gcName);
        newInfotmation[0]++;
        newInfotmation[1] += duration;
        infPerMinute.remove(gcName);
        infPerMinute.put(gcName, newInfotmation);
        newInfotmation = infAboutWork.get(gcName);
        newInfotmation[0]++;
        newInfotmation[1] += duration;
        infAboutWork.remove(gcName);
        infAboutWork.put(gcName, newInfotmation);
    }
}

