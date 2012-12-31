import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

import objects.Box;

import scene.SceneObject;

public class Interpreter {
    public ArrayList<SceneObject> objects = new ArrayList<SceneObject>();
    public String consoleInput = new String();
    public String[] lines = null;

    public Interpreter() {

    }

    public String[] processInput() {
	lines = consoleInput.split("\\r?\\n");
	if (lines.length > 1)
	    processLast();
	return lines;
    }

    public void processLast() {
	// Connect
	if (lines[lines.length - 2].equals("> connect")
		&& lines[lines.length - 1].equals("> ")) {
	    consoleInput += "	ok\n";
	    consoleInput += "> ";
	}
	// Help
	if (lines[lines.length - 2].equals("> help")
		&& lines[lines.length - 1].equals("> ")) {
	    consoleInput += "help\n";
	    consoleInput += "   connect\n";
	    consoleInput += "   clear\n";
	    consoleInput += "   add\n";
	    consoleInput += "   remove\n";
	    consoleInput += "   change\n";
	    consoleInput += "   load\n";
	    consoleInput += "   save\n";
	    consoleInput += "> ";
	}
	// Clear console
	if (lines[lines.length - 2].equals("> clear")
		&& lines[lines.length - 1].equals("> ")) {
	    consoleInput += "	ok\n";
	    consoleInput += "> ";
	}
	// Add object
	if (lines[lines.length - 2]
		.matches("> add ([0-9]+) ([0-9]+),([0-9]+),([0-9]+)")
		&& lines[lines.length - 1].equals("> ")) {
	    
	    String parts[] = lines[lines.length - 2].split("\\s+");
	    parts = parts[3].split(",");
	    float x = Float.parseFloat(parts[0]);
	    float y = Float.parseFloat(parts[1]);
	    float z = Float.parseFloat(parts[2]);
	    
	    addObject(new Point3f(x, y, z), new Point3i(255, 0, 0));
	    consoleInput += "	ok\n";
	    consoleInput += "> ";
	}
    }
    // EXPERIMENTAL BLOCK
    public void addObject(Point3f location, Point3i color) {
	//Load primitive to use
	Box box = new Box(2);

	//Collect vertex, index, color, data
	float[] vertices;
	int[] indices;

	vertices = convertPoint3fArray(box.vertices);
	indices = convertPoint3iArray(box.indices);

	SceneObject object = null;
	object = new SceneObject(vertices, indices, color);
	object.id = 999;
	object.point = location;

	objects.add(object);
	System.out.println("Object added!");
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

    public float[] generateColorData(Point3i color, int length) {
	float[] data = new float[length];
	for (int i = 0; i < length; i = i + 3) {
	    data[i] = color.x / 255.0f;
	    data[i + 1] = color.y / 255.0f;
	    data[i + 2] = color.z / 255.0f;
	}
	return data;
    }
    // ////////////////////////////
}
