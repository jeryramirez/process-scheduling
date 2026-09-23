import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulingAlgorithms {
    public static void firstComeFirstServed(ArrayList<Process> processes){
        processes.sort(Comparator.comparingInt(process -> process._arrivingTime));

        StringBuilder line = new StringBuilder();
        StringBuilder timeline = new StringBuilder("\t" + 0);
        AtomicInteger totalRafagaCPU = new AtomicInteger();
        processes.forEach( ((process) -> {
            line.append(process._name + addTabs(totalRafagaCPU.get() + 1) + addBars(process._cpuBurst));
            totalRafagaCPU.addAndGet(process._cpuBurst);
            timeline.append(addTabs(process._cpuBurst) + totalRafagaCPU.get());
            System.out.println(line.toString());
            line.setLength(0);
        }));
        System.out.println(timeline.toString());

    }
    public static void addTimeLine(){

    }
    public static String addTabs(int tabsQuantity){
        StringBuilder tabs = new StringBuilder();

        for (int i = 0; i < tabsQuantity; i++){
            tabs.append("\t");
        }

        return tabs.toString();
    }

    public static String addBars(int barsQuantity){
        StringBuilder tabs = new StringBuilder();
        final String bars = "||||";
        for (int i = 0; i < barsQuantity; i++){
            tabs.append(bars);
        }

        return tabs.toString();
    }
}
