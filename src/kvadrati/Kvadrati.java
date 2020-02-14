package kvadrati;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Kvadrati extends JFrame {

	/* 
	 *	Created by Stefan Sokolovic 2017 
	 */
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Rectangle> kvadrati; // squares
	private HashMap<Integer,Color> boje; // colors of squares
	private ArrayList<Integer> boja; // for access colors
	private int selektovan; // selected square for delete
	private int d; // dimensions square
	private static int id=0;
	private MyPanel panel1;
	private JPanel panel2;
	private GridBagConstraints cbc;
	private JTextField text;
	private JButton dugme;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	new Kvadrati3();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					new Kvadrati();
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Kvadrati() throws HeadlessException {
		super();
		this.kvadrati = new ArrayList<>();
		this.selektovan = -1;
		this.boje = new HashMap<>();
		this.boja = new ArrayList<>();
		d=40;

		boje.put(0, Color.BLUE);
		boje.put(1, Color.GREEN);
		boje.put(2, Color.MAGENTA);
		boje.put(3, Color.RED);
		boje.put(4, Color.GRAY);
		boje.put(5, Color.PINK);
		boje.put(6, Color.ORANGE);
		boje.put(7, Color.YELLOW);
		
		panel1= new MyPanel();
		panel2= new JPanel();
		cbc = new GridBagConstraints();
		this.setBounds(120,120,600,350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//panel 1
		setLayout(new GridBagLayout());
		panel1.setFocusable(true);
		cbc.fill = GridBagConstraints.BOTH;
		cbc.gridx = 0;
		cbc.gridy = 0;
		cbc.weightx = 0.75;
		cbc.weighty = 1;
		panel1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		add(panel1,cbc);
		
		// panel 2
		cbc.fill = GridBagConstraints.BOTH;
		cbc.gridx = 1;
		cbc.gridy = 0;
		cbc.weightx = 0.25;
		cbc.weighty = 1;
		text = new JTextField();
		text.setBounds(40,40,70,25);
		text.setColumns(5);
		text.setText(String.valueOf(d));
		panel2.add(text);
		dugme= new JButton("Obrisi");
		dugme.setBounds(35,100,90,30);
		panel2.add(dugme);
		panel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		add(panel2,cbc);
		
		
	
	
		
		dugme.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						{
							// remove selected square
							if(selektovan != -1)
							{
								int left,right,top;
								left = kvadrati.get(selektovan).x;
							    right = kvadrati.get(selektovan).x+kvadrati.get(selektovan).width;
							    top = kvadrati.get(selektovan).y;
								kvadrati.remove(selektovan);
								boja.remove(selektovan);
								update(selektovan,left,right,top);
								selektovan = -1;
							}
						}
						
					}
			
				});
		
		panel1.addMouseListener(new MouseListener()
				{	
					@Override
					public void mouseClicked(MouseEvent e) {
						
						boolean selected = false;
						panel1.requestFocusInWindow();
						boja.add(id%8);
						id++;
						for(int i=0; i<kvadrati.size(); i++)
						{
							if(	e.getX() > kvadrati.get(i).x && 
								e.getX() < kvadrati.get(i).x+kvadrati.get(i).width && 
								e.getY() > kvadrati.get(i).y && 
								e.getY() < kvadrati.get(i).y+kvadrati.get(i).height)
							{
								if(selektovan != -1)
									{
										selektovan = -1;
										selected = true;
										break;
									}
								else
								{
									selektovan = i;
									selected = true;
									break;
								}
							}
						}
						if(selected==false) // insert new square
						{
							
							int dd = Integer.parseInt(text.getText());
							if(panel1.getWidth()>=e.getX()+dd && panel1.getHeight()>=e.getY()+dd)
							{
								kvadrati.add(new Rectangle(e.getX(),e.getY(),dd,dd));
								pomeri(kvadrati.size()-1,e.getX(),e.getY());	
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Kvadrat udara u ivicu");
							}
						}
						panel1.invalidate();
						panel1.repaint();
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
			
				});
		panel2.setLayout(null);
		this.setVisible(true);
	}
	
	private void update(int indeks,int left,int right, int top) // update panel1
	{
		
		
		for(int i=indeks;i<kvadrati.size();i++)
		{
			if(kvadrati.get(i).y <= top && 
			   kvadrati.get(i).x < left && 
			  (kvadrati.get(i).x+kvadrati.get(i).width) <= right)
				left = kvadrati.get(i).x;
			
			if(kvadrati.get(i).y <= top && 
			  (kvadrati.get(i).x+kvadrati.get(i).width) > right && 
			   kvadrati.get(i).x >= left)
				right = kvadrati.get(i).x+kvadrati.get(i).width;
		}
		for(int i=indeks;i<kvadrati.size();i++)
		{
			if(kvadrati.get(i).y <= top && 
			  (kvadrati.get(i).x+kvadrati.get(i).width) <= right &&
			   kvadrati.get(i).x >= left)
				pomeri(i,kvadrati.get(i).x,kvadrati.get(i).y);
		
		}
		
		panel1.invalidate();
		panel1.repaint();
	}

	private void pomeri(int indeks,int x, int y) // for squares above
	{
		
		int min=panel1.getHeight();
		for(int i=0;i<kvadrati.size();i++)
		{
			
			if(i != indeks)
			{
				if(
					((x <= kvadrati.get(i).x && kvadrati.get(i).x-x < kvadrati.get(indeks).width)
					|| (x+kvadrati.get(indeks).width >= kvadrati.get(i).x+kvadrati.get(i).width && 
					(x+kvadrati.get(indeks).width)-(kvadrati.get(i).x+kvadrati.get(i).width) < kvadrati.get(indeks).width)
					||( x>= kvadrati.get(i).x && x+kvadrati.get(indeks).width<=kvadrati.get(i).x+kvadrati.get(i).width))
						)
				
				{
					if(y < kvadrati.get(i).y+kvadrati.get(i).height)
					if(min > kvadrati.get(i).y)
						min = kvadrati.get(i).y;
				
				}
			}
		}
	
		kvadrati.get(indeks).y = min-kvadrati.get(indeks).height;
	}

	class MyPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyPanel() {
			super();
			// TODO Auto-generated constructor stub
		}

	//	@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
	
			for (int i=0; i<kvadrati.size(); i++)
			{
				g.setColor(boje.get(boja.get(i)));
				g.fillRect(kvadrati.get(i).x, kvadrati.get(i).y, kvadrati.get(i).width, kvadrati.get(i).height);
			}
			if(selektovan!=-1)
			{
				g.setColor(Color.BLACK);
				g.fillRect(kvadrati.get(selektovan).x, kvadrati.get(selektovan).y, kvadrati.get(selektovan).width, kvadrati.get(selektovan).height);
			}
		}

		
		
	}
}
