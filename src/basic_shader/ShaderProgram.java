package basic_shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

class Shader implements AutoCloseable {
	
	private int shaderId;
	public int getShaderId() { return shaderId; }
	
	public void run(ShaderConvention convention, int shaderEnum) {
		final int sourceAssignment = shaderEnum == GL_VERTEX_SHADER? 0 : 1;
		String source = ShaderConventionLinker.conventionalSources.get(convention)[sourceAssignment];
		
		shaderId = glCreateShader(shaderEnum);
        glShaderSource(shaderId, source);
        glCompileShader(shaderId);
        
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            String infoLog = glGetShaderInfoLog(shaderId, 512);
            String errMsg = "ERROR::SHADER::" + (shaderEnum == GL_VERTEX_SHADER? "VERTEX" : "FRAGMENT")
            		+ "COMPILATION_FAILED\n" + infoLog;
            System.err.println(errMsg);
        }
	}
	
	@Override
	public void close() {
		glDeleteShader(shaderId);
	}
}

public class ShaderProgram {
	
	private ShaderConvention convention = ShaderConvention.FILL;
	private int programId;
	
	public ShaderProgram() {}
	
	public ShaderProgram(ShaderConvention convention) { this.convention = convention; }
	
	public void run() {
		try (Shader vertex = new Shader();
				Shader fragment = new Shader()){
			vertex.run(convention, GL_VERTEX_SHADER);
			fragment.run(convention, GL_FRAGMENT_SHADER);
			
			programId = glCreateProgram();
			glAttachShader(programId, vertex.getShaderId());
			glAttachShader(programId, fragment.getShaderId());
			glLinkProgram(programId);
			
			int shaderSuccess = glGetProgrami(programId, GL_LINK_STATUS);
			if (shaderSuccess == GL_FALSE) {
			    String infoLog = glGetProgramInfoLog(programId, 512);
			    System.err.println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
			}
		}
	}
	
	public int getProgramId() { return programId; }
}
