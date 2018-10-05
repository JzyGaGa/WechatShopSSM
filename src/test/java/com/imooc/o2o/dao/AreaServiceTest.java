package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;

public class AreaServiceTest extends BaseTest{
	
	@Autowired
	private AreaService areaService;
	
	@Test
	public void testAreaService() {
		List<Area> areaList = areaService.getAreaList();
		assertEquals("东苑", areaList.get(0).getAreaName());
	}
}
