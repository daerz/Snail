var vm = new Vue({
	el:"#page",
	data:{
		beginTime:'',
		endTime:'',
		r_window:'',
		r_breakfast:'',
		r_smoken:'',
		rt_num:'',
		message:[],
		lists:[],
		totalpage:1,
		page:1,
		remind:'No money charged in this step',
		showmodal:true
	},
	created:function(){
		this.fromIndex();
		this.comment();
		/*//更改登录窗口
		$("#info-show").hide();
	   	axios.get('/Snail/users/user/'+'ni').then(function(res){
			console.log(res.data)
			if(res.data != null){
				$("#reg-show").hide();
				$("#info-show").show();
				vm.showmodal = false;
				$(".mfp-bg").hide();
				$("#sign-in").hide();
				$("#sign-msg").append(`<li><span style="color:white; font-size:16px; line-height:42px;">欢迎您,`+res.data+' '+`</span></li>`);
			}
		})*/
		
		axios.get('/Snail/roomType/source',{
			params:{
				r_window:this.r_window,
				r_breakfast:this.r_breakfast,
				r_smoken:this.r_smoken,
				beginTime:this.beginTime,
				endTime:this.endTime,
				rt_num:this.rt_num
			}
		})
		.then( response => {
			this.message = response.data;
			if(this.beginTime == null || this.beginTime == "" || this.endTime == null || this.endTime == ""){
				var currentDate = this.getCurrentDate();
				var Tomorrow = this.getTomorrow(1);
				this.beginTime = currentDate;
				this.endTime = Tomorrow;
			}
		})
	},
	methods:{
		upPage:function(){
			if(this.totalpage>1){
				this.totalpage = this.totalpage-1;
				this.comment();
			}
		},
	    nextPage:function(){
	    	if(vm.totalpage<vm.page){
	    		this.totalpage = this.totalpage+1;
	    		this.comment();
	    	}
	    },
		//评论axios
		comment:function(){
			console.log(this.totalpage)
			axios.get('/Snail/users/comment',{
				 params:{
					 page:this.totalpage,
				 }
			 })
			 .then(function(res){
				 vm.lists = res.data.list;
				 vm.page = res.data.page;
				 })
				 .catch(function(res){
					 console.log(res)
				 })
		},
		/*========================== 获取url传参 ==========================*/	
		getUrlParam:function(key){
			var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)return decodeURIComponent(r[2]); return null;
		},
		
		/*========================== get请求 ==========================*/	
		get:function(){
			axios.get('/Snail/roomType/source',{
				params:{
					r_window:this.r_window,
					r_breakfast:this.r_breakfast,
					r_smoken:this.r_smoken,
					beginTime:this.beginTime,
					endTime:this.endTime,
					rt_num:this.rt_num
				}
			})
			.then( response => {
				this.message = response.data;
				if(this.message.length == 0){
					this.remind = "符合条件的房间已订满，请重新选择！";
				}
			})
		},
		
		/*========================== index传参判断 ==========================*/
		fromIndex:function(){
			var countDate = this.getUrlParam("dates");
			var num = this.getUrlParam("qtyInput");
			if(num == "" || num == null || num < 0)
				num = 0;
			if(countDate == null || countDate == ""){
				this.rt_num = num;
				return;
			}
			if(countDate.indexOf(">") != -1){
				countDate = countDate.replace("+","");
				countDate = countDate.replace("+","");
				var dateArr = countDate.split(">");
				this.beginTime = dateArr[0];
				this.endTime = dateArr[1];
			}else{
				this.beginTime = this.getCurrentDate();
				this.endTime = this.getTomorrow(1);
			}
			this.rt_num = num;
		},
		
		/*========================== 时间对比 ==========================*/
		compareWithDates:function(beginTime, endTime) {
			var newTimes = Date.parse(new Date());
		    var beginArr = beginTime.split("-");
		    var begin = new Date(beginArr[0], beginArr[1], beginArr[2]);
		    var beginTimes = begin.getTime();

		    var endArr = endTime.split("-");
		    var end = new Date(endArr[0], endArr[1], endArr[2]);
		    var endTimes = end.getTime();
		    
		    if (beginTimes < newTimes || beginTimes >= endTimes)
		        return false;
		    else
		        return true;
		},
		
		/*========================== 时间对比 ==========================*/
	    getCurrentDate:function() {
	        var date = new Date();
	        var seperator1 = "-";
	        var year = date.getFullYear();
	        var month = date.getMonth() + 1;
	        var strDate = date.getDate();
	        if (month >= 1 && month <= 9) {
	            month = "0" + month;
	        }
	        if (strDate >= 0 && strDate <= 9) {
	            strDate = "0" + strDate;
	        }
	        var currentdate = year + seperator1 + month + seperator1 + strDate;
	        return currentdate;
	    },
		
	    getTomorrow:function(AddDayCount) {
    	    var dd = new Date();
    	    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    	    var y = dd.getFullYear();
    	    var m = dd.getMonth()+1;//获取当前月份的日期
    	    var d = dd.getDate();	
	    	if (d >= 0 && d <= 9) {
	            d = "0" + d;
	        }
    	    return y+'-'+(m<10?'0'+m:m)+'-'+d;
	    },
	    
	    
		/*========================== 条件判断 ==========================*/		
		compareInfo:function(condition){
			if(condition == null || condition == 1 || condition == 2){
				return true;
			}else{
				return false;
			}			
		},
		
		compareNum:function(num){
			if(num == null || num >= 0){
				return true;
			}				
			else
				return false;
		},
		
		
		screen:function(){			
			/*========================== 时间判断 ==========================*/
			var a = $("#date").val();

			if(a == "" || a == null){
				var currentDate = this.getCurrentDate();
				var Tomorrow = this.getTomorrow(1);
				this.beginTime = currentDate;
				this.endTime = Tomorrow;
			}
			if(a.indexOf(">") != -1){
				var dateArr = a.split(">");
				var beginDay = dateArr[0].trim();
				var endDay = dateArr[1].trim();
				if(beginDay.match("(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)")
						&& endDay.match("(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)")){
					var boolean = this.compareWithDates(beginDay,endDay);
					if(boolean){
						this.beginTime = beginDay;
						this.endTime = endDay;
					}else{
						this.remind = '日期格式不合法，请重新选择！';
						return;
					}			
				}else{
					this.remind = '日期格式不合法，请重新选择！';
					return;
				}
			}else{
				this.remind = '日期格式不合法，请重新选择！';
				return;
			}
			
			/*========================== 时间判断 ==========================*/
			
			this.r_window=$("#r_window").val();
			this.r_breakfast=$("#r_breakfast").val();
			this.r_smoken=$("#r_smoken").val();
			this.rt_num=$("#rt_num").val();
			var w_boo = this.compareInfo(this.r_window);
			var b_boo = this.compareInfo(this.r_breakfast);
			var s_boo = this.compareInfo(this.r_smoken);
			if(this.rt_num > 20){
				this.remind = '本店暂不提供超过20人的住宿！';
				return;
			}
			var n_boo = this.compareNum(this.rt_num);
			if(w_boo && b_boo && s_boo && n_boo){
				this.get();
			}else{
				this.remind = '请选择正确格式信息！';
				return;
			}
			this.remind = 'No money charged in this step';		
		},
		
		roomTypeDetail:function(rt_id,r_num,idCount){
			window.open("contacts2.html?rt_id="+rt_id+"&r_num="+r_num+"&idCount="+idCount+"&beginTime="+this.beginTime+"&endTime="+this.endTime+"","_blank");
		},
		
		booking:function(rt_id,r_num,idCount){
			window.open("cart-1.html?rt_id="+rt_id+"&r_num="+r_num+"&idCount="+idCount+"&beginTime="+this.beginTime+"&endTime="+this.endTime+"","_blank");
		}
	}
})
	
