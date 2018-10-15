package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryEnum;

public class  ProductCategoryExecution{
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//返回的结果
	private List<ProductCategory> ProductCategoryList;
	
	//失败的时候返回的构造器
	public ProductCategoryExecution(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	public ProductCategoryExecution(ProductCategoryEnum pc) {
		this.state=pc.getState();
		this.stateInfo=pc.getStateInfo();
	}
	//成功的时候返回的构造器
	public ProductCategoryExecution(List<ProductCategory> list,int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
		this.ProductCategoryList=list;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public List<ProductCategory> getProductCategoryList() {
		return ProductCategoryList;
	}
	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		ProductCategoryList = productCategoryList;
	}
	
}
