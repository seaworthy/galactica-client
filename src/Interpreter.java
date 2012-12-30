import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;

import objects.Box;

import scene.SceneObject;

public class Interpreter {
    public SceneObject cachedSceneObject = null;

    public Interpreter() {

    }

    public List<String> processCommand(String command) {
	ArrayList<String> messages = new ArrayList<String>();// "invalid command";
	messages.add("system$ invalid command, try 'help'");

	if (command.matches("connect")) {
	    messages.remove(0);
	    messages.add("ok");
	}
	if (command.matches("add ([0-9]+) ([0-9]+),([0-9]+),([0-9]+)")) {
	    messages.remove(0);
	    Box box = new Box(2);

	    cachedSceneObject = new SceneObject(box.points, box.indices,
		    generateColorData(new int[] { 255, 255, 0 }, box.points.length));
	    cachedSceneObject.id = 999;
	    cachedSceneObject.point = new Point3f(8, 8, 8);
	    messages.add("id: " + cachedSceneObject.id);
	    messages.add("Object " + cachedSceneObject.hashCode() + " added");
	}
	if (command.matches("remove ([0-9]+),([0-9]+),([0-9]+)")) {
	    messages.remove(0);
	    messages.add("ok");
	}
	if (command.matches("change ([0-9]+),([0-9]+),([0-9]+) ([0-9]+)")) {
	    messages.remove(0);
	    messages.add("ok");
	}
	if (command.matches("load")) {
	    messages.remove(0);
	    messages.add("ok");
	}
	if (command.matches("save")) {
	    messages.remove(0);
	    messages.add("ok");
	}
	if (command.matches("help")) {
	    messages.remove(0);
	    messages.add("system$ Available command list");
	    messages.add("connect");
	    messages.add("add");
	    messages.add("remove");
	    messages.add("change");
	    messages.add("save");
	    messages.add("load");
	    messages.add("clear");
	    messages.add("help");

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
