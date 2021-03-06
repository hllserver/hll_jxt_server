var gridOption = {
	columns : [ {
		select : "checkBox",
		name : "",
		width:30
	}, {
		field : "id",
		name : "ID",
		hidden:true
	},{
		field : "priCode",
		name : "权限名",
		width : 150,
		editable: true,
		rule:{
			requerd:true,
			type:'string',
			reg:/^\s*[a-zA-Z][a-zA-Z0-9]{3,49}\s*$/,
			maxLength:50,
			hint:'以字母开头，长度为 4-50个字符'
		}
	}, {
		field : "priDesc",
		name : "权限描述",
		width : 200,
		showTip:true,
		rule:{
			requerd:true,
			type:'string',
			minLength:1,
			maxLength:30
		},
		editable: true
	}, {
		field : "priType",
		name : "权限类型",
		width : 100,
		dropBox:[{value:'1',display:'页面权限'},{value:'2',display:'方法权限'}]
	}, {
		field : "priUrl",
		name : "链接",
		width : 300,
		editable: true
	} ],
	url : "privilege/getPrivilegeList.action",
	id : "grid",       //表格 放置的位置 id,就是一个 div 的 id
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
//查询
function search(){
	$("#grid").empty();
	_Grid(gridOption);
}
//保存数据
function save(){
	var valid=_isValid(gridOption);
	if(valid==false){
		return;
	}
	
	var insertdRecords = _getInsertedRecords(gridOption);
	var updatedRecords = _getUpdatedRecords(gridOption);
	var deletedRecords = _getDeletedRecords(gridOption);
	if(insertdRecords.length==0 && updatedRecords.length==0 && deletedRecords.length==0){
		return ;
	}
	var RecordsO= new Object();
	RecordsO.insertedRecords=insertdRecords;
	RecordsO.updatedRecords=updatedRecords;
	RecordsO.deletedRecords=deletedRecords;
	_post({
		url:"privilege/saveAll.action",
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
					content:"权限名 "+context+"重复了"
				});
			}else{
				$("#grid").empty();
				_Grid(gridOption);
			}
		}
	});
}
//页面加载 完成后 立即执行
$(document).ready(function() {
	_Grid(gridOption);
})
