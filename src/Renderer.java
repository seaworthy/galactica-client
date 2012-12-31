import gui.GUI;

import java.nio.IntBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import scene.Scene;

import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

@SuppressWarnings("serial")
public class Renderer extends GLCanvas implements GLEventListener {
    GL2 gl;
    GLU glu;

    // Major classes
    Mouse mouse = new Mouse();
    Keyboard keyboard = new Keyboard();
    Interpreter interpreter = new Interpreter();

    String consoleInput = new String();

    // Object related
    Integer lastObject = null;
    
    //GUI related
    GUI gui = new GUI();

    // Scene related
    Scene scene = new Scene();
    float cameraDistance = 128;
    float zoomStep = 1f;
    float cameraAngleInXZ = 0;
    float cameraAngleInYZ = 0;
    float cameraAngleInYX = 0;
    float panStep = 1;

    // Memory
    private static final int BUFFER_SIZE = 512;

    public Renderer() {
	this.addGLEventListener(this);
	AWTMouseAdapter mouseAdapter = new AWTMouseAdapter(mouse);
	this.addMouseListener(mouseAdapter);
	this.addMouseMotionListener(mouseAdapter);

	AWTKeyAdapter keyAdapter = new AWTKeyAdapter(keyboard);
	this.addKeyListener(keyAdapter);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
	gl = drawable.getGL().getGL2();
	glu = new GLU();
	scene.gl = gl;
	gui.gl = gl;
	// OLD
	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	gl.glClearDepth(1.0f);
	gl.glEnable(GL_DEPTH_TEST);
	gl.glDepthFunc(GL_LEQUAL);
	gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	gl.glShadeModel(GL_SMOOTH);

	// NEW
	// gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	// gl.glDepthFunc(GL_LESS);
	// gl.glEnable(GL_DEPTH_TEST);
	// gl.glShadeModel(GL_FLAT);
	// gl.glDepthRange(0.0, 1.0); /* The default z mapping */
    }

    @Override
    public void display(GLAutoDrawable drawable) {
	interpreter.consoleInput = keyboard.message;
	// keyboard.message = "";
	processCommands();

	gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	setCamera();

	gl.glRotatef(cameraAngleInXZ, 0.f, 1.f, 0.f);

	gl.glRotatef(cameraAngleInYX,
		(float) Math.cos(Math.toRadians(cameraAngleInXZ)), 0.f,
		(float) Math.sin(Math.toRadians(cameraAngleInXZ)));

	if (keyboard.showViewVolume)
	    showViewVolume(-32.0f, 32.0f, -32.0f, 32.0f, -32.0f, 32.0f);

	scene.make(gl, GL_RENDER);
	scene.manager.objects.addAll(interpreter.objects);	
	interpreter.objects.clear();

	gui.cameraDistance = cameraDistance;
	gui.cameraAngleInYZ = cameraAngleInYZ;
	gui.cameraAngleInXZ = cameraAngleInXZ;
	
	if (keyboard.window == 0)
	    gui.showDefault(scene.manager.objects.size(), lastObject, scene.selected);
	if (keyboard.window == 1) 
	    gui.showConsole(interpreter.processInput());
	if (keyboard.window == 2)
	    gui.showHelp();

	if (mouse.point != null) {
	    userPickAt(mouse.point.getX(), mouse.point.getY());
	    mouse.point = null;
	}
	keyboard.message = interpreter.consoleInput;
	gl.glFlush();
    }

    void processCommands() {
        if (keyboard.resetView) {
            cameraDistance = 128;
            cameraAngleInXZ = 0;
            cameraAngleInYZ = 0;
            cameraAngleInYX = 0;
            keyboard.resetView = false;
        }
        if (keyboard.zoomOut) {
            cameraDistance += zoomStep;
        }
        if (keyboard.zoomIn) {
            cameraDistance -= zoomStep;
        }
        if (keyboard.panLeft) {
            cameraAngleInXZ -= panStep;
        }
        if (keyboard.panRight) {
            cameraAngleInXZ += panStep;
        }
        if (keyboard.panUp) {
            cameraAngleInYZ -= panStep;
            cameraAngleInYX -= panStep;
        }
        if (keyboard.panDown) {
            cameraAngleInYZ += panStep;
            cameraAngleInYX += panStep;
        }
    
        if (keyboard.clearSelection) {
            scene.selected.clear();
            keyboard.clearSelection = false;
        }
        if (keyboard.deleteSelected) {
            for (Integer hash : scene.selected) {
        	scene.manager.removeObject(hash);
            }
            scene.selected.clear();
            keyboard.deleteSelected = false;
        }
    }

