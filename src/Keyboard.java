import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class Keyboard implements KeyListener {
    public String message = "> ";
    // Scene actions
    public boolean showViewVolume = false;
    public boolean resetScene = false;
    public boolean enableMotion = false;
    public boolean loadScene = false;

    // Object actions
    public boolean clearSelection = false;
    public boolean deleteSelected = false;

    // Camera controls
    public boolean resetView = false;
    public boolean zoomOut = false;
    public boolean zoomIn = false;
    public boolean panLeft = false;
    public boolean panRight = false;
    public boolean panUp = false;
    public boolean panDown = false;

    // GUI
    int window = 0;

    public Keyboard() {
	System.out.println("KeyListener attached");
    }

    @Override
    public void keyPressed(KeyEvent e) {
	System.out.println("Key pressed: " + e.getKeyCode());
	int activeWindow = window;
	// Default
	if (window == 0) {
	    if (e.getKeyCode() == 192) { // ~
		activeWindow = 1;
	    }
	    if (e.getKeyCode() == 27) { // ESC
		activeWindow = 2;
	    }

	    if (e.getKeyCode() == 32) { // 0 (Reset view)
		resetView = true;
		message += "View reset\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 90) { // Z (Zoom-out begin)
		zoomOut = true;
		message += "Zooming out\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 88) { // X (Zoom-in begin)
		zoomIn = true;
		message += "Zooming in\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 8) { // DEL
		deleteSelected = true;
		message += "Selected object(s) deleted\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 48) { // 0
		clearSelection = true;
		message += "Selection cleared\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 82) { // R
		resetScene = true;
		message += "Scene reset\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 77) { // M
		boolean enabled;
		enabled = enableMotion;
		if (enabled) {
		    enableMotion = false;
		    message += "Motion disabled\n";
		    message += "> ";
		} else {
		    enableMotion = true;
		    message += "Motion enabled\n";
		    message += "> ";
		}
	    }
	    if (e.getKeyCode() == 76) { // L
		loadScene = true;
		message += "Map loaded\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 37) { // Left
		panLeft = true;
		message += "Rotating left\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 39) { // Right
		panRight = true;
		message += "Rotating right\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 38) { // UP
		panUp = true;
		message += "Panning up\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 40) { // Down
		panDown = true;
		message += "Panning down\n";
		message += "> ";
	    }
	    if (e.getKeyCode() == 45) { // -
		boolean enabled;
		enabled = showViewVolume;
		if (enabled)
		    showViewVolume = false;
		else
		    showViewVolume = true;
	    }
	}
	// Console
	if (window == 1) {
	    // Reserve '~' and 'DEL' keys for other functions
	    if (e.getKeyCode() == 192) { // ~
		activeWindow = 0;
	    }
	    if (e.getKeyCode() != 192 && e.getKeyCode() != 8) {
		message += e.getKeyChar();
	    }
	    if (e.getKeyCode() == 8 && message.length() > 0) {
		message = message.substring(0, message.length() - 1);
	    }
	    if (e.getKeyCode() == 10) {
		message += "> ";
	    }
	}
	// Help
	if (window == 2) {
	    if (e.getKeyCode() == 27) { // ESC
		activeWindow = 0;
	    }
	}
	window = activeWindow;
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
