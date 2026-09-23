public class Process {
    String _name;
    int _arrivingTime;
    int _cpuBurst;
//    int _beginningTime;
//    int _finishTime;
//    int _executionTime;
//    int _waitingTime;

    Process(
        String name,
        int arrivingTime,
        int cpuBurst
    ){
        this._name = name;
        this._arrivingTime = arrivingTime;
        this._cpuBurst = cpuBurst;
    }

    public void showProperties (){
        System.out.println(this._name);
        System.out.println(this._arrivingTime);
        System.out.println(this._cpuBurst);

    }
}
