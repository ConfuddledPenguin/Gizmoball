package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.BorderFactory;

import model.Ball;
import model.IBall;
import model.Model;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;

public  class RunBoard extends Board {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected IBall ball;
	
	public RunBoard(int w, int h, Model m) {
		
		width = w;
		height = h;
		gizmoList = new ArrayList<IGizmo>();
		m.addObserver(this);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(width, height));
	}

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,255));
		drawGizmos(g2);
		
		if (ball != null) {
			g2.setColor(ball.getColour());
			int x = (int) ((ball.getX() * 20) - ball.getRadius());
			int y = (int) ((ball.getY() * 20) - ball.getRadius());
			int width = (int) (20 * ball.getRadius());
			g2.fillOval(x, y, width, width);
		} 
		
		
}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Ball)
			this.ball = (IBall)arg;
			
		if (arg instanceof Gizmo){
			gizmoList.add((Gizmo)arg);
			//System.out.print("GIZMO");
		}
	
		repaint();
	}
}
