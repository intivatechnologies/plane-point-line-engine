package glsl_shader;

import java.util.ArrayList;
import java.util.Arrays;

import core.TextMemorySet;

/**
 * Serves shader operations by executing valid changes to
 * shader source code.
 */
public class ShaderSourcePackaging extends TextMemorySet {
	private static ShaderSourcePackaging instance;
	
	public static ShaderSourcePackaging getInstance() {
		if(instance == null)
			instance = new ShaderSourcePackaging();
		return instance;
	}
	
	private ShaderSourcePackaging() {
		
	}
}
