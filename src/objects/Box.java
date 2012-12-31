package objects;

import java.util.ArrayList;

import javax.vecmath.Point3f;

public class Box {
    public Point3f[] points;
    public ArrayList<Point3f> vertices = new ArrayList<Point3f>();
    public int[] indices;

    public Box(float a) {
	a = a / 2;
	vertices.add(new Point3f (-a, a, -a));//A0
	vertices.add(new Point3f (a, a, -a));//A1
	vertices.add(new Point3f (a, a, a));//A2
	vertices.add(new Point3f (-a, a, a));//A3
	vertices.add(new Point3f (-a, -a, -a));//B0
	vertices.add(new Point3f (a, -a, -a));//B1
	vertices.add(new Point3f (a, -a, a));//B2
	vertices.add(new Point3f (-a, -a, a));//B3

	indices = new int[] { 4, 5, 6, 6, 7, 4, // Bottom
		0, 1, 5, 5, 4, 0, // Back
		3, 0, 4, 4, 7, 3, // Left
		0, 1, 2, 2, 3, 0, // Top
		3, 2, 6, 6, 7, 3, // Front
		2, 1, 5, 5, 6, 2 // Right
	};
    }
}