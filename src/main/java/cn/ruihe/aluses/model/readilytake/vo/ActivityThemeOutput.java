package cn.ruihe.aluses.model.readilytake.vo;

/**
 * <p>Title: ActivityThemeOutput.java</p>
 * <p>Description: API06 获取活动召集主题</p>
 * @author try
 * @date 2015年7月16日-下午6:15:41
 * @version 1.0
 */
public class ActivityThemeOutput {
	
	/**  **/
    private int id;
    
    /** leancloud标识  **/
    private int parentid;
    
    /** 主题名称 **/
    private String typename;
    
    /** 主题描述 **/
    private String describe;
    
    /** 主题状态  **/
    private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
    

    
}
