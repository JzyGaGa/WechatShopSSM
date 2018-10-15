package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.ProductImg;

public interface ProductImageDao {
	/**
	 * 批量插入图片列表
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
}
