package scene;

import javax.vecmath.Point3f;

public class SceneObject {
    public int id;
    public Point3f point = new Point3f ();

    public float[] points;
    public int[] indices;
    public float[] colors;    
    
    public SceneObject(float[] pointsNew, int[] indicesNew, float [] colorsNew) {
	points = pointsNew;
	indices = indicesNew;
	colors = colorsNew;	
    }
}