package glsl.syntax;

import glsl.shader.ShaderLayoutOperationException;

public class GLSLModifierParser {
	
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
			parsed = "float";
			break;
		case gmod_int:
			parsed = "int";
			break;
		case gmod_m4:
			parsed = "m4";
			break;
		case gmod_v2:
			parsed = "v2";
			break;
		case gmod_v3:
			parsed = "v3";
			break;
		case gmod_v4:
			parsed = "v4";
			break;
		case gmod_void:
			parsed = "void";
			break;
		}
		
		return parsed;
	}
	
	public static GLSLModifier parse(String modifier) {
		GLSLModifier parsed;
		switch(modifier) {
		case "layout":
			parsed = GLSLModifier.gmod_layout;
			
		case "attribute":
		default:
			parsed = GLSLModifier.gmod_attr;
			break;
		case "in":
			parsed = GLSLModifier.gmod_in;
			break;
		case "out":
			parsed = GLSLModifier.gmod_out;
			break;
		case "uniform":
			parsed = GLSLModifier.gmod_unif;
			break;
			
		case "bool":
			parsed = GLSLModifier.gmod_bool;
			break;
		case "float":
			parsed = GLSLModifier.gmod_float;
			break;
		case "int":
			parsed = GLSLModifier.gmod_int;
			break;
		case "mat4":
			parsed = GLSLModifier.gmod_m4;
			break;
		case "vec2":
			parsed = GLSLModifier.gmod_v2;
			break;
		case "vec3":
			parsed = GLSLModifier.gmod_v3;
			break;
		case "vec4":
			parsed = GLSLModifier.gmod_v4;
			break;
		case "void":
			parsed = GLSLModifier.gmod_void;
			break;
		}
		
		return parsed;
	}
	
	public enum GLSLModifier {
		gmod_layout,
		gmod_attr, gmod_in, gmod_out, gmod_unif,
		gmod_bool, gmod_float, gmod_int, gmod_m4, gmod_v2, gmod_v3, gmod_v4, gmod_void
	}
}
