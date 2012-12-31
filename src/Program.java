import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("serial")
public class Program extends JFrame {
    private static String TITLE = "Galactica Client";
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final int FPS = 60;
    private static GLCanvas canvas = new Renderer();
    
    public Program() {
	canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
	
	this.getContentPane().add(canvas);
	this.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		new Thread() {
		    @Override
		    public void run() {
			if (animator.isStarted())
			    animator.stop();
			System.exit(0);
		    }
		}.start();
	    }
	});
	this.setTitle(TITLE);
	this.pack();
	this.setVisible(true);
	animator.start();
    }

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new Program();
	    }
	});
    }
}