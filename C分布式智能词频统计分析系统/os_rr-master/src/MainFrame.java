

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Thread.sleep;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class MainFrame {
    private JButton bFIle, bStart, bStop;//三个按钮
    private JTextField tTime, tCur;
    private JTextArea tAll, tReady, tInput, tOutput, tWait;//队列文本域显示
    private File file;
    private JLabel lInfo;
    private ArrayList<PCB> allQue = new ArrayList<PCB>();
    private ArrayList<PCB> readyQue = new ArrayList<PCB>();
    private ArrayList<PCB> inQue = new ArrayList<PCB>();
    private ArrayList<PCB> outQue = new ArrayList<PCB>();
    private ArrayList<PCB> waitQue = new ArrayList<PCB>();
    private volatile Thread blinker;
    private long count = 0;

    public MainFrame() {
        //初始化界面
        init();
        //选择文件按钮 并添加事件监视器
        bFIle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
                readFile();
                saveLog("读取文件成功!\r\n________________________\r\n");
                showAll(allQue);

            }
        });
        //开始按钮
        bStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //若存储文件内容的集合为空
                if (allQue.size()==0) {
                    //弹出对话框
                    JOptionPane.showMessageDialog(null, "请重新选择文件!", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                //初始化队列，准备开始调度
                if (boolTTime()) {
                    initQue();
                    startRun();

                }
            }

        });
        //停止按钮
        bStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bStart.setText("继续调度");
                blinker = null;//停止线程
            }
        });
    }

    //添加日志内容
    private void saveLog(String str) {
        File file = new File("log.txt");
        try {//创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
            //创建输入流 读取日志
            FileInputStream in = new FileInputStream("log.txt");
            //byte数组——存储读取内容
            byte[] b = new byte[1024];
            //存储每次读取的数据长度
            int i;
            //字符串变量接收文件内容
            StringBuilder wenzi = new StringBuilder();
            // 通过输入流读取文件内容，直到文件末尾
            while ((i = in.read(b)) > 0) {
                // 将读取到的字节转换为字符串并附加到 StringBuilder 中
                wenzi.append(new String(b, 0, i));
            }
            in.close();//关闭输入流

            // 将新日志（传入的 str）追加到读取的内容后面
            wenzi.append("\r\n" + str);//创建新的内容=之前的内容+新输入的内容

            // 创建输出流，用于写入文件
            FileOutputStream out = new FileOutputStream("log.txt");
            // 将拼接后的内容转换为字节数组
            byte[] newb = wenzi.toString().getBytes();
            // 将字节数组写入文件
            out.write(newb);
            out.close();
        }
        //捕获异常
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean boolTTime() {
        //判断时间片
        //检查输入框内容是否为空
        if (tTime.getText().equals("")) {
            //输入时间片大小
            JOptionPane.showMessageDialog(null, "请输入时间片大小(数字)!", "提示", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            try {
                //输入内容转为double类型
                parseDouble(tTime.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "时间片大小为数字!请重新输入!", "提示", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        return true;
    }

    //启动调度
    public void startRun() {
        //创建Runnable对象 函数接口 可执行的任务
        Runnable runnable = new Runnable() {
            public void run() {
                saveLog("------正在进行调度-------");
                lInfo.setText("------正在进行调度-------");
                //所有队列有进程需调度 且线程未被中断
                while (allQue.size() > 0 && blinker != null) {
                    try {//可捕获异常
                        //将就绪队列中的进程调度到CPU执行的逻辑
                        runReady();
                        runIn();
                        runOut();
                        runWait();
                        showAll(allQue);//展示所有队列状态
                        saveLog("就绪队列:\t" + tAll.getText() + "\r\n");
                        showReady(readyQue);
                        saveLog("后备就绪队列:\t" + tReady.getText() + "\r\n");
                        showIn(inQue);
                        saveLog("输入队列:\t" + tInput.getText() + "\r\n");
                        showOut(outQue);
                        saveLog("输出队列:\t" + tOutput.getText() + "\r\n");
                        showWait(waitQue);
                        saveLog("等待队列:\t" + tWait.getText() + "\r\n");

                        saveLog("_______________________________");
                        count++;
                        System.out.println(count);//调度执行的次数
                        //暂停线程
                        sleep(Long.parseLong(tTime.getText()));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //调度完成
                if (allQue.size() == 0) {
                    bStart.setText("开始调度");
                    tCur.setText("");//清空当前进程的状态文本框
                    lInfo.setText("调度已完成(*^_^*)");
                    saveLog("调度已完成");
                } else {
                    lInfo.setText("调度已停止/(ㄒoㄒ)/~~");
                    saveLog("调度已停止");
                }
            }


        };
        //创建新线程
        blinker = new Thread(runnable);
        blinker.start();

    }

    //显示所有队列状态 展示每个进程剩余时间片
    private void showAll(ArrayList<PCB> allQue) {
        tAll.setText("");//清空
        //for-each 循环 遍历 allQue 队列中的每个进程（PCB 对象）
        // 计算每个进程的剩余时间片 allTime 初始0.0
        for (PCB p : allQue) {
            Double allTime = 0.0;
            // 遍历PCB对象p中的指令列表
            for (Instructions i : p.getpInstructions()) {
                //获取当前指令剩余片
                allTime += i.getIRemainTime();
            }
            //将当前进程的信息附加到已有的文本内容之后 tAll 显示所有进程的剩余时间片
            tAll.setText(tAll.getText() + "\r\n" + p.getpName()+"剩余时间片"+allTime);
        }
    }
    //遍历等待队列的PCB
    private void showWait(ArrayList<PCB> waitQue) {
        tWait.setText("");
        ///遍历 waitQue 列表中的每一个 PCB 对象
        for (PCB p : waitQue) {
            //获取每个进程的第一条指令的剩余时间片
            Double allTime = p.getpInstructions().get(0).getIRemainTime();
            //显示了每个进程的名称及其第一条指令的剩余时间片
            tWait.setText(tWait.getText() + "\r\n" + p.getpName()+"当前指令剩余时间片"+allTime);
        }
    }

    private void showOut(ArrayList<PCB> outQue) {
        tOutput.setText("");
        for (PCB p : outQue) {
            Double allTime = p.getpInstructions().get(0).getIRemainTime();
            tOutput.setText(tOutput.getText() + "\r\n" + p.getpName()+"当前指令剩余时间片"+allTime);
        }
    }

    private void showIn(ArrayList<PCB> inQue) {
        tInput.setText("");
        for (PCB p : inQue) {
            Double allTime = p.getpInstructions().get(0).getIRemainTime();
            tInput.setText(tInput.getText() + "\r\n" + p.getpName()+"当前指令剩余时间片"+allTime);
        }
    }

    private void showReady(ArrayList<PCB> que) {
        tReady.setText("");
        for (PCB p : que) {
            Double allTime = p.getpInstructions().get(0).getIRemainTime();
            tReady.setText(tReady.getText() + "\r\n" + p.getpName()+"当前指令剩余时间片"+allTime);
        }
    }

    private void initQue() {
        //把就绪进程排成一个就绪队列,即readyQue
        //I，O，W三条指令实际上是不占有CPU的，执行这三条指令就应该将进程放入对应的等待队列（输入等待队列，输出等待队列 ，其他等待队列）。
        //先清空队列
        readyQue.clear();
        inQue.clear();
        outQue.clear();
        waitQue.clear();
        //遍历存储所有进程的队列
        for (PCB p : allQue) {
            readyQue.add(p);
        }
    }

    private void runReady() {
        if (readyQue.size() > 0) {
            //检查第一个指令为输入
            if (readyQue.get(0).getpInstructions().get(0).getIName() == 'I') {
                inQue.add(readyQue.get(0));//添加到in末尾 表示该进程正在等待输入
                readyQue.remove(readyQue.get(0)); //将进程从就绪队列移除
            } else if (readyQue.get(0).getpInstructions().get(0).getIName() == 'O') {
                outQue.add(readyQue.get(0));//添加到out末尾
                readyQue.remove(readyQue.get(0));
            } else if (readyQue.get(0).getpInstructions().get(0).getIName() == 'W') {
                waitQue.add(readyQue.get(0));//添加到wait末尾
                readyQue.remove(readyQue.get(0));
            }
            //进程第一个指令为停止
            else if (readyQue.get(0).getpInstructions().get(0).getIName() == 'H') {
                //说明该进程完成
                allQue.remove(readyQue.get(0));//将所有队列移除
                readyQue.remove(readyQue.get(0));
            }
            else {
                //调度第一个进程的第一个指令
                readyQue.get(0).getpInstructions().get(0).subIRemainTime();//调度首队列一次
                //更新当前正在执行的进程的名称 并通过 tCur.setText() 显示该进程的名称
                tCur.setText(readyQue.get(0).getpName());
                if (readyQue.get(0).getpInstructions().get(0).getIRemainTime() == 0) {
                    //如果剩余时间为0,则说明运行完成
                    //把该指令移除,根据下一个指令判断队列
                    readyQue.get(0).getpInstructions().remove(0);
                    //检查下一个指令类型。如果是计算（'C'）指令，
                    // 表示该进程可以继续执行，因此将它添加回就绪队列
                    if (readyQue.get(0).getpInstructions().get(0).getIName() == 'C') {
                        readyQue.add(readyQue.get(0));//添加到ready末尾
                    } else if (readyQue.get(0).getpInstructions().get(0).getIName() == 'I') {
                        inQue.add(readyQue.get(0));//添加到in末尾
                    } else if (readyQue.get(0).getpInstructions().get(0).getIName() == 'O') {
                        outQue.add(readyQue.get(0));//添加到out末尾
                    } else if (readyQue.get(0).getpInstructions().get(0).getIName() == 'W') {
                        waitQue.add(readyQue.get(0));//添加到wait末尾
                    } else if (readyQue.get(0).getpInstructions().get(0).getIName() == 'H') {
                        //说明该进程完成
                        allQue.remove(readyQue.get(0));
                    }
                    readyQue.remove(readyQue.get(0));
                } else {
                    readyQue.add(readyQue.get(0));//移动到末尾
                    readyQue.remove(readyQue.get(0));
                }
            }
        }
    }

    private void runIn() {
        if (!inQue.isEmpty()) {
            // 遍历 inQue 中的每个 PCB
            for (int i = 0; i < inQue.size(); i++) {
                //获取队列中的第 i 个进程控制块（PCB）
                PCB p = inQue.get(i);
                // 调用首个指令的 subIRemainTime 方法
                p.getpInstructions().get(0).subIRemainTime();

                // 如果首指令的剩余时间为 0
                if (p.getpInstructions().get(0).getIRemainTime() == 0) {
                    // 移除完成的指令
                    p.getpInstructions().remove(0);

                    // 判断下一个指令并移动 PCB
                    if (!p.getpInstructions().isEmpty()) {
                        //获取下一个指令名称
                        char nextIName = p.getpInstructions().get(0).getIName();
                        switch (nextIName) {
                            case 'C':
                                readyQue.add(p); // 添加到 ready 队列
                                break;
                            case 'I':
                                inQue.add(p); // 添加到 in 队列
                                break;
                            case 'O':
                                outQue.add(p); // 添加到 out 队列
                                break;
                            case 'W':
                                waitQue.add(p); // 添加到 wait 队列
                                break;
                            case 'H':
                                // 进程完成
                                allQue.remove(p);
                                break;
                        }
                    } else {
                        // 如果指令列表为空，说明进程完成
                        allQue.remove(p);
                    }

                    // 从当前队列移除 PCB
                    inQue.remove(p);
                    i--; // 移除后列表长度减少，需要调整索引
                }
            }
        }
    }
    //出队列
    private void runOut() {
        if (!outQue.isEmpty()) {
            // 遍历 inQue 中的每个 PCB
            for (int i = 0; i < outQue.size(); i++) {
                //获取当前进程
                PCB p = outQue.get(i);
                // 调用首个指令的 subIRemainTime 方法
                p.getpInstructions().get(0).subIRemainTime();

                // 如果首指令的剩余时间为 0
                if (p.getpInstructions().get(0).getIRemainTime() == 0) {
                    // 移除完成的指令
                    p.getpInstructions().remove(0);

                    // 判断下一个指令并移动 PCB
                    if (!p.getpInstructions().isEmpty()) {
                        char nextIName = p.getpInstructions().get(0).getIName();
                        switch (nextIName) {
                            case 'C':
                                readyQue.add(p); // 添加到 ready 队列
                                break;
                            case 'I':
                                inQue.add(p); // 添加到 in 队列
                                break;
                            case 'O':
                                outQue.add(p); // 添加到 out 队列
                                break;
                            case 'W':
                                waitQue.add(p); // 添加到 wait 队列
                                break;
                            case 'H':
                                // 进程完成
                                allQue.remove(p);
                                break;
                        }
                    } else {
                        // 如果指令列表为空，说明进程完成
                        allQue.remove(p);
                    }

                    // 从当前队列移除 PCB
                    outQue.remove(p);
                    i--; // 移除后列表长度减少，需要调整索引
                }
            }
        }
    }

    private void runWait() {
        if (waitQue.size() > 0) {
            waitQue.get(0).getpInstructions().get(0).subIRemainTime();//调度首队列一次
            if (waitQue.get(0).getpInstructions().get(0).getIRemainTime() == 0) {
                //如果剩余时间为0,则说明运行完成
                //把该指令移除,根据下一个指令判断队列
                waitQue.get(0).getpInstructions().remove(0);
                if (waitQue.get(0).getpInstructions().get(0).getIName() == 'C') {
                    readyQue.add(waitQue.get(0));//添加到ready末尾
                } else if (waitQue.get(0).getpInstructions().get(0).getIName() == 'I') {
                    inQue.add(waitQue.get(0));//添加到in末尾
                } else if (waitQue.get(0).getpInstructions().get(0).getIName() == 'O') {
                    outQue.add(waitQue.get(0));//添加到out末尾
                } else if (waitQue.get(0).getpInstructions().get(0).getIName() == 'W') {
                    waitQue.add(waitQue.get(0));//添加到wait末尾
                } else if (waitQue.get(0).getpInstructions().get(0).getIName() == 'H') {
                    //说明该进程完成
                    allQue.remove(waitQue.get(0));
                }
                waitQue.remove(waitQue.get(0));
            } else {
                waitQue.add(waitQue.get(0));//移动到末尾
                waitQue.remove(waitQue.get(0));
            }
        }
    }

    private void readFile() {
        //读取文件
        if (file != null) {
            try {
                //读取字符输入流 逐行读取文件内容
                BufferedReader in = new BufferedReader(new FileReader(file));
                String str;
                allQue.clear();//清空
                PCB pcb = null;//存储当前正在创建的进程控制块
                //逐行读取文件内容，直到文件末尾
                while ((str = in.readLine()) != null) {
                    //当前行以p开头
                    if (str.charAt(0) == 'P') {
                        //创建新的进程
                        pcb = new PCB();
                        pcb.setpName(str);
                    } else {
                        //创建新的指令集
                        Instructions instructions = new Instructions();
                        instructions.setIName(str.charAt(0));
                        instructions.setIRuntime(parseDouble(str.substring(1)));
                        instructions.setIRemainTime(instructions.getIRuntime());//刚开始剩余时间与运行时间一致
                        assert pcb != null;
                        pcb.getpInstructions().add(instructions);
                        if (instructions.getIName() == 'H') {
                            //H代表当前进程结束,添加到就绪队列
                            allQue.add(pcb);
                        }

                    }
                }
            } catch (IOException e) {
                System.out.println("文件读取错误!");
            }
        }
    }

    private void chooseFile() {
        //选择文件
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
        JFileChooser jfc = new JFileChooser(".");//当前目录下
        jfc.setFileFilter(filter);
        jfc.setMultiSelectionEnabled(false);//不允许多选
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = jfc.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            //JFileChooser.APPROVE_OPTION是个整型常量，代表0。
            // 就是说当返回0的值我们才执行相关操作，否则什么也不做。
            file = jfc.getSelectedFile();
        }

    }

    private void init() {
        //界面初始化
        JFrame jFrame = new JFrame();
        jFrame.setTitle("时间片轮转调度");
        Container con = jFrame.getContentPane();
        con.setLayout(null);
        bFIle = new JButton("打开文件");
        bFIle.setBounds(50, 50, 150, 30);

        bStart = new JButton("开始调度");
        bStart.setBounds(220, 50, 150, 30);

        bStop = new JButton("暂停调度");
        bStop.setBounds(390, 50, 150, 30);

        JLabel ltime = new JLabel("时间片大小:");
        ltime.setBounds(560, 50, 80, 30);
        tTime = new JTextField();
        tTime.setBounds(650, 50, 150, 30);
        tTime.setText("500");

        JLabel lCur = new JLabel("当前运行进程:");
        lCur.setBounds(50, 100, 150, 30);
        tCur = new JTextField();
        tCur.setBounds(50, 130, 150, 30);

        lInfo = new JLabel("");//提示信息
        lInfo.setBounds(350, 130, 300, 30);
        lInfo.setForeground(Color.red);


        JLabel lall = new JLabel("就绪队列:");
        lall.setBounds(50, 200, 150, 30);
        tAll = new JTextArea(6, 4);
        tAll.setBounds(50, 230, 150, 250);

        JLabel lr = new JLabel("后备就绪队列:");
        lr.setBounds(220, 200, 150, 30);
        tReady = new JTextArea(6, 4);
        tReady.setBounds(220, 230, 150, 250);

        JLabel lin = new JLabel("输入等待队列:");
        lin.setBounds(390, 200, 150, 30);
        tInput = new JTextArea(6, 4);
        tInput.setBounds(390, 230, 150, 250);

        JLabel lout = new JLabel("输出等待队列:");
        lout.setBounds(560, 200, 150, 30);
        tOutput = new JTextArea(6, 4);
        tOutput.setBounds(560, 230, 150, 250);

        JLabel lw = new JLabel("其他等待队列:");
        lw.setBounds(730, 200, 150, 30);
        tWait = new JTextArea(6, 4);
        tWait.setBounds(730, 230, 150, 250);

        con.add(bFIle);
        con.add(bStart);
        con.add(bStop);
        con.add(ltime);
        con.add(tTime);
        con.add(lCur);
        con.add(tCur);
        con.add(lInfo);
        con.add(lall);
        con.add(tAll);
        con.add(lr);
        con.add(tReady);
        con.add(lin);
        con.add(tInput);
        con.add(lout);
        con.add(tOutput);
        con.add(lw);
        con.add(tWait);
        jFrame.setBounds(200, 200, 1000, 600);  //设置窗口的属性 窗口位置以及窗口的大小
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }


    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();
    }

}
