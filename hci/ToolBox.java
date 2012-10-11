package hci;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.event.KeyEvent;

import java.io.File;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hci.utils.*;


public class ToolBox extends JPanel{

	private static final long serialVersionUID = 1L;

	// initialise the frames
	ImageLabeller imageLabeller = null;
	ImagePanel imagePanel = null;
	
	
	
	
	
	// default constructor
	public ToolBox(ImagePanel ip, ImageLabeller il){
	
		imagePanel = ip;
		imageLabeller = il;
		
		this.setVisible(true);
		
		closeObjButton();
		fileBrowserButton();
		//undoButton();
		

	}
	
	
	public void closeObjButton(){
		
		JButton closeObjButton = new JButton("Close object");
		closeObjButton.setMnemonic(KeyEvent.VK_N);
		closeObjButton.setSize(50, 20);
		closeObjButton.setEnabled(true);
		closeObjButton.setToolTipText("Click to close the current object. (N)");
		
		closeObjButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
					
					System.out.println("mouse click"); 
					
			    	imagePanel.addNewPolygon();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
		
		this.add(closeObjButton);
			
	}
	
	public void fileBrowserButton(){
		
		JButton fileBrowserButton = new JButton("Open Image");
		fileBrowserButton.setMnemonic(KeyEvent.VK_O);
		fileBrowserButton.setSize(50, 20);
		fileBrowserButton.setEnabled(true);
		fileBrowserButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
					
					System.out.println("mouse click"); 
					
			    	imageLabeller.openFileBrowser();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
	
		this.add(fileBrowserButton);
		
	}
	
	

	
	
	


}