package com.jinrl.powermodule.common;

public class Error {

	public static void outs(String string, String string2) {
		System.out.print(string);		
	}

	public static void outs(String string) {
		System.out.print(string);			
	}

	public static void oute(Exception e, String string) {
		System.out.print(string);	
		
	}

	public static void oute(Exception e) {
		e.printStackTrace();	
		
	}



}
