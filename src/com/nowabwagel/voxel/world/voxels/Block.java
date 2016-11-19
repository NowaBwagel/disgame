package com.nowabwagel.voxel.world.voxels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nowabwagel.voxel.Assets;
import com.nowabwagel.voxel.world.World;

public class Block {

	public enum BlockOpacity {
		Opaque, Transparent
	}

	public static final Block TEST = new Block("test", true);
	public static final Block STONE = new Block("stone", false).setOpacity(BlockOpacity.Opaque);

	private String name;
	private BlockOpacity opacity = BlockOpacity.Opaque;

	private TextureRegion[] sideTextures;

	public Block(String name, boolean uniqueSides) {
		this.name = name;

		TextureRegion notexture = Assets.textures.findRegion("notexture");
		TextureRegion tempRegion = null;

		if (uniqueSides) {
			sideTextures = new TextureRegion[6];
			if ((tempRegion = Assets.textures.findRegion("block_" + name + "_north")) != null)
				sideTextures[0] = tempRegion;
			else {
				Gdx.app.log("blockTextureError", "Error can't find block_" + name + "_north in AssetsManager");
				// TODO: Texture Error Logger
				sideTextures[0] = notexture;
			}
			if ((tempRegion = Assets.textures.findRegion("block_" + name + "_east")) != null)
				sideTextures[1] = tempRegion;
			else {
				Gdx.app.log("blockTextureError", "Error can't find block_" + name + "_east in AssetsManager");
				// TODO: Texture Error Logger
				sideTextures[1] = notexture;
			}
			if ((tempRegion = Assets.textures.findRegion("block_" + name + "_south")) != null)
				sideTextures[2] = tempRegion;
			else {

				Gdx.app.log("blockTextureError", "Error can't find block_" + name + "_south in AssetsManager");
				// TODO: Texture Error Logger
				sideTextures[2] = notexture;
			}
			if ((tempRegion = Assets.textures.findRegion("block_" + name + "_west")) != null)
				sideTextures[3] = tempRegion;
			else {
				Gdx.app.log("blockTextureError", "Error can't find block_" + name + "_west in AssetsManager");
				// TODO: Texture Error Logger
				sideTextures[3] = notexture;
			}
			if ((tempRegion = Assets.textures.findRegion("block_" + name + "_up")) != null)
				sideTextures[4] = tempRegion;
			else {
				Gdx.app.log("blockTextureError", "Error can't find block_" + name + "_up in AssetsManager");
				// TODO: Texture Error Logger
				sideTextures[4] = notexture;
			}
			if ((tempRegion = Assets.textures.findRegion("block_" + name + "_down")) != null)
				sideTextures[5] = tempRegion;
			else {
				Gdx.app.log("blockTextureError", "Error can't find block_" + name + "_down in AssetsManager");
				// TODO: Texture Error Logger
				sideTextures[5] = notexture;
			}

		} else {
			sideTextures = new TextureRegion[1];
			if ((tempRegion = Assets.textures.findRegion("block_" + name)) != null)
				sideTextures[0] = tempRegion;
			else {
				Gdx.app.log("blockTextureError", "Error can't find block_" + name + " in AssetsManager");
				// TODO: Texture Error Logger
				sideTextures[0] = notexture;
			}
		}

		for (TextureRegion region : sideTextures) {
			region.flip(true, false);
		}

	}

	public TextureRegion getFaceTexture(World.Direction side) {
		switch (side) {
		case DOWN:
			return getDown();
		case EAST:
			return getEast();
		case NORTH:
			return getNorth();
		case SOUTH:
			return getSouth();
		case UP:
			return getUp();
		case WEST:
			return getWest();
		default:
			return null;

		}
	}

	public BlockOpacity getOpacity() {
		return opacity;
	}

	public Block setOpacity(BlockOpacity opacity) {
		this.opacity = opacity;
		return this;
	}

	private TextureRegion getNorth() {
		return sideTextures[0];
	}

	private TextureRegion getEast() {
		if (sideTextures.length != 6)
			return getNorth();
		return sideTextures[1];
	}

	private TextureRegion getSouth() {
		if (sideTextures.length != 6)
			return getNorth();
		return sideTextures[2];
	}

	private TextureRegion getWest() {
		if (sideTextures.length != 6)
			return getNorth();
		return sideTextures[3];
	}

	private TextureRegion getUp() {
		if (sideTextures.length != 6)
			return getNorth();
		return sideTextures[4];
	}

	private TextureRegion getDown() {
		if (sideTextures.length != 6)
			return getNorth();
		return sideTextures[5];
	}
}
