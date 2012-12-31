import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;

import objects.Box;

import scene.SceneObject;

public class Interpreter {
    public SceneObject cachedSceneObject = null;
    public String consoleInput = new String();
    public String[] lines = null;

    public Interpreter() {

    }

    public String[] processInput() {
	//consoleInput = string;
	lines = consoleInput.split("\\r?\\n");
	if (lines.length > 1)
	    processLast();
	return lines;
    }

    public void processLast() {
	//Connect
	if (lines[lines.length - 2].equals("> connect")
		&& lines[lines.length - 1].equals("> "))
	    System.out.println("PASS");
	//Help
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
	//Clear console
	if (lines[lines.length - 2].equals("> clear")
		&& lines[lines.length - 1].equals("> ")) {
	    consoleInput = "> ";
	}
    }

    public List<String> processCommand(String command) {
	ArrayList<String> messages = new ArrayList<String>();// "invalid command";
	messages.add("system$ invalid command, try 'help'");

	if (command.matches("add ([0-9]+) ([0-9]+),([0-9]+),([0-9]+)")) {
	    messages.remove(0);
	    Box box = new Box(2);

	    cachedSceneObject = new SceneObject(box.points, box.indices,
		    generateColorData(new int[] { 255, 255, 0 },
			    box.points.length));
	    cachedSceneObject.id = 999;
	    cachedSceneObject.point = new Point3f(8, 8, 8);
	    messages.add("id: " + cachedSceneObject.id);
	    messages.add("Object " + cachedSceneObject.hashCode() + " added");
	}
	return messages;
    }

    public float[] generateColorData(int[] color, int length) {
	float[] data = new float[length];
	for (int i = 0; i < length; i = i + 3) {
	    data[i] = color[0] / 255.0f;
	    data[i + 1] = color[1] / 255.0f;
	    data[i + 2] = color[2] / 255.0f;
	}
	return data;
    }

}
