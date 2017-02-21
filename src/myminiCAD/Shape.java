package myminiCAD;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public abstract class Shape implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public abstract void draw(Graphics g);
	public abstract boolean inShape(Point p);
}
