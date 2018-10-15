package com.imooc.o2o.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao ShopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, MultipartFile shopImage) {
		// 初值判断
		if (shop == null) {
			return new ShopExecution(ShopSateEnum.NULL_SHOP);
		}
		try {

			// 给店铺信息赋初值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			int effectedNum = ShopDao.insertShop(shop);
			// 添加店铺信息
			if (effectedNum < 1) {
				// 为什么用ShopOperationException，用exception事物不能回滚，而runntimeexception可以。
				throw new ShopOperationException("店铺创建失败");
			} else {

				if (shopImage != null) {
					// 存储图片
					try {

						addShopImg(shop, shopImage);

					} catch (Exception e) {
						throw new ShopOperationException("addPhotoshop error:" + e.getMessage());
					}
					// 更新店铺图片的地址
					effectedNum = ShopDao.updateShop(shop);
					if (effectedNum < 1) {
						throw new ShopOperationException("更新地址失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop ERROR!" + e.getMessage());
		}

		return new ShopExecution(ShopSateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, MultipartFile shopImage) {
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		// 返回生成水印图片地址的同时，更新地址
		String shopImgAddr = ImageUtil.generateThumbnail(shopImage, dest);
		shop.setShopImg(shopImgAddr);

	}

	/**
	 * 返回根据id查找的shop
	 */
	@Override
	public Shop getByShopId(long shopId) {
		Shop shop = ShopDao.queryByShopId(shopId);
		return shop;
	}

	@Override
	@Transactional
	public ShopExecution modifyShop(Shop shop, MultipartFile shopImg) {
		// 判断店铺是否存在
		// shop!=null&&shop.getShopId()!=null
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopSateEnum.NULL_SHOP);
		} else {
			try {
				// 判断是否要处理图片
				if (shopImg != null) {
					Shop tempShop = ShopDao.queryByShopId(shop.getShopId());
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					// 对shop里面图片的url修改
					addShopImg(shop, shopImg);
					// 更新数据库信息
				}
				int updateShop = ShopDao.updateShop(shop);
				if (updateShop < 1)
					return new ShopExecution(ShopSateEnum.INNER_ERROR);
				else {
					return new ShopExecution(ShopSateEnum.SUCCESS, shop);
				}

			} catch (Exception e) {
				throw new ShopOperationException("modify error" + e.getMessage());
			}
		}
	}

	@Override
	public ShopExecution getShopListAndcount(Shop shopCodition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.getRowIndex(pageIndex, pageSize);
		List<Shop>  shopList = ShopDao.queryShopList(shopCodition, rowIndex, pageSize);
		int count = ShopDao.queryShopCount(shopCodition);
		ShopExecution se=new ShopExecution();
		if(shopList==null) {
			se.setState(ShopSateEnum.INNER_ERROR.getState());
		}else {
			se.setShoplist(shopList);
			se.setCount(count);
		}
		return se;
	}

}
