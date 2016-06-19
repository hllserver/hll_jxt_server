$(document).ready(function(){
	$(".loadImage").on("click", function() {
		debugger;
		var src=$(this)[0].src;
		$(".inputUser").css("background-image","url("+src+")");
		if(src.indexOf("qqinput")>0){
			$("#logType").val("qq");
			$(".inputUser").val("请输入qq账号");
		}
		if(src.indexOf("weChatinput")>0){
			$("#logType").val("weChat");
			$(".inputUser").val("请输入微信帐号");
		}
		if(src.indexOf("emailinput")>0){
			$("#logType").val("email");
			$(".inputUser").val("请输入邮箱帐号");
		}
		if(src.indexOf("loginput")>0){
			$("#logType").val("phone");
			$(".inputUser").val("请输入手机号码");
		}
		if(src.indexOf("bloginput")>0){
			$("#logType").val("blog");
			$(".inputUser").val("请输入微博帐号");
		}
	});
});
$("#inputUser").click(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/)){
		inp.val("");
	}
});
$("#inputUser").blur(function(){
	var inp = $(this);
	var da = inp.val();
	if(da.match(/^.*[\u4e00-\u9fa5]+.*$/) || da==null || da==""){
		var type=$("#logType").val();
		if(type=="qq"){
			inp.val("请输入qq账号");
		}else if(type=="blog"){
			inp.val("请输入微博帐号");
		}else if(type=="weChat"){
			inp.val("请输入微信帐号");
		}else if(type=="email"){
			inp.val("请输入邮箱帐号");
		}else if(type=="phone"){
			inp.val("请输入手机号码");
		}
	}
});
$("#inputpsw").click(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/)){
		inp.val("");
		inp.attr("type","password");
	}
});
$("#inputpsw").blur(function(){
	var inp = $(this);
	if(inp.val()==null || inp.val()=="" || inp.val().match(/.*^[\u4e00-\u9fa5]+.*$/)){
		inp.val("请输入密码");
		inp.attr("type","text");
	}
});
//跳转到注册页面
function regist(){
	$.ajax({
		url : "front/regist.html",
		type : 'GET',
		success : function(data) {
			$("#div2").html(data);
		}
	});
}
//登陆
function log(){
	var acc = $("#inputUser");
	var psw = $("#inputpsw");
	var type = $("#logType");
	var key = true;
	if(acc.val()==null || acc.val()==""){
		key= false;
		_showMessage({title:"提示",content:"账号不能为空"});
		return;
	}
	if(psw.val()==null || psw.val()==""){
		key= false;
		_showMessage({title:"提示",content:"密码不能为空"});
		return;
	}
	$.ajax({
		url : "user/login/"+acc.val()+"/"+psw.val()+"/"+type.val()+".action",
		type : 'POST',
		contentType : 'application/json',
		data : "",
		dataType : 'json',
		success : function(value, type){
			if(value.type=='0'){
				_showMessage({title:"提示",content:"登陆失败"});
			}else if(value.type<5){
				window.location.href=value.url;
			}else if(value.type==5){
				$.ajax({
					url : "front/first.html",
					type : 'GET',
					success : function(data) {
						$("#div2").html(data);
						if(value.nickName){
							$("#ufo").html("<font>欢迎："+value.nickName+"</font><a href='user/logout.action'>退出</a>");
						}else if(value.email){
							$("#ufo").html("<font>欢迎："+value.email+"</font><a href='user/logout.action'>退出</a>");
						}else if(value.tel){
							$("#ufo").html("<font>欢迎："+value.tel+"</font><a href='user/logout.action'>退出</a>");
						}
					}
				});
			}
		}
	});
}


