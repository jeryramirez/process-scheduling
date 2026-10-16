import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class SchedulingAlgorithms {

    private final ArrayList<Process> processes;
    private Process currentProcess;

    SchedulingAlgorithms(ArrayList<Process> process){
        this.processes = process;
        this.currentProcess = processes.get(0);
    }
    private final GanttChart ganttChart = new GanttChart();

    private final ArrayList<String> processList = new ArrayList<String>();

    private int quantum = 1;
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
        this.processes.sort(Comparator.comparingInt(process -> process._arrivingTime));
        this.processes.sort(Comparator.comparingInt(process -> process._priority));

        this.ganttChart.print(this.processes);

        SchedulingProcess.chooseAnAlgorithms(this.processes);

    }

    public void shortestRemainingTimeFirst(){
        ArrayList<Process> processesCloned = new ArrayList<Process> (this.processes.size());

        for(Process pro: this.processes){
            processesCloned.add(new Process(pro._name, pro._arrivingTime, pro._cpuBurst,pro._priority));
        }

        processesCloned.sort(Comparator.comparingInt(process -> process._arrivingTime));

        this.iterateProcesses(processesCloned, this.ganttChart);
        System.out.println(ganttChart.timeline.toString());
        SchedulingProcess.chooseAnAlgorithms(this.processes);
    }
    private void iterateProcesses(ArrayList<Process> processes, GanttChart ganttChart){
        this.currentProcess = processes.get(0);
        if(this.processList.isEmpty()) this.processList.add(this.currentProcess._name);
        int processListSize = this.processList.size();

        if(this.processList.get(processListSize - 1) == this.currentProcess._name){
            if(this.processList.size() == 1) ganttChart.lastRafaga.addAndGet(1);
        } else {
            ganttChart.line.append(this.processList.get(processListSize - 1) + addTabs(ganttChart.lastRafaga.get() ) + addBars(ganttChart.barsQunatity.get()));
            ganttChart.lastRafaga.addAndGet(ganttChart.barsQunatity.get());
            ganttChart.timeline.append(addTabs(ganttChart.barsQunatity.get()) +  ganttChart.totalRafagaCPU.get());
            ganttChart.barsQunatity.getAndSet(0);
            System.out.println(ganttChart.line.toString());

            ganttChart.line.setLength(0);
        }

        this.currentProcess._cpuBurst --;
        ganttChart.barsQunatity.addAndGet(1);

        if(processes.size() == 1 && this.currentProcess._cpuBurst == 0){
            ganttChart.totalRafagaCPU.getAndAdd(1);
            ganttChart.timeline.append(addTabs(ganttChart.barsQunatity.get()) +  ganttChart.totalRafagaCPU.get());
            ganttChart.line.append(this.processList.get(processListSize - 1) + addTabs(ganttChart.lastRafaga.get()  ) + addBars(ganttChart.barsQunatity.get()));
            System.out.println(ganttChart.line.toString());
            if(processes.isEmpty()) return;
        }

        ganttChart.totalRafagaCPU.addAndGet(1);
        processes.removeIf( p -> p._cpuBurst == 0 );
        if(processes.isEmpty()) return;
        processes.sort(Comparator.comparingInt(process1 -> process1._cpuBurst));
        processList.add(currentProcess._name);

        iterateProcesses(processes, ganttChart);
    }

    public void roundRobin(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa el quantum para este algoritmo: ");
        this.quantum = Math.round(scanner.nextFloat());

        ArrayList<Process> processesCloned = new ArrayList<Process> (this.processes.size());

        for(Process pro: this.processes){
            processesCloned.add(new Process(pro._name, pro._arrivingTime, pro._cpuBurst,pro._priority));
        }

        processesCloned.sort(Comparator.comparingInt(process -> process._arrivingTime));

        processesCloned.forEach( (process) -> {
            processList.add(process._name);
        });

        this.iterateRoundRobinProcesses(processesCloned, this.ganttChart);
        System.out.println(ganttChart.timeline.toString());
        SchedulingProcess.chooseAnAlgorithms(this.processes);
    }

    private void iterateRoundRobinProcesses(ArrayList<Process> processes, GanttChart ganttChart){
        this.currentProcess = processes.get(0);

        if(ganttChart.lastRafaga.get() == 0) ganttChart.lastRafaga.addAndGet(1);

        if(currentProcess._cpuBurst < quantum) ganttChart.barsQunatity.addAndGet(currentProcess._cpuBurst);
        else ganttChart.barsQunatity.addAndGet(quantum);


        if(currentProcess._cpuBurst < quantum) ganttChart.totalRafagaCPU.addAndGet(currentProcess._cpuBurst);
        else ganttChart.totalRafagaCPU.addAndGet(quantum);
        ganttChart.line.append(this.processList.get(0) + addTabs(ganttChart.lastRafaga.get() ) + addBars(ganttChart.barsQunatity.get()));
        ganttChart.lastRafaga.addAndGet(ganttChart.barsQunatity.get());
        ganttChart.timeline.append(addTabs(ganttChart.barsQunatity.get()) +  ganttChart.totalRafagaCPU.get());
        ganttChart.barsQunatity.getAndSet(0);

        System.out.println(ganttChart.line.toString());

        ganttChart.line.setLength(0);

        this.currentProcess._cpuBurst -= this.quantum;


        if(currentProcess._cpuBurst > 0){
            processList.remove(0);
            processList.add(currentProcess._name);
            processes.remove(0);
            processes.add(currentProcess);

        }else {
            processes.remove(0);
            processList.remove(0);
        }

        if(processes.isEmpty()) return;

        this.iterateRoundRobinProcesses(processes, ganttChart);
    }

    public String addTabs(int tabsQuantity){
        StringBuilder tabs = new StringBuilder();

        for (int i = 0; i < tabsQuantity; i++){
            tabs.append("\t");
        }

        return tabs.toString();
    }

    public String addBars(int barsQuantity){
        StringBuilder tabs = new StringBuilder();
        final String bars = "||||";
        for (int i = 0; i < barsQuantity; i++){
            tabs.append(bars);
        }

        return tabs.toString();
    }
}