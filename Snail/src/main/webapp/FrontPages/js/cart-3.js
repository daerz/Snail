var cart3=new Vue({
	el:"#confirm",
	data:{
		out_trade_no:"***",
		total_amount:"***",
		trade_no:"***",
	
		
	},
	created:function(){
		this.out_trade_no=this.getUrlParam("out_trade_no");
		this.total_amount=this.getUrlParam("total_amount");
		this.trade_no=this.getUrlParam("trade_no");
		
	},
	methods:{
		getUrlParam:function(name){
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			  var r = window.location.search.substr(1).match(reg);
			  if (r != null) return decodeURIComponent(r[2]); return null;
		}
	}

})
