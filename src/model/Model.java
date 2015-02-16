package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.gizmos.IGizmo;
import interfaces.IModel;

public class Model extends Observable implements IModel {
	
	private List<IGizmo> gizmoList;
	
	public Model(){
		gizmoList = new ArrayList<IGizmo>();
	}
	
	public void addGizmo(IGizmo gizmo){
		gizmoList.add(gizmo);
		notifyObservers(gizmo);
	}
	
}
