//=======================登录==============================
 

   var big = new Vue({
	   el:'#big',
	   data:{
		   big2:true,
		   msg:'',
		   u_name: '',
		   u_pass: '',
		   show: true,
		   timer: null,
		   count: '',
		   phone:'',
		   code:'',
		   showmodal:true,
	   },
	   created:function(){
		   
	   	axios.defaults.withCredentials = true;
	   	$("#info-show").hide();
	   	$("#order-show").hide();
	   	axios.get('/Snail/users/user/'+'ni').then(function(res){
			console.log(res.data)
			if(res.data != null){
				$("#reg-show").hide();
				$("#info-show").show();
				$("#order-show").show();
				big.showmodal = false;
				$(".mfp-bg").hide();
				$("#sign-in").hide();
				$("#sign-msg").append(`<li><span style="color:#fc5b62; font-size:16px; line-height:42px;">欢迎您,`+res.data+' '+`</span></li>`);
			}
		})
	   },
	   methods: {
		   getUrlParam: function (name) {
			  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			  var r = window.location.search.substr(1).match(reg);
			  if (r != null) return decodeURIComponent(r[2]); 
			  return null;
			},
		   changeDisplay:function(){
			   big.msg = "";

			   $("#login").hide();
			   $("#reg").show()
			   
		   },
		   changeDisplay2:function(){
			   big.msg = "";
		   	$("#reg").hide();
		   	$("#login").show()
		   	
		   },
	   	getCode:function() {
	   		this.show = false;
	   		const TIME_COUNT = 60;
	   		if (!this.timer) {
	   			this.count = TIME_COUNT;
	   			this.show = false;
	   			//axios
	   			
	   			axios.post('/Snail/users/sms',	
	   			Qs.stringify({
	   					phone:big.phone
	   			})).then(function(res) {
	   				big.msg = res.data
					if(res.data != "验证码获取成功"){
						console.log(2345)
						big.count = 0;
						}
	   				console.log(res.data)
	   			}),function(err){
	   				
	   			}
	   			this.timer = setInterval(() => {
	   				
	   				if (this.count > 0 && this.count <= TIME_COUNT) {
	   					this.count--;
	   					
	   				} else {
	   					this.show = true;
	   					clearInterval(this.timer);
	   					this.timer = null;
	   				}
	   			}, 1000)
	   		}
	   	}
	   },
	   mounted:function(){
	   	console.log(2)
	   	jigsaw.init(document.getElementById('captcha2'), function () {
	   		
	   		axios.post('/Snail/users/user/sms', 
	   		Qs.stringify({
	   			phone: big.phone,
	   			code: big.code,
	   		})).then(function(res) {
				$(".refreshIcon").click();
	   			big.msg = res.data;
				if(res.data == "登录成功"){
					big.showmodal = false;
					$(".mfp-bg").hide();
					$("#sign-in").hide();
					location.reload() ;
				$("#sign-msg").append(`<span style="color:#fc5b62; font-size:16px; line-height:42px;">欢迎您,`+big.phone+' '+`</span>`);
				}
	   			console.log(res.data)
	   		}),function(err){
	   			
	   		}
	   	})
	   	
		console.log(1)
		jigsaw.init(document.getElementById('captcha'), function () {
			
			axios.post('/Snail/users/user/login', 
			Qs.stringify({
				
				u_phone: big.u_name,
				u_pass: big.u_pass,
				
			})).then(function(res) {
				$(".refreshIcon").click();
				big.msg = res.data;
				if(res.data == "登录成功"){
					big.showmodal = false;
					$(".mfp-bg").hide();
					$("#sign-in").hide();
					location.reload() ;
						$("#sign-msg").append(`<span style="color:#fc5b62; font-size:16px; line-height:42px;">欢迎您,`+big.u_name+' '+`</span>`);
				}	
			}),function(err){
				console.log(err.data)
			}
		})
		let type = this.getUrlParam("type");
		if(type=="register"){
			$("#sign-in").click();
		}
		
	   }
   })
   
 

