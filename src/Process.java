public class Process {
    String _name;
    int _arrivingTime;
    int _cpuBurst;
    int _priority;
//    int _beginningTime;
//    int _finishTime;
//    int _executionTime;
//    int _waitingTime;

    Process(
        String name,
        int arrivingTime,
        int cpuBurst,
        int priority
    ){
        this._name = name;
        this._arrivingTime = arrivingTime;
        this._cpuBurst = cpuBurst;
        this._priority = priority;
    }

    public void showProperties (){
        System.out.println(this._name);
        System.out.println(this._arrivingTime);
        System.out.println(this._cpuBurst);
        System.out.println(this._priority);

    }
}
