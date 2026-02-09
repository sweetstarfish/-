public class Instructions {//进程中的指令的对象模型
    private char IName;           //指令类型
    private double IRuntime;   //指令总运行时间
    private double IRemainTime;  //指令剩余运行时间

    public char getIName() {
        return IName;
    }

    public void setIName(char IName) {
        this.IName = IName;
    }

    public void setIRuntime(double IRuntime) {
        this.IRuntime = IRuntime;
    }

    public double getIRuntime() {
        return IRuntime;
    }

    public void setIRemainTime(double IRemainTime) {
        this.IRemainTime = IRemainTime;
    }

    public double getIRemainTime() {
        return IRemainTime;
    }
    public void subIRemainTime(){
        //剩余时间减1
        this.setIRemainTime(this.getIRemainTime()-1);
        //调用getIRemainTime()获取当前剩余时间-1，再通过setIRemainTime更新剩余时间
    }
}
