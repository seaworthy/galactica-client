import javax.media.nativewindow.util.Point;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class Mouse implements MouseListener {
    public Point point = null;

    public Mouse() {
	System.out.println("MouseListener attached");
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
	point = new Point();
	point.setX(e.getX());
	point.setY(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	
    }

    @Override
    public void mouseWheelMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
}