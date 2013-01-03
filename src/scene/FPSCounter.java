package scene;

public class FPSCounter {
    private int startTime;
    private int endTime;
    private int frameTimes = 0;
    private short frames = 0;
    public String rate = "0 FPS";

    public void startCounter() {
	startTime = (int) System.currentTimeMillis();
    }
    public void postCounter() {
	endTime = (int) System.currentTimeMillis();
	frameTimes = frameTimes + endTime - startTime;
	frames += 1;
	if (frameTimes >= 1000) {
	    rate = Long.toString(frames) + " fps";
	    frames = 0;
	    frameTimes = 0;
	}
    }
}