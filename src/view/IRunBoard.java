package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;

public interface IRunBoard {

	public abstract Dimension getPreferredSize();

	public abstract void paintComponent(Graphics g);

	public void nextColour();
	
	public abstract void update(Observable o, Object arg);

}