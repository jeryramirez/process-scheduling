import java.util.ArrayList;
import java.util.Scanner;

public class SchedulingProcess {
    public static ArrayList<Process> createProcesses(){
        ArrayList<Process> processes = new ArrayList<Process>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 3; i++){
            System.out.print("Ingresa el Tiempo de Llegada para proceso P" + (i+1) + ": ");
            int arrivingTime = scanner.nextInt();

            System.out.print("Ingresa el Tiempo de Rafaga de CPU para proceso P" + (i+1) + ": ");
            int cpuBurst = scanner.nextInt();

            Process newProcess = new Process(
            "P" + (i+1),
                arrivingTime,
                cpuBurst
            );

            processes.add(newProcess);
            System.out.println("Process P" + (i+1) + " added." );
        }

        return processes;
    }

    public static void chooseAnAlgorithms(ArrayList<Process> processes){

        String[] algorithms = { "FCFS", "SJF"};
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
                break;
        }
    }
}
