$(document).ready(function(){
	_post({
		url:"driverSchoolMsg/getDriverSchoolMsg.action",
		data:"",
		success:function(da){
			for(key in da){
				$("input[name="+key+"]").val(da[key]);
				$("textarea[name="+key+"]").val(da[key]);
			}
		}
	});
});
function sumb(){
	//驾校名校验
	var schoolName=$("#schoolName").val();
	if(schoolName==null || schoolName=="" || schoolName.length>50){
		_showMessage({title:"提示",content:"驾校名小于50个字符,必填!"});
		return;
	}
	//驾校电话
	var tel=$("#tel").val();
	var reg=/^\s*[0-9]{1,12}\s*$/
	if(!reg.test(tel)){
		_showMessage({title:"提示",content:"驾校电话不大于12个数字,必填!"});
		return;
	}
	//邮箱
	var email=$("#email").val();
	if(email.length>50){
		_showMessage({title:"提示",content:"邮箱不能大于50个字符!"});
		return;
	}
	//微信
	var wechat=$("#wechat").val();
	if(wechat.length>50){
		_showMessage({title:"提示",content:"微信号不能大于50个字符!"});
		return;
	}
	//qq
	var qq=$("#qq").val();
	var reg=/^\s*[0-9]{0,50}\s*$/
	if(!reg.test(qq)){
		_showMessage({title:"提示",content:"QQ号不能大于50个数字!"});
		return;
	}
	//驾校人数
	var empno=$.trim($("#empno").val());
	if(empno!=null && empno!=""){
		try{
			var nu=Number(empno);
			if(nu<0 || nu>999999){
				_showMessage({title:"提示",content:"人数必须大于0且小于999999的整数!"});
				return;
			}
		}catch(e){
			_showMessage({title:"提示",content:"人数必须大于0且小于999999的整数!"});
			return;
		}
	}
	//场地面积
	var scale=$.trim($("#scale").val());
	if(scale!=null && scale!=""){
		try{
			var nu=Number(scale);
			if(nu<0 || nu>999999999){
				_showMessage({title:"提示",content:"场地面积必须大于0且小于999999999!"});
				return;
			}
		}catch(e){
			_showMessage({title:"提示",content:"场地面积必须大于0且小于999999999!"});
			return;
		}
	}
	//训练车数
	var carno=$.trim($("#carno").val());
	if(carno!=null && carno!=""){
		try{
			var nu=Number(carno);
			if(nu<0 || nu>999999){
				_showMessage({title:"提示",content:"训练车数必须大于0且小于999999!"});
				return;
			}
		}catch(e){
			_showMessage({title:"提示",content:"训练车数必须大于0且小于999999!"});
			return;
		}
	}
	//各种练车的价格
	var price=$("input[id='Price']");
	for(var i=0; i<price.size(); i++){
		var pricei=$.trim(prive[i]);
		if(pricei!=null && pricei!=""){
			try{
				var nu=Number(pricei);
				if(nu<0 || nu>9999999.99){
					_showMessage({title:"提示",content:"练车价格必须大于0且小于9999999.99!"});
					return;
				}
			}catch(e){
				_showMessage({title:"提示",content:"练车价格必须大于0且小于9999999.99!"});
				return;
			}
		}
	}
	//驾校地址
	debugger;
	var kk = $("#possion").val();
	var possion=$.trim($("#possion").val());
	if(possion==null || possion=="" || possion.length>100){
		_showMessage({title:"提示",content:"驾校地址不大于100个字符，必填!"});
		return;
	}
	//优惠政策
	var policy=$("#policy").val();
	if(policy!=null && policy!=""){
		policy=$.trim(policy);
		if(policy.length>400){
			_showMessage({title:"提示",content:"优惠政策不大于400个字符!"});
			return;
		}
	}
	//驾校简介
	var intruduce=$("#intruduce").val();
	if(intruduce==null ||intruduce==""){
		_showMessage({title:"提示",content:"驾校简介必填，且不大于700个字符!"});
		return;
	}else{
		intruduce=$.trim(intruduce);
		if(intruduce.length>700){
			_showMessage({title:"提示",content:"驾校简介必填，且不大于700个字符!"});
			return;
		}
	}
	var kk = $("#form11").serializeObject();
	_post({
		url:"driverSchoolMsg/modify.action",
		data:kk,
		success:function(da){
			var res="";
			if(da.da=='0'){res="操作失败";}
			if(da.da=='1'){res="操作成功";}
			_showMessage({title:'提示',content:res});
		}
	});
}
function fn(t){
	var div1 = $(t).parent().next();
	var k = div1.is(":hidden");
	if(k==false){div1.hide();}else{div1.show();}
}
function callback(d){
	if(d=='0'){
		_showMessage({title:"提示",content:"操作失败!"});
	}else{
		_showMessage({title:"提示",content:"操作成功!"});
	}
}