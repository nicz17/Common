package common.view.dnd;

/**
 * Command to execute when a Drag-n-Drop action is finished.
 * 
 * @author nicz
 *
 */
public abstract class DropCommand {

	private String sourceId;
	private int targetId;
	private String targetName;
	
	/**
	 * The code to execute when dropping on the target.
	 */
	public abstract void execute();
	
	/**
	 * Gets the identifier of the item that was dragged
	 * 
	 * @return the id of the item that was dragged
	 */
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	/**
	 * Gets the index of the item that was dropped on
	 * 
	 * @return the index of the item that was dropped on
	 */
	public int getTargetId() {
		return targetId;
	}
	
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
}
