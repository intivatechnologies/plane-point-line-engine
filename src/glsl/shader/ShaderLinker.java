package glsl.shader;

import java.util.HashMap;
import java.util.Map;

public class ShaderLinker {
	
	public enum CommunicationKey {
		EMPTY,
		FILL
	}
	
	private static ShaderLinker instance;
	public static ShaderLinker getInstance() {
		if(instance == null)
			instance = new ShaderLinker();
		return instance;
	}
	
	private Map<CommunicationKey, String[][]> sources = new HashMap<>(
		Map.of(
				CommunicationKey.EMPTY,
			new String[][] {
				{ 
					"#version 330 core\n",
					
				    "layout (location = 0) in vec3 aPos;\n",
				    
				    "void main()",
				    
				    "{",
				    "    ",
				    "}"
				},
				{ 
					"#version 330 core\n",
				    
				    "out vec4 FragColor;\n",
				    
				    "void main()",
				    "{",
				    "    FragColor = vec4(1.0, 0.0, 0.0, 1.0);",
				    "}"
				}
			}
		)
	);
	
	/*
	public void addSource(ShaderConvention convention) throws IllegalArgumentException {
		if(sources.containsKey(convention))
			throw new IllegalArgumentException("Failure adding new source because the key is already in use.");
		else
			sources.put(convention, null);
	}
	*/
	
	public String[][] getShaderSource(CommunicationKey convention){
		if(!sources.containsKey(convention)) {
			String[][] loadedModel;
			
			switch(convention) {
			case CommunicationKey.FILL:
				loadedModel = new String[][] {
					{
						"#version 330 core\n",
						
					    "layout (location = 0) in vec3 aPos;\n",
					    
					    "void main()",
					    
					    "{",
					    "    gl_Position = vec4(aPos.xyz, 1.0);",
					    "}"
					},
					{
						"#version 330 core\n",
					    
					    "out vec4 FragColor;\n",
					    
					    "void main()",
					    "{",
					    "    FragColor = vec4(1.0, 0.0, 0.0, 1.0);",
					    "}"
					}
				};
				break;
			default:
				loadedModel = null;
				break;
			}
			
			sources.put(convention, loadedModel);
			return loadedModel;
		} else
			return sources.get(convention);
	}
}
