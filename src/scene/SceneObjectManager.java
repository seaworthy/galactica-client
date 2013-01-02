package scene;

import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

import objects.Cube;
import objects.Sphere;

public class SceneObjectManager {
    public int objectId = 0;
    public ArrayList<SceneObject> objects = new ArrayList<SceneObject>();

    public SceneObjectManager() {

    }
    public void addCube(Point3f location, Point3i color) {
	Cube box = new Cube(2);
	
	SceneObject object = null;

	float[] vertices = convertPoint3fArray(box.vertices);
	int[] indices = convertPoint3iArray(box.indices);

	object = new SceneObject(vertices, indices, color);
	object.id = objectId;
	object.point = location;

	objectId += 1;
	objects.add(object);
    }
    public void addSphere(Point3f location, Point3i color) {
	Sphere sphere = new Sphere(2);
	
	SceneObject object = null;

	float[] vertices = convertPoint3fArray(sphere.vertices);
	int[] indices = convertPoint3iArray(sphere.indices);

	object = new SceneObject(vertices, indices, color);
	object.id = objectId;
	object.point = location;

	objectId += 1;
	objects.add(object);
    }
    public void removeObject(int hash) {
	int index = getIndex(hash);
	objects.remove(index);
    }
    public void changeObjectColor(int hash, Point3i color) {	
	int index = getIndex(hash);
	objects.get(index).flushColor(color);
    }
    public int getIndex(int hash) {
	int index = 0, i = 0;
	for (SceneObject object : objects) {
	    if (object.hashCode() == hash)
		index = i;
	    i += 1;
	}
	return index;
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
    public int[] convertPoint3iArray(ArrayList<Point3i> indices) {
	int[] data = new int[indices.size() * 3];
	int i = 0;
	for (Point3i index : indices) {
	    data[i] = index.x;
	    data[i + 1] = index.y;
	    data[i + 2] = index.z;
	    i += 3;
	}
	return data;
    }
}