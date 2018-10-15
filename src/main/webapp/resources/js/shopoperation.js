/**
 *data 里存的是什么呢？
*/
$(function(){
	var initUrl='getshopinitinfo';
	var registerShopUrl='registershop';
	//获取哪里的shopid
	var shopId=getQueryString('shopId');
	alert(shopId);
	var shopInfoUrl='getshopbyid?shopId='+shopId;
	var editShopUrl='modifyshop';
	//非空true：false;
	var isEdit=shopId?true:false;
	/**
	 *获取信息并放入下拉框
	 */
	if(isEdit){
		//
		getShopInfo(shopId);
	}else{
		getShopInitInfo();
	}
	//调用方法
	function getShopInfo(shopId){
		$.getJSON(shopInfoUrl,function(data){
			if(data.success){
				var shop=data.shop;
				$('#shop-name').val(shop.shopName);
				// 店铺名称不能修改
//				$('#shop-name').attr('disabled', 'disabled');
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				//item=shopCategoryList ++ data-id="'+item.shopCategoryId+'" 
				var shopCategory='<option data-id="'+shop.shopCategory.shopCategoryId+'">'
				+shop.shopCategory.shopCategoryName+'</option>';
				var tempAreaHtml='';
				data.areaList.map(function(item,index){
					//item=areaList
					tempAreaHtml+='<option data-id="'+item.areaId+'">'
					+item.areaName+'</option>';
				});
				//向下拉框里面添加内容
				$('#shop-category').html(shopCategory);
				//设置不能够更改
				$('#shop-category').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				//赋值area 的数据
//				$('#area').attr('data-id',shop.areaId);
				$("#area option[data-id='" + shop.area.areaId + "']")
				.attr("selected", "selected");
			}else{
				$.toast(data.errMsg);
			}
		});
	};
	function getShopInitInfo(){
		$.getJSON(initUrl,function(data){
			if(data.success){
				var tempHtml='';
				var tempAreaHtml='';
				//data 从后台把数据取出来
				data.shopCategoryList.map(function(item,index){
					//item=shopCategoryList ++ data-id="'+item.shopCategoryId+'" 
					tempHtml+='<option data-id="'+item.shopCategoryId+'">'
					+item.shopCategoryName+'</option>';
				});
				
				data.areaList.map(function(item,index){
					//item=areaList
					tempAreaHtml+='<option data-id="'+item.areaId+'">'
					+item.areaName+'</option>';
				});
				//向下拉框里面添加内容
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	};
//坐等submit搞起
	$('#submit').click(function(){
		//register的时候，不需要因为是自增的
		var shop={};
		shop.shopName=$('#shop-name').val();
		shop.shopAddr=$('#shop-addr').val();
		shop.phone=$('#shop-phone').val();
		shop.shopDesc=$('#shop-desc').val();
		shop.shopId=shopId;
		shop.shopCategory={
				/**
				 * data('id'),取得是data data-id 
				 */
				shopCategoryId:$('#shop-category').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		shop.area={
				areaId:$('#area').find('option').not(function(){
					return !this.selected;
				}).data('id')
		};
		//[0] files是dom对象特有的方法，所以要加[]转换为dom对象
		var shopImg=$('#shop-img')[0].files[0];
		var formData=new FormData();
		formData.append('shopImg',shopImg);
		alert(formData.get('shopImg'));
		formData.append('shopStr',JSON.stringify(shop));
		alert(formData.get('shopStr'));
		formData.append('66',1);
		formData.append("77",1);
		var verifyCodeActual=$('#j_captcha').val();
		if(!verifyCodeActual){
			$.toast('请输入验证码!');
			return;
		}
		formData.append('verifyCodeActual',verifyCodeActual);
		var newData=new FormData();
		newData.append("username","张三");
		$.ajax({
			url: isEdit?editShopUrl:registerShopUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			async:true,
			success : function(data) {
				if (data.success) {
					$.toast("提交成功！");
				} else {
					$.toast("提交失败！" + data.errMsg);
				}
			}
		});
	});
});
