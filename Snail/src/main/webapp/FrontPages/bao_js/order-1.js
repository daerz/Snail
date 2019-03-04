
var vm= new Vue({
	el:"#order-1",
	data:{
	currentPage:"1",
	totalPage:"1",
	content:"",
	orderList:""
	},
	created:function(){
		axios.defaults.withCredentials = true;
		$("#info-show").hide();
	   	axios.get('http://localhost:8080/Snail/users/user/'+'ni').then(function(res){
			console.log(res.data)
			if(res.data != null){
				$("#reg-show").hide();
				$("#info-show").show();
				$(".mfp-bg").hide();
				$("#sign-in").hide();
				$("#sign-msg").append(`<li><span style="color:#fc5b62; font-size:16px; line-height:42px;">欢迎您,`+res.data+' '+`</span></li>`);
			}
		})
	},
	mounted:function(){
		this.findOrder(this.currentPage);
	},
	methods:{
		getPage:function(){
			var page=$("#page").val();
			var pageNum=parseInt(page);
			
			if(pageNum-this.currentPage!=0&&pageNum-this.totalPage<=0&&pageNum>0){
				this.currentPage=pageNum;
				this.findOrder(page);
			}
			
		},
		nextPage:function(){
			if(this.currentPage-this.totalPage<0){
				this.currentPage++;
				this.findOrder(this.currentPage);
				
			}
		},
		lastPage:function(){
			if(this.currentPage>1){
				this.currentPage--;
				this.findOrder(this.currentPage);
				
			}
		},
		
		
		findOrder:function(page){
			
			axios.post('/Snail/order/queryOrderByPage',Qs.stringify({
				page:page,
			})).then(function(response){
				console.log(response);
				if(response.data.orderList!=null){
					vm.orderList=response.data.orderList;

					var totalPage=response.data.totalPage;
					if(totalPage==0){
						totalPage=1;
					}
					vm.totalPage=totalPage;
				}else{
					vm.totalPage="1";
				}
				
			}).catch(function (error) {
	            console.log(error);
	          });
		},
		
		checkOut : function(id) {

			console.log(id);
			axios.post('/Snail/snail/aliPay',Qs.stringify({
				o_id:id,
			})).then(function(response){
				
				if(response.data.paystate==400){
												
					window.location.href="/Snail/FrontPages/404.html?result="+response.data.result;
				}
				if(response.data.paystate==200){
					$("#alipay").append(response.data.result);

					
				}
			}).catch(function(error){
				 console.log(error);
			})
	
		},
		
		backOut:function(id){
			axios.delete('/Snail/order/quitOrderClient',
				{params:{o_id:id}
			}).then(function(response){
				if(response.data.state==1){
					window.location.reload();
				}
				if(response.data.state==0){
					alert(response.data.message);
				}
//				$("#refund").append(response.data.result);

				
			}).catch(function(error){
				 console.log(error);
			})
		},
		
		comment:function(order){
			location.href="comment.html?order="+order+"";
		}
		
}})