package view;

import interfaces.IView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Ball;
import model.IBall;
import model.Model;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;

public  class RunBoard extends JPanel implements Observer, IView {

	private static final long serialVersionUID = 1L;
	private static final int L = 20;
	private Model model;
	protected int width;
	protected int height;
	protected IBall ball;
	protected List<IGizmo> gizmoList;
	
	public RunBoard(int w, int h, Model m) {
		
		width = w;
		height = h;
		model = m;
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

		if(!gizmoList.isEmpty()){
			for (int i=0; i < gizmoList.size(); i++){
				IGizmo gizmo = gizmoList.get(i);
				g2.fillRect(gizmo.getXPos()*L,gizmo.getYPos()*L,gizmo.getWidth()*L,gizmo.getHeight()*L);
			}
		
		}
		
		ball = model.getBall();
		if (ball != null) {
			g2.setColor(ball.getColour());
			int x = (int) ((ball.getX() * 20) - ball.getRadius());
			int y = (int) ((ball.getY() * 20) - ball.getRadius());
			int width = (int) (2 * ball.getRadius());
			g2.fillOval(x, y, width, width);
		}	
}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Ball)
			this.ball = (IBall)arg;
			
		if (arg instanceof Gizmo){
			gizmoList.add((Gizmo)arg);
			System.out.print("GIZMO");
		}
		
		repaint();
	}
}
