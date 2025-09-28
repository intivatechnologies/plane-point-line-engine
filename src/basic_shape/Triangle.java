package basic_shape;

import static org.lwjgl.opengl.GL11.*;

public class Triangle extends Shape {
	@Override
	public void draw() {
		super.draw();
		glDrawArrays(GL_TRIANGLES, 0, 3);
	}
	
	@Override
	protected void fillVertices() {
		super.fillVertices();
		vertices = new float[] {
			-0.5f, -0.5f, 0.0f, // left  
	         0.5f, -0.5f, 0.0f, // right 
	         0.0f,  0.5f, 0.0f  // top
		};
	}
}
