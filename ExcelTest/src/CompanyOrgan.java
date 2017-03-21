
public class CompanyOrgan {
	
	private Long organId;
	
	private Long parentOrganId;
	
	private int level;
	
	private boolean isLeaf;
	
	public CompanyOrgan() {
		super();
	}

	public CompanyOrgan(Long organId, Long parentOrganId, int level, boolean isLeaf) {
		super();
		this.organId = organId;
		this.parentOrganId = parentOrganId;
		this.level = level;
		this.isLeaf = isLeaf;
	}

	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	public Long getParentOrganId() {
		return parentOrganId;
	}

	public void setParentOrganId(Long parentOrganId) {
		this.parentOrganId = parentOrganId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
