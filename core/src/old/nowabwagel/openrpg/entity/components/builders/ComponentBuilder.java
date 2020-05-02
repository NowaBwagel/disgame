package old.nowabwagel.openrpg.entity.components.builders;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.JsonValue;

public abstract class ComponentBuilder<T extends Component> {

	public abstract T build(JsonValue componentdata);
}
