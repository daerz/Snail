var vm = new Vue({
	el:"#page",
	data:{
		beginTime:'',
		endTime:'',
		r_window:'',
		r_breakfast:'',
		r_smoken:'',
		rt_num:'',
		message:[]
	},
	created:function(){
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
			
		})
	},
	methods:{
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
				
			})
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

			if(a == ""){
				var currentDate = this.getCurrentDate();
				var Tomorrow = this.getTomorrow(1);
				this.beginTime = currentDate;
				this.endTime = Tomorrow;
			}
			
			var d_boo = a.match("[0-9]{2}-[0-9]{2}-[0-9]{2}\\s>\\s[0-9]{2}-[0-9]{2}-[0-9]{2}");
			if(!d_boo && a != ""){
				alert("日期格式不合法，请重新选择！");
				return;
			}
			if(a != null && d_boo){
				var dateArr = $("#date").val().split(">");
				var begin = dateArr[0].trim().split("-");
				begin.splice(2,1,"20"+begin[2]);
				begin.unshift(begin[2]);
				begin.pop();
				var beginDay = begin.join("-")
				var end = dateArr[1].trim().split("-");
				end.splice(2,1,"20"+end[2]);
				end.unshift(end[2]);
				end.pop();
				var endDay = end.join("-")
				if(beginDay.match("(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)")
						&& endDay.match("(?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)")){
					var boolean = this.compareWithDates(beginDay,endDay);
					if(boolean){
						this.beginTime = beginDay;
						this.endTime = endDay;
					}else{
						alert("日期格式不合法，请重新选择！");
						return;
					}			
				}else{
					alert("日期格式不合法，请重新选择！");
					return;
				}
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
				alert("本店暂不提供超过20人的住宿！");
				return;
			}
			var n_boo = this.compareNum(this.rt_num);
			if(w_boo && b_boo && s_boo && n_boo){
				this.get();
			}else{
				alert("请选择正确格式信息！");
				return;
			}
					
		},
		
		roomTypeDetail:function(rt_id,r_num,idCount){
			location.href="contacts2.html?rt_id="+rt_id+"&r_num="+r_num+"&idCount="+idCount+"&beginTime="+this.beginTime+"&endTime="+this.endTime+"";
		},
		
		booking:function(rt_id,r_num,idCount){
			location.href="cart.html?rt_id="+rt_id+"&r_num="+r_num+"&idCount="+idCount+"&beginTime="+this.beginTime+"&endTime="+this.endTime+"";
		}
	}
})
	
