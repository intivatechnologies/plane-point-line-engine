package glsl_shader;

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Map;

public class ShaderConventionLinker {
	
	private static ShaderConventionLinker instance;
	
	private Map<ShaderConvention, ShaderSourceModel> sources = new HashMap<>(
		Map.of(
			ShaderConvention.EMPTY,
			new AdvancedShaderSourceModel(new ArrayList<String>(Arrays.asList(
				"#version 330 core",
				
			    "layout (location = 0) in vec3 aPos;",
			    
			    "void main()",
			    
			    "{",
			    "    ",
			    "}"
			)), new ArrayList<String>(Arrays.asList(
				"#version 330 core",
			    
			    "out vec4 FragColor;",
			    
			    "void main()",
			    "{",
			    "    FragColor = vec4(1.0, 0.0, 0.0, 1.0);",
			    "}"
		    )))
		)
	);
	
	public static ShaderConventionLinker getInstance() {
		if(instance == null)
			instance = new ShaderConventionLinker();
		return instance;
	}
	
	public void addSource(ShaderConvention convention) throws IllegalArgumentException {
		if(sources.containsKey(convention))
			throw new IllegalArgumentException("Failure adding new source because the key is already in use.");
		else
			sources.put(convention, null);
	}
	
	public ShaderSourceModel getSource(ShaderConvention convention){
		if(!sources.containsKey(convention)) {
			AdvancedShaderSourceModel loadedModel;
			
			switch(convention) {
			case ShaderConvention.FILL:
				loadedModel = new AdvancedShaderSourceModel(new ArrayList<String>(
					Arrays.asList(
						"#version 330 core\n",
						
					    "layout (location = 0) in vec3 aPos;\n",
					    
						"uniform mat4 u_model;\n",
						"uniform mat4 u_projection;\n",
					    
					    "void main()\n",
					    
					    "{\n",
					    "    gl_Position = vec4(aPos.xyz, 1.0);\n",
					    "}"
					)),
					new ArrayList<String>(Arrays.asList(
						"#version 330 core\n",
					    
					    "out vec4 FragColor;\n",
					    
					    "void main()\n",
					    "{\n",
					    "    FragColor = vec4(1.0, 0.0, 0.0, 1.0);\n",
					    "}"
					)
				));
				break;
			default:
				loadedModel = null;
				break;
			}
			
			sources.put(convention, loadedModel);
			return loadedModel;
		}
		
		return sources.get(convention);
	}
}
