package view;

import interfaces.IView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Model;

public  class Board extends JPanel implements Observer, IView {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;

	public Board(int w, int h, Model m) {
		
		width = w;
		height = h;
		
		m.addObserver(this);
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setPreferredSize(new Dimension(width, height));
	}

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	private void addGizmo(){
		
	}
	
	private void addBall(){
		
	}
	
	private void addFlipper(){
		
	}
	
	private void addAbsorber(){
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		/*if(arg instanceof Gizmo)
			addGizmo(arg);
		else if(arg instanceof Ball)
			addBall(arg);
		else if(arg instanceof Flipper)
			addFlipper(arg);
		else if(arg instanceof Absorber)
			addAbsorber(arg);*/
	}
}
