package hci;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import hci.utils.*;

/**
 * Handles image editing panel
 * 
 * @author Michal
 * 
 */
public class ImagePanel extends JPanel implements MouseListener {
	/**
	 * some java stuff to get rid of warnings
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * image to be tagged
	 */
	BufferedImage image = null;

	ImageLabeller imageLabeller = null;
	
	AnnotatedImages savedImage = new AnnotatedImages();
	
	boolean loadedPolys = false;

	/**
	 * list of current polygon's vertices
	 */
	private ArrayList<Point> currentPolygon = null;

	/**
	 * list of polygons
	 */
	private ArrayList<ArrayList<Point>> polygonsList = null;

	/**
	 * default constructor, sets up the window properties
	 */

	public ImagePanel() {
		currentPolygon = new ArrayList<Point>();
		polygonsList = new ArrayList<ArrayList<Point>>();

		this.setVisible(true);

		Dimension panelSize = new Dimension(800, 600);
		this.setSize(panelSize);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);

		addMouseListener(this);
	}

	public ArrayList<Point> getPolygon() {
		return currentPolygon;
	}

	/**
	 * extended constructor - loads image to be labelled
	 * 
	 * @param imageName
	 *            - path to image
	 * @throws Exception
	 *             if error loading the image
	 */
	public ImagePanel(String imageName, ImageLabeller imageLabeller)
			throws Exception {
		this();
		this.imageLabeller = imageLabeller;

		image = ImageIO.read(new File(imageName));
		if (image.getWidth() > 800 || image.getHeight() > 600) {
			int newWidth = image.getWidth() > 800 ? 800
					: (image.getWidth() * 600) / image.getHeight();
			int newHeight = image.getHeight() > 600 ? 600
					: (image.getHeight() * 800) / image.getWidth();
			System.out.println("SCALING TO " + newWidth + "x" + newHeight);
			Image scaledImage = image.getScaledInstance(newWidth, newHeight,
					Image.SCALE_FAST);
			image = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(scaledImage, 0, 0, this);
		}
	}

	/**
	 * Displays the image
	 */
	public void ShowImage() {
		Graphics g = this.getGraphics();

		if (image != null) {
			g.drawImage(image, 0, 0, null);
			load();
		}
	}
	
	public void load() {
		String imgHash = imageToMD5(image);
		String fileName = "." + File.separator + "data" + File.separator
				+ imgHash + ".txt";
		File file = new File(fileName);
		if (file.exists()) {
			System.out.println("Loading saved polygons");
			try {
				FileReader fileReader = new FileReader(fileName);
				BufferedReader inStream = new BufferedReader(fileReader);
				String line;
				while ((line = inStream.readLine()) != null) {
					System.out.println(line);
					String[] tokens = line.split(";");
					
					// ignore polygons with less than 3 points
					if (tokens.length < 4) continue;
					
					String name = tokens[0];
					ArrayList<Point> polygon = new ArrayList<Point>();
					for (int i = 1; i < tokens.length; i++) {
						int x = Integer.parseInt(tokens[i].split(",")[0]);
						int y = Integer.parseInt(tokens[i].split(",")[1]);
						Point p = new Point(x, y);
						polygon.add(p);
					}
					drawPolygon(polygon);
					finishPolygon(polygon);
					if(!loadedPolys) savedImage.addImage(name, polygon);
				}
				loadedPolys = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println("No saved data to load");
		}
	}

	public void save(AnnotatedImages savedImages) {
		String imgHash = imageToMD5(image);
		String fileName = "." + File.separator + "data" + File.separator
				+ imgHash + ".txt";
		File file = new File(fileName);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		try {
			if (!file.exists())
				file.createNewFile();
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter outStream = new BufferedWriter(fileWriter);
			for (int i = 0; i < savedImages.getPolygonCount(); i++) {
				System.out.println(savedImages.getName(i));
				outStream.write(savedImages.getName(i) + savedImages.getCSVPointList(i) + "\n");
			}
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String imageToMD5(BufferedImage bfImg) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ImageIO.write(bfImg, "jpeg", outputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		byte[] data = outputStream.toByteArray();

		System.out.println("Start MD5 Digest");
		MessageDigest md;
		byte[] hash;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(data);
			hash = md.digest();
		} catch (NoSuchAlgorithmException e) {
			hash = null;
			e.printStackTrace();
		}
		String md5 = returnHex(hash);
		System.out.println(md5);
		return md5;
	}

	static String returnHex(byte[] inBytes) {
		String hexString = "";
		for (int i = 0; i < inBytes.length; i++) {
			hexString += Integer.toString((inBytes[i] & 0xff) + 0x100, 16)
					.substring(1);
		}
		return hexString;
	}

	public void rePaint(Graphics g) {
		ShowImage();
		for (ArrayList<Point> polygon : polygonsList) {
			drawPolygon(polygon);

		}

		// display current polygon
		drawPolygon(currentPolygon);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		// display iamge
		ShowImage();
		System.out.println("Painting");
		// display all the completed polygons
		for (ArrayList<Point> polygon : polygonsList) {
			drawPolygon(polygon);
			finishPolygon(polygon);
		}

		shade(g);

		// display current polygon

	}

	public void shade(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.blue);

		ArrayList<Point> polygon = new ArrayList<Point>();
		if (polygonsList.size() != 0) {

			for (int i = 0; i < polygonsList.size(); i++) {
				System.out.println("Filling polygon: " + i);

				polygon = polygonsList.get(i);

				Polygon p = new Polygon();

				for (int j = 0; j < polygon.size(); j++) {
					p.addPoint(polygon.get(j).getX(), polygon.get(j).getY());
				}

				g2.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 0.2f));
				g2.fillPolygon(p);

			}

		}
	}

	/**
	 * displays a polygon without last stroke
	 * 
	 * @param polygon
	 *            to be displayed
	 */
	public void drawPolygon(ArrayList<Point> polygon) {
		Graphics2D g = (Graphics2D) this.getGraphics();
		g.setColor(Color.RED);
		for (int i = 0; i < polygon.size(); i++) {
			Point currentVertex = polygon.get(i);

			if (i != 0) {
				Point prevVertex = polygon.get(i - 1);
				g.drawLine(prevVertex.getX(), prevVertex.getY(),
						currentVertex.getX(), currentVertex.getY());
			}
			g.fillOval(currentVertex.getX() - 5, currentVertex.getY() - 5, 5, 5);

		}

		// Point firstVertex = polygon.get(0);
		// Point lastVertex = polygon.get(polygon.size() - 1);
		// g.drawLine(firstVertex.getX(), firstVertex.getY(), lastVertex.getX(),
		// lastVertex.getY());

	}

	/**
	 * displays last stroke of the polygon (arch between the last and first
	 * vertices)
	 * 
	 * @param polygon
	 *            to be finished
	 */
	public void finishPolygon(ArrayList<Point> polygon) {
		// if there are less than 3 vertices than nothing to be completed

		Point firstVertex = polygon.get(0);
		Point lastVertex = polygon.get(polygon.size() - 1);

		Graphics2D g = (Graphics2D) this.getGraphics();
		g.setColor(Color.RED);

		g.drawLine(firstVertex.getX(), firstVertex.getY(), lastVertex.getX(),
				lastVertex.getY());

		System.out.println("finish Polygon");

	}

	/**
	 * moves current polygon to the list of polygons and makes pace for a new
	 * one
	 */
	public void addNewPolygon() {
		// finish the current polygon if any
		if (currentPolygon.size() != 0) {

			polygonsList.add(currentPolygon);
			Graphics2D g = (Graphics2D) this.getGraphics();
			g.setColor(Color.RED);
			paint(g);

		}

		currentPolygon = new ArrayList<Point>();

	}

	public void openFileBrowser() {

	}

	public void undo() {
		ArrayList<Point> polygon = getPolygon();
		Graphics2D g = (Graphics2D) this.getGraphics();

		if (polygon.size() > 0) {

			polygon.remove(polygon.size() - 1);
			rePaint(g);
		}

		else {
			JOptionPane.showMessageDialog(imageLabeller,
					"Need a new point to undo");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		// check if the cursos withing image area
		if (x > image.getWidth() || y > image.getHeight()) {
			// if not do nothing
			return;
		}

		Graphics2D g = (Graphics2D) this.getGraphics();

		// if the left button than we will add a vertex to poly
		if (e.getButton() == MouseEvent.BUTTON1) {
			g.setColor(Color.GREEN);
			if (currentPolygon.size() != 0) {
				Point lastVertex = currentPolygon
						.get(currentPolygon.size() - 1);
				g.drawLine(lastVertex.getX(), lastVertex.getY(), x, y);
			}

			if (currentPolygon.size() == 0) {
				g.fillOval(x - 5, y - 5, 10, 10);
			} else {
				g.fillOval(x - 5, y - 5, 5, 5);
			}

			currentPolygon.add(new Point(x, y));
			System.out.println(x + " " + y);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			System.out.println("right click undo");
			undo();
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
