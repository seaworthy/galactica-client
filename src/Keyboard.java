import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;


public class Keyboard implements KeyListener {
    //public List<String> consoleOutput = new ArrayList<String>();;
    public String message = "> ";
    //Scene actions
    public boolean showViewVolume = false;
    
    //Object actions
    public boolean clearSelection = false;
    public boolean deleteSelected = false;

    //Camera controls
    public boolean resetView = false;
    public boolean zoomOut = false;
    public boolean zoomIn = false;
    public boolean panLeft = false;
    public boolean panRight = false;
    public boolean panUp = false;
    public boolean panDown = false;

    //GUI
    public boolean showHelp = false;
    public boolean showConsole = false;
    public boolean removeLastCharacter = false;

       
    public Keyboard() {
	System.out.println("KeyListener attached");
    }

    @Override
    public void keyPressed(KeyEvent e) {
	System.out.println("Key pressed: " + e.getKeyCode());
	if (showConsole) {
	    /* TODO Add carriage return when console toggled off */
	    //Reserve '~' and 'DEL' keys for other functions
	    if (e.getKeyCode() != 192 && e.getKeyCode() != 8) {
		message += e.getKeyChar();
	    }
	    if (e.getKeyCode() == 8 && message.length() > 0) {
		message = message.substring(0, message.length() - 1);
	    }
	    if (e.getKeyCode() == 10) {
		message += "> ";
	    }

	} else {
	    // 0 (Reset view)
	    if (e.getKeyCode() == 32) {
		resetView = true;
		message += "View reset\n";
		message += "> ";
	    }
	    // Z (Zoom-out begin)
	    if (e.getKeyCode() == 90) {
		zoomOut = true;
		message += "Zooming out\n";
		message += "> ";
	    }
	    // X (Zoom-in begin)
	    if (e.getKeyCode() == 88) {
		zoomIn = true;
		message += "Zooming in\n";
		message += "> ";
	    }
	    // DEL
	    if (e.getKeyCode() == 8) {
		deleteSelected = true;
		message += "Selected object(s) deleted\n";
		message += "> ";
	    }
	    // 0
	    if (e.getKeyCode() == 48) {
		clearSelection = true;
		message += "Selection cleared\n";
		message += "> ";
	    }
	    // Left
	    if (e.getKeyCode() == 37) {
		panLeft = true;
		message += "Rotating left\n";
		message += "> ";
	    }
	    // Right
	    if (e.getKeyCode() == 39) {
		panRight = true;
		message += "Rotating right\n";
		message += "> ";
	    }
	    // UP
	    if (e.getKeyCode() == 38) {
		panUp = true;
		message += "Panning up\n";
		message += "> ";
	    }
	    // Down
	    if (e.getKeyCode() == 40) {
		panDown = true;
		message += "Panning down\n";
		message += "> ";
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
