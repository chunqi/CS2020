package sg.edu.nus.cs2020;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Visualizer extends Component {
	public static final int PIXEL_SIZE = 5;
	public static final int CANVAS_WIDTH = 85*PIXEL_SIZE;
	
	private static final long serialVersionUID = 1L;
	private static final String KEY = "PLAYSOFF";
	
	private BufferedImage m_img;
	
	private ArrayList<Integer> m_data;
	private int m_rowSize;
	private int m_colSize;
	
	public Visualizer(String filename) {
		m_data = new ArrayList<Integer>();
		
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while(bufferedReader.ready()) {
				m_data.add(Integer.parseInt(bufferedReader.readLine()));
			}
			
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void _show() {
		Frame f = new JFrame("Visualizer");
		f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
		f.add(this);
        f.pack();
        f.setVisible(true);
	}
	
	public Dimension getPreferredSize() {
        if (m_img == null) return new Dimension(0,0);
        else  return new Dimension(m_img.getWidth(null), m_img.getHeight(null));
    }
	
	public void paint(Graphics g) {
        g.drawImage(m_img, 0, 0, null);
    }
	
	private void _init(int dataSize) {
		m_colSize = CANVAS_WIDTH / PIXEL_SIZE;
		m_rowSize = dataSize / m_colSize + 1;
		m_img = new BufferedImage(CANVAS_WIDTH, m_rowSize * PIXEL_SIZE, BufferedImage.TYPE_INT_RGB);
	}
	
	private void _drawPixel(int row, int col, int r, int g, int b) {
		int rgb = (r <<= 16) | (g <<= 8) | b;
		for(int x = 0; x < PIXEL_SIZE; x++) {
			for(int y = 0; y < PIXEL_SIZE; y++) {
				m_img.setRGB(col * PIXEL_SIZE + y, row * PIXEL_SIZE + x, rgb);
			}
		}
		repaint();
	}
	
	public void drawIntensity() {
		_init(m_data.size());
		for(int x = 0; x < m_data.size(); x++) {
			int data = m_data.get(x);
			int row = x / m_colSize;
			int col = x % m_colSize;
			char key = KEY.charAt(x % 8);
			data ^= key;
			_drawPixel(row, col, data, data, data);
		}
		_show();
	}
	
	public void drawTriChannel() {
		_init(m_data.size() / 3);
		for(int x = 0; x < m_data.size() / 3; x++) {
			int r = m_data.get(x*3);
			int g = m_data.get(x*3+1);
			int b = m_data.get(x*3+2);
			int row = x / m_colSize;
			int col = x % m_colSize;
			char key = KEY.charAt(x % 8);
			r ^= key;
			g ^= key;
			b ^= key;
			_drawPixel(row, col, r, g, b);
		}
		_show();
	}
	
	public static void main(String[] args) {
		Visualizer v = new Visualizer("input.txt");
		v.drawIntensity();
		//v.drawTriChannel();
	}
}
