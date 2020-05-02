package com.nowabwagel.openrpg;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FloatArray;

public class VectorUtils {

	public static void addAll(Vector3[] vects, float[] texArray, FloatArray array) {
		for (int i = 0; i < vects.length; i++) {
			array.add(vects[0].x, vects[0].y, vects[0].z);
			array.add(texArray[i * 2], texArray[i * 2 + 1]);
		}
	}
}
