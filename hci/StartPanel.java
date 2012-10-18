package hci;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import hci.utils.*;

/**
 * Handles image editing panel
 * @author Michal
 *
 */
public class StartPanel extends JPanel implements MouseListener {
	/**
	 * some java stuff to get rid of warnings
	 */
	private static final long serialVersionUID = 1L;
	
	// Initialise the main frame
	ImageLabeller imageLabeller = null;
	
	/**
	 * default constructor, sets up the window properties
	 */
	
	public StartPanel() {

		this.setVisible(true);

		Dimension panelSize = new Dimension(800, 600);
		this.setSize(panelSize);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
		
		addMouseListener(this);
	}
	
	public StartPanel(ImageLabeller imageLabeller) {
		this();
		this.imageLabeller = imageLabeller;
		
		fileBrowserButton();
		archiveButton();
	}
	
	public void fileBrowserButton() {
		JButton fileBrowserButton = new JButton("Open image from file");
		fileBrowserButton.setMnemonic(KeyEvent.VK_O);
		fileBrowserButton.setSize(300, 300);
		fileBrowserButton.setEnabled(true);
		fileBrowserButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
					
					System.out.println("clicked filebrowser button"); 
					
			    	imageLabeller.openFileBrowser();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
	
		this.add(fileBrowserButton);
	}
	
	public void archiveButton() {
		JButton archiveButton = new JButton("Load image catalogue");
		archiveButton.setMnemonic(KeyEvent.VK_O);
		archiveButton.setSize(300, 300);
		archiveButton.setEnabled(true);
		archiveButton.addMouseListener(new MouseListener() {
		
			public void mouseClicked(MouseEvent e) {
					
					System.out.println("clicked archive button"); 
					
			    	imageLabeller.loadArchive(imageLabeller);
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
		
		});
	
		this.add(archiveButton);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("left click");
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			System.out.println("right click");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}
