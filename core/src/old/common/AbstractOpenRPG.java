package old.common;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.nowabwagel.openrpg.Assets;

public abstract class AbstractOpenRPG extends Game {
	// TODO: add ref to GameRegistry stuff

	public static boolean isClient() {
		if (Gdx.app.getType().equals(ApplicationType.HeadlessDesktop)) {
			return false;
		} else
			return true;
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.instance.getAssetManager().dispose();
	}

}
