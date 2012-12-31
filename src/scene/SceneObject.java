package scene;

import javax.vecmath.Point3f;
//import javax.vecmath.Point3i;
import javax.vecmath.Point3i;

//import objects.Box;

public class SceneObject {
    public int id;
    public Point3f point = new Point3f ();
    public Point3i color = new Point3i ();
    
    public float[] vertices;
    public int[] indices;
    public float[] colors;    
    
    public SceneObject(float[] arg0, int[] arg1, Point3i arg2) {
	vertices = arg0;
	indices = arg1;
	colors = generateColorData(arg2, arg0.length);
//	this.make(new Point3f(0, 0, 0f), new Point3i(0, 0, 255));
    }
//    public SceneObject make(Point3f location, Point3i color) {
//	Box box = new Box(2);
//
//	float[] vertices, colors;
//	int[] indices;
//
//	SceneObject object = null;
//
//	vertices = convertPoint3fArray(box.vertices);
//	indices = convertPoint3iArray(box.indices);
//	colors = generateColorData(color, vertices.length);
//
//	object = new SceneObject(vertices, indices, colors);
//	object.id = objectId;
//	object.point = location;
//
//	objectId += 1;
//	return object;
//    }
    
    
    public float[] generateColorData(Point3i color, int length) {
	float[] data = new float[length];
	for (int i = 0; i < length; i = i + 3) {
	    data[i] = color.x / 255.0f;
	    data[i + 1] = color.y / 255.0f;
	    data[i + 2] = color.z / 255.0f;
	}
	return data;
    }
}