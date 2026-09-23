import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        final ArrayList<Process> processes = SchedulingProcess.createProcesses();
        SchedulingProcess.chooseAnAlgorithms(processes);
    }
}

// SIMULAR DIAGRAMA DE GANTT