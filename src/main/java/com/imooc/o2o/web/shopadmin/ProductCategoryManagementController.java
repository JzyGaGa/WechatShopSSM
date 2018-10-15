package com.imooc.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryEnum;
import com.imooc.o2o.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	
	@Autowired
	private ProductCategoryService ProductCategoryService;
	//Get还是post
	@RequestMapping(value="deleteProductCategory",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request ){
		Map<String,Object> mapModel=new HashMap<String,Object>();
		if(productCategoryId!=null&&productCategoryId>0) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute("shop");
				ProductCategoryExecution pc= ProductCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pc.getState()==ProductCategoryEnum.SUCCESS.getState()) {
					mapModel.put("success", true);
				}else {
					mapModel.put("success", false);
					mapModel.put("errMsg", pc.getStateInfo());
				}
				
			}catch (RuntimeException e) {
				mapModel.put("success", false);
				mapModel.put("errMsg", e.getMessage() );
				return mapModel;
			}
		}else {
			mapModel.put("success", false);
			mapModel.put("errMsg", "至少选择一个商品");
		}
		return mapModel;
	}
	
	
	@RequestMapping(value="addproductcategorys",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>addProductCategorys(@RequestBody List<ProductCategory> ProductCategoryList,
			HttpServletRequest request){
		Map<String,Object> mapModel =new HashMap<String,Object>();
		if(ProductCategoryList==null||ProductCategoryList.size()<1) {
			mapModel.put("success", false);
			mapModel.put("errMsg", "传入数据为空");
			return mapModel;
		}else {
			//尽量少依赖前台传来的数据
			Shop currentshop= (Shop) request.getSession().getAttribute("shop");
			for(ProductCategory tempShop:ProductCategoryList) {
				tempShop.setShopId(currentshop.getShopId());
			}
			
			try {
				ProductCategoryExecution pe = ProductCategoryService.batchAddProductCategory(ProductCategoryList);
				if(pe.getState()==ProductCategoryEnum.SUCCESS.getState()) {
					mapModel.put("success", true);
				}else {
					mapModel.put("success", false);
					mapModel.put("success", pe.getStateInfo());
				}
				return mapModel;
			}catch (Exception e) {
				mapModel.put("success", false);
				mapModel.put("errMsg", e.getMessage());
				return mapModel;
			}
			
		}
	}
	
	@RequestMapping(value="getproductcategorylist",method=RequestMethod.GET)
	@ResponseBody
	public Result<List<ProductCategory>> ProductCategoryManagement(HttpServletRequest request){
		//tobemoved
		Shop shop=new Shop();
		shop.setShopId(20l);
		request.getSession().setAttribute("shop", shop);
		//无论什么情况，最差都会赋值为null
		List<ProductCategory> list=null;
		Shop currentShop = (Shop) request.getSession().getAttribute("shop");
		ProductCategoryEnum pc = ProductCategoryEnum.INNER_ERROR;
		if(currentShop!=null&&currentShop.getShopId()!=null) {
			try {
				list = ProductCategoryService.queryProductCategoryList(currentShop.getShopId());
					return new Result<List<ProductCategory>>(true,list);
			    }catch (Exception e) {
			    	return new Result<List<ProductCategory>>(false,pc.getStateInfo(),pc.getState());
				}
		}else {
			return new Result<List<ProductCategory>>(false,pc.getStateInfo(),pc.getState());
		}
	}
}