    private void setCamera() {
	gl.glMatrixMode(GL_PROJECTION);
	gl.glLoadIdentity();

	float widthHeightRatio = (float) getWidth() / (float) getHeight();
	glu.gluPerspective(45, widthHeightRatio, 1, 1000);
	glu.gluLookAt(0, 0, cameraDistance, /* look from camera XYZ */
		0, 0, 0, /* look at the origin */
		0, 1, 0); /* positive Y up vector */

	gl.glMatrixMode(GL_MODELVIEW);
	gl.glLoadIdentity();
    }

    public void showViewVolume(float x1, float x2, float y1, float y2,
            float z1, float z2) {
        // gl.glTranslatef(20f,0,0);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL_LINE_LOOP);
        gl.glVertex3f(x1, y1, -z1);
        gl.glVertex3f(x2, y1, -z1);
        gl.glVertex3f(x2, y2, -z1);
        gl.glVertex3f(x1, y2, -z1);
        gl.glEnd();
    
        gl.glBegin(GL_LINE_LOOP);
        gl.glVertex3f(x1, y1, -z2);
        gl.glVertex3f(x2, y1, -z2);
        gl.glVertex3f(x2, y2, -z2);
        gl.glVertex3f(x1, y2, -z2);
        gl.glEnd();
    
        gl.glBegin(GL_LINES);
        gl.glVertex3f(x1, y1, -z1);
        gl.glVertex3f(x1, y1, -z2);
        gl.glVertex3f(x1, y2, -z1);
        gl.glVertex3f(x1, y2, -z2);
        gl.glVertex3f(x2, y1, -z1);
        gl.glVertex3f(x2, y1, -z2);
        gl.glVertex3f(x2, y2, -z1);
        gl.glVertex3f(x2, y2, -z2);
        gl.glEnd();
    }

    private void userPickAt(int x, int y) {
        int[] selectBuf = new int[BUFFER_SIZE];
        IntBuffer selectBuffer = Buffers.newDirectIntBuffer(BUFFER_SIZE);
        int hits;
        int viewport[] = new int[4];
        gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
    
        gl.glSelectBuffer(BUFFER_SIZE, selectBuffer);
        gl.glRenderMode(GL_SELECT);
    
        gl.glInitNames();
        gl.glPushName(0);
    
        gl.glMatrixMode(GL_PROJECTION);
        // gl.glPushMatrix();
        gl.glLoadIdentity();
    
        // 5x5 picking region
        glu.gluPickMatrix((double) x, (double) (viewport[3] - y), 5.0, 5.0,
        	viewport, 0);
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(0, 0, cameraDistance, 0, 0, 0, 0, 1, 0);
        gl.glRotatef(cameraAngleInXZ, 0.f, 1.f, 0.f);
    
        gl.glRotatef(cameraAngleInYX,
        	(float) Math.cos(Math.toRadians(cameraAngleInXZ)), 0.f,
        	(float) Math.sin(Math.toRadians(cameraAngleInXZ)));
    
        scene.make(gl, GL_SELECT);
    
        // gl.glPopMatrix();
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glFlush();
    
        hits = gl.glRenderMode(GL_RENDER);
        selectBuffer.get(selectBuf);
        processHits(hits, selectBuf);
    }

    private void processHits(int hits, int buffer[]) {
	int names, offset = 0;

	System.out.println("hits = " + hits);
	for (int i = 0; i < hits; i++) {
	    names = buffer[offset];
	    System.out.println(" number of names for hit = " + names);
	    offset++;
	    System.out.println("  z1 is " + buffer[offset]);
	    offset++;
	    System.out.println(" z2 is " + buffer[offset]);
	    offset++;
	    System.out.print("\n   the name is ");
	    for (int j = 0; j < names; j++) {
		System.out.println("" + buffer[offset]);
		lastObject = buffer[offset];
		if (scene.selected.contains(lastObject))
		    scene.selected.remove(lastObject);
		else
		    scene.selected.add(lastObject);
		offset++;
	    }
	    System.out.println();
	}
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
	    int height) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
    }
}
