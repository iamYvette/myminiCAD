package myminiCAD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawListener implements MouseListener, MouseMotionListener {
	public Graphics2D g;
	public int x1,x2,x3,y1,y2,y3;
	public String str;
	public ButtonGroup bg;
	public Color color;
	public MiniCAD cad;
	public boolean flag = true;
	private JPanel pl;
	
	public Random  rad = new Random();
	public DrawListener(Graphics g){
		this.g = (Graphics2D)g;
	}
	public DrawListener(Graphics g, ButtonGroup bg){
		this.g = (Graphics2D)g;
		this.bg = bg;
	}
	public DrawListener(Graphics g, MiniCAD cad,JPanel pl){
		this.g = (Graphics2D)g;
//		this.bg = bg;
		this.cad = cad;
		this.pl=pl;
	}
	@Override
	public void mousePressed(MouseEvent event ){
		if(event.getButton()==1)
		{
			x1 = event.getX();
			y1 = event.getY();
		}
	}
	@Override
	public void mouseReleased(MouseEvent event){
		if(event.getButton()==1){
			
			x2 = event.getX();
			y2 = event.getY();
			
			if(cad.getCommand().equals("Circle")){
				Shape circle = new Circle(x1,y1,(int)Math.sqrt(Math.pow((int)(x2-x1), 2)+Math.pow((int)(y2-y1), 2)),
						cad.getColor());
				cad.addShape(circle);
				pl.repaint();
			}
			else if("Line".equals(cad.getCommand())){
				Shape line = new Line(x1,y1,x2,y2,cad.getColor());
				cad.addShape(line);
				pl.repaint();
			}
			else if("Rectangle".equals(cad.getCommand())){
				Shape rectangle = new Rectangle(x1,y1,x2,y2,cad.getColor());
				cad.addShape(rectangle);
				pl.repaint();
			}
			else if("Text".equals(cad.getCommand())){
				str = JOptionPane.showInputDialog(null,"Enter your text");
				if(str!=null){
					Shape text = new writeString(x1,y1,str,cad.getColor());
					cad.addShape(text);
					pl.repaint();
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent event){
		if(event.getButton()==3){
			int count = event.getClickCount();
			if(count ==2){
				int a = event.getX();
				int b = event.getY();
				if(cad.getShapeIndex(a, b)!=-1)
				{
					cad.deleteShape(cad.getShapeIndex(a, b));
					pl.repaint();
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent event){
		
	}
	@Override
	public void mouseDragged(MouseEvent event){
	
	}
	@Override
	public void mouseExited(MouseEvent event){
		
	}
	@Override
	public void mouseMoved(MouseEvent event){
		
	}
}
