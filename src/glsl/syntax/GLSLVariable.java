package glsl.syntax;

import java.util.ArrayList;
import glsl.shader.ShaderLayoutOperationException;
import glsl.syntax.GLSLModifierParser.GLSLModifier;

public class GLSLVariable {
	
	private String name = "";
	private ArrayList<GLSLModifier> modifiers = new ArrayList<>();
	
	public void setName(String name) { this.name = name; }
	
	/**
	 * Stores gl_mod to the variable.
	 * @param gl_mod
	 */
	public void define_a_modifier(String gl_mod) { modifiers.add(GLSLModifierParser.parse(gl_mod)); }
	
	public String print() {
		StringBuilder sb = new StringBuilder("");
		for(GLSLModifier gm : modifiers) {
			try {
				sb.append(GLSLModifierParser.parse(gm)).append(' ');
			} catch (ShaderLayoutOperationException e) {
				System.err.println("An error was thrown because an unintended modifier (layout) was disclosed in the wrong object.");
				e.printStackTrace();
			}
		}
		
		return sb.append(name).append(";\n").toString();
	}
}
