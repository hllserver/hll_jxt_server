$(document).ready(function(){
	$("#account").hide();
	var acc = $("#account").val();
	//查询驾校具体信息
	$.ajax({
		url : "getRecommendSchoolInfo/"+acc+".action",
		type : 'POST',
		contentType : 'application/json',
		data : "",
		dataType : 'json',
		success : function(value, type){
			$("#scname").text(value.schoolName);
			$("#tel").text(value.tel);
			$("#qq").text(value.qq);
			$("#wechat").text(value.wechat);
			$("#possion").text(value.possion);
			$("#policy").text(value.policy);
			$("#intruduce").text(value.intruduce);
		}
	});
	//查询驾校相关的图片
	$.ajax({
		url : "getRecommendSchoolPic/"+acc+".action",
		type : 'POST',
		contentType : 'application/json',
		data : "",
		dataType : 'json',
		success : function(value){
			//驾校风采 图片
			var spirit = value.spirit;
			for(var i=0; i<spirit.length; i++){
				if(spirit[i].pic){
					if(i==0){
						$("#pich").attr("src","../file/pic/"+spirit[0].pic);
					};
					var img = $("<img class='honpic' width='80' height='80' src='../file/pic/"+spirit[i].pic+"'/>");
					$("#picht").append(img);
				}
			}
			$(".honpic").click(function(){
				var src=$(this).attr('src');
				$("#pich").attr("src",src);
			});
			//资质荣誉照片
			var honor = value.honor;
			for(var i=0; i<honor.length; i++){
				if(honor[i].pic){
					var img = $("<img class='honpic' width='120' height='120' src='../file/pic/"+honor[i].pic+"'/>");
					$("#honorp").append(img);
				}
			}
		}
	});
});