package scene;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.util.gl2.GLUT;

import static javax.media.opengl.GL2.*;

public class Scene {
    public GL2 gl;

    public SceneObjectManager manager = new SceneObjectManager();
    public ArrayList<Integer> selected = new ArrayList<Integer>();

    public Scene() {
	manager.addObject(new Point3f(0, 0, 0), new Point3i(0, 0, 255));
	// Add 5 random objects to scene
	Random random;

	for (int i = 0; i < 5; i++) {
	    random = new Random();
	    manager.addObject(new Point3f(random.nextInt(32) - 16,
		    random.nextInt(32) - 16, random.nextInt(32) - 16),
		    new Point3i(0, 255, 0));
	}

	// Add a cluster of objects 
	manager.addObject(new Point3f(0, 0, 0), new Point3i(0, 0, 255));
	for (int i = 1; i < 5; i++)
	    manager.addObject(new Point3f(4.f * i, 0, 0), new Point3i(255, 0, 0));
	for (int i = 1; i < 5; i++)
	    manager.addObject(new Point3f(-4.f * i, 0, 0), new Point3i(255, i * 40, 0));
	for (int i = 1; i < 5; i++)
	    manager.addObject(new Point3f(0, 4.f * i, 0), new Point3i(0, 255, 0));
	for (int i = 1; i < 5; i++)
	    manager.addObject(new Point3f(0, -4.f * i, 0), new Point3i(255, i * 40, 0));
	for (int i = 1; i < 5; i++)
	    manager.addObject(new Point3f(0, 0, 4.f * i), new Point3i(0, 0, 255));
	for (int i = 1; i < 5; i++)
	    manager.addObject(new Point3f(0, 0, -4.f * i), new Point3i(255, i * 40, 0));
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
	for (SceneObject object : manager.objects) {
	    int hash = object.hashCode();
	    if (mode == GL_SELECT)
		gl.glLoadName(hash);

	    // gl.glLoadIdentity();
	    gl.glTranslatef(object.point.x, object.point.y, object.point.z);
	    //Base object color
	    float[] colors = object.colors;
	    if (selected.contains(hash)) {
		//Selected object color
		colors = flushColor(new Point3i(255, 255, 255),
			object.vertices.length * 3);
		String string = Integer.toString(object.id) + " "
			+ Arrays.asList(object.point);

		setText(string);
	    }
	    renderObject(object.vertices, object.indices, colors);
	    gl.glTranslatef(-object.point.x, -object.point.y, -object.point.z);

	}
	gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
	gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
    }
    public void renderObject(float[] points, int[] indices, float[] colors) {
	FloatBuffer pointsFloatBuffer = Buffers.newDirectFloatBuffer(points);
	IntBuffer indicesIntBuffer = Buffers.newDirectIntBuffer(indices);
	FloatBuffer colorsFloatBuffer = Buffers.newDirectFloatBuffer(colors);

	// gl.glEnableClientState( GL.GL_NORMAL_ARRAY );
	gl.glVertexPointer(3, GL.GL_FLOAT, 0, pointsFloatBuffer);
	gl.glColorPointer(3, GL.GL_FLOAT, 0, colorsFloatBuffer);
	gl.glDrawElements(GL.GL_TRIANGLES, 36, GL.GL_UNSIGNED_INT,
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
