import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class SchedulingAlgorithms {

    private final ArrayList<Process> processes;
    private Process currentProcess;

    SchedulingAlgorithms(ArrayList<Process> process){
        this.processes = process;
        this.currentProcess = processes.get(0);
    }
    private final GanttChart ganttChart = new GanttChart();

    private final ArrayList<String> processList = new ArrayList<String>();

    public void firstComeFirstServed(){
        this.processes.sort(Comparator.comparingInt(process -> process._arrivingTime));

        this.ganttChart.print(processes);

        SchedulingProcess.chooseAnAlgorithms(this.processes);
    }
    public void shortestJobFirst(){
        this.processes.sort(Comparator.comparingInt(process -> process._cpuBurst));

        this.ganttChart.print(processes);

        SchedulingProcess.chooseAnAlgorithms(this.processes);

    }

    public void prioritySchedule(){
        this.processes.sort(Comparator.comparingInt(process -> process._priority));

        this.ganttChart.print(processes);

        SchedulingProcess.chooseAnAlgorithms(this.processes);

    }

    public void shortestRemainingTimeFirst(){
        ArrayList<Process> processesCloned = new ArrayList<Process> (this.processes.size());

        for(Process pro: this.processes){
            processesCloned.add(new Process(pro._name, pro._arrivingTime, pro._cpuBurst,pro._priority));
        }

        processesCloned.sort(Comparator.comparingInt(process -> process._arrivingTime));

        this.iterateProcesses(processesCloned, this.ganttChart);

        SchedulingProcess.chooseAnAlgorithms(this.processes);
    }
    private void iterateProcesses(ArrayList<Process> processes, GanttChart gantChart){
        this.currentProcess = processes.get(0);
        if(this.processList.isEmpty()) this.processList.add(this.currentProcess._name);
        int processListSize = this.processList.size();

        if(this.processList.get(processListSize - 1) == this.currentProcess._name){
            if(this.processList.size() == 1) gantChart.lastRafaga.addAndGet(1);
        } else {
            gantChart.line.append(this.processList.get(processListSize - 1) + addTabs(gantChart.lastRafaga.get() ) + addBars(gantChart.barsQunatity.get()));
            gantChart.lastRafaga.addAndGet(gantChart.barsQunatity.get());
            gantChart.barsQunatity.getAndSet(0);

            System.out.println(gantChart.line.toString());

            gantChart.line.setLength(0);
        }
        this.currentProcess._cpuBurst --;
        gantChart.barsQunatity.addAndGet(1);

        if(processes.size() == 1 && this.currentProcess._cpuBurst == 0){

            gantChart.line.append(this.processList.get(processListSize - 1) + addTabs(gantChart.lastRafaga.get()  ) + addBars(gantChart.barsQunatity.get()));
            System.out.println(gantChart.line.toString());
            if(processes.isEmpty()) return;
        }
        gantChart.totalRafagaCPU.addAndGet(1);

        processes.removeIf( p -> p._cpuBurst == 0 );
        if(processes.isEmpty()) return;
        processes.sort(Comparator.comparingInt(process1 -> process1._cpuBurst)); // reordenar la lista
        processList.add(currentProcess._name); // agregar nombre del proceso al final de la lista (fines de compraracion

        iterateProcesses(processes, gantChart); // iterar de nuevo
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