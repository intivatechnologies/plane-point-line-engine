package basic_shape;

import static org.lwjgl.opengl.GL15.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class JointShape extends Shape {
	protected int[] indices;
	private int ebo;
	
	@Override
	public void unbind() {
		super.unbind();
		glDeleteBuffers(ebo);
	}
	
	@Override
	protected void generateExtraBuffers() {
		super.generateExtraBuffers();
		ebo = glGenBuffers();
	}
	
	@Override
	protected void bindExtraBuffers() {
		super.bindExtraBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		
		fillIndices();
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
		indexBuffer.put(indices).flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
	}
	
	protected void fillIndices() {}
}
