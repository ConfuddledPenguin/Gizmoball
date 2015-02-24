package model;

public class Global {
	
	static int BOARDHEIGHT = 20;
	static int BOARDWIDTH = 20;
	static int L = 20;  // 1 L = 20 pixels
	/**
	 * Expressed in terms of L
	 */
	public static double GRAVITY = -25; //16.0;
	/**
	 * Expressed in terms of seconds
	 */
	static double FRICTIONMU = 0.025;
	/**
	 * Expressed in terms of L
	 */
	static double FRICTIONMU2 = 0.026;
	
	public static double REFRESHTIME = 16.66666666666666667 * 6;
	
	Global(int boardHeight, int boardWidth) {
		
		Global.BOARDHEIGHT = boardHeight;
		Global.BOARDWIDTH = boardWidth;
	}
	

}
