package view;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Observer;

import javax.swing.JPanel;

import model.gizmos.Absorber;
import model.gizmos.Circle;
import model.gizmos.IGizmo;
import model.gizmos.Square;
import model.gizmos.Triangle;
import model.gizmos.Gizmo.Orientation;

public abstract class Board extends JPanel implements Observer {
	
	private static final long serialVersionUID = -8454000231742359788L;
	protected List<IGizmo> gizmoList;
	protected static final int L = 20;

	protected void drawGizmos(Graphics2D g2d){
		
		if (!gizmoList.isEmpty()) {
			for (int i = 0; i < gizmoList.size(); i++) {
				IGizmo gizmo = gizmoList.get(i);
				
				int x = gizmo.getXPos()*L;
				int y = gizmo.getYPos()*L;
				int width = gizmo.getWidth()*L;
				int height = gizmo.getHeight()*L;
				int radius = width/2;
						
						
				if (gizmo instanceof Square)
					g2d.fillRect(x, y, width, height);
				else if (gizmo instanceof Circle)
					g2d.fillOval(x-radius, y-radius, width, height);
				else if (gizmo instanceof Triangle) {
					if (((Triangle) gizmo).getOrientation().equals(Orientation.BottomLeft)) {
						g2d.fillPolygon(new int[] {x,x,x+width}, new int[] {y,y+height,y+height}, 3);
					}else if (((Triangle) gizmo).getOrientation().equals(Orientation.BottomRight)) {
						g2d.fillPolygon(new int[] {x+width,x+width,x}, new int[] {y,y+height,y+height}, 3);
					}else if (((Triangle) gizmo).getOrientation().equals(Orientation.TopLeft)) {
						g2d.fillPolygon(new int[] {x,x,x+width}, new int[] {y+height,y,y}, 3);
					}else if (((Triangle) gizmo).getOrientation().equals(Orientation.TopRight)) {
						g2d.fillPolygon(new int[] {x,x+width,x+width}, new int[] {y,y,y+height}, 3);
					}
				} else if(gizmo instanceof Absorber){
					g2d.fillRect(x, y, width, height);
				}

			}
		}
	}

}
