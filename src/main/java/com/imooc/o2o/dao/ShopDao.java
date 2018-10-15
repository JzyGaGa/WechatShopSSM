package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 
	 * @param shopCondition
	 * @param rowIndex
	 * @param PageSize
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
			@Param("rowIndex")int rowIndex,@Param("PageSize")int PageSize);
	/**
	 * 查询店铺名，店铺状态，店铺类别，区域Id，ower的数量
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	/**
	 * 插入店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店铺信息  
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
	/**
	 * 查询商品id
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
}
