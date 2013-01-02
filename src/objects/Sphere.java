package objects;

import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

public class Sphere {
    public ArrayList<Point3f> vertices = new ArrayList<Point3f>();
    public ArrayList<Point3i> indices = new ArrayList<Point3i>();

    public Sphere(float r) {
	int stacks = 6;
	int slices = 6;
	float x, y, z, theta, phi;

	vertices.add(new Point3f(0, 0, 1));

	for (int i = 1; i <= stacks - 1; i++) {
	    for (int j = 1; j <= slices; j++) {
		theta = (float) (Math.PI * i / stacks);
		phi = (float) (2 * Math.PI * j / slices);
		x = (float) (Math.sin(theta) * Math.cos(phi));
		y = (float) (Math.sin(theta) * Math.sin(phi));
		z = (float) Math.cos(theta);
		vertices.add(new Point3f(x, y, z));

	    }
	}

	vertices.add(new Point3f(0, 0, -1.f));
	
	//Sphere is rendered string from +z and going into -z 
	for (int i = 1; i<=slices; i++) {
	    indices.add(new Point3i(0, i, i+1));
	}
	indices.remove(indices.size()-1);
	indices.add(new Point3i(0, slices, 1));
	
	// vertices.add(new Point3f(-a, a, -a));// A0
	// vertices.add(new Point3f(a, a, -a));// A1
	// vertices.add(new Point3f(a, a, a));// A2
	// vertices.add(new Point3f(-a, a, a));// A3
	// vertices.add(new Point3f(-a, -a, -a));// B0
	// vertices.add(new Point3f(a, -a, -a));// B1
	// vertices.add(new Point3f(a, -a, a));// B2
	// vertices.add(new Point3f(-a, -a, a));// B3

	// indices.add(new Point3i(4, 5, 6));// Bottom
	// indices.add(new Point3i(6, 7, 4));
	// indices.add(new Point3i(0, 1, 5));// Back
	// indices.add(new Point3i(5, 4, 0));
	// indices.add(new Point3i(3, 0, 4));// Left
	// indices.add(new Point3i(4, 7, 3));
	// indices.add(new Point3i(0, 1, 2));// Top
	// indices.add(new Point3i(2, 3, 0));
	// indices.add(new Point3i(3, 2, 6));// Front
	// indices.add(new Point3i(6, 7, 3));
	// indices.add(new Point3i(2, 1, 5));// Right
	// indices.add(new Point3i(5, 6, 2));
    }
}