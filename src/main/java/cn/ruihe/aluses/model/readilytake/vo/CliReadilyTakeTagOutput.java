package cn.ruihe.aluses.model.readilytake.vo;

/**
 * <p>Title: CliReadilyTakeCommentByMate.java</p>
 * <p>Description: API01 获取随手拍标签 </p>
 * @author try
 * @date 2015年7月16日-上午11:34:49
 * @version 1.0
 */
public class CliReadilyTakeTagOutput {
	
	/** 标识 **/
    private int id;
    
    /** 上一级标识 **/
    private int parentid;
    
    /** 描述 **/
    private String describe;
    
     /** 状态 **/
    private int status;
    
    /** 颜色  **/
    private String color;
    
    /** typename  **/
    private String typename;
    
    
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

    
}
