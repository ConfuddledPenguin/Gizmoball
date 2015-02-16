package model.gizmos;

import java.awt.Point;

public interface IGizmo {
	void trigger();
	void connection(IGizmo g);
	void setPos(int x, int y);
	void setWidthHeight(int w, int h);
	Point getPos();
	int getWidth();
	int getHeight();
}
