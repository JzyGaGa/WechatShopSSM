package com.imooc.o2o.enums;
/**
 * 对每次对ShopDao的结果封装.
 * @author 59842
 *
 */
public enum ShopSateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
	PASS(2,"通过审核"),INNER_ERROR(-1001,"内部错误"), NULL_SHOPID(-1002,"ShopId"),
	NULL_SHOP(-1003,"shop信息为空");
	
	private int state;
	private String stateInfo;
	
	private ShopSateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	/**
	 * 依据传入的state返回相应的enum值
	 */
	public static ShopSateEnum stateOf(int state) {
		
		for(ShopSateEnum stateEnum:values()) {
			if(stateEnum.getState()==state) {
				return stateEnum;
			}
		}
		return null;
	}

	public  String getStateInfo() {
		
		return stateInfo;
	}
	
	public  int getState() {
		
		return state;
	}
}