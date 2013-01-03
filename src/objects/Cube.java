package objects;

import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

public class Cube {
    public ArrayList<Point3f> vertices = new ArrayList<Point3f>();
    public ArrayList<Point3i> indices = new ArrayList<Point3i>();
    public ArrayList<Point3f> normals = new ArrayList<Point3f>();

    public Cube(float a) {
	a = a / 2;
	vertices.add(new Point3f(-a, a, -a));// A0
	vertices.add(new Point3f(a, a, -a));// A1
	vertices.add(new Point3f(a, a, a));// A2
	vertices.add(new Point3f(-a, a, a));// A3
	vertices.add(new Point3f(-a, -a, -a));// B0
	vertices.add(new Point3f(a, -a, -a));// B1
	vertices.add(new Point3f(a, -a, a));// B2
	vertices.add(new Point3f(-a, -a, a));// B3

	indices.add(new Point3i(4, 5, 6));// Bottom B0, B1, B2	
	indices.add(new Point3i(6, 7, 4));// Bottom B2, B3, B0
	
	indices.add(new Point3i(0, 1, 5));// Back
	indices.add(new Point3i(5, 4, 0));

	indices.add(new Point3i(3, 0, 4));// Left
	indices.add(new Point3i(4, 7, 3));
	
	indices.add(new Point3i(2, 1, 0));// Top
	indices.add(new Point3i(0, 3, 2));
	
	indices.add(new Point3i(6, 2, 3));// Front
	indices.add(new Point3i(3, 7, 6));
	
	indices.add(new Point3i(5, 1, 2));// Right
	indices.add(new Point3i(2, 6, 5));
	
	for (int i = 0; i < indices.size(); i++) 
		normals.add(calculateNormal(indices.get(i)));
    }

    public Point3f calculateNormal(Point3i index) {
	Triangle triangle = new Triangle();

	triangle.v1 = vertices.get(index.x);
	triangle.v2 = vertices.get(index.y);
	triangle.v3 = vertices.get(index.z);
	
	return triangle.calculateNormal(triangle);
    }
}