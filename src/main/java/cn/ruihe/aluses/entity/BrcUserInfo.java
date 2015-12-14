package cn.ruihe.aluses.entity;

/**
 * @author YKX
 * 
 * @description
 */
public class BrcUserInfo {
	// "ID": 3, //等级id
	// "bIsDeleted": false,
	// "dEditTime": "2015-09-20T12:18:48",
	// "dInsertTime": "2015-09-20T12:18:48",
	// "dPreferentialPrice": 0,
	// "iBirthDayPresentIntegral": 20,
	// "iClientID": 7031925, //注册用户id
	// "iHolidayPresentIntegral": 5,
	// "iOrder": 3,
	// "iRequireExperience": 2500,
	// "sHeadImg":
	// "http://115.28.48.78:8099/Files/Client/2015/09/22/22090055684.png",
	// //头像地址
	// "sName": "金牌会员" //等级名称

	private int ID;
	private String dEditTime;
	private int iClientID;
	private int iHolidayPresentIntegral;
	private int iOrder;
	private boolean bIsDeleted;
	private String dInsertTime;
	private int dPreferentialPrice;
	private int iRequireExperience;
	private String sName;
	private String sHeadImg;
	private int iBirthDayPresentIntegral;
	private String sNickName;

	public void setSNickName(String sNickName) {
		this.sNickName = sNickName;
	}

	public String getSNickName() {
		return this.sNickName;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDEditTime() {
		return dEditTime;
	}

	public void setDEditTime(String dEditTime) {
		this.dEditTime = dEditTime;
	}

	public int getIClientID() {
		return iClientID;
	}

	public void setIClientID(int iClientID) {
		this.iClientID = iClientID;
	}

	public int getIHolidayPresentIntegral() {
		return iHolidayPresentIntegral;
	}

	public void setIHolidayPresentIntegral(int iHolidayPresentIntegral) {
		this.iHolidayPresentIntegral = iHolidayPresentIntegral;
	}

	public int getIOrder() {
		return iOrder;
	}

	public void setIOrder(int iOrder) {
		this.iOrder = iOrder;
	}

	public boolean isBIsDeleted() {
		return bIsDeleted;
	}

	public void setBIsDeleted(boolean bIsDeleted) {
		this.bIsDeleted = bIsDeleted;
	}

	public String getDInsertTime() {
		return dInsertTime;
	}

	public void setDInsertTime(String dInsertTime) {
		this.dInsertTime = dInsertTime;
	}

	public int getDPreferentialPrice() {
		return dPreferentialPrice;
	}

	public void setDPreferentialPrice(int dPreferentialPrice) {
		this.dPreferentialPrice = dPreferentialPrice;
	}

	public int getIRequireExperience() {
		return iRequireExperience;
	}

	public void setIRequireExperience(int iRequireExperience) {
		this.iRequireExperience = iRequireExperience;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String sName) {
		this.sName = sName;
	}

	public String getSHeadImg() {
		return sHeadImg;
	}

	public void setSHeadImg(String sHeadImg) {
		this.sHeadImg = sHeadImg;
	}

	public int getIBirthDayPresentIntegral() {
		return iBirthDayPresentIntegral;
	}

	public void setIBirthDayPresentIntegral(int iBirthDayPresentIntegral) {
		this.iBirthDayPresentIntegral = iBirthDayPresentIntegral;
	}

}
