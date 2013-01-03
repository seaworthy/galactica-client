package objects;

import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Point3i;

public class Sphere {
    public ArrayList<Point3f> vertices = new ArrayList<Point3f>();
    public ArrayList<Point3i> indices = new ArrayList<Point3i>();
    public ArrayList<Point3f> normals = new ArrayList<Point3f>();

    public Sphere(float r) {
	int stacks = 12;
	int slices = 12;
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

	for (int i = 1; i < slices; i++) {
	    indices.add(new Point3i(0, i, i + 1));
	}
	indices.add(new Point3i(0, slices, 1));

	for (int j = 1; j < stacks - 1; j++) {
	    for (int i = 1; i < slices; i++) {
		indices.add(new Point3i(slices * (j - 1) + i, slices * (j - 1)
			+ i + 1, slices * j + i + 1));
		indices.add(new Point3i(slices * j + i + 1, slices * j + i,
			slices * (j - 1) + i));
	    }
	    indices.add(new Point3i(slices * j, slices * (j - 1) + 1, slices
		    * j + 1));
	    indices.add(new Point3i(slices * j + 1, slices * (j + 1), slices
		    * j));
	}

	for (int i = 1; i < slices; i++) {
	    indices.add(new Point3i((slices - 1) * (stacks - 1) + i - 1,
		    (slices - 1) * (stacks - 1) + i, slices * (stacks - 1) + 1));

	}
	indices.add(new Point3i(slices * (stacks - 1), (slices - 1)
		* (stacks - 1), slices * (stacks - 1) + 1));
	// for (Point3i index : indices) {
	// System.out.println(index);
	// }
//	Triangle triangle = new Triangle();
//	for (int i = 0; i < indices.size(); i++) {
//	    triangle.x = vertices.get(indices.get(i).x);
//	    triangle.y = vertices.get(indices.get(i).y);
//	    triangle.z = vertices.get(indices.get(i).z);
//	    normals.add(triangle.calculateNormal(triangle));
//
//	    Point3f A = new Point3f(triangle.y.x - triangle.x.x, triangle.y.y
//		    - triangle.x.y, triangle.y.z - triangle.x.z);
//
//	    Point3f B = new Point3f(triangle.y.x - triangle.z.x, triangle.y.y
//		    - triangle.z.y, triangle.y.z - triangle.z.z);
//
//	    x = A.y * B.z - A.z * B.y;
//	    y = -(A.x * B.z - A.z * B.x);
//	    z = A.x * B.y - A.y * B.x;
//	    
//	    normals.set(i, new Point3f(10*x, 10*y, 10*z));
//
//	    System.out.format("x: %s, y: %s, z: %s, n: %s%n", triangle.x,
//		    triangle.y, triangle.z, normals.get(i));
//
//	}
//	// for (Point3f normal : normals) {
//	// System.out.println(normal);
//	// }
    }
}