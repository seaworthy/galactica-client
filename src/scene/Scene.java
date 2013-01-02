package scene;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.gl2.GLUT;

import static javax.media.opengl.GL2.*;

public class Scene {
    public GL2 gl;

    public ArrayList<Integer> selected = new ArrayList<Integer>();
    public SceneObjectManager manager = new SceneObjectManager();

    public Scene() {
	manager.addCube(new Point3f(0, 0, 0), new Point3i(0, 0, 255));
    }

    private void setText(String string) {
	GLUT glut = new GLUT();
	gl.glColor3f(1f, 0, 0);
	gl.glRasterPos3f(2, 2, 0);
	glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, string);
    }

    public void make(GL2 gl, int mode) {

	gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

	// gl.glLoadIdentity();
	String string = new String();
	for (SceneObject object : manager.objects) {
	    int hash = object.hashCode();
	    if (mode == GL_SELECT)
		gl.glLoadName(hash);

	    // gl.glLoadIdentity();
	    gl.glTranslatef(object.start.x, object.start.y, object.start.z);
	    // Base object color
	    float[] colors = object.colors;
	    if (selected.contains(hash)) {
		// Selected object color
		colors = flushColor(new Point3i(255, 255, 255),
			object.vertices.length * 3);
		string = Integer.toString(object.id) + " "
			+ Arrays.asList(object.start);

		setText(string);
	    }
	    renderObject(object.vertices, object.indices, colors);
	    gl.glTranslatef(-object.start.x, -object.start.y, -object.start.z);

	}
	gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
	gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
    }

    public void calculatePositions(float step) {
	//* TODO Round start/end location to avoid unneccesary position calculations
	Point3f totalDisplacement = new Point3f();
	Point3f stepDisplacement = new Point3f();
	float distance, length;
	for (SceneObject object : manager.objects) {
	    if (object.start != object.end) {
		totalDisplacement.x = Math.abs(object.end.x - object.start.x);
		totalDisplacement.y = Math.abs(object.end.y - object.start.y);
		totalDisplacement.z = Math.abs(object.end.z - object.start.z);

		distance = (float) Math.sqrt(Math.pow(totalDisplacement.x, 2)
			+ Math.pow(totalDisplacement.y, 2)
			+ Math.pow(totalDisplacement.z, 2));

		length = distance / step;

		stepDisplacement = new Point3f(totalDisplacement.x / length,
			totalDisplacement.y / length, totalDisplacement.z
				/ length);
		System.out.println(totalDisplacement);
		// To smooth out movement the program has to figure out how to
		// divide displacements in x,y,z into equal segments
		// Find minimum of 3 and divide by step to get number of
		// intervals
		// Find distance from start to end and divide by step

		// Calculate step in x, and adjust start location
		if (object.start.x > object.end.x)
		    object.start.x -= stepDisplacement.x;
		else if (object.start.x < object.end.x)
		    object.start.x += stepDisplacement.x;
		// Calculate step in y, and adjust start location
		if (object.start.y > object.end.y)
		    object.start.y -= stepDisplacement.y;
		else if (object.start.y < object.end.y)
		    object.start.y += stepDisplacement.y;
		// Calculate step in z, and adjust start location
		if (object.start.z > object.end.z)
		    object.start.z -= stepDisplacement.z;
		else if (object.start.z < object.end.z)
		    object.start.z += stepDisplacement.z;
	    }
	}
    }

    public void renderObject(float[] points, int[] indices, float[] colors) {
	FloatBuffer pointsFloatBuffer = Buffers.newDirectFloatBuffer(points);
	IntBuffer indicesIntBuffer = Buffers.newDirectIntBuffer(indices);
	FloatBuffer colorsFloatBuffer = Buffers.newDirectFloatBuffer(colors);

	// gl.glEnableClientState( GL.GL_NORMAL_ARRAY );
	gl.glVertexPointer(3, GL.GL_FLOAT, 0, pointsFloatBuffer);
	gl.glColorPointer(3, GL.GL_FLOAT, 0, colorsFloatBuffer);
	// System.out.println(indices.length);
	gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT,
		indicesIntBuffer);
    }

    public float[] flushColor(Point3i color, int length) {
	float[] data = new float[length];
	for (int i = 0; i < length; i = i + 3) {
	    data[i] = color.x / 255.0f;
	    data[i + 1] = color.y / 255.0f;
	    data[i + 2] = color.z / 255.0f;
	}
	return data;
    }
}