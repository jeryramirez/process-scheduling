import java.util.concurrent.atomic.AtomicInteger;

public class GanttChart {
    StringBuilder line = new StringBuilder();
    AtomicInteger totalRafagaCPU = new AtomicInteger();
    AtomicInteger lastRafaga = new AtomicInteger();
    AtomicInteger barsQunatity = new AtomicInteger();
    Process currentProcess;
}
