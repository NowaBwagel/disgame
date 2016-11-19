package com.nowabwagel.voxel.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nowabwagel.voxel.Assets;

public class BlockOLD {
	public static final BlockOLD STONE = new BlockOLD("stone", Assets.textures, false);

	public static enum Sides {
		FRONT, BACK, BOTTOM, TOP, LEFT, RIGHT
	}

	private String name;
	private boolean uniqueSides;
	private TextureRegion[] sides;

	public BlockOLD(String name, TextureAtlas atlas, boolean uniqueSides) {
		this.name = name;
		this.uniqueSides = uniqueSides;

		if (uniqueSides) {
			sides = new TextureRegion[6];
			sides[0] = atlas.findRegion("block_" + name + "_front");
			sides[1] = atlas.findRegion("block_" + name + "_back");
			sides[2] = atlas.findRegion("block_" + name + "_bottom");
			sides[3] = atlas.findRegion("block_" + name + "_top");
			sides[4] = atlas.findRegion("block_" + name + "_left");
			sides[5] = atlas.findRegion("block_" + name + "_right");
		} else {
			sides = new TextureRegion[1];
			sides[0] = atlas.findRegion("block_" + name);
		}

		for (int i = 0; i < sides.length; i++) {
			if (sides[i] == null)
				sides[i] = atlas.findRegion("notexture");
		}
	}

	public TextureRegion getFront() {
		return sides[0];
	}

	public TextureRegion getBack() {
		if (!uniqueSides)
			return getFront();
		return sides[1];
	}

	public TextureRegion getBottom() {
		if (!uniqueSides)
			return getFront();
		return sides[2];
	}

	public TextureRegion getTop() {
		if (!uniqueSides)
			return getFront();
		return sides[3];
	}

	public TextureRegion getLeft() {
		if (!uniqueSides)
			return getFront();
		return sides[4];
	}

	public TextureRegion getRight() {
		if (!uniqueSides)
			return getFront();
		return sides[5];
	}

	public String getName() {
		return name;
	}
}
