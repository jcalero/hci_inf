package hci;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingUtilities;


import java.awt.event.KeyEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
	
	AnnotatedImages savedImages = null;
	
	private DefaultListModel names;
	private JList nameJList;
	private JScrollPane scrollPane;
	
	// default constructor
	public ToolBox(ImagePanel imagePanel, ImageLabeller imageLabeller){
	
		this.imagePanel = imagePanel;
		this.imageLabeller = imageLabeller;
		
		this.savedImages = imagePanel.savedImage;
		
		
		textBox();
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setVisible(true);
		
		closeObjButton();
		fileBrowserButton();
		undoButton();
		quitButton();
		//redoButton();
		saveButton();
		resetButton();
		
	}
	
	public void textBox(){
		
		
		JPanel textPanel = new JPanel();	
		textPanel.setLayout(new BorderLayout());
		textPanel.setBackground(Color.BLACK);
		textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		ArrayList<String> savedNames = savedImages.getNameList();
		
		names = new DefaultListModel();
		names.addElement("fake test");
		names.addElement("22nd jump street");
		names.addElement("cookie monster");
		
		for (int i = 0; i<savedNames.size(); i++){
			names.addElement(savedNames.get(i));
		}

	
		nameJList = new JList(names);
		nameJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nameJList.setSelectedIndex(0);
		nameJList.setVisibleRowCount(5);
		nameJList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					//highlight selected image
				}
			}
		});
	
	//	ArrayList<JLabel> nameList = new ArrayList<JLabel>();
		//create a scrollPane and add JList 
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(150, 150));
		scrollPane.setViewportView(nameJList);
	//	scrollPane.add(nameJList);
	//	scrollPane.getViewport().add(nameJList);
		this.add(scrollPane);
		textPanel.add(scrollPane);
		
		this.add(textPanel,BorderLayout.NORTH);
		
		
	}	

	public void resetButton(){
		
		JButton resetButton = new JButton("reset");
		resetButton.setMnemonic(KeyEvent.VK_N);
		resetButton.setSize(50, 20);
		resetButton.setEnabled(true);
		resetButton.setToolTipText("QuitProgram");
		
		
		
		resetButton.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("mouse click reset");		
			    int answer = JOptionPane.showConfirmDialog(imageLabeller, "Are you sure you want to reset, you will lose all changes :(", null, JOptionPane.YES_NO_OPTION);
			    //save all records to file
			    if (answer == JOptionPane.YES_OPTION){
			    	
			    	imagePanel.resetScreen();
			    	
			    	//clear the scrollpanel
			    	names.removeAllElements();
			    	
			    	savedImages = null;
			    	imagePanel.save(savedImages);
			    	
			    	
			    	
			    }
			    
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
		
		this.add(resetButton, BorderLayout.SOUTH);
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
			    		
			    		CustomDialog userInput = new CustomDialog(imageLabeller, imagePanel, message, savedImages);
			    		
			    		names.addElement(userInput.getUserInput());
			    		int lastIndex = nameJList.getLastVisibleIndex();
			    		nameJList.setSelectedIndex(lastIndex);
			    		nameJList.ensureIndexIsVisible(lastIndex);
			    	//	scrollPane.setViewportView(nameJList);
			    		scrollPane.revalidate();
			    		
			    		
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
	
	public void saveButton() {
		JButton saveButton = new JButton("Save");
		saveButton.setMnemonic(KeyEvent.VK_S);
		saveButton.setSize(50, 20);
		saveButton.setEnabled(true);
		saveButton.setToolTipText("Click to save current work.");
		
		saveButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
				
				if (savedImages!=null){
					System.out.println("button click save"); 
					imagePanel.save(savedImages);
					JOptionPane.showMessageDialog(imageLabeller, "saved");
			
				} 
			}	
			
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
		
		this.add(saveButton);
	}
	

	
	
	

	
	
	


}