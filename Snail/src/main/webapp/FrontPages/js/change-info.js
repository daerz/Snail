var info = new Vue({
	   el:'#page',
	   data:{
		   msg:'',
		   phone:'',
		   name:'',
		   sex:'',
		   code:'',
		   time:''
	   },
	   created:function(){
		   console.log(222222222222)
		   axios.defaults.withCredentials = true;
		   axios.get('/Snail/users/user/'+'ni').then(function(res){
		   	console.log(res.data)
		   	if(res.data != null){
		   		info.phone = res.data;
		   		big.showmodal = false;
		   		$(".mfp-bg").hide();
		   		$("#sign-in").hide();
		   		$("#sign-msg").append(`<span style="color:#fc5b62; font-size:16px; line-height:42px;">欢迎您,`+res.data+' '+`</span>`);
		   	}
		   }),
		   
		   /*axios.get('http://localhost:8080/Snail/users/user/'+'ni').then(function(res){
		   console.log(res.data)
		   if(res.data != null){
		   	info.phone = res.data;
		   }
		   })*/
		   
		   axios.get('/Snail/users/vip').then(function(res){
		   console.log(res.data)
		   if(res.data != null){
		   	info.name = res.data.v_name;
		   	info.sex = res.data.v_sex;
		   	info.time = res.data.v_birthday;
		   	if(res.data.v_code == 1){
		   		info.code = '普通会员';
		   	}else if(res.data.v_code == 2){
		   		info.code = '白银会员';
		   	}else if(res.data.v_code == 3){
		   		info.code = '黄金会员';
		   	}else if(res.data.v_code ==4){
		   		info.code = '白金会员';
		   	}
		   }
		   })
	   },
	   methods:{
		   changeinfo:function(){
			   axios.post('/Snail/users/vip',
			   Qs.stringify({
				
				v_name: this.name,
				v_sex: this.sex,
				
			})).then(function(res){
				if(res.data == true){
					location.href="user-info.html";
				}else{
					info.msg = "修改失败";
				}
				
			})
		   }
	   },
   }) 