package old.common.world.terrain.block;

public class Block {

	private String key;
	private int type;

	public Block(String key) {
		this.key = key;
	}
	// Mesh

	public String getKey() {
		return key;
	}

	public int getType() {
		return type;
	}

}
