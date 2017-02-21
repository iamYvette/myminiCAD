package myminiCAD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;

public class Rectangle extends Shape {
	private int x1;
	private int y1;
	private int x2;
	private  int y2;
	private Color color;
	private HashSet<Point> point = new HashSet<Point>();
	
	public Rectangle(int x1, int y1, int x2 ,int y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;	
		this.color = color;
		existPoints();
	}
	public void existPoints(){
		int x,y;
		for(x = Math.min(x1, x2);x<Math.max(x1, x2);x++){
			for(int j=-2;j<=2;j++){
				point.add(new Point(x,y1+j));
				point.add(new Point(x,y2+j));
			}	
		}
		for(y = Math.min(y1, y2);y<Math.max(y1, y2);y++){
			for(int j=-5;j<=5;j++){
				point.add(new Point(x1+j,y));
				point.add(new Point(x2+j,y));
			}	
		}
		
	}
	@Override
	public boolean inShape(Point p){
		return point.contains(p);
	}
	
	@Override
	public void draw(Graphics g) {
		int width = Math.abs(x2-x1) ;
		int height = Math.abs(y2-y1);
		g.setColor(color);
		g.drawRect(x1, y1, width, height);
	}

}
