
var schoolGridOption = {
		columns :[{
			field : "id",
			name : "ID",
			hidden:true
		},{
			field : "account",
			name : "账号",
			width : 50,
			rule:{
				requerd:true,
			}
		}, {
			field : "schoolName",
			name : "驾校名",
			width : 50,
			rule:{
				requerd:true,
				type:"string",
				maxLength:50,
				minLength:1
			},
			editable: true
		}, {
			field : "address",
			name : "地址",
			width : 50,
			rule:{
				requerd:true,
				type:"string",
				maxLength:80,
				minLength:1
			},
			editable: true
		}, {
			field : "tel",
			name : "电话",
			width : 50,
			rule:{
				type:'string',
				reg:/^\s*[0-9]{0,12}\s*$/,
				hint:"0至12位数字"
			},
			editable: true
		}, {
			field : "email",
			name : "邮箱",
			width : 50,
			rule:{
				type:'string',
				maxLength:50,
				hint:'小于50个字符'
			},
			editable: true
		}, {
			field : "policy",
			name : "优惠政策",
			width : 50,
			rule:{
				type:'string',
				maxLength:50,
				hint:'小于400个字符'
			},
			editable: true
		}, {
			field : "registeredTime",
			name : "注册时间",
			width : 50,
		}, {
			field : "scale",
			name : "规模",
			width : 50,
			rule:{
				type:"number",
				min:0,
				max:99999
			},
			editable: true
		},{
			field : "intruduce",
			name : "简介",
			width : 350,
			rule:{
				type:'string',
				maxLength:700
			},
			editable: true
		}],
		url : "driverSchool/getDriverSchoolList.action" ,//对应操作类action
		id  :  "schoolGrid",//表格的位置id,所在页面div的id
		gridId :  "schoolGrid1", //表格id，表格属性
		fromId:'formId'
			
};

//插入数据
function insertSchool(){
	_insert(schoolGridOption);
}
//标记为删除
function markAsDelete(){
	_markAsDelete(schoolGridOption);
}

//取消删除
function removeMarkAsDelete(){
	_removeDeleteMark(schoolGridOption);
}

//查询
function search(){
	$("#schoolGrid").empty();
	_Grid(schoolGridOption);
}

//保存数据
function saveSchool(){
	var valid=_isValid(schoolGridOption);
	if(valid==false){
		return;
	}
	var updatedRecords=_getUpdatedRecords(schoolGridOption);
	if(updatedRecords.length==0){
		return;
	}
	var RecordsO=new Object();
	RecordsO.updatedRecords=updatedRecords;
	_post({
		url:"driverSchool/saveAll.action",
		data:JSON.stringify(RecordsO),
		success:function(value,type){
			if(value.result){//如果保存失败
				var da = value.result;
				context="[";
				for(var i=0; i<da.length; i++){
					context=context+" "+da[i];
				}
				context=context+" ]"
				_showMessage({
					title:"操作失败",
					content:"驾校账号 "+context+"重复了"
				});
			}else{
				$("#schoolGrid").empty();
				_Grid(schoolGridOption);
			}
		}
	});
}

//页面加载 完成后 立即执行
$(document).ready(function() {
	debugger;
	_Grid(schoolGridOption);
})
