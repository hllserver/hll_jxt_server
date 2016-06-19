$(document).ready(function(){
	$("#newPlace").bind("click",function(){
		$("#placeMsg").show();
		$("input[type='text']").val("");
		$("img").attr('src','#');
	});
	findData();
});
function findData(){
	$("#placeMsg").hide();
	//保存所有的场地数据 
	var allPlace;
	//查询用户权限页面 和  基本信息
	_get({
		url:"driverSchoolPlace/getSchoolPlace.action",
		data:"",
		success:function(da){
			//获取场地列表
			var places = da.places;
			allPlace=places;
			//场地名导航栏
			for(var i=0; i<places.length; i++){
				var placei=$("<button>"+places[i].placeName+"</button>");
				placei.addClass("_btn");
				$("#allPlace").append(placei);
				//为placei绑定事件
				(function(pla){
					placei.bind("click",function(){
						showPlace(pla);
					});
				})(places[i])
			}
			var item=da.item;
			$("#_item").html(item);

		}
	});
}
//显示场景
function showPlace(data){
	$("#placeMsg").show();
	$("#pname").val(data.placeName);
	$("#pposition").val(data.position);
	$("#parea").val(data.area);
	$("#pid").val(data.id);
	$("#pscale").val(data.carNo);
	$("#premark").val(data.remark);
	$("img[id*='img']").attr("src","");
	if(data.pic1!=null && data.pic1!=""){
		$("#img1").attr({"src":"file/pic/"+data.pic1});
	}
	$("#img1").attr({"width":"160px","height":"160px"});
	if(data.pic2!=null && data.pic2!=""){
		$("#img2").attr({"src":"file/pic/"+data.pic2});
	}
	$("#img2").attr({"width":"160px","height":"160px"});
	if(data.pic3!=null && data.pic3!=""){
		$("#img3").attr({"src":"file/pic/"+data.pic3});
	}
	$("#img3").attr({"width":"160px","height":"160px"});
	if(data.pic4!=null && data.pic4!=""){
		$("#img4").attr({"src":"file/pic/"+data.pic4});
	}
	$("#img4").attr({"width":"160px","height":"160px"});
	if(data.pic5!=null && data.pic5!=""){
		$("#img5").attr({"src":"file/pic/"+data.pic5});
	}
	$("#img5").attr({"width":"160px","height":"160px"});
}

function submitCheck(){
	debugger;
	var pname=$.trim($("#pname").val());
	if(pname.length==0 || pname.length>50){
		_showMessage({title:"提示",content:"场地名称不能 为空，且必须小于50个字符"});
		return false;
	}
	var psn=$.trim($("#pposition").val());
	if(psn.length==0 || psn.length>100){
		_showMessage({title:"提示",content:"场地位置不能 为空，且必须小于100个字符"});
		return false;
	}
	var parea=$("#parea").val();
	var reg=/^\s*[1-9]?[0-9]{0,7}\s*$/;
	if(!reg.test(parea)){
		_showMessage({title:"提示",content:"场地面积不能 为空，必须是小于99999999，且大于0的整数!"});
		return false;
	}
	var pscale=$("#pscale").val();
	if(!reg.test(pscale)){
		_showMessage({title:"提示",content:"场地规模不能 为空，必须是小于99999999，且大于0的整数!"});
		return false;
	}
	return true;
}

function callback(da){
	if(da=="1"){
		_showMessage({title:"提示",content:"操作成功"});
		var files=$("input[type='file']");
		for(var i=0; i<files.size(); i++){
			file = $(files[i]);
			file.after(file.clone().val(""));   
			file.remove(); 
		}
		$("#allPlace").empty();
		findData();
	}
	if(da=="0"){
		_showMessage({title:"提示",content:"操作失败"});
	}
}

function deletePlace(){
	var id=$("#pid").val();
	_get({
		url:"driverSchoolPlace/deleteSchoolPlace/"+id+".action",
		data:"",
		success:function(da){
			if(da.state=="1"){
				_showMessage({title:"提示",content:"删除成功"});
				var files=$("input[type='file']");
				for(var i=0; i<files.size(); i++){
					file = $(files[i]);
					file.after(file.clone().val(""));   
					file.remove(); 
				}
				$("#allPlace").empty();
				findData();
			}else{
				_showMessage({title:"提示",content:"删除失败"});
			}
		}
	});
}




