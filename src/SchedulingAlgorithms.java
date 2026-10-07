import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulingAlgorithms {

    static StringBuilder line = new StringBuilder();
    static AtomicInteger totalRafagaCPU = new AtomicInteger();
    static AtomicInteger lastRafaga = new AtomicInteger();
    static ArrayList<String> processList = new ArrayList<String>();

    static ArrayList<LastProcess> processesListHistory = new ArrayList<LastProcess>();

    static AtomicInteger barsQunatity = new AtomicInteger();
    static Process currentProcess;

    static LastProcess lastProcess;
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
        SchedulingProcess.chooseAnAlgorithms(processes);
    }
    public static void shortestJobFirst(ArrayList<Process> processes){
        processes.sort(Comparator.comparingInt(process -> process._cpuBurst));

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
        SchedulingProcess.chooseAnAlgorithms(processes);

    }

    public static void prioritySchedule(ArrayList<Process> processes){
        processes.sort(Comparator.comparingInt(process -> process._priority));

        StringBuilder line = new StringBuilder();
        StringBuilder timeline = new StringBuilder("\t" + 0);
        AtomicInteger totalRafagaCPU = new AtomicInteger();
        processes.forEach( ((process) -> {
//            System.out.println(process._name);

            line.append(process._name + addTabs(totalRafagaCPU.get() + 1) + addBars(process._cpuBurst));
            totalRafagaCPU.addAndGet(process._cpuBurst);
            timeline.append(addTabs(process._cpuBurst) + totalRafagaCPU.get());
            System.out.println(line.toString());
            line.setLength(0);
        }));
        System.out.println(timeline.toString());
        SchedulingProcess.chooseAnAlgorithms(processes);

    }

    public static void shortestRemainingTimeFirst(ArrayList<Process> processes){
        ArrayList<Process> processesCloned = new ArrayList<Process> (processes.size());

        for(Process pro: processes){
            processesCloned.add(new Process(pro._name, pro._arrivingTime, pro._cpuBurst,pro._priority));
        }

        processesCloned.sort(Comparator.comparingInt(process -> process._arrivingTime));

        iterateProcesses(processesCloned);
        totalRafagaCPU.set(0);
        lastRafaga.set(0);
        line.setLength(0);
        barsQunatity.set(0);
        SchedulingProcess.chooseAnAlgorithms(processes);
    }
    public static void iterateProcesses(ArrayList<Process> processes){
        currentProcess = processes.get(0);
        if(processList.isEmpty()) processList.add(currentProcess._name);
        int processListSize = processList.size();

        if(processList.get(processListSize - 1) == currentProcess._name){
            barsQunatity.addAndGet(1);
            if(processList.size() == 1) lastRafaga.addAndGet(1);
            currentProcess._cpuBurst --;

        } else {

            line.append(processList.get(processListSize - 1) + addTabs(lastRafaga.get() ) + addBars(barsQunatity.get()));
            lastRafaga.addAndGet(barsQunatity.get());
            barsQunatity.getAndSet(0);
            currentProcess._cpuBurst --;
            barsQunatity.addAndGet(1);
            System.out.println(line.toString());

            line.setLength(0);
        }

        if(processes.size() == 1 && currentProcess._cpuBurst == 0){

            line.append(processList.get(processListSize - 1) + addTabs(lastRafaga.get()  ) + addBars(barsQunatity.get()));
            System.out.println(line.toString());
            if(processes.isEmpty()) return;
        }
        totalRafagaCPU.addAndGet(1);

        processes.removeIf( p -> p._cpuBurst == 0 );
        if(processes.isEmpty()) return;
        processes.sort(Comparator.comparingInt(process1 -> process1._cpuBurst)); // reordenar la lista
        processList.add(currentProcess._name); // agregar nombre del proceso al final de la lista (fines de compraracion

        iterateProcesses(processes); // iterar de nuevo
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

class LastProcess {
    String name;
    int waitingTime;
    int cpuBurst;

    LastProcess(String name, int waitingTime, int cpuBurst){
        this.name = name;
        this.waitingTime = waitingTime;
        this.cpuBurst = cpuBurst;
    }
}