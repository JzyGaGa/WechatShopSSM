package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.enums.ProductStateEnum;

public class ProductExecution {
	//状态代码
	private int state;
	//状态标识
	private String stateInfo;
	//单个
	private Product product;
	//全部
	private List<Product> productList;
	
	public ProductExecution() {
		
	}
	//失败
	public ProductExecution(ProductStateEnum ps) {
		this.state=ps.getState();
		this.stateInfo=ps.getStateInfo();
	}
	//成功返回单个
	public ProductExecution(ProductStateEnum ps,Product product) {
		this.state=ps.getState();
		this.stateInfo=ps.getStateInfo();
		this.product=product;
	}
	//成功返回多个
	public ProductExecution(ProductStateEnum ps,List<Product> productList) {
		this.state=ps.getState();
		this.stateInfo=ps.getStateInfo();
		this.productList=productList;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
}
