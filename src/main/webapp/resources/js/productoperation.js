$(function(){
	
	//从url获取是否含有shopid
	var productId=getQueryString('productId');
	//通过productid获取商品信息的URL
	var infoUrl='getproductbyid?productId='+productId;
	//获取当前商铺的列表的URL
	var categoryUrl='getproductcategorylist';
	//更新商品信息列表的url
	var productPostUrl='modifyProduct';
	//由于商品添加和编辑是同一个页面
	//利用标识符来表明本次是添加还是编辑操作
	var isEdit=false;
	if(productId){
		//若有id则为修改信息
		getInfo(productId);
		isEdit=true;
	}else{
		getCategory();
		productPostUrl='addProduct';
	}
	//获取要编辑的信息，并赋值给表单
	function getinfo(id){
		$.getJSON(infoUrl,function(data){
			
			if(data.success){
				//从返回JSON数据获取product对象信息，并赋值给表单
				var product=data.product;
				$('#product-name').val(product.productName);
				$('#priority').val(product.priority);
				$('#normal-price').val(product.normalPrice);
				$('#promotion-price').val(product.promotionPrice);
				$('#product-desc').val(product.productDesc);
				//生成前端类别选择的以及所有类别
				var optionHtml='';
				var optionArr=data.productCategpryList;
				var optionSelected=product.productCategory.productCategoryId;
				//
				optionArr.map(function(item,index){
					var isSelect=optionSelected===item.productCategoryId?'selected':'';
					optionHtml +='<option data-value="'
						+item.productCategoryId
						+'"'
						+isSelected
						+'>'
						+item.productCategoryName
						+'</option>';
				});
				$('#category').html(optionHtml);
			}
		
			
		});
	}
	//为商品添加操作提供该店铺下的所有类别列表
	function getCategpry(){
		$.getJSON(categoryUrl,function(data){
			if(data.success){
				var productCategoryList=data.data;////有问题
				var optionHtml='';
				productCategoryList.map(function(item,index){
					optionHtml+='<option data-value="'
						+item.productCategoryId+'">'
						+item.productCategoryName+'</option>';
				});
			}
		});
		$('#category').html(optionHtml);
	}
	
	//提交按钮的时间响应
	$('#submit').click(function(){
		//创建商品的json对象，返回到后端
		var product={};
		
		product.productName=$('#product-name').val();
		product.productDesc=$('#product-desc').val();
		product.priority=$('priority').val();
		product.normalPrice=$('#normal-price').val();
		product.promotionPrice=$('#promotion-price').val();
		//获取选定的商品类别
		product.productCategory={
				productCategoryId:$('#category').find('option').not(
						function(){
							return !this.selected;
						}).data('value')
		}
		product.productId=productId;
		//获取缩略图的文件流
		var thumbnail=$('#small-img')[0].files[0];
		//生成表单对象，用于接受参数并传递给后台
		var formDate=new formDate();
		formDate.append('thumbnail',thumbnail);
		//遍历商品详情图控件，获取里面文件流
		$('.detail-img').map(function(index,item){
			//判段该空间是否已选择了文件
			if($('.detail-img')[index].files.length>0){
				//
				formData.append('productImg'+index,$('.detail-img')[index].files[0]);
				
				
			}
		});
		//
		formData.append('productStr',JOSN.stringify(product));
		//获取表单里输入的验证码；
		var verifyCodeActual=$('#j_captcha').val();
		if(!verifyCodeActual){
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual",verifyCodeActual);
		//将数据提交至后台操作
		$.ajax({
			url:productPostUrl,
			type:'POST',
			data:formData,
			contentType:false,
			processData:false,
			cache:false,
			success:function(data){
				if(data.success){
					$.toast('提交成功！');
					$('#captcha_img').click();
				}else{
					$.toast('提交失败！');
					$('#capthcha_img').click();
				}
			}
		})
	});
	
	  
})