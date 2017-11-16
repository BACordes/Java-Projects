import java.awt.Graphics;
import javax.swing.*;
import java.awt.*; 
import java.awt.event.*; 

import javax.swing.JPanel;

class ScreenSaver extends JPanel 
implements ActionListener, MouseWheelListener{

private int x[]=new int[60]; // x coordinates of edges
private int y[]=new int[60]; // y coordinates of edges
private int numOfPoints=0; // how many end points have been 
						// created for spiral
private int radius=10; // distance between the next end point
					// and the center
private  int delay=1000; // how fast end points are added ms
private Timer timer=null; // used to control drawing spiral

private double centerX=-1;
private double centerY=-1;

/**
* Creates timer and register mouse wheel event handlers.
*/
public ScreenSaver(){
timer=new Timer(delay, this);
timer.start();
this.addMouseWheelListener(this);
}

/**
* Responds to mouse wheel scrolls.
* 
* Scrolling up the wheel will make it fast to draw the spiral.
* Otherwise, it will be slower.
*/
public void mouseWheelMoved(MouseWheelEvent e){

int Scroll = e.getWheelRotation();
if (Scroll > 0) {
	delay+= 20;
}
else {
	delay-= 20;
}
if(delay<0) {
	delay=0;
}
timer.setDelay(delay);
timer.restart();
}
/**
* Responds to the timer by adding an end point on the spiral.
*/
public void actionPerformed(ActionEvent e){


addAPoint();
if(numOfPoints == 59) {
	radius = 10;
}
	else {
		radius +=3;
	}

numOfPoints = (numOfPoints+1)%60;

// updating the panel
repaint();
}

/**
* Adds a point to the spiral. 
* 
* The coordinates will be saved in the instance array variables:
* x and y
*/
private void addAPoint(){

// getting the center of the screen
if(centerX<0)
	centerX=getSize().getWidth()/2;
if(centerY<0)
	centerY=getSize().getHeight()/2;

// finding the coordinates of the new point
double x=centerX+Math.cos(numOfPoints*Math.PI/3)*radius;
double y=centerY+Math.sin(numOfPoints*Math.PI/3)*radius;

// saving the point for display
this.x[numOfPoints]=(int)x;
this.y[numOfPoints]=(int)y;
}

/**
* Draws the spiral in the x and y arrays
*/
public void paintComponent(Graphics g){
g.clearRect(0,0,(int)getSize().getWidth(),(int)getSize().getHeight());
g.drawPolyline(x,y,numOfPoints);
}


}

/**
* A frame containing the screen saver
* @author Dahai Guo
*
*/
public class Lab4 extends JFrame{

/**
* Creates a frame with only one component: ScreenSaver
*/
public Lab4(){
super("Lab 4");
setSize(600,600);
ScreenSaver saver=new ScreenSaver();
setLayout(new BorderLayout());
add(saver, BorderLayout.CENTER);

}

/**
* Launches the application.
* @param args
*/
public static void main(String args[]){
Lab4 lab4=new Lab4();
lab4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
lab4.setSize( 600, 600 ); // set frame size
lab4.setResizable(true);
lab4.setVisible( true ); // display frame
}
}