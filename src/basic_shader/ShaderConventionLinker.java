package basic_shader;

//import java.util.HashMap;
import java.util.Map;

public class ShaderConventionLinker {
	
	public static Map<ShaderConvention, String[]> conventionalSources = Map.of(
		ShaderConvention.FILL, new String[] {
			"#version 330 core\n" +
		
		    "layout (location = 0) in vec3 aPos;\n" +
		    "void main()\n" +
		    
		    "{\n" +
		    "    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
		    "}",
		    
		    "#version 330 core\n" +
		    
		    "out vec4 FragColor;\n" +
		    
		    "void main()\n" +
		    "{\n" +
		    "    FragColor = vec4(1.0, 0.0, 0.0, 1.0);\n" +
		    "}"
		}
	);
}
