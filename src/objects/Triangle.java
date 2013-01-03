package objects;

import javax.vecmath.Point3f;

public class Triangle {
    Point3f v1 = new Point3f();
    Point3f v2 = new Point3f();
    Point3f v3 = new Point3f();

    public Triangle() {
    }

    // Triangle triangle = new Triangle();
    // for (int i = 0; i < indices.size(); i++) {
    // triangle.x = vertices.get(indices.get(i).x);
    // triangle.y = vertices.get(indices.get(i).y);
    // triangle.z = vertices.get(indices.get(i).z);
    // normals.add(triangle.calculateNormal(triangle));
    //
    // Point3f A = new Point3f(triangle.y.x - triangle.x.x, triangle.y.y
    // - triangle.x.y, triangle.y.z - triangle.x.z);
    //
    // Point3f B = new Point3f(triangle.y.x - triangle.z.x, triangle.y.y
    // - triangle.z.y, triangle.y.z - triangle.z.z);
    //
    // normals.set(i, new Point3f(A.y*B.z - A.z*B.y, -(A.x*B.z - A.z*B.x),
    // A.x*B.y - A.y*B.x));
    //
    // System.out.format("x: %s, y: %s, z: %s, n: %s%n", triangle.x,
    // triangle.y, triangle.z, normals.get(i));
    //
    // }
    public Point3f calculateNormal(Triangle triangle) {
	Point3f A = new Point3f(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z);
	Point3f B = new Point3f(v2.x - v3.x, v2.y - v3.y, v2.z - v3.z);

	Point3f normal = new Point3f();

//	normal.x = A.y * B.z - A.z * B.y;
//	normal.y = A.z * B.x - A.x * B.z;
//	normal.z = A.x * B.y - A.y * B.x;

	normal.x = B.y * A.z - B.z * A.y;
	normal.y = B.z * A.x - B.x * A.z;
	normal.z = B.x * A.y - B.y * A.x;
	
	float magnitude = calculateMagnitude(normal);
	//
	normal.x /= magnitude;
	normal.y /= magnitude;
	normal.z /= magnitude;

	return normal;
    }

    public float calculateMagnitude(Point3f normal) {
	return (float) Math.sqrt((normal.x * normal.x) + (normal.y * normal.y)
		+ (normal.z * normal.z));
    }
}
