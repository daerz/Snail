/* 用户信息js */

   var info = new Vue({
	   el:'#login',
	   data:{
		   msg:'',
		   phone:'',
		   name:'',
		   sex:'',
		   code:'',
		   time:''
	   },
	   created:function(){
		   axios.defaults.withCredentials = true;
		   axios.get('/Snail/users/user/'+'ni').then(function(res){
		   	
		   	if(res.data != null){
		   		info.phone = res.data;
		   		console.log(res.data)
		   	}
		   })
		   
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
		   chanagepass:function(oid){
        		location.href="change-pass.html?phone="+this.phone+"";
	   },
	   }
   })
   
   
   