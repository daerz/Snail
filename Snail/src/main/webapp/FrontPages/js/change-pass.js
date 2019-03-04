var reg = new Vue({
			el: '#register', //注册div的class	
			data: {
				show: true,
				timer: null,
				count: '',
				phone:'',
				pass : '',
				pass2 : '',
				code: '',
				msg : '',
				
			},
			
			methods: {
				getUrlParam:function(name){
        		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        		 var r = window.location.search.substr(1).match(reg);
        		 if (r != null) return decodeURIComponent(r[2]); return null;
        	},
				getCode() {
					this.show = false;
					const TIME_COUNT = 60;
					if (!this.timer) {
						this.count = TIME_COUNT;
						this.show = false;
						//axios
						
						console.log(reg.phone)
						
						axios.post('/Snail/users/sms',	
						Qs.stringify({
								 phone:reg.phone
						})).then(function(res) {
							reg.msg = res.data
							$(".refreshIcon").click();
							if(res.data != "验证码获取成功"){
								console.log(2345)
								reg.count = 0;
								}
							console.log(res.data)
						}),function(err){
							console.log(err.data)
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
			created:function(){
				axios.defaults.withCredentials = true;
				this.phone = this.getUrlParam("phone");
				console.log(this.phone)
			},
			mounted:function(){
			console.log(3)
			jigsaw.init(document.getElementById('captcha'), function () {
				axios.put('/Snail/users/user', 
				Qs.stringify({
					
					u_phone: reg.phone,
					u_pass: reg.pass,
					pass_two : reg.pass2,
					code :reg.code,
					
				})).then(function(res) {
					reg.msg = res.data
					if(res.data == '修改成功'){
						
					}
					console.log(res.data)
				}),function(err){
					console.log(err.data)
				}
				
			})
			}
		});