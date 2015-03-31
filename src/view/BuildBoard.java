package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.Global;
import model.IBall;
import model.IModel;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;
import controller.ConnectGizmoListener;
import controller.ConnectKeyListener;
import controller.MouseClickedListener;
import controller.MouseDraggedListener;
import controller.MouseMovementListener;

/**
 * 
 * A class creating a Build Board for the user to create or edit Gizmoball games
 * 
 * @author Andrew Scott
 *
 */

public class BuildBoard extends Board {

	private static final long serialVersionUID = -4952517095084067303L;
	private List<Rectangle> cells;

	private boolean moving = false;
	private ActionListener connectingGizmos = null;
	private ActionListener connectingKey = null;
	private Point absorberStart = null;
	private Point selectedCell;
	private Point clickedCell;
	private Point moveTarget;
	private IModel model;
	private GUI ui;
	
	/**
	 * Constructor
	 * 
	 * @param m : Game Model
	 * @param listener : Event Listener
	 * @param ui : Parent GUI
	 */
	public BuildBoard(final IModel m, final ActionListener listener, final GUI ui) {

		super(m);
		this.model = m;
		this.ui = ui;
		
		cells = new ArrayList<>(Global.BOARDWIDTH * Global.BOARDHEIGHT);
		m.addObserver(this);
		moveTarget = new Point (0,0);

		addMouseMotionListener(new MouseMovementListener(this));

		addMouseListener(new MouseClickedListener(this, model, ui, listener));

		addMouseMotionListener(new MouseDraggedListener(this));
	}
	/**
	 * Builds the popup context menu for a gizmo
	 * 
	 * @param listener The listener to use to listen for events
	 * @param g The gizmo to build for
	 * @return The menu
	 */
	public JPopupMenu createGizmoPopupMenu(ActionListener listener, IGizmo g) {

		JPopupMenu popup = new JPopupMenu();
		
		if(g.getType() != Gizmo.Type.Absorber){
			JMenu rotate = new JMenu("Rotate");

			JMenuItem clockwise = new JMenuItem("Clockwise");
			clockwise.addActionListener(listener);
			rotate.add(clockwise);

			JMenuItem aClockwise = new JMenuItem("Anti-Clockwise");
			aClockwise.addActionListener(listener);
			rotate.add(aClockwise);

			popup.add(rotate);
		}
		
		JMenu connect = new JMenu("Connect");
		
		JMenuItem connectGizmo = new JMenuItem("Connect Gizmo to Gizmo");
		connectGizmo.addActionListener( new ConnectGizmoListener(g, ui, model));
		connect.add(connectGizmo);
		
		JMenuItem connectKey = new JMenuItem("Connect Key to Gizmo");
		connectKey.addActionListener( new ConnectKeyListener(g, ui, model));
		connect.add(connectKey);
		
		popup.add(connect);
		
		JMenu disConnect = new JMenu("Disconnect");
		
		JMenu disConnectGizmo = new JMenu("Disconnect Gizmo to Gizmo");
		
		for(IGizmo g2: g.getConnections()){
			
			JMenuItem gItem = new JMenuItem(g2.getType().toString() + " at " + g2.getXPos() + ":" + g2.getYPos());
			gItem.addActionListener(new controller.DisconnectGizmoListener(model, g, g2));
			disConnectGizmo.add(gItem);
		}
		
		disConnect.add(disConnectGizmo);
		
		JMenu disConnectKey = new JMenu("Disconnect Key to Gizmo");
		
		/*
		 * I don't even what to think about this piece of code, lets just pretend
		 * it doesn't exist.
		 * 
		 * There isn't the world a better place?
		 */
		for(Entry<Integer, HashSet<IGizmo>> connections: model.getKeyStrokes().entrySet()){
			
			for(IGizmo g2: connections.getValue()){
				
				if(g2 == g){
					JMenuItem keyItem = new JMenuItem("Key " + KeyEvent.getKeyText(connections.getKey()));
					int keyValue = connections.getKey(); // get integer value of key
					keyItem.addActionListener(new controller.DisconnectKeyListener(model, keyValue, g2));
					disConnectKey.add(keyItem);
				}
			}
		}
		
		disConnect.add(disConnectKey);
				
		popup.add(disConnect);

		if(g.getType() == Gizmo.Type.Absorber){
			
			JMenuItem exitV = new JMenuItem("Set Exit Velocity");
			exitV.addActionListener(listener);
			popup.add(exitV);
			
		}
		
		JMenuItem jm2 = new JMenuItem("Delete");
		jm2.addActionListener(listener);
		popup.add(jm2);

		return popup;
	}
	
