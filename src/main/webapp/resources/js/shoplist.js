$(function(){
	
	//用的时候加不用的时候不用加
	//	那是一个事件对象可以用for in循环输出里面的属性等一些东西
	//	其中returnValue和cancelBubble用的还是比较多的
	getlist();
	function getlist(e){
		
		$.ajax({
			url:"getshoplist",
			type:"get",
			datatype:"json",
			success:function(data){
				if(data.success){
					handleList(data.shopList);
					handleUser(data.user);
					
				}
			}
			
		});
	}
	
	function handleUser(user){
		$('#user-name').text(user.name);
	}
	
	function handleList(data){
		var html='';
		data.map(function(item,index){
			html+=' <div class="row row-shop"><div class="col-40">'+item.shopName+
			'</div><div class="col-40">'+shopStatus(item.enableStatus)+
			'</div><div class="col-20">'
			+goShop(item.enableStatus,item.shopId)+'</div></div>'
		});
		$('.shop-wrap').html(html);
	}
	
	function shopStatus(data){
		if(data==0){
			return '审核中';
		}else if(data=-1){
			return '店铺非法';
		}else if(data=-1){
			return '审核通过';
		}
	}
	
	function goShop(status,id){
		if(status==1){
			return '<a href="shopmanagement?shopId='+id+'">修改</a>';
		}else{
			return '';
		}
		
	}
	
})