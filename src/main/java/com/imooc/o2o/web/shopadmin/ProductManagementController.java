package com.imooc.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {
	
	@Autowired
	private ProductService productService;
	//最大的图片数量
	private static final int IMAGEMAXCOUNT=6;
	
	@RequestMapping(value="/addProduct",method=RequestMethod.POST)
	public Map<String, Object> addProduct(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//验证码验证
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//将json数据转换为java对象
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		//json转换成字符串 获得product的字符串
		String productStr = HttpServletRequestUtil.getString(request,
				"productStr");
		
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile thumbnail = null;
		List<MultipartFile> productImgs = new ArrayList<MultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				thumbnail = (CommonsMultipartFile) multipartRequest
						.getFile("thumbnail");
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					 MultipartFile productImg = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImg != null) {
						productImgs.add(productImg);
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			//获取product
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//插入数据
		if (product != null && thumbnail != null && productImgs.size() > 0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute(
						"currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				ProductExecution pe = productService.addProduct(product,
						(MultipartFile)thumbnail, productImgs);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}
}
