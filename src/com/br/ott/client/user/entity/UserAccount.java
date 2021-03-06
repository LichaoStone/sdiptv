package com.br.ott.client.user.entity;

import java.io.Serializable;
import java.util.Date;

import com.br.ott.client.common.DistrictCache;
import com.br.ott.client.common.entity.District;
import com.br.ott.common.util.DateUtil;

public class UserAccount implements Serializable{
	/** */
	private static final long serialVersionUID = 4002451156813693523L;
	/**账号ID*/
	private String id;
	/** 用户名 */
	private String trueName;
	private String phone;
	private String email;
	private String pwd;
	private String sex;
	private String birthday;
	private String nickName;
	private String city;
	private String photos;
	/** 注册时间 */
	private Date registerTime;
	private String address;
	/** 邮编 */
	private String postCode;
	/** 充值余额 */
	private float resetBalance;
	/** 虚拟币余额 */
	private float virtualBlance;
	private String status;

	//是否是业务会员(1：是，0：不是)
	private String isMember;

	private String orderColumn;
	//用戶IPTV帐号
	private String userId;
	//儿童锁密码
	private String childPwd;
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCityName() {
		District dic = DistrictCache.getCityByAreaCode(city);
		if (dic != null) {
			return dic.getName();
		}
		return "";
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getRegTime() {
		if (registerTime == null) {
			return "";
		} else {
			return DateUtil.parseDate(registerTime, "yyyy-MM-dd HH:mm:ss");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public float getResetBalance() {
		return resetBalance;
	}

	public void setResetBalance(float resetBalance) {
		this.resetBalance = resetBalance;
	}

	public float getVirtualBlance() {
		return virtualBlance;
	}

	public void setVirtualBlance(float virtualBlance) {
		this.virtualBlance = virtualBlance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChildPwd() {
		return childPwd;
	}

	public void setChildPwd(String childPwd) {
		this.childPwd = childPwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsMember() {
		return isMember;
	}

	public void setIsMember(String isMember) {
		this.isMember = isMember;
	}

}
