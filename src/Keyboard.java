
import java.util.ArrayList;
import java.util.List;

import scene.SceneObject;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;


public class Keyboard implements KeyListener {
    public List<String> consoleOutput = new ArrayList<String>();;
    public boolean resetView = false;
    public boolean zoomOut = false;
    public boolean zoomIn = false;

    public boolean clearSelection = false;
    public boolean deleteSelected = false;
    public boolean showHelp = false;
    public boolean showConsole = false;
    public boolean removeLastCharacter = false;
    public boolean showViewVolume = false;

    public boolean panLeft = false;
    public boolean panRight = false;
    public boolean panUp = false;
    public boolean panDown = false;

    public String command = "> ";
    public String text = null;
   
    public Interpreter interpreter = new Interpreter();
    
    public ArrayList<SceneObject> cachedSceneObjectList = new ArrayList<SceneObject>();

    public Keyboard() {
	System.out.println("KeyListener attached");
    }

    @Override
    public void keyPressed(KeyEvent e) {
	System.out.println("Key pressed: " + e.getKeyCode());
	if (showConsole) {
	    if (e.getKeyCode() == 8 && command.length() > 2)
		command = command.substring(0, command.length() - 1);
	    else if (e.getKeyCode() != 192)
		command += e.getKeyChar();

	    if (e.getKeyCode() == 10) {
		command = command.substring(2, command.length() - 1);
		consoleOutput.add("user$ " + command);
		
		cachedSceneObjectList.add(interpreter.cachedSceneObject);

		consoleOutput.addAll(interpreter.processCommand(command));
		command = "> ";
	    }
	} else {
	    // 0 (Reset view)
	    if (e.getKeyCode() == 32) {
		resetView = true;
		text = "View reset";
	    }
	    // Z (Zoom-out begin)
	    if (e.getKeyCode() == 90) {
		zoomOut = true;
		text = "Zooming out";
	    }
	    // X (Zoom-in begin)
	    if (e.getKeyCode() == 88) {
		zoomIn = true;
		text = "Zooming in";
	    }
	    // DEL
	    if (e.getKeyCode() == 8) {
		deleteSelected = true;
		text = "Selected object(s) deleted";
	    }
	    // 0
	    if (e.getKeyCode() == 48) {
		clearSelection = true;
		text = "Selection cleared";
	    }
	    // Left
	    if (e.getKeyCode() == 37) {
		panLeft = true;
		text = "Rotating left";
	    }
	    // Right
	    if (e.getKeyCode() == 39) {
		panRight = true;
		text = "Rotating right";
	    }
	    // UP
	    if (e.getKeyCode() == 38) {
		panUp = true;
		text = "Panning up";
	    }
	    // Down
	    if (e.getKeyCode() == 40) {
		panDown = true;
		text = "Panning down";
	    }
	    // ESC
	    if (e.getKeyCode() == 27) {
		boolean enabled;
		enabled = showHelp;
		if (enabled)
		    showHelp = false;
		else
		    showHelp = true;
	    }
	    // -
	    if (e.getKeyCode() == 45) {
		boolean enabled;
		enabled = showViewVolume;
		if (enabled)
		    showViewVolume = false;
		else
		    showViewVolume = true;
	    }
	}
	// ~
	if (e.getKeyCode() == 192) {
	    boolean enabled;
	    enabled = showConsole;
	    if (enabled)
		showConsole = false;
	    else
		showConsole = true;
	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
	System.out.println("Key released: " + e.getKeyCode());
	if (e.getKeyCode() == 90) {
	    zoomOut = false;
	}
	if (e.getKeyCode() == 88) {
	    zoomIn = false;
	}
	// Left
	if (e.getKeyCode() == 37) {
	    panLeft = false;
	}
	// Right
	if (e.getKeyCode() == 39) {
	    panRight = false;
	}
	if (e.getKeyCode() == 38) {
	    panUp = false;
	}
	// Down
	if (e.getKeyCode() == 40) {
	    panDown = false;
	}
    }

    @Override
    public void keyTyped(KeyEvent e) {
	System.out.println("Key typed: " + e.getKeyCode());
    }
}
