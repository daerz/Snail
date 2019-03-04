var vm=new Vue({
	el:"#page",
	data:{
		beginTime:"...",//开始时间
		endTime:"...",	//结束时间
		num:"1",		//选房数量
		roomType:"",	//房间类型
		idCount:"",		//房间字符串
		rt_id:"",		//房间类型id
		arrNum:"",		//房间数量数组
		imgPath:""	,	//图片路径
		userPhone:"",	//用户电话
		vip:"",			//vip
		discount:""	,	//折扣
		totalMoney:"",	//总金额
		price:""	,	//单价
		beforMoney:"",	//原价
		rt_name:"",		//房型
		rt_note:"",		//房型介绍
		totalDay:"",	//居住天数
		message:"",
		messageStyle:"",
	},
	created:function(){
		axios.defaults.withCredentials = true;
		this.rt_id = this.getUrlParam("rt_id");
		var r_num = this.getUrlParam("r_num");
		this.idCount = this.getUrlParam("idCount");
		this.beginTime = this.getUrlParam("beginTime");
		this.endTime = this.getUrlParam("endTime");
		
		var totalNum=parseInt(r_num);
		var arrNum = new Array(totalNum);
		for (var i = 0; i < arrNum.length; i++) {
			arrNum[i]=i+1;
		}
		this.arrNum=arrNum;
		var sDate1=this.getUrlParam("beginTime");
		var sDate2=this.getUrlParam("endTime");

		this.datedifference(sDate1,sDate2);
		
		//更改登录
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
		//加载房间类型信息
		
		axios.get('/Snail/roomType/source/cart',{
			params:{
				rt_id:this.rt_id,
				idCount:this.idCount
			}
		}).then( response => {
			
			console.log(response);
			return response.data;
			
			
			
		})
		.then((data)=>{
	
			vm.imgPath=data[0].rt_img;
			vm.price=data[1][0].r_price;
			vm.rt_name=data[0].rt_name;
			vm.rt_note=data[0].rt_note;
		})
		.then(()=>{
			//加载会员信息：用户信息，折扣信息
			axios.get('/Snail/userInfo/getUserInfoByShiro').then(function(response){
				console.log(response);
				var data=response.data;
				vm.discount=data.discount;
				vm.vip=data.vip;
				vm.userPhone=data.userPhone;
//				vm.totalMoney=(data.discount * vm.price).toFixed(2);
			})
			.then(()=>{
			
				this.beforMoney=(this.price*vm.totalDay).toFixed(2);
				this.totalMoney=(this.discount * this.price*vm.totalDay).toFixed(2);
			})
			.catch(function (error) {
	            console.log(error);
	        });
			
		})
		.catch( err => {
			console.log(err);
		});
		
		


	},

	mounted:function(){

	},
	
	methods:{
		
		//天数计算
		datedifference:function (sDate1,sDate2) {    //sDate1和sDate2是2006-12-18格式  
	        var dateSpan=null;
	        var  tempDate=null;
	        var  iDays=null;
	        sDate1 = Date.parse(sDate1);
	        sDate2 = Date.parse(sDate2);
	        dateSpan = sDate2 - sDate1;
	        dateSpan = Math.abs(dateSpan);
	        iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
	        this.totalDay=iDays;
	    },
		
		createOrder:function(){
			var message=vm.validatePhone();
			console.log(message);
			if(message==false){
				return;
			}
			//下订单请求
			axios.post('/Snail/order/createOrder',Qs.stringify( {
	            
				beginTime : this.beginTime,
				endTime : this.endTime,
				num : this.num,
				idCount : this.idCount,
				phone	:this.userPhone,
	          }))
	          .then(function (response) {



	        	 console.log(response);



	        	  if(response.data.statecode==1){
	               	  var orderBean=response.data.orderBean;
	               	 
						var o_id = orderBean.o_id;
	  					var phone = orderBean.o_u_phone;
	  					var phone2 = orderBean.o_phone;
	  					var totalPrice = orderBean.o_r_price;
	  					var intime = orderBean.o_intime;
	  					var outtime = orderBean.o_outtime;
	  					var ordertime = orderBean.o_ordertime; 
	  					
	  					 window.location.href="/Snail/FrontPages/cart-2.html?o_id="+o_id+"&phone="+phone+"&phone2="+phone2+"&totalPrice="+totalPrice+"&intime="+intime+"&outtime="+outtime+"&ordertime="+ordertime+"&num="+vm.num+"&roomType="+vm.rt_name;  
	        	  }
	        	  if(response.data.statecode==0){
//	        		  window.location.href="/Snail/FrontPages/404.html"
	        			
	    			
	        		  
	        		 
	        		  vm.message=response.data.message;
	        	  }
	        	  
	          })
			
			
			
			
		},
		
		getUrlParam:function(name){
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			  var r = window.location.search.substr(1).match(reg);
			  if (r != null) return decodeURIComponent(r[2]); return null;
		},
		countMoney:function(){
			
			vm.beforMoney=(vm.num*vm.price*vm.totalDay).toFixed(2);
			vm.totalMoney=(vm.num*vm.discount*vm.price*vm.totalDay).toFixed(2);
			
		},
		
		validatePhone:function(){
			var phone=$("#phone").val();
			
			if(!(/^1[34578]\d{9}$/.test(phone))){
				
				$("#message").html("号码格式有误");
				return false;
			}
			$("#message").html("");
			return true;
		}
	},
	

})



