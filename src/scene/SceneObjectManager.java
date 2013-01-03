package scene;

import java.util.ArrayList;

import java.util.Random;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

import objects.Cube;
import objects.Sphere;

public class SceneObjectManager {
    public int objectId = 0;
    public ArrayList<SceneObject> objects = new ArrayList<SceneObject>();

    public SceneObjectManager() {

    }

    public void loadMap() {
	// Add 5 random objects to scene
	Random random;

	for (int i = 0; i < 5; i++) {
	    random = new Random();
	    addCube(new Point3f(random.nextInt(64) - 32,
		    random.nextInt(64) - 32, random.nextInt(64) - 32),
		    new Point3i(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
	}
	// Add a cluster of objects
	addCube(new Point3f(0, 0, 0), new Point3i(0, 0, 255));
	for (int i = 1; i < 5; i++)
	    addCube(new Point3f(4.f * i, 0, 0), new Point3i(255, 0, 0));
	for (int i = 1; i < 5; i++)
	    addCube(new Point3f(-4.f * i, 0, 0), new Point3i(255, i * 40, 0));
	for (int i = 1; i < 5; i++)
	    addCube(new Point3f(0, 4.f * i, 0), new Point3i(0, 255, 0));
	for (int i = 1; i < 5; i++)
	    addCube(new Point3f(0, -4.f * i, 0), new Point3i(255, i * 40, 0));
	for (int i = 1; i < 5; i++)
	    addCube(new Point3f(0, 0, 4.f * i), new Point3i(0, 0, 255));
	for (int i = 1; i < 5; i++)
	    addCube(new Point3f(0, 0, -4.f * i), new Point3i(255, i * 40, 0));
    }

    public void addCube(Point3f location, Point3i color) {
	Cube cube = new Cube(2);

	SceneObject object = null;

	float[] vertices = unpackPoint3fList(cube.vertices);
	int[] indices = unpackPoint3iList(cube.indices);
	float[] normals = unpackPoint3fList(cube.normals);

	object = new SceneObject(vertices, indices, normals, color);
	object.id = objectId;
	object.start = location;
	object.end = location;

	//* TODO NORMALS TEST
	//object.normals = cube.normals;
	
	objectId += 1;
	objects.add(object);
    }
    public void addSphere(Point3f location, Point3i color) {
	Sphere sphere = new Sphere(2);

	SceneObject object = null;

	float[] vertices = unpackPoint3fList(sphere.vertices);
	int[] indices = unpackPoint3iList(sphere.indices);
	float[] normals = new float[0];

	object = new SceneObject(vertices, indices, normals, color);
	object.id = objectId;
	object.start = location;
	// object.end = location;
	object.end = new Point3f(0, 0, 0);

	//* TODO NORMALS TEST
	//object.normals = sphere.normals;
	
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
    public float[] unpackPoint3fList(ArrayList<Point3f> list) {
	float[] data = new float[list.size() * 3];
	int i = 0;
	for (Point3f element : list) {
	    data[i] = element.x;
	    data[i + 1] = element.y;
	    data[i + 2] = element.z;
	    i += 3;
	}
	return data;
    }
    public int[] unpackPoint3iList(ArrayList<Point3i> list) {
	int[] data = new int[list.size() * 3];
	int i = 0;
	for (Point3i element : list) {
	    data[i] = element.x;
	    data[i + 1] = element.y;
	    data[i + 2] = element.z;
	    i += 3;
	}
	return data;
    }
}