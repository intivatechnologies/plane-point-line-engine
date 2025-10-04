package glsl_shader;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

abstract public class ShaderSourceModel {
	abstract public String getSource(int shaderEnum);
	
	protected boolean requestsShaderType(int shaderEnum) {
		return shaderEnum == GL_VERTEX_SHADER;
	}
}
