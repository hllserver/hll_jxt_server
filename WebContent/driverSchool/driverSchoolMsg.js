$(document).ready(function(){
	ready();	
});

  function ready(){
	  _post({
			url:"driverSchoolMsg/getDriverSchoolMsg.action",
			data:"",
			success:function(da){
				if(da!=null){
					$("#schoolName").val(da.schoolName);
					$("#position").val(da.position);
					$("#tel").val(da.tel);
					$("#email").val(da.email);
					$("#registeredTime").val(da.registeredTime);
					$("#scale").val(da.scale);
					$("#remark").val(da.remark);
					$("#info").val(da.intruduce)
				}
			}
		});
  }

function save(){
	var valid=_isValid(gridOption);
	if(valid==false){
		return;
	}
	debugger;
	var jsonuserinfo = $("#formId").serializeObject();
	_post({
		url:"driverSchoolMsg/modify.action",
		data:jsonuserinfo,
		success:function(da){
			if(da){
				_showMessage({
					title:"恭喜你",
					content:"操作成功"
				});
			}else _showMessage({
				title:"很遗憾",
				content:"操作失败"
			});
			ready();
		}
	});
}