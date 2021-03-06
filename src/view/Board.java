package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import javax.swing.JPanel;

import model.Global;
import model.IBall;
import model.IModel;
import model.gizmos.Gizmo;
import model.gizmos.IGizmo;

public abstract class Board extends JPanel implements Observer {
	
	private static final long serialVersionUID = -8454000231742359788L;
	protected List<IGizmo> gizmoList;
	protected static final int L = Global.L;
	protected List<IBall> balls;
	protected int[] colour = {240,172,217};
	private int tickerC = 0;
	private Boolean direction = null;
	
	protected IModel model;
	
	public Board(IModel model) {		
		gizmoList = new ArrayList<IGizmo>(model.getGizmos());
		balls = new ArrayList<IBall>(model.getBalls());
		this.model = model;
	}
	

	protected void drawGizmos(Graphics2D g2d){
		
		 g2d.setRenderingHint(
                 RenderingHints.KEY_ANTIALIASING, 
                 RenderingHints.VALUE_ANTIALIAS_ON);
		
		// update list of balls so they appear instantly when added to the board
		balls = new ArrayList<IBall>(model.getBalls());

		if (!gizmoList.isEmpty()) {
			for (int i = 0; i < gizmoList.size(); i++) {
				IGizmo gizmo = gizmoList.get(i);
				
				int x = gizmo.getXPos()*L;
				int y = gizmo.getYPos()*L;
				int width = gizmo.getWidth()*L;
				int height = gizmo.getHeight()*L;
						
				if (gizmo.getType() == Gizmo.Type.Square){
					
					if(gizmo.isTriggered()){
						g2d.setColor(new Color(0,165,255));
					}else{
						g2d.setColor(new Color(0,0,255));
					}
					g2d.fillRect(x, y, width, height);
				} else if (gizmo.getType() == Gizmo.Type.Circle){
					if(gizmo.isTriggered()){
						g2d.setColor(new Color(255,255,0));
					}else{
						g2d.setColor(new Color(255,165,0));
					}
					g2d.fillOval(x, y, width, height);
				} else if (gizmo.getType() == Gizmo.Type.Triangle) {
					if(gizmo.isTriggered()){
						g2d.setColor(new Color(165,255,0));
					}else{
						g2d.setColor(new Color(255,255,0));
					}
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
				} else if(gizmo.getType() == Gizmo.Type.LeftFlipper){
					
					RoundRectangle2D rectangle = null;
					AffineTransform transform;
					Shape transformed = null;
					
					switch (gizmo.getOrientation()) {
						case TopLeft:
							rectangle = new RoundRectangle2D.Double(gizmo.getXPos()*Global.L,gizmo.getYPos()*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
							
							break;
						case TopRight:
							rectangle = new RoundRectangle2D.Double((gizmo.getXPos()+1)*Global.L,gizmo.getYPos()*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
							
							break;
						case BottomRight:
							rectangle = new RoundRectangle2D.Double((gizmo.getXPos()+1)*Global.L,(gizmo.getYPos()+1)*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
							
							break;
						case BottomLeft:
							rectangle = new RoundRectangle2D.Double((gizmo.getXPos())*Global.L,(gizmo.getYPos()+1)*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
							
							break;
						default:
							break;
					}
					
					transform = new AffineTransform();
					transform.rotate(Math.toRadians(gizmo.getAngle() + gizmo.getOrientation().getAngle()), rectangle.getX()+1 + rectangle.getWidth()/2, rectangle.getY() + rectangle.getHeight()/4);
					transformed = transform.createTransformedShape(rectangle);
					
					g2d.setColor(new Color(0,255,0));
					g2d.fill(transformed);
					
			        
				} else if(gizmo.getType() == Gizmo.Type.RightFlipper){
					
					RoundRectangle2D rectangle = null;
					AffineTransform transform;
					Shape transformed = null;
					
					switch (gizmo.getOrientation()) {
						case TopLeft:
							
							rectangle = new RoundRectangle2D.Double((gizmo.getXPos()+1)*Global.L,gizmo.getYPos()*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
							
							break;
						case TopRight:
							rectangle = new RoundRectangle2D.Double((gizmo.getXPos()+1)*Global.L,(gizmo.getYPos()+1)*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
							
							break;
						case BottomRight:
							rectangle = new RoundRectangle2D.Double((gizmo.getXPos()+0)*Global.L,(gizmo.getYPos()+1)*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
														
							break;
						case BottomLeft:
							rectangle = new RoundRectangle2D.Double((gizmo.getXPos())*Global.L,(gizmo.getYPos())*Global.L,gizmo.getWidth()/2*Global.L,gizmo.getHeight()*Global.L,20,20);
							
							break;
						default:
							break;
					}
					
					transform = new AffineTransform();
					transform.rotate(Math.toRadians(gizmo.getAngle() + gizmo.getOrientation().getAngle()), rectangle.getX()+1 + rectangle.getWidth()/2, rectangle.getY() + rectangle.getHeight()/4);
					transformed = transform.createTransformedShape(rectangle);
					
					g2d.setColor(new Color(0,255,0));
					g2d.fill(transformed);
				}
			}
		}
	}
	
	public void nextColour(){
		if(Global.discoMode){
			if(tickerC > 99){
				direction = false;
			} else if(tickerC < 1){
				direction = true;
			}
			
			if(direction){
				tickerC++;
			} else {
				tickerC--;
			}
			
			colour[0] = (255 * tickerC) / 100;
			colour[1] = (255 * (100 - tickerC)) / 100; 
		} else if(Global.raveMode){
			 Random rand = new Random();
			 colour[0] = rand.nextInt((255 - 0) + 1);
			 colour[1] = rand.nextInt((255 - 0) + 1);
			 colour[2] = rand.nextInt((255 - 0) + 1);
		} else {
			colour[0] = 240;
			colour[1] = 172;
			colour[2] = 217;
		}
		
	}


	public ActionListener getConnectingKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
