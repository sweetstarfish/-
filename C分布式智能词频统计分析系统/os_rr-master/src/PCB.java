

import java.util.ArrayList;
import java.util.List;

public class PCB { //模拟进程控制块
    private String pName;//进程名称
    private List pInstructions = new ArrayList<Instructions>();//进程中的指令列表
    // list 存储进程中所有指令   p-指向instruction指令的列表
    private int CurrentInstruction;        //当前运行指令索引

    public String getpName() {//获取进程名
        return pName;
    }

    public void setpName(String pName) {//设置进程名
        this.pName = pName;
    }

    public int getCurrentInstruction() {
        return CurrentInstruction;
    }

    public void setCurrentInstruction(int currentInstruction) {
        CurrentInstruction = currentInstruction;
    }

    public List<Instructions> getpInstructions() {//获取进程中指令列表
        return pInstructions;
    }

    public void setpInstructions(List<Instructions> pInstructions) {
        this.pInstructions = pInstructions;
    }
}
