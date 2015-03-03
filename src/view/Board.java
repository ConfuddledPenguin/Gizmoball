package view;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.swing.JPanel;

import model.IModel;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;

public abstract class Board extends JPanel implements Observer {
	
	private static final long serialVersionUID = -8454000231742359788L;
	protected List<IGizmo> gizmoList;
	protected static final int L = 20;
	
	protected IModel model;
	
	public Board(IModel model) {
		
		gizmoList = new ArrayList<IGizmo>(model.getGizmos());
	}

	protected void drawGizmos(Graphics2D g2d){
		
		if (!gizmoList.isEmpty()) {
			for (int i = 0; i < gizmoList.size(); i++) {
				IGizmo gizmo = gizmoList.get(i);
				
				int x = gizmo.getXPos()*L;
				int y = gizmo.getYPos()*L;
				int width = gizmo.getWidth()*L;
				int height = gizmo.getHeight()*L;						
						
				if (gizmo.getType() == Gizmo.Type.Square)
					g2d.fillRect(x, y, width, height);
				else if (gizmo.getType() == Gizmo.Type.Circle)
					g2d.fillOval(x, y, width, height);
				else if (gizmo.getType() == Gizmo.Type.Triangle) {
					if ((gizmo).getOrientation().equals(Gizmo.Orientation.BottomLeft)) {
						g2d.fillPolygon(new int[] {x,x,x+width}, new int[] {y,y+height,y+height}, 3);
					}else if (gizmo.getOrientation().equals(Gizmo.Orientation.BottomRight)) {
						g2d.fillPolygon(new int[] {x+width,x+width,x}, new int[] {y,y+height,y+height}, 3);
					}else if (gizmo.getOrientation().equals(Gizmo.Orientation.TopLeft)) {
						g2d.fillPolygon(new int[] {x,x,x+width}, new int[] {y+height,y,y}, 3);
					}else if ( gizmo.getOrientation().equals(Gizmo.Orientation.TopRight)) {
						g2d.fillPolygon(new int[] {x,x+width,x+width}, new int[] {y,y,y+height}, 3);
					}
				} else if(gizmo.getType() == Gizmo.Type.Absorber) {
					g2d.fillRect(x, y, width, height);
				}
			}
		}
	}
}
