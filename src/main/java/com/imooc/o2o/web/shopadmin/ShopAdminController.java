package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping(value="/shopadmin",method= {RequestMethod.GET})
public class ShopAdminController {
	
	@RequestMapping(value="/shopoperation")
	public String ShopOperation() {
		return "shop/shopoperation";
	}
	
	@RequestMapping(value="/shoplist")
	public String shoplist() {
		return "shop/shoplist";
	}
	/**
	 * 商店信息管理
	 * @return
	 */
	@RequestMapping(value="/shopmanagement")
	public String shopmanagement() {
		return "shop/shopmanagement";
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/shopcategorylist")
	public String shopcategorylist() {
		return "shop/shopcategorylist";
	}
	
	@RequestMapping(value="/productoperation")
	public String productoperation() {
		return "shop/productoperation";
	}
}
