package com.rj.data;

import java.io.IOException;
import java.io.InputStream;

public class InvokeBat {
    public void runbat(String batName) {
        try {
            Process ps = Runtime.getRuntime().exec(batName);
            InputStream in = ps.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
           //     System.out.print(c);// ����㲻��Ҫ����������п���ע����
            }
            in.close();
            ps.waitFor();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("child thread done");
    }
//    public static void main(String[] args) {
//        InvokeBat test1 = new InvokeBat();
//        String batName = "E:\\SupermapExec\\auto_air_day.bat";
//        test1.runbat(batName);
//        System.out.println("main thread");
//    }
    
}