package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class ControllerTest {
	@RequestMapping("/1")
	public void demo() {
		System.out.println("666");
	}
}
