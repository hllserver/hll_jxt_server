var registMethod="email";
function getVerifyCode(){
	var account = $(".inputUser").val();
	if(account){
		$.ajax({
			url : "user/verifyCode/"+account+"/"+registMethod+".action",
			type : 'POST',
			contentType : 'application/json',
			data : "",
			dataType : 'json',
			success : function(value, type){
				alert(value.result);
			}
		});
	}else{
		_showMessage({
			title:"操作失败",
			content:"账号不能为空"
		});
		return;
	}
}
function toRegist(){
	var account = $("#account").val();
	var psw1 = $("#psw1").val();
	var psw = $("#psw2").val();
	var code = $("#code").val();
	if(isNull(account) || isNull(psw) || isNull(code) || isNull(psw1)){
		_showMessage({
			title:"提示",
			content:"请将信息填写完整"
		});
		return;
	}
	if(psw1 != psw){
		_showMessage({
			title:"提示",
			content:"两次输入的密码不一致"
		});
		return;
	}
	if(account.match(/^.*[\u4e00-\u9fa5]+.*$/)){
		_showMessage({
			title:"提示",
			content:"账号填写有误"
		});
		return;
	}
	if(psw1.match(/^.*[\u4e00-\u9fa5]+.*$/)){
		_showMessage({
			title:"提示",
			content:"密码填写有误由字母数字组成，6-15位"
		});
		return;
	}
	if(code.match(/^.*[\u4e00-\u9fa5]+.*$/)){
		_showMessage({
			title:"提示",
			content:"验证码填写有误"
		});
		return;
	}
	$.ajax({
		url : "user/registAccount/"+account+"/"+psw+"/"+code+"/"+registMethod+".action",
		type : 'POST',
		contentType : 'application/json',
		data : "",
		dataType : 'json',
		success : function(value, type){
			alert(value.result);
		}
	});
}
$("#mailreg").click(function(){
	$("#regtype").val("email");
	$("#account").val("请输入邮箱号码");
	$("#psw1").val("请输入密码");
	$("#psw1").attr("type","text");
	$("#psw2").attr("type","text");
	$("#psw2").val("请重新输入密码");
	$("#code").val("请输入邮箱验证码");
});
$("#phonereg").click(function(){
	$("#regtype").val("phone");
	$("#account").val("请输入电话号码");
	$("#psw1").val("请输入密码");
	$("#psw1").attr("type","text");
	$("#psw2").attr("type","text");
	$("#psw2").val("请重新输入密码");
	$("#code").val("请输入电话验证码");
});
$("#account").click(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/)){
		inp.val("");
	}
});
$("#account").blur(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/) || inp.val()==null || inp.val()==""){
		var k = $("#regtype").val();
		if(k=="email"){
			inp.val("请输入邮箱号码");
		}else if(k=="phone"){
			inp.val("请输入电话号码");
		}
	}
});
$("#psw1,#psw2").click(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/)){
		inp.val("");
		inp.attr("type","password");
	}
});
$("#psw1").blur(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/) || inp.val()==null || inp.val()==""){
		inp.val("请输入密码");
		inp.attr("type","text");
	}
});
$("#psw2").blur(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/) || inp.val()==null || inp.val()==""){
		inp.val("请重新输入密码");
		inp.attr("type","text");
	}
});
$("#code").click(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/)){
		inp.val("");
	}
});
$("#code").blur(function(){
	var inp = $(this);
	if(inp.val().match(/^.*[\u4e00-\u9fa5]+.*$/) || inp.val()==null || inp.val()==""){
		var k = $("#regtype").val();
		if(k=="email"){
			inp.val("请输入邮箱验证码");
		}else if(k=="phone"){
			inp.val("请输入电话验证码");
		}
	}
});
function isNull(s){
	if(s==null || s=="" || s==undefined){
		return true;
	}else{
		return false;
	}
}