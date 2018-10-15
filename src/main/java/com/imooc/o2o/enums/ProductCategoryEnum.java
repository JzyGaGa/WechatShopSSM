package com.imooc.o2o.enums;

public enum ProductCategoryEnum {
	
	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_SHOP(-1002, "Shop信息为空"), EMPETY_LIST(-1003, "请输入商品目录信息");
	private int state;
	private String stateInfo;
	
	private ProductCategoryEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
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
	
	/**
	 * 依据传入的state返回相应的enum值
	 */
	public static String stateOf(int state) {
		for(ProductCategoryEnum productCategoryEnum:values()) {
			if(productCategoryEnum.getState()==state) {
				return productCategoryEnum.getStateInfo();
			}
		}
		return null;
	}
	
}
