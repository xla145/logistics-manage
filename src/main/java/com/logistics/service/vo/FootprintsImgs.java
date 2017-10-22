
package com.logistics.service.vo;

import cn.assist.easydao.annotation.Id;

import cn.assist.easydao.annotation.Temporary;

import cn.assist.easydao.pojo.BasePojo;

/** footprints_imgs
	ID	INT(11)
	MFID	VARCHAR(11)
	URL	VARCHAR(255)
*/
public class FootprintsImgs extends BasePojo {
	@Temporary
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private String mfid;
	private String url;

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setMfid(String mfid){
		this.mfid=mfid;
	}
	public String getMfid(){
		return mfid;
	}
	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}
}

