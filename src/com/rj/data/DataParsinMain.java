package com.rj.data;

public class DataParsinMain { 
    
    public static void main(String[] args) {
    	
		while (true) {
			try {
				new DataParsingThread().dataParsing();			
				//System.out.println("6");
				Thread.sleep(1000 * 60 * 7);//��7����
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("����ִ�г���");
			}
		}
    }

} 
			
		
