package objects;

// TODO Use ArrayList<Point3f>
public class Box {
    public float[] points;
    public int[] indices;

    public Box(float a) {
	a = a / 2;
	points = new float[] { -a, a, -a, // A0
		a, a, -a, // A1
		a, a, a, // A2
		-a, a, a, // A3
		-a, -a, -a, // B0
		a, -a, -a, // B1
		a, -a, a, // B2
		-a, -a, a // B3
	};
	indices = new int[] { 4, 5, 6, 6, 7, 4, // Bottom
		0, 1, 5, 5, 4, 0, // Back
		3, 0, 4, 4, 7, 3, // Left
		0, 1, 2, 2, 3, 0, // Top
		3, 2, 6, 6, 7, 3, // Front
		2, 1, 5, 5, 6, 2 // Right
	};
	/*
	 * float[] colors = { color[0], color[1], color[2], //A0 0 color[0],
	 * color[1], color[2], //A1 1 color[0], color[1], color[2], //A2 2
	 * color[0], color[1], color[2], //A3 3 color[0], color[1], color[2],
	 * //B0 4 color[0], color[1], color[2], //B1 4 color[0], color[1],
	 * color[2], //B2 6 color[0], color[1], color[2] //B3 7 };
	 */

    }
}