package model;

public class Global {
	
	public static int BOARDHEIGHT = 20;
	public static int BOARDWIDTH = 20;

	static int L = 20;  // 1 L = 20 pixels
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
	
	public static double REFRESHTIME = 50; // 50fps
	
	public static double MOVETIME = REFRESHTIME / 1000;
	
	Global(int boardHeight, int boardWidth) {
		
		Global.BOARDHEIGHT = boardHeight;
		Global.BOARDWIDTH = boardWidth;
	}
	

}