	/**
	 * Create a popup context menu for an empty area
	 * 
	 * @param listener The listener to use for events
	 * 
	 * @return The menu
	 */
	public JPopupMenu createEmptyPopupMenu(ActionListener listener) {

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
		
		JMenuItem absorber = new JMenuItem("Absorber");
		absorber.addActionListener(listener);
		addGizmo.add(absorber);
		
		
		
		popup.add(addGizmo);
		
		JMenuItem AddBall = new JMenuItem("Add Ball");
		AddBall.addActionListener(listener);
		addGizmo.add(AddBall);

		popup.add(AddBall);

		return popup;

	}
	
	/**
	 * Creates a popup context menu for when there is a ball selected
	 * 
	 * @param listener The listener to use
	 * @param b The ball to build for
	 * @return the menu
	 */
	public JPopupMenu createBallPopupMenu(ActionListener listener, IBall b) {

		JPopupMenu popup = new JPopupMenu();

		JMenuItem jm = new JMenuItem("Set Velocity");
		jm.addActionListener(listener);
		popup.add(jm);
		
		JMenuItem jm2 = new JMenuItem("Delete");
		jm2.addActionListener(listener);
		popup.add(jm2);

		return popup;

	}

	/**
	 * Returns the selected cell
	 * 
	 * @return this.selectedCell
	 */
	public Point getSelectedCell() {
		return selectedCell;
	}

	/**
	 * Returns the clicked cell
	 * 
	 * @return this.clickedCell
	 */
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
	
	/**
	 * Sets the starting point for an absorber definition by the user.
	 * @param p the starting point in the grid
	 */
	public void setAbsorberStart(Point p) {
		moveTarget = p;
		absorberStart = p;
	}
	
	/**
	 * Get the absorber starting point
	 * 
	 * @return The point
	 */
	public Point getAbsorberStart() {
		return absorberStart;
	}
	
	/**
	 * Let the ui know that we are connecting gizmos
	 */
	public void connectingGizmos(ActionListener listener){
		connectingGizmos = listener;
	}
	
	/**
	 * Cancel connecting the gizmos
	 */
	public void cancelGizmoConnect(){
		connectingGizmos = null;
	}
	
	/**
	 * Get the listener responsible for the gizmo connecting
	 * 
	 * @return The listener
	 */
	public ActionListener getConnectingGizmos(){
		return connectingGizmos;
	}
	
	public void setConnectingGizmo(ActionListener listener){
		connectingGizmos = listener;
	}
	
	public void setConnectingKey(ActionListener listener) {
		connectingKey = listener;
		
	}
	
	public Point getMoveTarget(){
		return moveTarget;
	}
	
	public void setMoveTarget(Point p){
		moveTarget = p;
	}
	
	/**
	 * Set the selected cell
	 * @param p the cell selected
	 */
	public void setSelectedCell(Point p){
		selectedCell = p;
	}
	
	/**
	 * Set the clicked cell
	 * 
	 * @param clickedCell the clicked cell
	 */
	public void setClickedCell(Point clickedCell) {
		this.clickedCell = clickedCell;
	}
	
	/**
	 * Moving a gizmo? let me know
	 * 
	 * @param value tru for moving, otherwise false
	 */
	public void setGizmoMoving(boolean value){
		moving = value;
	}
	
	/**
	 * Get whether a gizmo is being moved;
	 * 
	 * @return this.moving
	 */
	public boolean isGizmoMoving(){
		return moving;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);

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
			g2d.setColor(Color.GRAY);
			g2d.fill(cell);
		}

		if (clickedCell != null) {
			int index = clickedCell.x + (clickedCell.y * Global.BOARDWIDTH);
			Rectangle cell = cells.get(index);
			g2d.setColor(Color.MAGENTA);
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
	
	@SuppressWarnings("unchecked")
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
	
	public ActionListener getConnectingKey() {
		return connectingKey;
	}
	
	public void cancelKeyConnect() {
		connectingKey = null;
	}
	
}
