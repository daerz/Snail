/*
* @Author: Larry
* @Date:   2016-12-15 17:20:54
* @Last Modified by:   qinsh
* @Last Modified time: 2016-12-24 21:51:08
* +----------------------------------------------------------------------
* | LarryBlogCMS [ LarryCMS网站内容管理系统 ]
* | Copyright (c) 2016-2017 http://www.larrycms.com All rights reserved.
* | Licensed ( http://www.larrycms.com/licenses/ )
* | Author: qinshouwei <313492783@qq.com>
* +----------------------------------------------------------------------
*/


var vm = new Vue({
		el:'#mainsection',
		data:{
			msg:[],
			roomNum:0,
			roomGroup:[],
			peopleNum:'0',
			Reservation:'0',
			Untreated:'0',
			emptyroom:0
		},
		methods:{
	        get:function(){
	            //发送get请求
	            this.$http.get('/Snail/room/group').then(res => {
	            	console.log(res.body);
	            	this.msg=res.body;
	            },() => {
	                console.log('请求失败处理');
	            });
	        },
			group:function(){
				
				var roomGroupName = [];
				var roomInfo = [];
				
				 for (var i=0;i<this.roomGroup.length;i++){
					 roomGroupName.push(this.roomGroup[i].name);
					 roomInfo.push(this.roomGroup[i]);
					 this.roomNum = this.roomNum + parseInt(this.roomGroup[i].value);
				 }
				var myChart = echarts.init(document.getElementById('larry-seo-stats'));
				option = {
				    title : {
				        text: '房型占比',
				        subtext: '房间总数'+this.roomNum,
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: roomGroupName
				    },
				    series : [
				        {
				            name: '访问来源',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:roomInfo,
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
				};
				myChart.setOption(option);
			}
			
		},
		mounted:function(){
			
			 axios.get('/Snail/room/group').then(res => {
	            	vm.roomGroup=res.data;
	            	vm.group();
	            }).catch(function (error) {
	                console.log(error);
	            })
			 
			 axios.get('/Snail/room/empty').then(res => {
	            	vm.emptyroom=res.data.length;
	            }).catch(function (error) {
	                console.log(error);
	            })
			 
			 axios.get('/Snail/order/selectLive',{params:{l_flag:1}}).then(res => {
	           
	            	vm.peopleNum=res.data.list.length;
	            	
	            }).catch(function (error) {
	                console.log(error);
	            })
			 
			 
			 let myDate = new Date();
			 let y=myDate.getFullYear();    //获取完整的年份(4位,1970-????)
			 let m=myDate.getMonth();       //获取当前月份(0-11,0代表1月)
			 let d=myDate.getDate();        //获取当前日(1-31)
			 
			 axios.post('/Snail/order/queryOrder',Qs.stringify({
				 o_intime:y+"-"+m+1+"-"+d,
				 o_flag:0
			 })).then(res => {
	           
	            	vm.Reservation=res.data.length;
	            	
	            }).catch(function (error) {
	                console.log(error);
	            })
			 
			 
			 axios.post('/Snail/order/queryOrder',Qs.stringify({
				 o_intime:y+"-"+m+1+"-"+d,
				 o_state:1,
				 o_flag:0
			 })).then(res => {
	    
	            	vm.Untreated=res.data.length;
	            	
	            }).catch(function (error) {
	                console.log(error);
	            })
			 
			 
			
		}
	})

var goEasy = new GoEasy({
	appkey: 'BC-22d9da6f0d0146f98a7aebf2420c8c69'
	});
layui.use(['layer'],function(){
      window.layer = layui.layer;
    });
var open = function(){
	layer.open({
		content:"有新的订单了"
	})
}
goEasy.subscribe({
	channel:'my_channel',
	onMessage: function(message){
			if(message.content==1){
				vm.Untreated+=1;
				vm.Reservation+=1;
				open()
			}
		}
	});