package com.logistics.service.model;

import com.logistics.service.vo.member.Member;
import com.logistics.service.vo.member.MemberAddr;

import java.util.List;

public class MemberModel extends Member {

	/**
	* @Fields serialVersionUID : TODO()
	*/
	private static final long serialVersionUID = 1L;
	
	private List<MemberAddr> addressList;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<MemberAddr> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<MemberAddr> addressList) {
		this.addressList = addressList;
	}
}
