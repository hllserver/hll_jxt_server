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
		field : "account",
		name : "账号",
		width : 100
	}, {
		field : "tel",
		name : "电话",
		width : 100,
		rule:{
			type:'string',
			reg:/^\s*[0-9]{0,12}\s*$/,
			hint:"0至12位数字"
		},
		editable:true
	}, {
		field : "email",
		name : "邮箱",
		editable:true,
		rule:{
			type:'string',
			maxLength:50,
			hint:'小于50个字符'
		},
		width : 100
	}, {
		field : "qq",
		name : "qq号",
		editable:true,
		rule:{
			type:'string',
			reg:/^\s*[0-9]{0,20}\s*$/,
			hint:"小于20位数字"
		},
		width : 100
	}, {
		field : "weChat",
		name : "微信号",
		editable:true,
		rule:{
			type:'string',
			maxLength:20,
			hint:'小于20个字符'
		},
		width : 100
	} , {
		field : "type",
		name : "用户角色类型",
		editable:true,
		rule:{
			requerd:true
		},
		dropBox:[{value:'1',display:'系统管理员'},
		         {value:'2',display:'系统普通管理员'},
		         {value:'3',display:'驾校管理员'},
		         {value:'4',display:'驾校普通管理员'},
		         {value:'5',display:'普通用户'}],
		width : 100
	} , {
		field : "nickName",
		name : "昵称",
		editable:true,
		rule:{
			type:'string',
			maxLength:50,
			hint:'小于50个字符'
		},
		width : 100
	} ],
	url : "user/findRoleList.action",
	id : "gridDivId",   
	gridId : "grid1", 
	fromId : "formid"
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
function removeMarkAsDelete(){
	_removeDeleteMark(gridOption);
}
//查询
function search(){
	$("#gridDivId").empty();
	_Grid(gridOption);
}
//保存
function save(){
	var valid=_isValid(gridOption);
	if(valid==false){
		return;
	}
	var updatedRecords = _getUpdatedRecords(gridOption);
	if(updatedRecords==null || updatedRecords.length==0){
		return ;
	}
	var RecordsO= new Object();
	RecordsO.updatedRecords=updatedRecords;
	_post({
		url:"user/saveAll.action",
		data:JSON.stringify(RecordsO),
		success:function(value, type){
			if(value.result){//如果保存失败
				var da = value.result;
				context="[";
				for(var i=0; i<da.length; i++){
					context=context+" "+da[i];
				}
				context=context+" ]"
				_showMessage({
					title:"操作失败",
					content:"用户账号 "+context+"重复了"
				});
			}else{
				$("#gridDivId").empty();
				_Grid(gridOption);
			}
		}
	});
}

$(document).ready(function(){
	_Grid(gridOption);
});