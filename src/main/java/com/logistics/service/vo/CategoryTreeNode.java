package com.logistics.service.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *	权限树
 *
 * @author caibin
 */
public class CategoryTreeNode {

	private Integer catId;
	private String name;
	private Integer parentId;
	private Integer isShow;
	private String imgUrl;
	private List<CategoryTreeNode> children = new ArrayList<CategoryTreeNode>();
	
	
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public List<CategoryTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<CategoryTreeNode> children) {
		this.children = children;
	}
}
