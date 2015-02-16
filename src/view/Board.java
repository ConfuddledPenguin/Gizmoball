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

import model.Model;
import model.gizmos.IGizmo;

public  class Board extends JPanel implements Observer, IView {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected List<IGizmo> gizmoList;
	
	public Board(int w, int h, Model m) {
		
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
		if(!gizmoList.isEmpty()){
			for (IGizmo gizmo : gizmoList){
				g2.fillRect(gizmo.getPos().x, gizmo.getPos().y,gizmo.getWidth(),gizmo.getHeight());
			}
	}
}
	
	@Override
	public void update(Observable o, Object arg) {
	
		if (arg instanceof IGizmo)
			gizmoList.add((IGizmo)arg);
		
		repaint();
	}
}
