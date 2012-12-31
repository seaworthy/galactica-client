import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

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
import com.jogamp.opengl.util.gl2.GLUT;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2.*;
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
    Scene scene = null;
    Integer lastObject = null;

    // Scene related
    float cameraDistance = 128;
    float zoomStep = 1f;
    float cameraAngleInXZ = 0;
    float cameraAngleInYZ = 0;
    float cameraAngleInYX = 0;
    float panStep = 1;

    // Memory
    private static final int BUFFER_SIZE = 512;

    List<String> shortcutList = Arrays.asList(new String[] {
	    "ESC - Key Shorcuts", "~ - Console", "X - Zoom-In", "Z - Zoom-Out",
	    "LEFT - Rotate View to the Left",
	    "RIGHT - Rotate View to the Right", "UP - Pan Camera Up",
	    "DOWN - Pan Camera Down", "0 - Reset Camera View",
	    "- - Toggle View Volume", "DEL - Delete Selected Object" });

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
	scene = new Scene();
	scene.gl = gl;

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

	// if (keyboard.interpreter.cachedSceneObject !=null) {
	// scene.visibleObjectList.add(keyboard.interpreter.cachedSceneObject);
	// keyboard.interpreter.cachedSceneObject = null;
	// }
	scene.make(gl, GL_RENDER);
	scene.objects.addAll(interpreter.objects);
	interpreter.objects.clear();
	// scene.sceneObjectList.addAll(new SceneObject());
	// scene.add
	// scene.change
	// scene.update

	if (keyboard.showConsole)
	    showConsole();
	else
	    showGUI();

	if (mouse.point != null) {
	    userPickAt(mouse.point.getX(), mouse.point.getY());
	    mouse.point = null;
	}
	keyboard.message = interpreter.consoleInput;
	gl.glFlush();
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

    private void showGUI() {
	if (keyboard.showHelp) {
	    showHelp();
	} else {
	    gl.glColor3f(0, 1f, 0);
	    setText("# of scene objects: "
		    + Integer.toString(scene.objects.size()), -5.f, 3.f);
	    setText("Selected objects: " + scene.selected, -5.f, 2.8f);
	    if (lastObject != null) {
		setText("Last selected object: " + Integer.toString(lastObject),
			-5.f, 2.6f);
	    }
	    gl.glColor3f(0, 1.f, 1.f);
	    setText("Distance: " + Float.toString(cameraDistance), -5, -2.6f);
	    setText("Rotation Angle (Azimuth): "
		    + Float.toString(cameraAngleInXZ), -5.f, -2.8f);
	    setText("Pan Angle (Altitude): " + Float.toString(cameraAngleInYZ),
		    -5.f, -3.0f);
	}
    }

    private void showConsole() {
	gl.glColor3f(0, 1f, 0);
	String lines[] = interpreter.processInput();
	int start, end;
	start = 0;
	end = lines.length;
	// Input on the last line of console
	if (end > 30)
	    start = end - 30;
	int j = 0;
	for (int i = start; i < end; i++) {
	    setText(lines[i], -5.f, 3.f - 0.2f * (j));
	    j += 1;
	}
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

    private void showHelp() {
	// On-screen text
	gl.glColor3f(1.f, 0, 0);

	setText("KEY SHORTCUTS", -5.f, 3.f);
	gl.glColor3f(0, 1.f, 0);

	int i = 0;
	for (String string : shortcutList) {
	    setText(string, -5.f, 2.8f - 0.2f * i);
	    i += 1;
	}
    }

    private void setText(String string, float x, float y) {
	gl.glLoadIdentity();
	GLUT glut = new GLUT();
	gl.glRasterPos3f(x, y, cameraDistance - 10);
	glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, string);
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
		scene.removeObject(hash);
	    }
	    scene.selected.clear();
	    keyboard.deleteSelected = false;
	}
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
