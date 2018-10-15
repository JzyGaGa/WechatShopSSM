package com.imooc.o2o.util;

public class PageCalculator {
	
	public  static int  getRowIndex(int pageIndex, int pageSize) {
		//就是从页面的代码中，页码数转换为即将在传入数据库的列数
		return pageIndex>1?(pageIndex-1)*pageSize:0;
	}
}
