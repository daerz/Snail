

var vm = new Vue({
	el : "#car-2",
	data : {
		o_id : "...",
		phone : "...",
		phone2 : "...",
		totalPrice : "0.00",
		intime : "...",
		outtime : "...",
		ordertime : "..",
		num : "0",
		roomType : "..."
		
	},
	created : function() {
		axios.defaults.withCredentials = true;
		
		this.num=	this.getUrlParam("num")				//this.num
		this.o_id = this.getUrlParam("o_id")	
  		this.phone = this.getUrlParam("phone")	
  		this.phone2 = this.getUrlParam("phone2")	
  		this.totalPrice = this.getUrlParam("totalPrice")	
  		this.intime = this.getUrlParam("intime")	
  		this.outtime = this.getUrlParam("outtime")	
  		this.ordertime = this.getUrlParam("ordertime")	
		this.roomType=this.getUrlParam("roomType");
		$("#info-show").hide();
	   	axios.get('/Snail/users/user/'+'ni').then(function(res){
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
		//下订单请求
		/*axios.post('/Snail/order/createOrder',Qs.stringify( {
            
			beginTime : this.getUrlParam("beginTime"),
			endTime : this.getUrlParam("endTime"),
			num : this.getUrlParam("num")	,
			idCount : this.getUrlParam("idCount"),
			phone	:this.getUrlParam("phone")
          }))
          .then(function (response) {



        	 console.log(response);



        	  if(response.data.statecode==1){
               	  var orderBean=response.data.orderBean;
               	  
					vm.o_id = orderBean.o_id;
  					vm.phone = orderBean.o_u_phone;
  					vm.phone2 = orderBean.o_phone;
  					vm.totalPrice = orderBean.o_r_price;
  					vm.intime = orderBean.o_intime;
  					vm.outtime = orderBean.o_outtime;
  					vm.ordertime = orderBean.o_ordertime; 
            		  
        	  }
        	  if(response.data.statecode==0){
        		  console.log("数据错误");
        	  }
        	  
          })*/
          
	},
	methods : {
		
			getUrlParam:function(name){
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
				  var r = window.location.search.substr(1).match(reg);
				  if (r != null) return decodeURIComponent(r[2]); return null;
			},
			//支付请求
			checkout : function() {

				
				axios.post('/Snail/snail/aliPay',Qs.stringify({
					o_id:vm.o_id,
				})).then(function(response){
					
					if(response.data.paystate==400){
													
						window.location.href="404.html?result="+response.data.result;
					}
					if(response.data.paystate==200){
						$("#alipay").append(response.data.result);
						$("#alipay").form().submit();
						
					}
				})
				
	
		}
	
	}

});