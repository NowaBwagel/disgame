package old.client.rendering.terrain;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class BlockMaterialManager {

	private Map<String, TextureAtlas> atlasMap;
	// Amount of textures not registered to blocks.
	private int unusedTextures = 0;

	public BlockMaterialManager() {
		atlasMap = new HashMap<>();
	}

	public boolean registerBlockAtlas(String atlasName, TextureAtlas atlas) {
		if (atlasMap.containsKey(atlasName)) {
			return false;
		}
		atlasMap.put(atlasName, atlas);
		unusedTextures += atlas.getRegions().size;
		return true;
	}

	public int getUnusedTextures() {
		return unusedTextures;
	}
}
