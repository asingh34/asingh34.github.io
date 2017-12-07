import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.QuadCurve2D;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Interface extends JComponent implements ActionListener, ChangeListener, ItemListener{
	
	public JPanel panel;
	//Launch Button and Label:
	public JButton LaunchButton;
	public JLabel LaunchLabel;

	
	//Angle of elevation, speed, fuse delay text field: 
	protected JTextField speed; 
	protected JLabel speedLabel;
	protected JTextField thetaInput; 
	protected JLabel thetaLabel; 
	protected JTextField time; 
	protected JLabel timeLabel; 
	
	
	
	String [] colors = {"Color Options", "Red", "Green", "Blue", "Yellow", "Orange"};
	protected JComboBox colorSelector;
	String [] styles = {"Style Options", "Style 1", "Style 2", "Style 3", "Style 4", "Style 5"};
	protected JComboBox styleSelector;
	
	String vString,tString;
	int vInt, tInt; 
	double gravity = 9.8; 
	String thetaString; 
	int thetaInt; 
	String colorChoice;
	String styleChoice;
	
	int x; 
	int y; 
	


	public Interface(){
		super();
		panel = new JPanel();
		
		time= new JTextField(10); 
		timeLabel = new JLabel();
		timeLabel.setText("Enter time");
		add(timeLabel);
		time.addActionListener(this);
		add(time);
		
		//launch button
		LaunchButton = new JButton();
		LaunchLabel = new JLabel(); 
		LaunchLabel.setText("Launch");
		add(LaunchLabel);
		LaunchButton.addActionListener(this);
		add(LaunchButton);
		//color selector 
		colorSelector= new JComboBox(colors);
		colorSelector.setSelectedItem(1);
		colorSelector.addActionListener(this);
		add(colorSelector);
		//style selector
		styleSelector = new JComboBox(styles);
		styleSelector.setSelectedItem(1);
		styleSelector.addActionListener(this);
		add(styleSelector); 
		//parameters 
		speed = new JTextField(10);
		speedLabel = new JLabel();
		speedLabel.setText("Enter your speed");
		add(speedLabel);
		speed.addActionListener(this);
		add(speed);
		thetaInput = new JTextField(10); 
		thetaLabel = new JLabel(); 
		thetaLabel.setText("Enter your angle of elevation");
		add(thetaLabel); 
		thetaInput.addActionListener(this);
		add(thetaInput);
	
		setSize(300,300);
		setLayout(new FlowLayout());
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == speed) {
			vString = speed.getText();
			vInt = (int) Double.parseDouble(vString);
			
		}else if (arg0.getSource() == thetaInput) {
			thetaString = thetaInput.getText();
			thetaInt = (int) Double.parseDouble(thetaString);
			
		}else if (arg0.getSource() == colorSelector) {
			colorChoice = (String) colorSelector.getSelectedItem();
			
		} else if (arg0.getSource() == styleSelector) {
			styleChoice = (String) styleSelector.getSelectedItem();
			
		} else if (arg0.getSource() == LaunchButton) {
			repaint();
		} else if (arg0.getSource() == time) {
			tString =time.getText();
			tInt = (int) Double.parseDouble(tString);
		}
			
	}
	
	 public void paintComponent (Graphics g) {
		 Graphics2D g2 = (Graphics2D) g;
		 x = (int) (vInt*tInt*(Math.cos((Math.toRadians(thetaInt)))));
		 y = (int) ((vInt*tInt*(Math.sin(Math.toRadians(thetaInt))))-((9.8/2)*tInt*tInt));
		 if (x < 0) {
			 x = -1*x;
		 }
		 if (y < 0) {
			 y = -1*y;
		 }
		 QuadCurve2D q = new QuadCurve2D.Float();
		 q.setCurve(0, 0, 100, 3, x, y);
		 g2.draw(q);
		 
		 if (colorChoice == "Red") {
			 g.setColor(Color.RED);
		 }else if(colorChoice == "Green") {
			 g.setColor(Color.GREEN);
		 }else if(colorChoice == "Blue") {
			 g.setColor(Color.BLUE);
		 }else if(colorChoice == "Yellow") {
			 g.setColor(Color.YELLOW);
		 }else if(colorChoice == "Orange") {
			 g.setColor(Color.ORANGE);
		 }
		 
		 if(styleChoice == "Style 1") {
			 for (int i =0; i<10; i++) {
				 g.drawOval(x+(i*2), y-(i*2), getWidth()/3, getHeight()/3);
				 g.drawOval(x-(i*2), y+(i*2), getWidth()/3, getHeight()/3);
				 
			
			 }
			 
		 } else if (styleChoice == "Style 2") {
			 for (int i =0; i<12; i++) {
				 
				 g.drawOval(x, y, getWidth()/3, getWidth()/3);
				 g.drawOval(x, y, getWidth()/3, getHeight()/12);
				 g.drawOval(x, y, getWidth()/3, getHeight()/11);
				 g.drawOval(x, y, getWidth()/3, getHeight()/10);
				 g.drawOval(x, y, getWidth()/3, getHeight()/9);
				 g.drawOval(x, y, getWidth()/3, getHeight()/8);
				 g.drawOval(x, y, getWidth()/3, getHeight()/7);
				 g.drawOval(x, y, getWidth()/3, getHeight()/6); 
				 g.drawOval(x, y, getWidth()/20, getHeight()/20);
				 g.drawOval(x-10, y-10, getWidth()/20, getHeight()/20);
				 g.drawOval(x-20, y-20, getWidth()/20, getHeight()/20);
				 g.drawOval(x-30, y-30, getWidth()/20, getHeight()/20);
				 g.drawOval(x-40, y-40, getWidth()/20, getHeight()/20); 
			 } 
		 }else if (styleChoice == "Style 3") {
			 int random1 = (int ) Math.random() * 300 -150;
			 int random2 = (int ) Math.random() * 300 -150;
			 int random3 = (int ) Math.random() * 300 -150;
			 int random4 = (int ) Math.random() * 300 -150;
			 int random5 = (int ) Math.random() * 300 -150;
			 int random6 = (int ) Math.random() * 300 -150;
			 int random7 = (int ) Math.random() * 300 -150;
			 int random8 = (int ) Math.random() * 300 -150;
			 int random9 = (int ) Math.random() * 300 -150;
			 int random10 = (int ) Math.random() * 300 -150;
			 int random11 = (int ) Math.random() * 300 -150;
			 int random12 = (int ) Math.random() * 300 -150;
			 int random13 = (int ) Math.random() * 300 -150;
			 int random14 = (int ) Math.random() * 300 -150;
			 int random15 = (int ) Math.random() * 300 -150;
			 int random16 = (int ) Math.random() * 300 -150;
			 g.drawLine(x, y, random1, random2);
			 g.drawLine(x, y, -random3, -random4);
			 g.drawLine(x, y, random5, random6);
			 g.drawLine(x, y, -random7, random8);
			 g.drawLine(x, y, random9, random10);
			 g.drawLine(x, y, random11, -random12);
			 g.drawLine(x, y, 400, 100);
			 g.drawLine(x, y, 1000, 400);
		 }else if (styleChoice == "Style 4") {
			 for(int i = 1; i <=10; i++) {
				 if(i == 1 || i == 5) {
					 g.fillOval(x, y, (getWidth()*i)/10, (getHeight()*i)/10);
				 }
				 g.drawOval(x, y, (getWidth()*i)/10, (getHeight()*i)/10);
			 }
		 }else if (styleChoice == "Style 5") {
			int radius = 10;
			int width = getWidth()/2-radius;
			int height = getHeight()/2-radius;
			 for (int i =0; i<20; i++) {
			 g.drawOval(x, y, radius*i, radius*i);
			 }
			 
		 }
 }
	 
		 
	
	
	public static void main(String[] args) {
		Interface app = new Interface();
		app.setVisible(true);
		
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		  
		
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		

	
	}
}
