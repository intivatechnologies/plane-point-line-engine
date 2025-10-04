package basic_shape;

import static org.lwjgl.opengl.GL15.*;

public class Rectangle extends JointShape {
	
	@Override
	public void draw() {
		super.draw();
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0L);
	}
	
	@Override
	protected void fillVertices() {
		super.fillVertices();
		vertices = new float[] {
			0.5f,  0.5f, 0.0f,  // top right
	         0.5f, -0.5f, 0.0f,  // bottom right
	        -0.5f, -0.5f, 0.0f,  // bottom left
	        -0.5f,  0.5f, 0.0f
		};
	}
	
	@Override
	protected void fillIndices() {
		indices = new int[] {
			0, 1, 3,   // first triangle
		    1, 2, 3    // second triangle
		};
	}
}
