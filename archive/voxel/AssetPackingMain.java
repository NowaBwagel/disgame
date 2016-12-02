package com.nowabwagel.voxel;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPackingMain {
	public static void main(String[] args) {
		TexturePacker.process("unpacked/", "assets/", "voxel-assets");
	}
}
