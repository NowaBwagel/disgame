package com.nowabwagel.openrpg.world.block;

import com.nowabwagel.openrpg.registry.GameRegistrys;

public final class Block {

	private String blockUri;
	private String displayName = "Untitled Block";

	/* Block Properties */

	// General Properties
	// TODO: if liquid
	private int hardness = 2;
	private boolean physicsEnabled;
	// TODO: Sound

	// Rendering Properties
	private int managerID;
	private String regionName;
	private boolean translucent;
	private boolean doubleSided;
	private boolean shadowCasting = true;

	// Collision
	private boolean solid;
	private boolean targetable = true;
	private boolean climbable;

	// Physics
	private float mass = 10;
	private boolean friction;

	// Inventory Settings
	private boolean directPickup;
	private boolean stackable = true;

	public String getBlockUri() {
		return blockUri;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getHardness() {
		return hardness;
	}

	public boolean isPhysicsEnabled() {
		return physicsEnabled;
	}

	public String getRegionName() {
		return regionName;
	}

	public boolean isTranslucent() {
		return translucent;
	}

	public boolean isDoubleSided() {
		return doubleSided;
	}

	public boolean isShadowCasting() {
		return shadowCasting;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isTargetable() {
		return targetable;
	}

	public boolean isClimbable() {
		return climbable;
	}

	public float getMass() {
		return mass;
	}

	public boolean isFriction() {
		return friction;
	}

	public boolean isDirectPickup() {
		return directPickup;
	}

	public boolean isStackable() {
		return stackable;
	}

	public Block setDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public Block setHardness(int hardness) {
		this.hardness = hardness;
		return this;
	}

	public Block setPhysicsEnabled(boolean physicsEnabled) {
		this.physicsEnabled = physicsEnabled;
		return this;
	}

	public Block setRegionName(String regionName) {
		this.regionName = regionName;
		return this;
	}

	public Block setTranslucent(boolean translucent) {
		this.translucent = translucent;
		return this;
	}

	public Block setDoubleSided(boolean doubleSided) {
		this.doubleSided = doubleSided;
		return this;
	}

	public Block setShadowCasting(boolean shadowCasting) {
		this.shadowCasting = shadowCasting;
		return this;
	}

	public Block setSolid(boolean solid) {
		this.solid = solid;
		return this;
	}

	public Block setTargetable(boolean targetable) {
		this.targetable = targetable;
		return this;
	}

	public Block setClimbable(boolean climbable) {
		this.climbable = climbable;
		return this;
	}

	public Block setMass(float mass) {
		this.mass = mass;
		return this;
	}

	public Block setFriction(boolean friction) {
		this.friction = friction;
		return this;
	}

	public Block setDirectPickup(boolean directPickup) {
		this.directPickup = directPickup;
		return this;
	}

	public Block setStackable(boolean stackable) {
		this.stackable = stackable;
		return this;
	}

	public Block register(String blockUri) {
		this.blockUri = blockUri;
		GameRegistrys.BLOCK_REGISTRY.register(blockUri, this);
		return this;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
}
