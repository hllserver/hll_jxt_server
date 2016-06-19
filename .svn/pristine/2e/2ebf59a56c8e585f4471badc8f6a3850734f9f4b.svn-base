var nexthash=0,curHash='';

if("onhashchange" in window){
	//alert("the browser supports the hashchange event")
}

function getHash(){
	var h=location.hash;
	if(!h){
		return "";
	}else{
		return h;
	}
}

function changeHash(chain){
	location.hash=chain;
}

function changeHashCallBack(){
	var hash = getHash();
	if(curHash!=hash){
		curHash=hash;
	}
	var url = curHash.substring(1,curHash.length);
	$.ajax({
		url : url,
		type : 'GET',
		success : function(data) {
			$("#div2").html(data);
		}
	});
}

if(document.all && !document.documentMode){
	setInterval(changeHashCallBack,100);
}else{
	window.onhashchange=changeHashCallBack;
}
