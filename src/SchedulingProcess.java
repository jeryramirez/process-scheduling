import java.util.ArrayList;
import java.util.Scanner;

public class SchedulingProcess {
    public static ArrayList<Process> createProcesses(){
        ArrayList<Process> processes = new ArrayList<Process>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 4; i++){
            int arrivingTime = i;

            System.out.print("Ingresa los datos del proceso No." + (i+1) + "\n");
            System.out.print("\t - Ráfaga de CPU: ");
            int cpuBurst = Math.round(scanner.nextFloat());

            System.out.print("\t - Prioridad: ");
            int priority = Math.round(scanner.nextFloat());
//            System.out.print("Priority: " + priority);
            Process newProcess = new Process(
            "P" + (i+1),
                arrivingTime,
                cpuBurst,
                priority
            );

            processes.add(newProcess);
            System.out.println("Process P" + (i+1) + " added." );
        }

        return processes;
    }

    public static void chooseAnAlgorithms(ArrayList<Process> processes){

        String[] algorithms = { "FCFS", "SJF", "Priority", "SRTF"};
        Scanner scanner = new Scanner(System.in);

        System.out.print("Selecciona un algolitmo de la lista: \n");
        for (int i = 0; algorithms.length > i; i++){
            System.out.print((i+1) + ". " + algorithms[i] + "\n");
        }
        int option = scanner.nextInt();
        Menu menu = Menu.valueOf(algorithms[option-1]);
        switch (menu){
            case FCFS:
                SchedulingAlgorithms.firstComeFirstServed(processes);
                break;
            case SJF:
                SchedulingAlgorithms.shortestJobFirst(processes);
                break;
            case Priority:
                SchedulingAlgorithms.prioritySchedule(processes);
                break;
            case SRTF:
                SchedulingAlgorithms.shortestRemainingTimeFirst(processes);
        }
    }
}
