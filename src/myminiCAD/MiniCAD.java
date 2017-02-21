package myminiCAD;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MiniCAD extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color color = Color.BLACK;
	private String command="Circle";
	private ArrayList <Shape>list = new ArrayList<Shape>();
	private JPanel panelsubcenter;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public void initalFrame(){
		this.setSize(600,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		JMenu file = new JMenu("File");
		bar.add(file);
		JMenuItem ofile = new JMenuItem("Open File");
		ofile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("open");
				openfile();
				
			}
		});
		file.add(ofile);
		JMenuItem sfile = new JMenuItem("Save File");
		sfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				savefile();
				
			}
		});
		file.add(sfile);
		
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		panelcenter.setBackground(Color.GRAY);
		panel.add(panelcenter);
		
		panelsubcenter = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for ( Shape s : list )
				{
					s.draw(g);
				}			
			}
		};
		panelsubcenter.setBackground(Color.WHITE);
		panelsubcenter.setPreferredSize(new Dimension(500,300));
		panelcenter.add(panelsubcenter);
		
		JPanel panelleft = new JPanel();
		panelleft.setLayout(null);
		panelleft.setBackground(new Color(235,233,238));
		panelleft.setPreferredSize(new Dimension(100,0));
		panel.add(panelleft,BorderLayout.WEST);
		
		String[] buttonlabel = {"Circle","Line","Rectangle","Text"};
		for(int i=0;i<buttonlabel.length;i++){
			Button b = new Button(buttonlabel[i]);
			b.setBounds(5, 10+i*50+i*10, 90, 50);
			b.setActionCommand(buttonlabel[i]);
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					command = e.getActionCommand();
				}
			});
			panelleft.add(b);
		}
		
		
		JPanel paneldown = new JPanel();
		paneldown.setBackground(Color.gray);
		paneldown.setLayout(null);
		paneldown.setPreferredSize(new Dimension(0,80));
		panel.add(paneldown,BorderLayout.SOUTH);
		
		JPanel panelsubdown = new JPanel();
		panelsubdown.setLayout(null);
		panelsubdown.setBounds(15, 10, 190, 60);
		paneldown.add(panelsubdown);
		
		JPanel downleft = new JPanel();
		downleft.setLayout(null);
		downleft.setBounds(0, 0, 60, 60);
		panelsubdown.add(downleft);
		
		JPanel downright = new JPanel();
		downright.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		downright.setBounds(60, 0, 120, 60);
		panelsubdown.add(downright);
		
		JPanel button = new JPanel();
		button.setBounds(15, 15, 30, 30);
		button.setBackground(Color.black);
		downleft.add(button);
		
		Color[] colors = {Color.BLUE,Color.RED,Color.ORANGE,Color.GRAY,
				Color.BLACK,Color.YELLOW,Color.GREEN,Color.PINK};
		
		for(int i=0;i<colors.length;i++){
			JPanel bt2 = new JPanel();
			bt2.setBackground(colors[i]);
			bt2.setPreferredSize(new Dimension(30,30));
			bt2.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent  e){
					JPanel tp = (JPanel)e.getSource();
					color=tp.getBackground();
					button.setBackground(color);
				}
			});
			
			downright.add(bt2);
		}
		
		Graphics graphics = panelsubcenter.getGraphics();
		DrawListener draw = new DrawListener(graphics,this,panelsubcenter);
		panelsubcenter.addMouseListener(draw);
		panelsubcenter.addMouseMotionListener(draw);
	
		this.setVisible(true);	
	}
	
	public void savefile(){
		JFileChooser fileChooser=new JFileChooser();
	     fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	     int result =fileChooser.showSaveDialog(this);
	     if(result==JFileChooser.CANCEL_OPTION)
	              return ;
	     File fileName=fileChooser.getSelectedFile();
	     fileName.canWrite();

	     if (fileName==null||fileName.getName().equals(""))
	     JOptionPane.showMessageDialog(fileChooser,"Invalid File Name",
	             "Invalid File Name", JOptionPane.ERROR_MESSAGE);
	     else{
	       try {
	        fileName.delete();
	        FileOutputStream fos=new FileOutputStream(fileName);

	        output=new ObjectOutputStream(fos);
	                output.writeInt( list.size() );
	        
	        for(Shape s:list)
	        {
	          output.writeObject(s);
	          output.flush();
	          }
	        output.close();
	        fos.close();
	        }catch(IOException e){
	         e.printStackTrace();
	         }
	       }
	}
	
	public void getHelp(){
		
		try {
			URI uri = new URI("myhtml.html");
			Desktop desktop = Desktop.getDesktop();
			if(Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)){
				try {
					desktop.browse(uri);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("error is here");
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void openfile(){
		JFileChooser fileChooser=new JFileChooser();
	      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	      int result =fileChooser.showOpenDialog(this);
	      if(result==JFileChooser.CANCEL_OPTION)
	            return ;
	      File fileName=fileChooser.getSelectedFile();
	      fileName.canRead();
	      if (fileName==null||fileName.getName().equals(""))
	         JOptionPane.showMessageDialog(fileChooser,"Invalid File Name",
	              "Invalid File Name", JOptionPane.ERROR_MESSAGE);
	      else {
	       try {
	        FileInputStream fis=new FileInputStream(fileName);
	        input=new ObjectInputStream(fis);
	        list.clear();
	        Shape inputRecord;
	        int countNumber=input.readInt();
	        for(int i=0 ;i<countNumber ;i++)
	        {
	         inputRecord=(Shape)input.readObject();
	         list.add(inputRecord);
	         }
	        input.close();
	        panelsubcenter.repaint();
	        }catch(EOFException endofFileException){
	         JOptionPane.showMessageDialog(this,"no more record in file",
	           "class not found",JOptionPane.ERROR_MESSAGE );
	         }catch(ClassNotFoundException classNotFoundException){
	          JOptionPane.showMessageDialog(this,"Unable to Create Object",
	            "end of file",JOptionPane.ERROR_MESSAGE );
	          }catch (IOException ioException){
	           JOptionPane.showMessageDialog(this,"error during read from file",
	             "read Error",JOptionPane.ERROR_MESSAGE );
	           }
	       }
	}
	
	public String getCommand(){
		return command;
	}
	
	public void addShape(Shape s){
		list.add(s);
	}
	
	public Color getColor(){
		return color;
	}
	
	public int getShapeIndex(int a,int b){
		for (int i = 0;i<list.size();i++)
		{
			Shape s = list.get(i);
			if(s.inShape(new Point(a,b)))
				return i;
		}
		return -1;
	}
	
	public void deleteShape(int i){
		Shape s = list.get(i);
		list.remove(s);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniCAD cad = new MiniCAD();
		cad.initalFrame();
	}

}
