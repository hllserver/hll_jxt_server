
var studentGridOption = {
		columns :[{
			select : "checkBox",
			name : "权限名"
		},{
			field : "id",
			name : "ID",
			hidden:true
		}, {
			field : "name",
			name : "姓名",
			width : 20,
			editable: true
		},{
			field:"account",
			name:"账号",
			width:40,
			editable:true
		}, {
			field:"schoolAccount",
			name:"所属驾校",
			width:100,
			editable:true
		},{
			field : "nickname",
			name : "昵称",
			width : 50
			
		}, {
			field : "tel",
			name : "电话",
			width : 25,
			rule:{
				type:"number",
			},
			editable: true
		}, {
			field : "email",
			name : "邮箱",
			width : 50,
			editable: true
		}, {
			field : "qq",
			name : "QQ",
			width : 30,
			rule:{
				type:"number"
			},
			editable: true
		}, {
			field : "wechat",
			name : "微信",
			width : 150,
			editable: true
		}, {
			field : "gender",
			name : "性别",
			width : 15
			
		}, {
			field : "age",
			name : "年龄",
			width : 50,
			rule:{
				type:"number",
			},
			editable: true
		}, {
			field : "address",
			name : "地址",
			width : 150,
			editable: true
		}, {
			field : "idCard",
			name : "身份证号码",
			width : 50,
			rule:{
				type:'string',
			},
			editable: true
		},{
			field:"trainingStatus",
			name:"训练状态",
			width:100,
			editable:true
		},{
			field:"examStatus",
			name:"考试状态",
			width:100,
			editable:true
		},{
			field:"healthExamination",
			name:"体检情况",
			widtb:100,
			editable:true
		},{
			field:"carType",
			name:"车型",
			width:20,
			editable:true
		},{
			field:"createdBy",
			name:"添加者",
			width:50
		},{
			field:"createdDate",
			name:"创建时间",
			width:30
		},{
			field:"lastUpdatedBy",
			name:"最后修改者",
			width:30
		},{
			field:"lastUpdatedDate",
			name:"最后修改日期",
			width:30
		}],
		url : "studentInfo/getStudentInfoList.action" ,//对应操作类action
		id  :  "studentGrid",//表格的位置id,所在页面div的id
		gridId :  "studentGrid1", //表格id，表格属性
		fromId:'formId'
			
};

//插入数据
function insertStudent(){
	_insert(studentGridOption);
}
//标记为删除
function markAsDelete(){
	_markAsDelete(studentGridOption);
}

//取消删除
function removeMarkAsDelete(){
	_removeDeleteMark(studentGridOption);
}

//查询
function search(){
	$("#studentGrid").empty();
	_Grid(studentGridOption);
}

//保存数据
function saveStudent(){
	var valid=_isValid(gridOption);
	if(valid==false){
		return;
	}
	var insertedRecords= _getInsertedRecords(studentGridOption);
	var updatedRecords=_getUpdatedRecords(studentGridOption);
	var deletedRecords=_getDeletedRecords(studentGridOption);
	var RecordsO=new Object();
	RecordsO.insertedRecords=insertedRecords;
	RecordsO.updatedRecords=updatedRecords;
	RecordsO.deletedRecords=deletedRecords;
	_post({
		url:"studentInfo/saveAll.action",
		data:JSON.stringify(RecordsO),
		success:function(value,type){
			if(value.result){//如果保存失败
				var da = value.result;
				context="[";
				for(var i=0; i<da.length; i++){
					context=context+" "+da[i];
				}
				context=context+"]"
				_showMessage({
					title:"操作失败",
					content:"学生账号 "+context+"重复了"
				});
			}else{
				$("#studentGrid").empty();
				_Grid(studentGridOption);
			}
		}
	});
}

//页面加载 完成后 立即执行
$(document).ready(function() {
	_Grid(studentGridOption);
})
