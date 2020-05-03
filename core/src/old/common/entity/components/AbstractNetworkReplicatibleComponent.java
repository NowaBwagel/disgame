package old.common.entity.components;

public abstract class AbstractNetworkReplicatibleComponent extends AbstractExportableJsonComponent {

	private boolean requiresReplication;

	public AbstractNetworkReplicatibleComponent(String tag) {
		super(tag);
		// TODO Auto-generated constructor stub
	}

	public boolean isRequiresReplication() {
		return requiresReplication;
	}

	public void setRequiresReplication(boolean requiresReplication) {
		this.requiresReplication = requiresReplication;
	}

}
