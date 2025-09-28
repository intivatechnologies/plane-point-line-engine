package basic_shape;

import static org.lwjgl.opengl.GL11.*;

public class Circle extends Shape {
	final int NUM_SEGMENTS = 100;
	final float RADIUS = 0.5f;
	
	@Override
	public void draw() {
		super.draw();
		glDrawArrays(GL_TRIANGLE_FAN, 0, NUM_SEGMENTS + 2);
	}
	
	@Override
	protected void fillVertices() {
		super.fillVertices();
		vertices = new float[(NUM_SEGMENTS + 2) * 3];
		
		vertices[0] = 0.0f;
		vertices[1] = 0.0f;
		vertices[2] = 0.0f;
		for (int i = 0; i <= NUM_SEGMENTS; i++) {
		    double angle = 2.0 * Math.PI * i / NUM_SEGMENTS;
		    float x = (float)(RADIUS * Math.cos(angle));
		    float y = (float)(RADIUS * Math.sin(angle));
		    vertices[(i + 1) * 3]     = x;
		    vertices[(i + 1) * 3 + 1] = y;
		    vertices[(i + 1) * 3 + 2] = 0.0f;
		}
	}
}
