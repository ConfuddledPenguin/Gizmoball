package model;

class Global {
	
	public static int BOARDHEIGHT = 30;
	public static int BOARDWIDTH = 30;
	/**
	 * Expressed in terms of L
	 */
	public static double GRAVITY = 16.0;
	/**
	 * Expressed in terms of seconds
	 */
	public static double FRICTIONMU = 0.025;
	/**
	 * Expressed in terms of L
	 */
	public static double FRICTIONMU2 = 0.026;
	
	public Global(int boardHeight, int boardWidth) {
		
		Global.BOARDHEIGHT = boardHeight;
		Global.BOARDWIDTH = boardWidth;
	}
	

}
