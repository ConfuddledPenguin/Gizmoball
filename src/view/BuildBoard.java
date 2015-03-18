package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.Global;
import model.IBall;
import model.Model;
import model.gizmos.IGizmo;

public class BuildBoard extends Board {

	private static final long serialVersionUID = -4952517095084067303L;
	private List<Rectangle> cells;

	private boolean moving = false;
	private Point selectedCell;
	private Point clickedCell;
	private Point moveTarget;
	
	public BuildBoard(Model m, final ActionListener listener) {

		super(m);
		
		cells = new ArrayList<>(Global.BOARDWIDTH * Global.BOARDHEIGHT);
		m.addObserver(this);
		moveTarget = new Point (0,0);

		final JPopupMenu emptyPopup = createEmptyPopupMenu(listener);
		final JPopupMenu gizmoPopup = createGizmoPopupMenu(listener);

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				int width = getWidth();
				int height = getHeight();

				int cellWidth = width / Global.BOARDWIDTH;
				int cellHeight = height / Global.BOARDHEIGHT;

				int column = e.getX() / cellWidth;
				int row = e.getY() / cellHeight;

				selectedCell = new Point(column, row);
				repaint();

			}

		});

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				int width = getWidth();
				int height = getHeight();

				int cellWidth = width / Global.BOARDWIDTH;
				int cellHeight = height / Global.BOARDHEIGHT;
				int column = e.getX() / cellWidth;
				int row = e.getY() / cellHeight;

				clickedCell = new Point(column, row);
				repaint();
				moving = true;
				
				// popup menus are triggered in mousePressed rather than mouseReleased on Linux
				// so we check for popup triggers in both methods to ensure cross platform compatibility 
				if (e.isPopupTrigger()) {
					Point p = new Point(e.getX() /20, e.getY()/20);
					if(model.getGizmo(p) == null){
						emptyPopup.show(e.getComponent(), e.getX(), e.getY());
					}else{
						gizmoPopup.show(e.getComponent(), e.getX(), e.getY());
					}
					if (clickedCell == null)
						mousePressed(e);
				}
				
			}

			public void mouseReleased(MouseEvent e) {

				if (e.isPopupTrigger()) {
					Point p = new Point(e.getX() /20, e.getY() /20);
					if(model.getGizmo(p) == null){
						emptyPopup.show(e.getComponent(), e.getX(), e.getY());
					}else{
						gizmoPopup.show(e.getComponent(), e.getX(), e.getY());
					}
					if (clickedCell == null)
						mousePressed(e);
				}
				moving = false;
			}

		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				moveTarget = e.getPoint();	
				listener.actionPerformed(new ActionEvent(this, 0, "Move"));
				repaint();
				
				if(moving) clickedCell = getMousePt();
			}
		});
	}

	private JPopupMenu createGizmoPopupMenu(ActionListener listener) {

		JPopupMenu popup = new JPopupMenu();

		JMenu rotate = new JMenu("Rotate");

		JMenuItem clockwise = new JMenuItem("Clockwise");
		clockwise.addActionListener(listener);
		rotate.add(clockwise);

		JMenuItem aClockwise = new JMenuItem("Anti-Clockwise");
		aClockwise.addActionListener(listener);
		rotate.add(aClockwise);

		popup.add(rotate);
		
		JMenu connect = new JMenu("Connect");
		
		JMenuItem connectGizmo = new JMenuItem("Connect Gizmo to Gizmo");
		connectGizmo.addActionListener(listener);
		connect.add(connectGizmo);
		
		JMenuItem connectKey = new JMenuItem("Connect Key to Gizmo");
		connectKey.addActionListener(listener);
		connect.add(connectKey);
		
		popup.add(connect);
		
		JMenu disConnect = new JMenu("Disconnect");
		
		JMenuItem disConnectGizmo = new JMenuItem("Disconnect Gizmo to Gizmo");
		disConnectGizmo.addActionListener(listener);
		disConnect.add(disConnectGizmo);
		
		JMenuItem disConnectKey = new JMenuItem("Disconnect Key to Gizmo");
		disConnectKey.addActionListener(listener);
		disConnect.add(disConnectKey);
		
		popup.add(disConnect);

		JMenuItem jm2 = new JMenuItem("Delete");
		jm2.addActionListener(listener);
		popup.add(jm2);

		return popup;
	}
	
	private JPopupMenu createEmptyPopupMenu(ActionListener listener) {

		JPopupMenu popup = new JPopupMenu();

		JMenu addGizmo = new JMenu("Add Gizmo");

		JMenuItem square = new JMenuItem("Square");
		square.addActionListener(listener);
		addGizmo.add(square);

		JMenuItem circle = new JMenuItem("Circle");
		circle.addActionListener(listener);
		addGizmo.add(circle);

		JMenuItem triangle = new JMenuItem("Triangle");
		triangle.addActionListener(listener);
		addGizmo.add(triangle);
		
		JMenu addFlipper = new JMenu("Flipper");
		
		JMenuItem leftTriangle = new JMenuItem("Left Flipper");
		leftTriangle.addActionListener(listener);
		addFlipper.add(leftTriangle);
		
		JMenuItem rightTriangle = new JMenuItem("Right Flipper");
		rightTriangle.addActionListener(listener);
		addFlipper.add(rightTriangle);
		
		addGizmo.add(addFlipper);
		
		popup.add(addGizmo);
		
		JMenuItem AddBall = new JMenuItem("Ball");
		AddBall.addActionListener(listener);
		addGizmo.add(AddBall);

		popup.add(AddBall);
		

//		JMenu rotate = new JMenu("Rotate");
//
//		JMenuItem clockwise = new JMenuItem("Clockwise");
//		clockwise.addActionListener(listener);
//		rotate.add(clockwise);
//
//		JMenuItem aClockwise = new JMenuItem("Anti-Clockwise");
//		aClockwise.addActionListener(listener);
//		rotate.add(aClockwise);
//
//		popup.add(rotate);
//
//		JMenuItem jm2 = new JMenuItem("Delete");
//		jm2.addActionListener(listener);
//		popup.add(jm2);

		return popup;

	}

	public Point getSelectedCell() {
		return selectedCell;
	}

	public Point getclickedCell() {
		return clickedCell;
	}
	
	public Point getMousePt() {
		
		int width = getWidth();
		int height = getHeight();

		int cellWidth = width / Global.BOARDWIDTH;
		int cellHeight = height / Global.BOARDHEIGHT;
		
		int column = (int) (moveTarget.getX() / cellWidth);
		int row = (int) (moveTarget.getY() / cellHeight);		
		return new Point(column, row);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension((Global.L*Global.BOARDWIDTH), (Global.L*Global.BOARDHEIGHT));
	}

	@Override
	public void invalidate() {
		cells.clear();
		selectedCell = null;
		super.invalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		int width = getWidth();
		int height = getHeight();


		int cellWidth = width / Global.BOARDWIDTH;
		int cellHeight = height / Global.BOARDHEIGHT;

		int xOffset = (width - (Global.BOARDWIDTH * cellWidth)) / 2;
		int yOffset = (height - (Global.BOARDHEIGHT * cellHeight)) / 2;

		if (cells.isEmpty()) {
			for (int row = 0; row < Global.BOARDHEIGHT; row++) {
				for (int col = 0; col < Global.BOARDWIDTH; col++) {
					Rectangle cell = new Rectangle(xOffset + (col * cellWidth),
							yOffset + (row * cellHeight), cellWidth, cellHeight);
					cells.add(cell);
				}
			}
		}

		if (selectedCell != null) {
			int index = selectedCell.x + (selectedCell.y * Global.BOARDWIDTH);
			Rectangle cell = cells.get(index);
			g2d.setColor(Color.YELLOW);
			g2d.fill(cell);
		}

		if (clickedCell != null) {
			int index = clickedCell.x + (clickedCell.y * Global.BOARDWIDTH);
			Rectangle cell = cells.get(index);
			g2d.setColor(Color.RED);
			g2d.fill(cell);
		}

		g2d.setColor(Color.GRAY);
		for (Rectangle cell : cells) {
			g2d.draw(cell);
		}

		g2d.setColor(Color.BLUE);
		
		for (IBall ball: balls){
			g.setColor(new Color(148,0,211));
			int x = (int) ((ball.getX() * Global.L) - (ball.getRadius()*Global.L));
			int y = (int) ((ball.getY() * Global.L) - (ball.getRadius()*Global.L));
			int widthBall = (int) (Global.L * (ball.getRadius() * 2));
			g.fillOval(x, y, widthBall, widthBall);
		}

		drawGizmos(g2d);

		g2d.dispose();
	}
	
	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof IGizmo) {
			if (!gizmoList.contains(arg))
				gizmoList.add((IGizmo) arg);
			else
				gizmoList.remove(arg);
		} else if(arg instanceof List<?>){
			
			gizmoList = new ArrayList<>((List<IGizmo>) arg);
		}

		repaint();
	}
}
