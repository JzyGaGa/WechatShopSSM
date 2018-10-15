package com.imooc.o2o.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImageDao;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;

import ch.qos.logback.core.util.FileUtil;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productdao;
	@Autowired
	private ProductImageDao productImageDao;

	@Override
	@Transactional
	public ProductExecution addProduct(Product product, MultipartFile thumbnail,
			List<MultipartFile> productImgs) throws RuntimeException {
			if(product!=null&&product.getShop()!=null&&
					product.getShop().getShopId()!=null) {
				product.setCreateTime(new Date());
				product.setLastEditTime(new Date());
				//// 0下架1.在前端系统展示,默认为商品上架的状态
				product.setEnableStatus(1);
				//如果商品的缩略图不为空则添加
				if(thumbnail!=null) {
					//添加商品的主界面
					addThumbnail(product,thumbnail);
					
				}
				try {
					int effectedNum=productdao.insertProduct(product);
					if(effectedNum<1) {
						throw new RuntimeException("创建失败！");
					}
				}catch (Exception e) {
					throw new RuntimeException("创建失败！" +e.getMessage());
				}
				//插入详细图片
				if(productImgs!=null&&productImgs.size()>0) {
					addProductImgs(product, productImgs);
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);
			}else {
				return new ProductExecution(ProductStateEnum.EMPTY);
			}
					
	}
	//增加商品详情图
	private void addProductImgs(Product product, List<MultipartFile> productImgs) {
		//dest上传图片的相对路径
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		//相对路径 "/upload/item/shop/"+shopId+"/";
		List<String> imgAddrList = ImageUtil.generateNormalImgs(productImgs, dest);
		//imgAddrList是否为空					
		if (imgAddrList != null && imgAddrList.size() > 0) {
			List<ProductImg> productImgList = new ArrayList<ProductImg>();
			for (String imgAddr : imgAddrList) {
				ProductImg productImg = new ProductImg();
				productImg.setImgAddr(imgAddr);
				productImg.setProductId(product.getProductId());
				productImg.setCreateTime(new Date());
				//将产品的信息赋值给图片
				productImgList.add(productImg);
			}
			try {
				int effectedNum = productImageDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new RuntimeException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("创建商品详情图片失败:" + e.toString());
			}
		}
		
		
	}
	//增加商品的缩略图
	private void addThumbnail(Product product, MultipartFile thumbnail) {
		//获取目标的主地址
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		//将形成的图片放入dest地址中，赋值给product
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}
	
	

}
