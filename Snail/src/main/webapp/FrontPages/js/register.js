$(".modal_confirm").click(function(){
	location.href="index.html?type=register";
})
$(".modal_concel").click(function(){
	$(".modal_box").hide();
})
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
				getCode() {
					this.show = false;
					const TIME_COUNT = 60;
					if (!this.timer) {
						this.count = TIME_COUNT;
						this.show = false;
						//axios
						
						
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
							console.log(res.data);
						}),function(err){
							console.log(err.data);
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
			},
			mounted:function(){
			console.log(2)
			jigsaw.init(document.getElementById('captcha'), function () {
				
				
				axios.post('/Snail/users/user', 
				Qs.stringify({
					
					u_phone: reg.phone,
					u_pass: reg.pass,
					pass_two : reg.pass2,
					code :reg.code,
					
				})).then(function(res) {
					$(".refreshIcon").click();
					if(res.data=="注册成功"){
						
						$(".modal_box").show();
						$(".mo_content").html(res.data);
						$(".modal_confirm").show();
					}else{
						console.log(1)
						$(".modal_box").show();
						$(".mo_content").html(res.data);
						$(".modal_confirm").hide();
					}
				}),function(err){
					$(".refreshIcon").click();
					console.log(err.data);
				}
				
			})
			}
		});

