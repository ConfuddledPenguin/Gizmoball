package model;

public class Global {
	
	public static int BOARDHEIGHT = 20;
	public static int BOARDWIDTH = 20;

	public static int L = 20;  // 1 L = 20 pixels
	
	/**
	 * Expressed in terms of L
	 */
	public static double GRAVITY = 25;
	/**
	 * Expressed in terms of seconds
	 */
	static double FRICTIONMU = 0.025;
	/**
	 * Expressed in terms of L
	 */
	static double FRICTIONMU2 = 0.025;
	
	public static double REFREASHRATE = 50;
	
	public static double REFRESHTIME = 1000/50; // 50fps in ms
	
	public static double MOVETIME = REFRESHTIME / 1000; // in seconds
	
	public static Boolean discoMode = false;
	
	public static Boolean raveMode = false;

}
