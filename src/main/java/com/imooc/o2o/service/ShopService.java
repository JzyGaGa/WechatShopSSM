 package com.imooc.o2o.service;

import org.springframework.web.multipart.MultipartFile;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应的店铺列表
	 * @param shopCodition
	 * @param pageIndex 网页上面显示的列表数
	 * @param pageSize	网页大小
	 * @return
	 */
	public ShopExecution getShopListAndcount(Shop shopCodition,int pageIndex,int pageSize);
	/**
	 *  插入图片和商店信息，
	 * @param shop
	 * @param shopImage
	 * @return
	 */
	public ShopExecution addShop(Shop shop,MultipartFile shopImage);
	/**
	 * 通过id获取shop
	 * @param shopId
	 * @return
	 */
	public Shop getByShopId(long shopId);
	/**
	 * 修改图片
	 * @param shop
	 * @param shopImg
	 * @return
	 */
	public ShopExecution modifyShop(Shop shop,MultipartFile shopImg);
		
	
}
