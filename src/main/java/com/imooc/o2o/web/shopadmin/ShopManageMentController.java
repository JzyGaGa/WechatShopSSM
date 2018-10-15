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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManageMentController {
	
	@Autowired
	private ShopService shopService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ShopCategoryService ShopCategoryService;

	/**
	 * 	店铺列表controller层开发。
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshopmanagementinfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getshopmanagementinfo(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		 if(shopId<0) {
			 Object shopObject = request.getSession().getAttribute("shopId");
			 if(shopObject==null) {
				 //如果没有任何的userid属性，就把他调到商品列表
				 modelMap.put("url", "/o2o/shop/getshoplist");
				 modelMap.put("redirect", true);
			 }else {
				 //如果session里面有shopid
				 Shop shop=(Shop) shopObject;
				 modelMap.put("shopId",shop.getShopId());
				 modelMap.put("redirect", true);
			 }
		 }else {
			 //如果请求链接有shopid
			 Shop currentShop=new Shop();
			 currentShop.setShopId(shopId);
			 request.getSession().setAttribute("shopId", shopId);
			 modelMap.put("redirect", false);
		 }
		return modelMap;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshoplist",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//测试user,模拟登陆
		PersonInfo user = new PersonInfo();
		user.setUserId(9L);
		user.setName("test");
		request.getSession().setAttribute("user", user);
		PersonInfo usernew = (PersonInfo) request.getSession().getAttribute("user");
		Shop shopCondition=new Shop();
		shopCondition.setOwner(usernew);
		try {
			ShopExecution se = shopService.getShopListAndcount(shopCondition, 0, 100);
			modelMap.put("shopList", se.getShoplist());
			modelMap.put("user", user);
			modelMap.put("success", true);
			
		}catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	@RequestMapping(value="/modifyshop",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modifyshop(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//输入验证码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("sucess",false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//1. 接受并转换相应的参数，包括店铺的信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
		ObjectMapper objectMapper = new ObjectMapper();
		Shop shop=null;
		try {
			shop = objectMapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//spring中的处理对象
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
		request.getSession().getServletContext());
		//判断有没有上传文件流
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
			shopImg=(CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		//2.修改店铺信息
		if(shop!=null&&shop.getShopId()!=null) {
			//seession TODO 
			//
			PersonInfo owner= (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			//返回给结果实例
			ShopExecution se=null;
			try {
				se=shopService.modifyShop(shop, shopImg);
				if(se.getState()==ShopSateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
					
					//当修改成功的时候，保存到session中
					@SuppressWarnings("unchecked")
					List<Shop> shopList =(List<Shop>)request.getSession().getAttribute("shopList");
					if(shopList==null||shopList.size()==0) {
						shopList=new ArrayList<>();
					}
					shopList.add(shop);
					request.setAttribute("shopList", shopList);
					
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg",se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.getMessage());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请输入店铺信息");
			return modelMap;
		}
		
	}
	
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopById(HttpServletRequest request){
		//定义返回的map
		Map<String,Object> modelMap=new HashMap<String,Object>();
		long shopId= HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId>-1) {
		//我们只希望用户不能修改类别。
		try {
			List<Area> areaList = areaService.getAreaList();
			Shop shop = shopService.getByShopId(shopId);
			modelMap.put("shop", shop);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			return modelMap;
			}catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","empty shopid");
			return modelMap;
		}
	}
	/**
	 * 获取种类和地区
	 * @return
	 */
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<>();
		List<Area> areaList = new ArrayList<>();
		List<ShopCategory> shopCategoryList = new ArrayList<>();
		try {
			shopCategoryList = ShopCategoryService.queryShopCategory(new ShopCategory());
			areaList = areaService.getAreaList();
			//发送josn头
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	/**
	 * 获取种类和地区的名字
	 * @return
	 */
	@RequestMapping(value="/registershop",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> registerShop(HttpServletRequest request){
		Map<String, Object> modelMap=new HashMap<String, Object>();
		//输入验证码
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("sucess",false);
			modelMap.put("errMsg", "验证码错误");
			return modelMap;
		}
		//1. 接受并转换相应的参数，包括店铺的信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
		ObjectMapper objectMapper = new ObjectMapper();
		Shop shop=null;
		try {
			shop = objectMapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//spring中的处理对象
		CommonsMultipartFile shopImg=null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
		request.getSession().getServletContext());
		//判断有没有上传文件流
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=(MultipartHttpServletRequest) request;
			shopImg=(CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","没有上传图片");
			return modelMap;
		}
		//2.注册店铺
		if(shop!=null&&shopImg!=null) {
			PersonInfo owner= new PersonInfo();
			owner.setUserId(8L);
			shop.setOwner(owner);
			//返回给结果实例
			ShopExecution se=shopService.addShop(shop, shopImg);
			if(se.getState()==ShopSateEnum.CHECK.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
	
	}
}
