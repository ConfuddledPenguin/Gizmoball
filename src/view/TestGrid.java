package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import model.Model;
import model.gizmos.Circle;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;
import model.gizmos.Square;

public class TestGrid extends JPanel implements Observer {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4952517095084067303L;
	private int columnCount = 30;
    private int rowCount = 30;
    private List<Rectangle> cells;
    private Point selectedCell;
	private List<IGizmo> gizmoList;
	private static final int L = 20;
	
    public TestGrid(Model m) {
        cells = new ArrayList<>(columnCount * rowCount);
        gizmoList = new ArrayList<IGizmo>();
        m.addObserver(this);
        
    	final JPopupMenu popup = new JPopupMenu();
		JMenuItem jm1 = new JMenuItem("Add Gizmo");		
        popup.add(jm1);
        
    	JMenuItem jm2 = new JMenuItem("Delete Gizmo");
        popup.add(jm2);
        
        MouseAdapter mouseHandler;
        mouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();

                int width = getWidth();
                int height = getHeight();

                int cellWidth = width / columnCount;
                int cellHeight = height / rowCount;

                int column = e.getX() / cellWidth;
                int row = e.getY() / cellHeight;

                selectedCell = new Point(column, row);
                repaint();

            }
            
        };
        addMouseMotionListener(mouseHandler);
        addMouseListener( new MouseAdapter()	
        {
            public void mousePressed(MouseEvent e)
            {
                System.out.println("pressed");
            }

            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    
        
    
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
                    Rectangle cell = new Rectangle(
                            xOffset + (col * cellWidth),
                            yOffset + (row * cellHeight),
                            cellWidth,
                            cellHeight);
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

        g2d.setColor(Color.GRAY);
        for (Rectangle cell : cells) {
            g2d.draw(cell);
        }

		g2d.setColor(Color.BLUE);

		if(!gizmoList.isEmpty()){
			for (int i=0; i < gizmoList.size(); i++){
				IGizmo gizmo = gizmoList.get(i);
				if(gizmo instanceof Square)
					g2d.fillRect(gizmo.getXPos()*L,gizmo.getYPos()*L,gizmo.getWidth()*L,gizmo.getHeight()*L);
				else if (gizmo instanceof Circle)
					g2d.fillOval(gizmo.getXPos()*L,gizmo.getYPos()*L,gizmo.getWidth()*L,gizmo.getHeight()*L);
				
			}
		}
        
        g2d.dispose();
        
    }
    	
	@Override
	public void update(Observable o, Object arg) {
	
		if (arg instanceof IGizmo){
			gizmoList.add((IGizmo)arg);
		}
		
		repaint();
	}
}

