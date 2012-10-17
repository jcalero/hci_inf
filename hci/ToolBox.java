package hci;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import java.awt.event.KeyEvent;

import java.io.File;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hci.utils.*;
import java.util.ArrayList;

public class ToolBox extends JPanel{

	private static final long serialVersionUID = 1L;

	// initialise the frames
	ImageLabeller imageLabeller = null;
	ImagePanel imagePanel = null;
	
	// default constructor
	public ToolBox(ImagePanel imagePanel, ImageLabeller imageLabeller){
	
		this.imagePanel = imagePanel;
		this.imageLabeller = imageLabeller;
		
		
		this.setVisible(true);
		
		closeObjButton();
		fileBrowserButton();
		undoButton();
		quitButton();
		//redoButton();
		//saveButton();
	}
	
	public void quitButton(){
		
		JButton quitButton = new JButton("Quit");
		quitButton.setMnemonic(KeyEvent.VK_N);
		quitButton.setSize(50, 20);
		quitButton.setEnabled(true);
		quitButton.setToolTipText("QuitProgram");
		
		quitButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
					
				System.out.println("mouse click quit");		
			    int answer = JOptionPane.showConfirmDialog(imageLabeller, "Are you sure you want to quit :(", null, JOptionPane.YES_NO_OPTION);
			    //save all records to file
			    if (answer == JOptionPane.YES_OPTION){
			    	JOptionPane.showMessageDialog(imageLabeller, "hope you had fun");
			    	System.exit(0);
			    }
			    
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
		
		this.add(quitButton, BorderLayout.SOUTH);
			
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
					
					ArrayList<Point> polygon = imagePanel.getPolygon();
					
					if (polygon.size()>=3){
						
			    		imagePanel.addNewPolygon();
			    		String message = "Label the image!";
			    		AnnotatedImages savedImages = new AnnotatedImages();
			    		CustomDialog userInput = new CustomDialog(imageLabeller, imagePanel, message, savedImages);
			    		
			    		
			    		
			    	} else {
			    		JOptionPane.showMessageDialog(imageLabeller,
					    "Need at least 3 points");
			    	}
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
		
		this.add(closeObjButton, BorderLayout.CENTER);
			
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
	
	public void undoButton(){
		
		JButton undoButton = new JButton("undo");
		undoButton.setMnemonic(KeyEvent.VK_P);
		undoButton.setSize(50, 20);
		undoButton.setEnabled(true);
		undoButton.setToolTipText("Click to undo last Action.");
		
		undoButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
					System.out.println("button click undo"); 
					imagePanel.undo();
			}
					
			    	
		
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
		
		this.add(undoButton);
			
	}
	/*
	public void redoButton(){
		
		JButton undoButton = new JButton("redo");
		undoButton.setMnemonic(KeyEvent.VK_P);
		undoButton.setSize(50, 20);
		undoButton.setEnabled(true);
		undoButton.setToolTipText("Click to redo last Action.");
		
		undoButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
					
					System.out.println("mouse click redo"); 
					ArrayList<Point> polygon = imagePanel.currentPolygon;
					
					if (polygon.size() >= 1) {					
						
						polygon.remove(polygon.size()-1);
						Graphics g = imagePanel.getGraphics();
						imagePanel.paint(g);

					}
			}
					
			    	
		
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
		
		this.add(undoButton);
			
	}
	*/
	

	
	
	


}