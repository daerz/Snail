var info = new Vue({
	   el:'.bg_color_1',
	   data:{
		   msg:'',
		   phone:'',
		   order:'',
		   comment:'',
		   score:5
	   },
	   created:function(){
		   
		   console.log(222222222222)
		   axios.defaults.withCredentials = true;
		   
		   this.order = this.getUrlParam("order");
		   axios.get('/Snail/users/user/'+'ni').then(function(res){
		   	console.log(res.data)
		   	if(res.data != null){
		   		info.phone = res.data;
		   		big.showmodal = false;
		   		$(".mfp-bg").hide();
		   		$("#sign-in").hide();
		   		$("#sign-msg").append(`<span style="color:#fc5b62; font-size:16px; line-height:42px;">欢迎您,`+res.data+' '+`</span>`);
		   	}
		   })
	   },
	   methods:{
		   changeinfo:function(){
			   console.log(this.score)
			   axios.post('/Snail/users/comment',
			   Qs.stringify({
				
				c_name: this.phone,
				c_content:this.comment,
				c_score:this.score,
			})).then(function(res){
				if(res.data == true){
					info.msg = "评论成功";
				}else{
					info.msg = "评论失败";
				}
				
			})
		   },
		   getUrlParam:function(order){
				var reg = new RegExp("(^|&)" + order + "=([^&]*)(&|$)");
				var r = window.location.search.substr(1).match(reg);
				if (r != null)return decodeURIComponent(r[2]); return null;
			},
			
	   },
   }) 