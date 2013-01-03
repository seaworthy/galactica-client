package scene;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

public class SceneObject {
    public int id;
    
    public Point3f start = new Point3f ();
    public Point3f end = new Point3f ();
    
    public float[] vertices;
    public int[] indices;
    public float[] colors;
    public float[] normals;
        
    //http://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
    public enum Shape {
	    CUBE, SPHERE
	}
    
    public SceneObject(float[] arg0, int[] arg1, float[] arg2, Point3i arg3) {
	vertices = arg0;
	indices = arg1;
	normals = arg2;
	flushColor(arg3);
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