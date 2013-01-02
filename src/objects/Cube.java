package objects;

import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

public class Cube {
    public ArrayList<Point3f> vertices = new ArrayList<Point3f>();
    public ArrayList<Point3i> indices = new ArrayList<Point3i>();
    
    public Cube(float a) {
	a = a / 2;
	vertices.add(new Point3f (-a, a, -a));//A0
	vertices.add(new Point3f (a, a, -a));//A1
	vertices.add(new Point3f (a, a, a));//A2
	vertices.add(new Point3f (-a, a, a));//A3
	vertices.add(new Point3f (-a, -a, -a));//B0
	vertices.add(new Point3f (a, -a, -a));//B1
	vertices.add(new Point3f (a, -a, a));//B2
	vertices.add(new Point3f (-a, -a, a));//B3

	indices.add(new Point3i (4, 5, 6));//Bottom
	indices.add(new Point3i (6, 7, 4));
	indices.add(new Point3i (0, 1, 5));//Back
	indices.add(new Point3i (5, 4, 0));
	indices.add(new Point3i (3, 0, 4));//Left
	indices.add(new Point3i (4, 7, 3));
	indices.add(new Point3i (0, 1, 2));//Top
	indices.add(new Point3i (2, 3, 0));
	indices.add(new Point3i (3, 2, 6));//Front
	indices.add(new Point3i (6, 7, 3));
	indices.add(new Point3i (2, 1, 5));//Right
	indices.add(new Point3i (5, 6, 2));
    }
}