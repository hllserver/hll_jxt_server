var gridOption = {
	columns : [  {
		select : "checkBox",
		name : "",
	},{
		field : "id",
		name : "ID",
		hidden:true,
		width : 50
	}, {
		field : "roleCode",
		name : "角色编号",
		width : 150,
		rule:{
			requerd:true,
			type:"number",
			min:0,
			max:99
		},
		editable:true
	}, {
		field : "roleName",
		name : "角色名称",
		width : 150,
		rule:{
			requerd:true,
			type:"string",
			maxLength:50,
			minLength:1
		},
		editable:true
	}, {
		field : "privilegeCode",
		name : "角色拥有的权限",
		event:{//单元格 绑定事件
			name:"click",
			fn:function(context){
				choose(context);
			}
		},  
		width : 350
	} ],
	url : "role/findRoleList.action",
	id : "gridDiv",       //表格 放置的位置 id,就是一个 div 的 id
	gridId : "grid1",  //定义表格  的 id
	fromId : "formId"  //查询条件 from 的 id
};
//插入数据
function insert(){
	_insert(gridOption);
}
//标记为删除
function markAsDelete(){
	_markAsDelete(gridOption);
}
//取消删除
function removeDeleteMark(){
	_removeDeleteMark(gridOption);
}
function search(){
	_Grid(gridOption);
}
$(document).ready(function(){
	_Grid(gridOption);
});
//为角色添加权限
function choose(context){
	var code = context.parent().children().eq(3)[0].innerHTML;
	_post({
		url:"role/findPrivilege/"+code+".action",
		data:"",
		success:function(value, type){
			//所有的权限
			var allPri = value.all;
			//已经拥有的权限
			var ownPri = value.own;
			var size = allPri.length;
			var div = $("#choose");
			div.empty();
			div.append("<hr/>");
			for(var i=0; i<size; i++){
				var box = $("<input type='checkbox' class='box' value="+allPri[i].priCode+">");
				if(ownPri.indexOf(allPri[i].priCode)!=-1){
					box.prop("checked", true);
				}
				div.append(box);
				div.append("<font>"+allPri[i].priDesc+"</font>");
				div.append("<br/>");
			}
			var bu = $("<input type='button' value='确定' class='_btn'>");
			div.append("<br/>");
			div.append(bu);
			$(bu).bind("click",function(){
				selectPrivilege(context);
			});
		}
	});
}
//选中权限，填进表格
function selectPrivilege(context){
	//原始数据
	var oldValue=context.text();
	var box = $(".box");
	var size = box.size();
	var s="";
	for(var i=0; i<size; i++){
		if(box[i].checked==true){
			s=s+box[i].value+",";
		}
	}
	context.text(s);
	if(oldValue != s){
		
		//添加 修改标记
		context.parent().addClass("_update");
	}
	$("#choose").empty();
}

//保存数据
function save(){
	var isValid=_isValid(gridOption);
	if(!isValid){
		return;
	}
	var insertdRecords = _getInsertedRecords(gridOption);
	var updatedRecords = _getUpdatedRecords(gridOption);
	var deletedRecords = _getDeletedRecords(gridOption);
	if(insertdRecords.length==0 && updatedRecords.length==0 && deletedRecords.length==0){
		return;
	}
	var RecordsO= new Object();
	RecordsO.insertedRecords=insertdRecords;
	RecordsO.updatedRecords=updatedRecords;
	RecordsO.deletedRecords=deletedRecords;
	_post({
		url:"role/saveAll.action",
		data:JSON.stringify(RecordsO),
		success:function(value, type){
			var da = value.result;
			if(da){
				context="[";
				for(var i=0; i<da.length; i++){
					context=context+" "+da[i];
				}
				context=context+" ]"
				_showMessage({
					title:"操作失败",
					content:"角色编号 "+context+"重复了"
				});
			}else{
				$("#gridDiv").empty();
				_Grid(gridOption);
			}
		}
	});
}



