package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.BorderFactory;

import model.Ball;
import model.Global;
import model.IBall;
import model.IModel;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;

public class RunBoard extends Board {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	
	public RunBoard(int w, int h, IModel m) {
		
		super(m);
		
		width = w;
		height = h;
		m.addObserver(this);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(width, height));
		
		m.addBall(19.5,18.5,0,-50);
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
			g2.setColor(new Color(148,0,211));
			int x = (int) ((ball.getX() * Global.L) - (ball.getRadius()*Global.L));
			int y = (int) ((ball.getY() * Global.L) - (ball.getRadius()*Global.L));
			int width = (int) (Global.L * (ball.getRadius() * 2));
			g2.fillOval(x, y, width, width);
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof Ball)
			this.ball = (IBall) arg;

		if (arg instanceof Gizmo) {
			gizmoList.add((Gizmo) arg);
		} else if(arg instanceof List<?>){
			
			gizmoList = new ArrayList<IGizmo>( (List<IGizmo>) arg);
		}

		repaint();
	}
}
