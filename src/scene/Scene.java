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

import objects.Box;

import static javax.media.opengl.GL2.*;

public class Scene {
    public GL2 gl;

    public ArrayList<Integer> selectedObjectList = new ArrayList<Integer>();
    public ArrayList<SceneObject> visibleObjectList = new ArrayList<SceneObject>();

    public int objectId = 0;

    public Scene() {
	// Random
	Random random;

	for (int i = 0; i < 5; i++) {
	    random = new Random();
	    addObject(new Point3f(random.nextInt(32) - 16,
		    random.nextInt(32) - 16, random.nextInt(32) - 16),
		    new Point3i(0, 255, 0));
	}

	// Star
	addObject(new Point3f(0, 0, 0), new Point3i(0, 0, 255));
	for (int i = 1; i < 5; i++)
	    addObject(new Point3f(4.f * i, 0, 0), new Point3i(255, 0, 0));
	for (int i = 1; i < 5; i++)
	    addObject(new Point3f(-4.f * i, 0, 0), new Point3i(255, i * 40, 0));
	for (int i = 1; i < 5; i++)
	    addObject(new Point3f(0, 4.f * i, 0), new Point3i(0, 255, 0));
	for (int i = 1; i < 5; i++)
	    addObject(new Point3f(0, -4.f * i, 0), new Point3i(255, i * 40, 0));
	for (int i = 1; i < 5; i++)
	    addObject(new Point3f(0, 0, 4.f * i), new Point3i(0, 0, 255));
	for (int i = 1; i < 5; i++)
	    addObject(new Point3f(0, 0, -4.f * i), new Point3i(255, i * 40, 0));
    }

    public void make(GL2 gl, int mode) {
	gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
	gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

	int hash, id;
	float[] points, colors;
	int[] indices;
	String string;
	// gl.glLoadIdentity();
	for (SceneObject object : visibleObjectList) {
	    id = object.id;
	    hash = object.hashCode();
	    points = object.points;
	    indices = object.indices;
	    colors = object.colors;

	    if (mode == GL_SELECT)
		gl.glLoadName(hash);

	    // gl.glLoadIdentity();
	    gl.glTranslatef(object.point.x, object.point.y, object.point.z);
	    if (selectedObjectList.contains(hash)) {
		colors = generateColorData(new Point3i(255, 255, 255),
			points.length * 3);
		string = Integer.toString(id) + " "
			+ Arrays.asList(object.point);

		setText(string);
	    }
	    renderObject(points, indices, colors);
	    gl.glTranslatef(-object.point.x, -object.point.y, -object.point.z);

	}
	gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
	gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
    }

    private void setText(String string) {
	GLUT glut = new GLUT();
	gl.glColor3f(1f, 0, 0);
	gl.glRasterPos3f(2, 2, 0);
	glut.glutBitmapString(GLUT.BITMAP_HELVETICA_10, string);
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

    public void addObject(Point3f location, Point3i color) {
	Box box = new Box(2);

	float[] vertices, colors;
	int[] indices;

	SceneObject object = null;

	vertices = convertPoint3fArray(box.vertices);
	indices = box.indices;
	colors = generateColorData(color, vertices.length);

	object = new SceneObject(vertices, indices, colors);
	object.id = objectId;
	object.point = location;

	visibleObjectList.add(object);
	objectId += 1;
    }

    public void removeObject(int hash) {
	int index = 0, i = 0;
	for (SceneObject object : visibleObjectList) {
	    if (object.hashCode() == hash)
		index = i;
	    i += 1;
	}
	visibleObjectList.remove(index);
    }

    public float[] convertPoint3fArray(ArrayList<Point3f> vertices) {
	float[] data = new float[vertices.size() * 3];
	int i = 0;
	for (Point3f vertex : vertices) {
	    data[i] = vertex.x;
	    data[i + 1] = vertex.y;
	    data[i + 2] = vertex.z;
	    i += 3;
	}
	return data;
    }

    public float[] generateColorData(Point3i color, int length) {
	// TODO Data into ArrayList<Point3f>
	float[] data = new float[length];
	for (int i = 0; i < length; i = i + 3) {
	    data[i] = color.x / 255.0f;
	    data[i + 1] = color.y / 255.0f;
	    data[i + 2] = color.z / 255.0f;
	}
	return data;
    }
}
