$(function(){
	var listUrl='getproductcategorylist';
	var addUrl='addproductcategorys';
	var deleteUrl='deleteProductCategory';
	getlist();
	function getlist(){
		$.getJSON(listUrl,function(data){
			if(data.success){
				var dataList=data.data;
				$('.category-wrap').html('');
				var tempHtml='';
				//遍历获得的category列表。
				dataList.map(function(item,index){
					 tempHtml+=''+
					 '<div class="row row-product-category now"><div class="col-33 product-category-name">'
					+item.productCategoryName+'</div><div class="col-33">'+item.priority+
					'</div><div class="col-33"><a href="#" class="button delete" data-id="'
					+item. productCategoryId+'">删除</a></div></div>';
				});
				$('.category-wrap').append(tempHtml);
			}
		});
	};
	$('#new').click(function(){
		var tempHtml=''
			+ '<div class="row row-product-category now">'
			+ '<div class="col-33 product-category-name">'
			+ item.productCategoryName
			+ '</div>'
			+ '<div class="col-33">'
			+ item.priority
			+ '</div>'
			+ '<div class="col-33"><a href="#" class="button delete" data-id="'
			+ item.productCategoryId
			+ '">删除</a></div>'
			+ '</div>';
		$('.category-wrap').append(tempHtml);
	});
	//提交绑定
	$('#submit').click(function(){
		var tempArr=$('.temp');
		var productCategoryList=[];
		tempArr.map(function(item,index){
			var tempObj={};
			tempObj.productCategoryName=$('.category').val();
			tempObj.priority=$('.priority').val();
			if(tempObj.productCategoryName&&tempObj.priority){
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url: addUrl,
			type:'POST',
			data:JSON.stringify(productCategoryList),
			contentType:'application/json',
			success:function(data){
				if(data.success){
					$.toast('提交成功！');
					getlist();
				}else{
					$.toast('提交失败！');
				}
			}
		});
	});
	//删除绑定
	$('.category-wrap').on('click', '.row-product-category.now .delete',
			function(e) {
				var target = e.currentTarget;
				$.confirm('确定么?', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							productCategoryId : target.dataset.id,
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$.toast('删除成功！');
								getlist();
							} else {
								$.toast('删除失败！');
							}
						}
					});
				});
			});

	$('.category-wrap').on('click', '.row-product-category.temp .delete',
			function(e) {
				console.log($(this).parent().parent());
				$(this).parent().parent().remove();

			});
});