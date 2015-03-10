package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.swing.JPanel;

import model.Global;
import model.IBall;
import model.IModel;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;
import model.gizmos.LeftFlipper;
import model.gizmos.RightFlipper;

public abstract class Board extends JPanel implements Observer {
	
	private static final long serialVersionUID = -8454000231742359788L;
	protected List<IGizmo> gizmoList;
	protected static final int L = 20;
	protected List<IBall> balls;
	
	protected IModel model;
	
	public Board(IModel model) {		
		gizmoList = new ArrayList<IGizmo>(model.getGizmos());
		balls = new ArrayList<IBall>(model.getBalls());
		this.model = model;
	}

	protected void drawGizmos(Graphics2D g2d){
		
		// update list of balls so they appear instantly when added to the board
		balls = new ArrayList<IBall>(model.getBalls());

		if (!gizmoList.isEmpty()) {
			for (int i = 0; i < gizmoList.size(); i++) {
				IGizmo gizmo = gizmoList.get(i);
				
				int x = gizmo.getXPos()*L;
				int y = gizmo.getYPos()*L;
				int width = gizmo.getWidth()*L;
				int height = gizmo.getHeight()*L;
				int radius = width/2;
						
				if (gizmo.getType() == Gizmo.Type.Square){
					g2d.setColor(new Color(0,0,255));
					g2d.fillRect(x, y, width, height);
				} else if (gizmo.getType() == Gizmo.Type.Circle){
					g2d.setColor(new Color(255,165,0));
					g2d.fillOval(x, y, width, height);
				} else if (gizmo.getType() == Gizmo.Type.Triangle) {
					g2d.setColor(new Color(255,255,0));
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
					g2d.setColor(new Color(255,0,0));
					g2d.fillRect(x, y, width, height);
				} else if(gizmo instanceof LeftFlipper){
					int angleDeg = gizmo.getAngle();
					double angleRad = Math.toRadians(angleDeg);
					
			        g2d.translate(x, y);
			        g2d.rotate(angleRad);
			        g2d.setColor(new Color(0,255,0));
					g2d.fillRect(0, 0-Global.L, width, height);
					g2d.rotate(- angleRad);
					
			        g2d.translate(-x, -y);
			        
				} else if(gizmo instanceof RightFlipper){
					int angleDeg = gizmo.getAngle();
					double angleRad = Math.toRadians(angleDeg);

			        g2d.rotate(angleRad, x, y);
			        g2d.setColor(new Color(0,255,0));
			        // for some reason, decreasing X moves the gizmo down, perhaps because of rotate??
			        // we need it here to make the top of the flipper line up with other gizmos on the same line
					g2d.fillRect(x-(2*Global.L), y, width, height);
					g2d.rotate(- angleRad, x, y);
					
				}
			}
		}
	}
}
