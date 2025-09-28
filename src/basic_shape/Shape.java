package basic_shape;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public abstract class Shape {
	protected float[] vertices;
	private int vao, vbo;
	private int animationType = GL_STATIC_DRAW;
	
	public void run() {
		vao = glGenVertexArrays();
		vbo = glGenBuffers();
		generateExtraBuffers();
		
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		fillVertices();
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertexBuffer.put(vertices).flip();
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, animationType);
		
		bindExtraBuffers();
		
		/*
		 * 
		 * The vertex shader allows us to specify any input we want in the form of
		 * vertex attributes and while this allows for great flexibility, it does
		 * mean we have to manually specify what part of our input data goes to
		 * which vertex attribute in the vertex shader. This means we have to
		 * specify how OpenGL should interpret the vertex data before rendering.
		 */
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0L);
		glEnableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0); 
		glBindVertexArray(0); 
	}
	
	public void draw() {
		glBindVertexArray(vao);
	}
	
	public void unbind() {
		glDeleteVertexArrays(vao);
		glDeleteBuffers(vbo);
	}
	
	protected void generateExtraBuffers() {}
	
	protected void bindExtraBuffers() {}
	
	protected void fillVertices() {}
}
