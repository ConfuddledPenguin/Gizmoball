package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.IModel;
import model.Model;
import model.gizmos.IGizmo;

public class BuildBoard extends Board {

	private static final long serialVersionUID = -4952517095084067303L;
	private static final int columnCount = 30;
	private static final int rowCount = 30;


	private List<Rectangle> cells;

	private Point selectedCell;
	private Point clickedCell;
	
	private IModel model;
	
	public BuildBoard(IModel m, ActionListener listener) {
		
		model = m;
		
		cells = new ArrayList<>(columnCount * rowCount);
		gizmoList = new ArrayList<IGizmo>();
		m.addObserver(this);
		
		
		final JPopupMenu popup = createPopupMenu(listener);
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				int width = getWidth();
				int height = getHeight();

				int cellWidth = width / columnCount;
				int cellHeight = height / rowCount;

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
				
				int cellWidth = width / columnCount;
				int cellHeight = height / rowCount;
				
				int column = e.getX() / cellWidth;
				int row = e.getY() / cellHeight;
				
				if (clickedCell != null) {
					if (clickedCell.x == column && clickedCell.y == row) {
						// cell was already clicked so un-select it
						clickedCell = null;
					}
					else {
						clickedCell = new Point(column, row);
					}
				}
				else {
					clickedCell = new Point(column, row);
				}
				System.out.format("pressed column: %d, row: %d\n", column, row);
				
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
					if (clickedCell == null) mousePressed(e);
				}
			}
		});

	}
	
	private JPopupMenu createPopupMenu(ActionListener listener){
		
		JPopupMenu popup = new JPopupMenu();
		
		JMenu AddGizmo = new JMenu("Add");
		
		JMenuItem square = new JMenuItem("Square");
		square.addActionListener(listener);
		AddGizmo.add(square);
		
		JMenuItem circle = new JMenuItem("Circle");
		circle.addActionListener(listener);
		AddGizmo.add(circle);
		
		JMenuItem triangle = new JMenuItem("Triangle");
		triangle.addActionListener(listener);		
		AddGizmo.add(triangle);
		
		popup.add(AddGizmo);
		
		JMenu rotate = new JMenu("Rotate");
		
		JMenuItem clockwise = new JMenuItem("Clockwise");
		clockwise.addActionListener(listener);
		rotate.add(clockwise);
		
		JMenuItem aClockwise = new JMenuItem("Anti-Clockwise");
		aClockwise.addActionListener(listener);
		rotate.add(aClockwise);
		
		popup.add(rotate);	

		JMenuItem jm2 = new JMenuItem("Delete");
		jm2.addActionListener(listener);
		popup.add(jm2);
		
		return popup;

	}
	
	public Point getSelectedCell() {
		return selectedCell;
	}
	
	public Point getclickedCell() {
		return clickedCell;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
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

		int cellWidth = width / columnCount;
		int cellHeight = height / rowCount;

		int xOffset = (width - (columnCount * cellWidth)) / 2;
		int yOffset = (height - (rowCount * cellHeight)) / 2;

		if (cells.isEmpty()) {
			for (int row = 0; row < rowCount; row++) {
				for (int col = 0; col < columnCount; col++) {
					Rectangle cell = new Rectangle(xOffset + (col * cellWidth),
							yOffset + (row * cellHeight), cellWidth, cellHeight);
					cells.add(cell);
				}
			}
		}

		if (selectedCell != null) {
			int index = selectedCell.x + (selectedCell.y * columnCount);
			Rectangle cell = cells.get(index);
			g2d.setColor(Color.YELLOW);
			g2d.fill(cell);
		}
		
		if (clickedCell != null) {
			int index = clickedCell.x + (clickedCell.y * columnCount);
			Rectangle cell = cells.get(index);
			g2d.setColor(Color.RED);
			g2d.fill(cell);
		}

		g2d.setColor(Color.GRAY);
		for (Rectangle cell : cells) {
			g2d.draw(cell);
		}

		g2d.setColor(Color.BLUE);
		
		drawGizmos(g2d);

		g2d.dispose();

	}
	
	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof IGizmo) {
			if(!gizmoList.contains(arg))
				gizmoList.add((IGizmo) arg);
			else
				gizmoList.remove(arg);
		} else if(arg == null){
			gizmoList = model.getBoard().getGizmos();
		}

		repaint();
	}
}
