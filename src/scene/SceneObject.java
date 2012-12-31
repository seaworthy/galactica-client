package scene;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

public class SceneObject {
    public int id;
    
    public Point3f point = new Point3f ();
    
    public float[] vertices;
    public int[] indices;
    public float[] colors;    
    
    public SceneObject(float[] arg0, int[] arg1, Point3i arg2) {
	vertices = arg0;
	indices = arg1;
	flushColor(arg2);
    }
    public void flushColor(Point3i color) {
	int length = vertices.length;
	float[] data = new float[length];
	for (int i = 0; i < length; i = i + 3) {
	    data[i] = color.x / 255.0f;
	    data[i + 1] = color.y / 255.0f;
	    data[i + 2] = color.z / 255.0f;
	}
	colors = data;
    }
}