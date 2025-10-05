package glsl_shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

class Shader implements AutoCloseable {
	//Map<ShaderConvention, ShaderSourceModel> ConventionalShaderLinker.sources;
	
	private int shaderId;
	public int getShaderId() { return shaderId; }
	
	public void run(String sourceCode, int shaderEnum) {
		shaderId = glCreateShader(shaderEnum);
		glShaderSource(shaderId, sourceCode);
		glCompileShader(shaderId);
		
		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            String infoLog = glGetShaderInfoLog(shaderId, 512);
            String errMsg = "ERROR::SHADER::" + (shaderEnum == GL_VERTEX_SHADER? "VERTEX" : "FRAGMENT")
            		+ "::COMPILATION_FAILED\n" + infoLog;
            System.err.println(errMsg);
        }
	}
	
	@Override
	public void close() {
		glDeleteShader(shaderId);
	}
}

public class ShaderProgram {
	
	private int id;
	public int getId() { return id; }
	
	public void run(String vertexSrc, String fragmentSrc) {
		try (Shader vertex = new Shader();
				Shader fragment = new Shader()){
			vertex.run(vertexSrc, GL_VERTEX_SHADER);
			fragment.run(fragmentSrc, GL_FRAGMENT_SHADER);
			
			id = glCreateProgram();
			glAttachShader(id, vertex.getShaderId());
			glAttachShader(id, fragment.getShaderId());
			glLinkProgram(id);
			
			int shaderSuccess = glGetProgrami(id, GL_LINK_STATUS);
			if (shaderSuccess == GL_FALSE) {
			    String infoLog = glGetProgramInfoLog(id, 512);
			    System.err.println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
			}
		}
	}
}
