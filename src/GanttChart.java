import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GanttChart {
    public StringBuilder line = new StringBuilder();
    public AtomicInteger totalRafagaCPU = new AtomicInteger();
    public AtomicInteger lastRafaga = new AtomicInteger();
    public AtomicInteger barsQunatity = new AtomicInteger();
    private StringBuilder timeline = new StringBuilder("\t" + 0);

    public void print(ArrayList<Process> processes){
        processes.forEach( ((process) -> {
            line.append(process._name + addTabs(totalRafagaCPU.get() + 1) + addBars(process._cpuBurst));
            totalRafagaCPU.addAndGet(process._cpuBurst);
            timeline.append(addTabs(process._cpuBurst) + totalRafagaCPU.get());
            System.out.println(line.toString());
            line.setLength(0);
        }));
        System.out.println(timeline.toString());

    }

    private String addTabs(int tabsQuantity){
        StringBuilder tabs = new StringBuilder();

        for (int i = 0; i < tabsQuantity; i++){
            tabs.append("\t");
        }

        return tabs.toString();
    }

    private String addBars(int barsQuantity){
        StringBuilder tabs = new StringBuilder();
        final String bars = "||||";
        for (int i = 0; i < barsQuantity; i++){
            tabs.append(bars);
        }

        return tabs.toString();
    }
//    Process currentProcess;

//    GanttChart(
//        StringBuilder line,
//        AtomicInteger totalRafagaCPU,
//        AtomicInteger lastRafaga,
//        AtomicInteger barsQuantity
//    ){
//        this.line = line;
//        this.totalRafagaCPU = totalRafagaCPU;
//        this.lastRafaga = lastRafaga;
//        this.barsQunatity = barsQuantity;
//
//    }
}
