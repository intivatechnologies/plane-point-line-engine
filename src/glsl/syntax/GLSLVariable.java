package glsl.syntax;

import java.util.ArrayList;
import glsl.shader.ShaderLayoutOperationException;
import glsl.shader.ShaderNamingOperationException;
import glsl.syntax.GLSLModifierParser.GLSLModifier;

public class GLSLVariable {
	
	private String name = "";
	private ArrayList<GLSLModifier> modifiers = new ArrayList<>();
	
	public void setName(String name) { this.name = name; }
	public void defineModifier(GLSLModifier gl_mod) { modifiers.add(gl_mod); }
	
	public String attemptPrint() throws ShaderLayoutOperationException, ShaderNamingOperationException {
		StringBuilder sb = new StringBuilder("");
		for(GLSLModifier gm : modifiers) {
			String parsed;
			try {
				parsed = GLSLModifierParser.parse(gm);
			} catch(ShaderLayoutOperationException e) {
				throw new ShaderLayoutOperationException(e);
			}
			
			sb.append(parsed).append(' ');
		}
		
		if(name.length() == 0)
			throw new ShaderNamingOperationException();
		else
			return sb.append(name).toString();
	}
}
