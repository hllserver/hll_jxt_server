$(document).ready(function(){
	$("#recom").click(function(){
		$.post({
			url : "driverSchoolMsg/recommend/14601691329627.action",
			type : 'POST',
			data : "",
			success : function(value, type){
				
			}
		});
	});
});