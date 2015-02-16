package view;

import interfaces.IView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Model;s

public  class Board extends JPanel implements Observer, IView {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected List<Shape> gizmoList;
	
	public Board(int w, int h, Model m) {
		
		width = w;
		height = h;
		gizmoList = new ArrayList<Shape>();
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
			for (int i = 0; i < gizmoList.size(); i++){
				g2.draw(gizmoList.get(i));
				g2.fill(gizmoList.get(i));
			}
	}
}
	
	@Override
	public void update(Observable o, Object arg) {
	
		if (arg instanceof Shape)
			gizmoList.add((Shape)arg);
		
		repaint();
	}
}
