import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

import scene.SceneObject;
import scene.SceneObjectManager;

public class Interpreter {
    public SceneObjectManager manager = new SceneObjectManager();
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
	    consoleInput += "   add shape x,y,z R,G,B\n";
	    consoleInput += "   remove\n";
	    consoleInput += "   change\n";
	    consoleInput += "   load\n";
	    consoleInput += "   save\n";
	    consoleInput += "> ";
	}
	// Clear console
	// TODO Fix clear console
	if (lines[lines.length - 2].equals("> clear")
		&& lines[lines.length - 1].equals("> ")) {
	    consoleInput += "	ok\n";
	    consoleInput += "> ";
	}
	// List
	if (lines[lines.length - 2].equals("> list")
		&& lines[lines.length - 1].equals("> ")) {
	    listObjects();
	    consoleInput += "	ok\n";
	    consoleInput += "> ";
	}
	// Add object
	if (lines[lines.length - 2]
		.matches("> add ([0-9]+) ([0-9]+),([0-9]+),([0-9]+) ([0-9]+),([0-9]+),([0-9]+)")
		&& lines[lines.length - 1].equals("> ")) {

	    addObject(lines[lines.length - 2].split("\\s+"));
	    consoleInput += "	ok\n";
	    consoleInput += "> ";
	}
	// Remove
	// TODO Fix clear console
	if (lines[lines.length - 2].matches("> remove ([0-9]+)")
		&& lines[lines.length - 1].equals("> ")) {
	    removeObject(lines[lines.length - 2].split("\\s+"));
	    consoleInput += "	ok\n";
	    consoleInput += "> ";
	}
    }
    public void addObject(String[] args) {
	String arg1[] = args[3].split(",");// Origin
	String arg2[] = args[4].split(",");// Color
	Point3f origin = new Point3f(Float.parseFloat(arg1[0]),
		Float.parseFloat(arg1[1]), Float.parseFloat(arg1[2]));
	Point3i color = new Point3i(Integer.parseInt(arg2[0]),
		Integer.parseInt(arg2[1]), Integer.parseInt(arg2[2]));

	manager.addObject(origin, color);
	System.out.println("Object added!");
    }
    public void removeObject(String[] args) {
	manager.removeObject(Integer.parseInt(args[2]));
	System.out.println("Object removed!");
    }
    public void listObjects() {
	for (SceneObject object : manager.objects) {
	    consoleInput += "	" + object.hashCode() + "\n";
	}
	consoleInput += "> ";
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
