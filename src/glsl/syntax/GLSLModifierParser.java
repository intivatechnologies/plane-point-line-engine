package glsl.syntax;

import glsl.shader.ShaderLayoutOperationException;

public class GLSLModifierParser {
	
	public enum GLSLModifier {
		gmod_layout,
		gmod_attr, gmod_in, gmod_out, gmod_unif,
		gmod_bool, gmod_float, gmod_int, gmod_m4, gmod_v2, gmod_v3, gmod_v4, gmod_void
	}
	
	public static String parse(GLSLModifier modifier) throws ShaderLayoutOperationException {
		String parsed;
		switch(modifier) {
		case gmod_layout:
			throw new ShaderLayoutOperationException();
			
		case gmod_attr:
		default:
			parsed = "attribute";
			break;
		case gmod_in:
			parsed = "in";
			break;
		case gmod_out:
			parsed = "out";
			break;
		case gmod_unif:
			parsed = "uniform";
			break;
			
		case gmod_bool:
			parsed = "bool";
			break;
		case gmod_float:
			parsed = "bool";
			break;
		case gmod_int:
			parsed = "bool";
			break;
		case gmod_m4:
			parsed = "bool";
			break;
		case gmod_v2:
			parsed = "bool";
			break;
		case gmod_v3:
			parsed = "bool";
			break;
		case gmod_v4:
			parsed = "bool";
			break;
		case gmod_void:
			parsed = "bool";
			break;
		}
		
		return parsed;
	}
}
