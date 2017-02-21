package myminiCAD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class writeString extends Shape {
	private String str;
	private int x;
	private int y;
	private Color color;
	private ArrayList<Point> point = new ArrayList<Point>();
	
	public writeString(int x, int y,String str, Color color){
		this.x = x;
		this.y = y;
		this.str = str;
		this.color = color;
	}
	public void existPoints(){
		
	}
	@Override
	public boolean inShape(Point p){
		return point.contains(p);
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawString(str, x, y);
		
	}
}
