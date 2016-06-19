$(document).ready(function(){
	var url = "front/first.html";
	var hash = location.hash;
	if(hash){
		changeHash(hash);
		url = hash.substring(1,hash.length);
	}
	$.ajax({
		url : url,
		type : 'GET',
		success : function(data) {
			$("#div2").html(data);
		}
	});
});
function gotohref(con){
	var chain = $(con)[0].hash;
	changeHash(chain);
}
function _showMessage(op){
	var ti = new Date();
	var secend = ti.getTime();
	var title = op.title;
	var content = op.content;
	var width = op.width || 260;
	var height = op.height || 50;
	var winHeight = window.outerHeight;
	var winWidth = window.innerWidth;
	var div = $("<div></div>");
	div.attr("id",secend);
	div.css({"border":"1px solid","left":(winWidth-width)/2+"px","top":(winHeight-height)/3+"px",
			 "position":"absolute","z-index":"2"
			 });
	var div1 = $("<div></div>");//弹出框标题
	div1.text(title);
	div1.css({"background-color":"#D3D3D3","font-size":"22px","text-align":"center"});
	var div2 = $("<div></div>");//弹出框内容
	div2.text(content);
	div2.css({"overflow":"hidden","word-wrap":"break-word","word-break":"break-all",
		"width":width,"height":height,"background-color":"#FFF0F5"});
	var div3 = $("<div></div>");//弹出框操作
	div3.css({"background-color":"#D3D3D3","text-align":"center"});
	div3.html("<button onclick='_closeTipBox("+secend+")'>确定</button>")
	div.append(div1);
	div.append(div2);
	div.append(div3);
	$("body").append(div);
}
//关闭弹出框
function _closeTipBox(id){
	$("#"+id).remove();
}
//扫描权限
function scan(){
	$.ajax({
		url : "privilege/scan.action",
		type : 'POST',
		contentType : 'application/json',
		data : "",
		dataType : 'json',
		success : function(data) {
		}
	});
}