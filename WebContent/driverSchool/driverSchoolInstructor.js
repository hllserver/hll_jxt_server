var gridOption = {
		columns :[{
			select : "checkBox",
			name : "权限名",
		}, {
			field : "id",
			name : "Id",
			width : 50,
			hidden:true
		},{
			field:"",
			name:"教练账号",
			width:100
		},{
			field:"schoolAccount",
			name:"驾校编号",
			width:100
		},{
			field : "schoolName",
			name : "驾校名",
			width : 100,
			hidden: true,
		}, {
			field : "jobNumber",
			name : "工号",
			width : 100,
			rule:{
				requerd:true,
				type:"string",
				reg:/^\s*[0-9a-zA-Z]{1,30}\s*$/,
				hint:"1到30位数字或字符"
			},
			editable: true
		}, {
			field : "name",
			name : "姓名",
			width : 100,
			rule:{
				requerd:true,
				maxLength:10,
				hint:"不超过10个字符"
			},
		editable: true
		}, {
			field : "age",
			name : "年龄",
			width : 80,
			rule:{
				requerd:true,
				type:'number',
				max:100,
				hint:"小于100的数字"
			},
			editable: true
		}, {
			field : "tel",
			name : "电话",
			width : 80,
			rule:{
				requerd:true,
				type:'string',
				maxLength:12,
				hint:"小于12位的数字"
			},
			editable: true
		}, {
			field : "gender",
			name : "性别",
			width : 80,
			dropBox:[{value:'1',display:'男'},{value:'0',display:'女'}],
			rule:{
				requerd:true,
			},
			editable: true
		}, {
			field : "email",
			name : "邮箱",
			width : 90,
			rule:{
				type:'string',
				maxLength:50,
				hint:'小于50个字符'
			},
			editable: true
		}, {
			field : "remark",
			name : "备注",
			width : 150,
			rule:{
				type:'string',
				maxLength:200,
				hint:'小于200个字符'
			},
			editable: true
		}],
		url : "driverSchoolMsg/getDriverInstructor.action" ,//对应操作类action
		id  :  "gridId",//表格的位置id,所在页面div的id
		gridId :  "grid1", //表格id，表格属性
		fromId:'formId'
};
function insert(){
	_insert(gridOption);
}
function markAsDelete(){
	_markAsDelete(gridOption);
}
function removeDeleteMark(){
	_removeDeleteMark(gridOption);
}
function search(){
	$("#gridId").empty();
	_Grid(gridOption);
}
function save(){
	var valid=_isValid(gridOption);
	if(valid==false){
		return;
	}
	var insertedRecords= _getInsertedRecords(gridOption);
	var updatedRecords=_getUpdatedRecords(gridOption);
	var deletedRecords=_getDeletedRecords(gridOption);
	var RecordsO=new Object();
	if(insertedRecords.length==0 && updatedRecords.length==0 && deletedRecords.length==0){
		_showMessage({title:"提示",content:"没有修改 和、新增、删除的数据!"});
		return;
	}
	RecordsO.insertedRecords=insertedRecords;
	RecordsO.updatedRecords=updatedRecords;
	RecordsO.deletedRecords=deletedRecords;
	_post({
		url:"driverSchoolMsg/saveInstructor.action",
		data:JSON.stringify(RecordsO),
		success:function(value,type){
			if(value.result){
				var da =value.result;
				context="[";
				for(var i=0;i<da.length;i++){
					context=context+" "+da[i];
				}
				context=context+"]";
				_showMessage({
					title:"操作失败",
					content:"工号"+context+"重复了"
				});
			}
			else{
				$("#gridId").empty();
				_Grid(gridOption);
			}
		}
	});
}
$(document).ready(function(){
	_Grid(gridOption);
});