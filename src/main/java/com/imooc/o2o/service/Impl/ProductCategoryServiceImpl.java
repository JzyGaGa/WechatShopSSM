package com.imooc.o2o.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.enums.ProductCategoryEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Override
	public List<ProductCategory> queryProductCategoryList(long shopId) {
		List<ProductCategory> list = productCategoryDao.queryProductCategoryList(shopId);
		return list;
	}

	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> list)
			throws ProductCategoryOperationException {
		if (list == null || list.size() < 1) {

			return new ProductCategoryExecution(ProductCategoryEnum.EMPETY_LIST);
		} else {
			try {
				Integer resint = productCategoryDao.batchInsertProductCategory(list);
				if (resint <= 0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory" + e.getMessage());
			}
		}
	}
	
	//有两步
	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//TODO 将此商品类别下面置为空
		try {
			int resint = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (resint < 1) {
				throw new ProductCategoryOperationException("店铺类别删除失败");
			} else {
				ProductCategoryEnum productCategoryEnum = ProductCategoryEnum.SUCCESS;
				return new ProductCategoryExecution(productCategoryEnum);
			}
		} catch (Exception e) {

			throw new ProductCategoryOperationException("deleteProductCategoryERROR" + e.getMessage());
		}
	}

		
}
