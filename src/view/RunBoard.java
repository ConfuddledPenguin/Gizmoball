package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import model.Ball;
import model.Global;
import model.IBall;
import model.IModel;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;

/**
 * 
 * This class creates a board for running a game of GizmoBall
 * 
 * @author Andrew Scott
 *
 */
public class RunBoard extends Board implements IRunBoard {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected int firstImg = 0;
	
	/**
	 * Constructor 
	 * @param w : Width of Board
	 * @param h : Height of Board
	 * @param m : Game Model
	 */
	public RunBoard(int w, int h, IModel m) {
		
		super(m);
		
		width = w;
		height = h;
		m.addObserver(this);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(width, height));
	}

	/* (non-Javadoc)
	 * @see view.IRunBoard#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	/* (non-Javadoc)
	 * @see view.IRunBoard#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(this.colour[0],this.colour[1],this.colour[2]));
		
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,255));
		
		
		if(Global.discoMode){
			Image img;
			try {
				if(firstImg <5){
					img = ImageIO.read(this.getClass().getResource("images/discoball21.png"));
					g2.drawImage(img, (Global.BOARDWIDTH*Global.L)/2-125, 0, 250, 250, null);
					firstImg++;
				} else {
					img = ImageIO.read(this.getClass().getResource("images/discoball22.png"));
					g2.drawImage(img, (Global.BOARDWIDTH*Global.L)/2-125, 0, 250, 250, null);
					if(firstImg <10){
						firstImg++;
					} else {
						firstImg = 0;
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		drawGizmos(g2);
		
		for (IBall ball: balls){
			g2.setColor(new Color(148,0,211));
			int x = (int) ((ball.getX() * Global.L) - (ball.getRadius()*Global.L));
			int y = (int) ((ball.getY() * Global.L) - (ball.getRadius()*Global.L));
			int width = (int) (Global.L * (ball.getRadius() * 2));
			g2.fillOval(x, y, width, width);
		}
		
	}

	/* (non-Javadoc)
	 * @see view.IRunBoard#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void update(Observable o, Object arg) {

		if (arg instanceof Ball)
			if (!balls.contains(arg))
				balls.add((IBall) arg);
			else
				balls.remove(arg);
		if (arg instanceof Gizmo) {
			gizmoList.add((Gizmo) arg);
		} else if(arg instanceof List<?>){
			
			gizmoList = new ArrayList<IGizmo>( (List<IGizmo>) arg);
		}

		repaint();
	}
}
