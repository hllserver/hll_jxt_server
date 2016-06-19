var schoolPlaceGridOption = {
		columns :[{
			select : "checkBox",
			name : "权限名",
		}, {
			field : "schoolAccount",
			name : "所属驾校",
			width : 50,
		}, {
			field : "placeName",
			name : "场地名",
			width : 50,
			rule:{
				requerd:true,
			},
			editable: true
		}, {
			field : "area",
			name : "场地面积",
			width : 50,
			rule:{
				requerd:true,
				type:'number'
			},
			editable: true
		}, {
			field : "carNo",
			name : "训练车数量",
			width : 50,
			rule:{
				requerd:true,
				type:'number'
			},
			editable: true
		}, {
			field : "pic1",
			name : "场景一",
			width : 150,
			editable: true
		}, {
			field : "pic2",
			name : "场景二",
			width : 150,
			editable: true
		}, {
			field : "pic3",
			name : "场景三",
			width : 150,
			editable: true
		}, {
			field : "pic4",
			name : "场景四",
			width : 150,
			editable: true
		}, {
			field : "pic5",
			name : "场景五",
			width : 150,
			editable: true
		}, {
			field : "remark",
			name : "备注",
			width : 500,
			editable: true
		}],
		url : "driverSchoolPlace/getDriverSchoolPlaceList.action" ,//对应操作类action
		id  :  "schoolPlaceGrid",//表格的位置id,所在页面div的id
		gridId :  "schoolPlaceGrid1", //表格id，表格属性
		fromId:'formId'
			
};

//插入数据
function insertSchoolPlace(){
	_insert(schoolPlaceGridOption);
}
//标记为删除
function markAsDelete(){
	_markAsDelete(schoolPlaceGridOption);
}

//取消删除
function removeMarkAsDelete(){
	_removeDeleteMark(schoolPlaceGridOption);
}

//查询
function search(){
	$("#schoolPlaceGrid").empty();
	_Grid(schoolPlaceGridOption);
}

//保存数据
function saveSchoolPlace(){
	var valid=_isValid(gridOption);
	if(valid==false){
		return;
	}
	var insertedRecords= _getInsertedRecords(schoolPlaceGridOption);
	var updatedRecords=_getUpdatedRecords(schoolPlaceGridOption);
	var deletedRecords=_getDeletedRecords(schoolPlaceGridOption);
	var RecordsO=new Object();
	RecordsO.insertedRecords=insertedRecords;
	RecordsO.updatedRecords=updatedRecords;
	RecordsO.deletedRecords=deletedRecords;
	_post({
		url:"driverSchoolPlace/saveAll.action",
		data:JSON.stringify(RecordsO),
		success:function(value,type){
			if(value.result){
				var da=value.result;
				content="[";
				for(var i=0;i<da.length;i++){
					content=content+" "+da[i];
				}
				content=content+"]";
				_showMessage({
					title:"操作失败",
						content:"场地名"+content+"重复了"
				})
			}
			else{
				$("#schoolPlaceGrid").empty();
				_Grid(schoolPlaceGridOption);
			}
			
		}
	});
}

//页面加载 完成后 立即执行
$(document).ready(function() {
	_Grid(schoolPlaceGridOption);
})