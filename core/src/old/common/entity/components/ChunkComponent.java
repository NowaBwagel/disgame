package old.common.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ChunkComponent implements Component {

	private boolean dirty;
	private int encoded;

	private int x;
	private int y;

	public ChunkComponent(int x, int y) {
		this.x = x;
		this.y = y;
		this.dirty = true;
		encode();
	}

	/**
	 * Encode the chunk coordinates if its dirty.
	 */
	public void encode() {
		if (dirty) {
			encoded = x + 13;
			encoded = encoded * (y + 17);

			dirty = false;
		}
	}

	public int getEncoded() {
		if (dirty)
			encode();

		return encoded;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
		this.dirty = true;
	}

	public void setX(int x) {
		this.x = x;
		this.dirty = true;
	}

	public void setY(int y) {
		this.y = y;
		this.dirty = true;
	}
}
