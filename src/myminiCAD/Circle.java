package myminiCAD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;

public class Circle extends Shape {
	private int x1;
	private int y1;
	private int radius;
	private Color color;
	private HashSet<Point> point = new HashSet<Point>();
	
	public Circle(int x, int y, int radius, Color color)
	{
		this.x1 = x;
		this.y1 = y;
		this.radius = radius;
		this.color = color;
		existPoints();
	}
	public void existPoints(){
		int x,y;
		for(double i = 0;i<360;i+=0.01)
		{
			for (int j = -5;j<=5;j++)
			{
				x = x1+(int)((radius+j)*Math.cos((double)i/360*Math.PI*2));
				y = y1+(int)((radius+j)*Math.sin((double)i/360*Math.PI*2));
				point.add(new Point(x,y));
			}
		}
	}
	@Override
	public boolean inShape(Point p){
		return point.contains(p);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawOval(x1-radius, y1-radius, radius*2, radius*2);
	}
}
