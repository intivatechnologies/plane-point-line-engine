package glsl_shader;

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Map;

public class ConventionalShaderLinker {
	
	private static ConventionalShaderLinker instance;
	public static ConventionalShaderLinker getInstance() {
		if(instance == null)
			instance = new ConventionalShaderLinker();
		return instance;
	}
	
	private Map<ShaderConvention, ShaderSourceModel> sources = new HashMap<>(
		Map.of(
			ShaderConvention.EMPTY,
			new ShaderSourceModel(new ArrayList<String>(Arrays.asList(
				"#version 330 core\n",
				
			    "layout (location = 0) in vec3 aPos;\n",
			    
			    "void main()",
			    
			    "{",
			    "    ",
			    "}"
			)), new ArrayList<String>(Arrays.asList(
				"#version 330 core\n",
			    
			    "out vec4 FragColor;\n",
			    
			    "void main()",
			    "{",
			    "    FragColor = vec4(1.0, 0.0, 0.0, 1.0);",
			    "}"
		    )))
		)
	);
	
	public void addSource(ShaderConvention convention) throws IllegalArgumentException {
		if(sources.containsKey(convention))
			throw new IllegalArgumentException("Failure adding new source because the key is already in use.");
		else
			sources.put(convention, null);
	}
	
	public ShaderSourceModel getShaderSourceModel(ShaderConvention convention){
		if(!sources.containsKey(convention)) {
			ShaderSourceModel loadedModel;
			
			switch(convention) {
			case ShaderConvention.FILL:
				loadedModel = new ShaderSourceModel(new ArrayList<String>(
					Arrays.asList(
						"#version 330 core\n",
						
					    "layout (location = 0) in vec3 aPos;\n",
					    
					    "void main()",
					    
					    "{",
					    "    gl_Position = vec4(aPos.xyz, 1.0);",
					    "}"
					)),
					new ArrayList<String>(Arrays.asList(
						"#version 330 core\n",
					    
					    "out vec4 FragColor;\n",
					    
					    "void main()",
					    "{",
					    "    FragColor = vec4(1.0, 0.0, 0.0, 1.0);",
					    "}"
					)
				));
				break;
			default:
				loadedModel = null;
				break;
			}
			
			/*
			//TEST
			for(String line : loadedModel.getVertexMemorySet())
				System.out.println(line);
			for(String line : loadedModel.getFragmentMemorySet())
				System.out.println(line);
				*/
			System.out.println(loadedModel.getVertexMemorySet().getText());
			System.out.println(loadedModel.getFragmentMemorySet().getText());
			
			sources.put(convention, loadedModel);
			return loadedModel;
		}
		
		return sources.get(convention);
	}
}
