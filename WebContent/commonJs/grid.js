/**
 * 和表格相关的操作 , 增、删、改、查、取行数据、
 */
// 创建表格-----------------------------------------------------------------------------------------
function _Grid(option) {
	debugger;
	$("div[class*='_gvb"+option.gridId+"']").remove();
	if (option.url === undefined || option.columns === undefined) {
		return;
	}
	// 要查询 第几页
	var currentPage = 1;
	// 每页多少条
	var pageSize = 7;
	var temp = $("#" + option.gridId + "_cur_page").text();
	if (Number($("#" + option.gridId + "_cur_page").text()) > 0) {
		currentPage = $("#" + option.gridId + "_cur_page").text();
	}
	var temp1 = $("#" + option.gridId + "_page_select").text();
	if (Number($("#" + option.gridId + "_page_select").text()) > 0) {
		pageSize = $("#" + option.gridId + "_page_select").text();
	}
	var formData = [];
	if (option.fromId != undefined) {
		// form 转 json
		var formData = $("#" + option.fromId).serializeObject();
	}
	// 去掉 .action 后缀
	option.url = option.url.replace(".action", "");
	$.ajax({
				url : option.url + '/' + currentPage + '/' + pageSize
						+ ".action",
				type : 'POST',
				contentType : 'application/json',
				data : formData,
				async:true,
				dataType : 'json',
				success : function(da, type) {
					debugger;
					$("#"+option.id).empty();
					var data
					if (da.resultList != undefined) {
						data = da.resultList;
					} else {
						data = da;
					}
					// 创建表
					var table = document.createElement("table");
					table.className = "_table";
					table.style.border = "solid 1px";
					table.id = option.gridId;
					var trh = document.createElement("tr");
					var k = option.columns.length;
					for (var i = 0; i < k; i++) { // 表头
						// 添加 一个序号行
						if (i == 0) {
							var squence = document.createElement("th");
							squence.className = "_table_th";
							squence.innerHTML = "序号";
							trh.appendChild(squence);
						}
						var th = document.createElement("th");
						th.className = "_table_th";
						var width = option.columns[i].width;
						if(width!=undefined && width!=null && width!=""){
							th.width = width + 'px';
						}
						// 是否是复选框
						var select = option.columns[i].select;
						if (select && select != null) {
							th.innerHTML = "";
						}
						// 是否隐藏
						var hidden = option.columns[i].hidden;
						if(hidden && hidden==true){
							$(th).addClass("_hidden");
							th.innerHTML = tem;
						}
						// 是否有标题
						var tem = option.columns[i].name;
						if(tem && tem!=null) {
							th.innerHTML = tem;
						}
						// 是否可编辑
						var editable = option.columns[i].editable;
						if (editable && editable == true) {
							th.innerHTML = tem + "(※)";
						} 
						trh.appendChild(th);
						table.appendChild(trh);
					}
					var size = data.length;
					for (var i = 0; i < size; i++) {// 表中数据
						var tr = document.createElement("tr");
						tr.setAttribute('_row',i);
						// 序列
						var sq = document.createElement("td");
						sq.setAttribute("_cel",0);
						sq.className = "_table_td";
						sq.innerHTML = i;
						tr.appendChild(sq);
						tr.className = "_table_tr";
						for (var j = 0; j < k; j++) {
							var td = document.createElement("td");
							td.setAttribute("_cel",j+1);
							td.className = "_table_td";
							// 复选框
							var select = option.columns[j].select;
							if (select && select != null) {
								td.innerHTML = "<input class='_select' type='checkbox'/>";
							} 
							// 是否是下拉框
							var dropBox = option.columns[j].dropBox;
							if(dropBox){
								$(td).addClass("_dropBox");
							}
							// 插入数据
							var field = option.columns[j].field;
							if (data[i][field] != undefined) {
								// 如果以下拉框的形式展示数据，根据value显示相应的 display
								if(dropBox){
									for(var n=0; n<dropBox.length; n++){
										if(dropBox[n].value==data[i][field]){
											td.innerHTML = dropBox[n].display;
										}
									}
								}else{// 如果以普通形式展示数据
									td.innerHTML = data[i][field];
								}
							}
							// 是否可以编辑
							var editable = option.columns[j].editable;
							if (editable && editable == true) {
								td.className = td.className + " _edit";
								// 添加编辑的绑定事件
								(function(td,dropBox,option,column){
									$(td).bind('click',function(){
										_cellBindClick(this,dropBox,option,column);
									});
								})(td,dropBox,option,option.columns[j]);
							}
							// 是否隐藏
							var hidden = option.columns[j].hidden;
							if(hidden && hidden==true){
								$(td).addClass("_hidden");
							}
							//是否有鼠标悬停 提示内容 事件
							var showTip=option.columns[j].showTip;
							if(showTip){
								
								
								
								
							}
							// 是否有其它绑定事件
							var event = option.columns[j].event;	
							if(event){
								(function(td){
									$(td).bind(event.name,function(){
										event.fn($(td));
									});
								})(td)
							}
							tr.appendChild(td);
						}
						table.appendChild(tr);
					}
					var tb = document.getElementById(option.id);
					tb.appendChild(table);
					// -----------------添加分页标签-------------------------------
					var page = da.page;
					var div = document.createElement("div");
					div.id = option.gridId + "_page";
					var papeText = "<input id='"
							+ option.gridId
							+ "_first' type='button' value='首页' class='_btn'> <input id='"
							+ option.gridId
							+ "_prev' type='button' value='上一页' class='_btn'> "
							+ "<input id='"
							+ option.gridId
							+ "_next' type='button' value='下一页' class='_btn'> <input id='"
							+ option.gridId
							+ "_last' type='button' value='尾页' class='_btn'>"
							+ "转至第<input id='"
							+ option.gridId
							+ "_page_to' width='10' type='text'>页 <input type='button' value='go' class='_btn'> 当前第<span id='"
							+ option.gridId + "_cur_page'>1</span>页 ,"
							+ "每页<span id='" + option.gridId
							+ "_page_select'>7</span>条，共<span id="+option.gridId+"_total_page></span>页，共<span id='" + option.gridId
							+ "_total'></span>条";
					div.innerHTML = papeText;
					tb.appendChild(div);
					if (da.page != undefined) {
						var pageInfo = da.page;
						$("#" + option.gridId + "_cur_page").text(
								pageInfo.currentPage);
						$("#" + option.gridId + "_page_select").text(
								pageInfo.pageSize);
						$("#" + option.gridId + "_total").text(
								pageInfo.totalRecords);
						$("#" + option.gridId + "_total_page").text(
								pageInfo.totalPage);
					}
					// ------------------为页面绑定事件-----------------------------
					// 跳到首页
					$("#"+option.gridId+"_first").bind("click",function(){
						_go_to_first_page(option);
					});
					// 跳转到上一页
					$("#"+option.gridId+"_prev").bind("click",function(){
						_go_to_prev_page(option);
					});
					// 跳转到下一页
					$("#"+option.gridId+"_next").bind("click",function(){
						_go_to_next_page(option);
					});
					// 跳转到最后页
					$("#"+option.gridId+"_last").bind("click",function(){
						_go_to_last_page(option);
					});
				}
			});
}
// 跳转到首页-------------------------------------------------------------------------
function _go_to_first_page(option){
	// 当前页 设置为 1
	$("#" + option.gridId + "_cur_page").text("1");
	// $("#" + option.gridId + "_pageSize").text();
	// 重新加载表格
	_Grid(option);
}
// 跳转到上一页-----------------------------------------------------------------------
function _go_to_prev_page(option){
	// 得到当前页
	var curp = Number ($("#" + option.gridId + "_cur_page").text());
	// 如果当前页==1,则不变
	if(curp==1){
		curp=1;
	}else{
		curp = curp-1;
	}
	$("#" + option.gridId + "_cur_page").text(curp);
	// 重新加载表格
	_Grid(option);
}
// 跳转到下一页------------------------------------------------------------------------
function _go_to_next_page(option){
	// 得到当前页
	var curp = Number ($("#" + option.gridId + "_cur_page").text());
	curp = curp+1;
	$("#" + option.gridId + "_cur_page").text(curp);
	// 重新加载表格
	_Grid(option);
}
// 跳到最后一页--------------------------------------------------------------------------
function _go_to_last_page(option){
	// 得到最后页
	var curp = $("#" + option.gridId + "_total_page").text();
	$("#" + option.gridId + "_cur_page").text(curp);
	// 重新加载表格
	_Grid(option);
}
//单元格邦定编辑事件   -------------------------------------------------------------------------
function _cellBindClick(content,obj,option,column) {
	var tdObj = $(content);
	//得到验证条件
	var rule = column.rule;
	// 得到 行对象
	var trObj = tdObj.parent();
	//行号
	var rowNo = trObj.attr("_row");
	//列号
	var celNo = tdObj.attr("_cel");
	// 获取 tr 对象的 class 值
	var trClass = trObj.attr("class");
	// 获取 td 对象的 class 值
	var tdClass = tdObj.attr("class");
	var isDropBox = tdClass.match('_dropBox');
	// 得到当前显示值
	var oldText = tdObj.text();
	if(isDropBox && obj!=null){
		// 创建一个下拉框
		var select = $("<select></select>");
		// 设置下拉框的宽度 与 td 的宽度相同
		select.outerWidth(tdObj.outerWidth()-1);
		select.outerHeight(tdObj.outerHeight()-1);
		// 去掉 文本框外边距
		tdObj.css("padding","0");
		select.css("margin", 0);
		select.css("padding", 0);
		// 创建下拉框的元素
		for(var n=0; n<obj.length; n++){
			var opt = $("<option></option");
			opt.text(obj[n].display);
			// 表格解除点击事件
			tdObj.unbind('click');
			select.append(opt);
		}
		tdObj.html(select);
		// 下拉框失去焦点时 变为文本
		select.change(function() {
			// 文本新内容
			var newText = select.find("option:selected").text(); 
			tdObj.html(newText);
			// 如果 文本内容 发生改变，且 不为新增的记录 添加 类属性 _update
			if (trClass.match("_add") == null && oldText != newText) {
				trObj.addClass("_update");
			}
			// 恢复表格点击事件
			tdObj.bind('click',function(){_cellBindClick(content,obj,option,column)});
		});
		// 下拉框失去焦点时 变为文本
		select.blur(function() {
			// 文本新内容
			var newText = select.find("option:selected").text(); 
			tdObj.html(newText);
			// 如果 文本内容 发生改变，且 不为新增的记录 添加 类属性 _update
			if (trClass.match("_add") == null && oldText != newText) {
				trObj.addClass("_update");
			}
			// 恢复表格点击事件
			tdObj.bind('click',function(){_cellBindClick(content,obj,option,column)});
			//清除掉原来的验证，并重新验证
			if(rule){
				$("._gvb"+option.gridId+"_"+rowNo+"_"+celNo).remove();
				_doValid(rule,newText,tdObj,rowNo,celNo,option.gridId);
			}
		});
	}else{
		// 创建一个文本框
		var inputObj = $("<input type='text'>");
		inputObj.val(oldText);
		// 去掉文本框的边框
		inputObj.css("border-width", 0);
		inputObj.click(function() {
			return false;
		});
		// 设置文本框的宽度 与 td 的宽度相同
		inputObj.outerWidth(tdObj.outerWidth()-1);
		inputObj.outerHeight(tdObj.outerHeight()-1);
		// 去掉 文本框外边距
		tdObj.css("padding","0");
		inputObj.css("margin", 0);
		inputObj.css("padding", 0);
		inputObj.css("text-align", 'left');
		//inputObj.css("font-size", "16px");
		// inputObj.css("background-color",tdObj.css("background-color"));
		// 把文本框放入td中
		tdObj.html(inputObj);
		// 文本框失去焦点时 变为文本
		inputObj.blur(function() {
			// 文本新内容
			var newText = inputObj.val();
			tdObj.html(newText);
			// 如果 文本内容 发生改变，且 不为新增的记录 添加 类属性 _update
			if (trClass.match("_add") == null && oldText != newText) {
				trObj.addClass("_update");
			}
			//清除掉原来的验证，并重新验证
			if(rule){
				$("._gvb"+option.gridId+"_"+rowNo+"_"+celNo).remove();
				_doValid(rule,newText,tdObj,rowNo,celNo,option.gridId);
			}
		});
	}
}
// 插入一条数据----------------------------------------------------------------
function _insert(gridOption) {
	//得到整个表的数据(tr tr tr...)
	var gridData = $("#"+gridOption.gridId+" tr");
	//定义插入数据的行号
	var rowNo=0;
	if(gridData.size()>1){
		rowNo = gridData.eq(-1).attr("_row");
		rowNo = Number(rowNo) +1;
	}
	var culumns = gridOption.columns;
	var size = culumns.length;
	var tr = document.createElement("tr");
	tr.setAttribute("_row",rowNo);
	tr.className = "_table_tr _add";
	for (var i = 0; i < size; i++) {
		if (i == 0) {
			// 序列行
			var squ = document.createElement("td");
			squ.className = "_table_td";
			squ.setAttribute("_cel",0);
			squ.innerHTML = rowNo;
			tr.appendChild(squ);
		}
		var td = document.createElement("td");
		td.setAttribute("_cel",i+1);
		td.className = "_table_td";
		// 复选框
		var select = culumns[i].select;
		if (select && select != null) {
			td.innerHTML = "<input class='_select' type='checkbox'/>";
		}
		// 是否可以编辑
		var editable = culumns[i].editable;
		var dropBox = culumns[i].dropBox;
		if (editable && editable == true) {
			td.className = td.className + " _edit";
			$(td).addClass('_dropBox');
			// 添加编辑的绑定事件
			(function(td,dropBox,gridOption,culumn){
				$(td).bind('click',function(){
					_cellBindClick(td,dropBox,gridOption,culumn);
				})
			})(td,dropBox,gridOption,culumns[i])
		}
		// 是否隐藏
		var hidden = culumns[i].hidden;
		if(hidden && hidden==true){
			$(td).addClass("_hidden");
		}
		// 是否有绑定事件
		var event = culumns[i].event;
		if(event){
			$(td).bind(event.name,function(){
				event.fn($(td));
			});
		}
		tr.appendChild(td);
	}
	var table = document.getElementById(gridOption.gridId);
	table.appendChild(tr);
}

// 添加 删除标记----------------------------------------------------------------------
function _markAsDelete(gridOption) {
	var select = $("#" + gridOption.gridId + " ._select");
	var length = select.length;
	for (var i = 0; i < length; i++) {
		var st = select.eq(i);
		var selected = st.prop('checked');
		if (selected == true) {
			// 如果 该行被选中，为该行 class 添加 "_del"
			var tr = st.parent().parent();
			var td = tr.children();
			td.addClass("_del");
			tr.addClass("_tr_del")
			st.attr("checked", false);
		}
	}
}
// 取消删除标记----------------------------------------------------------------------
function _removeDeleteMark(gridOption) {
	var select = $("#" + gridOption.gridId + " ._select");
	var length = select.length;
	for (var i = 0; i < length; i++) {
		var st = select.eq(i);
		var selected = st.prop('checked');
		if (selected == true) {
			// 如果 该行被选中，为该行 class 添加 "_del"
			var tr = st.parent().parent();
			var td = tr.children();
			tr.removeClass("_tr_del");
			td.removeClass("_del");
			st.attr("checked", false);
		}
	}
}
// 获取 所有新增的记录。class 中 有 _add,且 无 _tr_del,则为 新增的记录-------------------------------------------------
function _getInsertedRecords(gridOption) {
	var culumns = gridOption.columns;
	var result = [];
	var select = $("#" + gridOption.gridId + " ._add");
	var length = select.length;
	for (var i = 0; i < length; i++) {
		// 按class 筛选数据
		var cla = select[i].className;
		var del = cla.match("_tr_del");
		if (del == null) {
			var record = select.eq(i).children();
			var size = record.length;
			var obj = {};
			for (var j = 1; j < size; j++) {
				// ----------------获取 值 ，，，后面或许会改----------------
				var text = record[j].innerHTML;
				var field = culumns[j - 1].field;
				// 下拉框
				var dropBox = culumns[j - 1].dropBox;
				if(dropBox && field){// 如果是下拉框
					for(var n=0; n<dropBox.length; n++){
						if(dropBox[n].display==text){
							obj[field] = dropBox[n].value;
							break;
						}
					}
				}else if (field != undefined) {
					obj[field] = text;
				}
			}
			result.push(obj);
		}
	}
	return result;
}

// 获取所有的 修改的记录 。 class 中有 _update 无 _tr_del 无 _add-----------------------------------------
function _getUpdatedRecords(gridOption) {
	var culumns = gridOption.columns;
	var result = [];
	var select = $("#" + gridOption.gridId + " ._update");
	var length = select.length;
	for (var i = 0; i < length; i++) {
		// 按class 筛选数据
		var cla = select[i].className;
		var del = cla.match("_tr_del");
		var add = cla.match("_add");
		if (del == null && add==null) {
			var record = select.eq(i).children();
			var size = record.length;
			var obj = {};
			for (var j = 1; j < size; j++) {
				// ----------------获取 值 ，，，后面或许会改----------------
				var text = record[j].innerHTML;
				var field = culumns[j - 1].field;
				// 下拉框
				var dropBox = culumns[j - 1].dropBox;
				if(dropBox && field){// 如果是下拉框
					for(var n=0; n<dropBox.length; n++){
						if(dropBox[n].display==text){
							obj[field] = dropBox[n].value;
							break;
						}
					}
				}else if (field != undefined) {
					obj[field] = text;
				}
			}
			result.push(obj);
		}
	}
	return result;
}
// 获取所有的 删除的记录 。 class 中有_tr_del 无 _add---------------------------------------------------
function _getDeletedRecords(gridOption) {
	var culumns = gridOption.columns;
	var result = [];
	var select = $("#" + gridOption.gridId + " ._tr_del");
	var length = select.length;
	for (var i = 0; i < length; i++) {
		// 按class 筛选数据
		var cla = select[i].className;
		var add = cla.match("_add");
		if (add == null) {
			var record = select.eq(i).children();
			var size = record.length;
			var obj = {};
			for (var j = 1; j < size; j++) {
				// ----------------获取 值 ，，，后面或许会改----------------
				var text = record[j].innerHTML;
				var field = culumns[j - 1].field;
				// 下拉框
				var dropBox = culumns[j - 1].dropBox;
				if(dropBox && field){// 如果是下拉框
					for(var n=0; n<dropBox.length; n++){
						if(dropBox[n].display==text){
							obj[field] = dropBox[n].value;
							break;
						}
					}
				}else if (field != undefined) {
					obj[field] = text;
				}
			}
			result.push(obj);
		}
	}
	return result;
}
//表格验证---------------------------------------------------------------------------------
function _isValid(option){
	var gridId = option.gridId;
	$("div[class*='_gvb"+gridId+"']").remove();
	//表格中数据的模板
	var columns = option.columns;
	var size = columns.length;
	//得到整个表的数据
	var grid = $("#"+gridId+" tr");
	var length = grid.size();
	var isValid=true;
	for(var i=1; i<length; i++){//从第二行开始，第一行为表头
		var rowValue = grid[i];
		var rowNo = rowValue.getAttribute("_row");
		for(var j=1; j<=size; j++){//从第二列开始，第一列为行号
			var rule = columns[j-1].rule;
			var td = $(rowValue).children().eq(j);//待验证单元格对象
			var celNo=td.attr("_cel");
			var field = $(rowValue).children().eq(j).text();
			if(rule){//如果该列有验证
				valid = _doValid(rule,field,td,rowNo,celNo,gridId);
				if(valid==false){
					isValid= false;
				}
			}
		}
	}
	return isValid;
}
//数据验证操作----------------------------
function _doValid(rule,field,td,rowNo,celNo,gridId){
	var tip="";//保存提示语
	var isValid=true;
	if(rule.type=='number'){//如果是数字验证类型
		var regExp=/^\s*[-+]?[0-9]*\s*$/;
		if(!regExp.test(field)){
			tip='非法数字';
		}else if((rule.min || rule.min==0) && rule.min>field){
			//最小值验证
			tip="必须大于"+rule.min;
		}else if(rule.max && rule.max<field){
			//最大值验证
			tip="必须小于"+rule.max;
		}
	}
	if(rule.type=='string'){//字符串验证类型
		if(field!=null){
			field = $.trim(field);
		}
		if(rule.maxLength && rule.maxLength<field.length){
			tip="最大长度 "+ rule.maxLength;
		}else if(rule.minLength && rule.minLength>field.length){
			tip="最小长度 "+ rule.minLength;
		}else if(rule.reg){
			if(!rule.reg.test(field)){
				tip="不合法输入";
			}
		}
	}
	if(rule.custom){//自定义校验
		if(rule.custom(field,td)==false){
			tip="不合法输入";
		}
	}
	if(rule.type=="date"){//日期校验
		
	}
	if(rule.requerd){//非空验证
		if(field==null || field==""){
			tip="不能为空";
		}
	}
	if(tip!=""){
		isValid=false;
		//定位单元格
		var pos = td.offset();
		//弹出框左上角的位置
		var left = pos.left;
		var top = td.outerHeight()+pos.top;
		//弹出警告框
		if(rule.hint){//如果有自定认提示
			_validBox(left,top-1,td.outerWidth(),rule.hint,gridId,rowNo,celNo);
		}else{//默认提示
			_validBox(left,top-1,td.outerWidth(),tip,gridId,rowNo,celNo);
		}
	}
	return isValid;
}
//警告弹出框-----------------------------------------------------------------------------
function _validBox(dleft,dtop,dwidth,content,gridId,rowNo,celNo){
	var div=$("<div></div>");
	var kk = $("<body>");
	$("body").append(div);
	div.attr("class","_gvb"+gridId+"_"+rowNo+"_"+celNo);
	div.css({"left":dleft+"px","top":dtop+"px","width":dwidth+"px","position":"absolute","z-index":"2",
		"background-color":"#FFA500","border": "1px #003399 solid"});
	div.text(content);
}

//用于提示的弹出框-------------------------------------------------------------
function _showMessage(op){
	var ti = new Date();
	var secend = ti.getTime();
	var title = op.title;
	var content = op.content;
	var width = op.width || 260;
	var height = op.height || 50;
	var winHeight = window.outerHeight;
	var winWidth = window.innerWidth;
	var div = $("<div></div>");
	div.attr("id",secend);
	div.css({"border":"1px solid","left":(winWidth-width)/2+"px","top":(winHeight-height)/3+"px",
			 "position":"absolute","z-index":"2"
			 });
	var div1 = $("<div></div>");//弹出框标题
	div1.text(title);
	div1.css({"background-color":"#D3D3D3","font-size":"22px","text-align":"center"});
	var div2 = $("<div></div>");//弹出框内容
	div2.text(content);
	div2.css({"overflow":"hidden","word-wrap":"break-word","word-break":"break-all",
		"width":width,"height":height,"background-color":"#FFF0F5"});
	var div3 = $("<div></div>");//弹出框操作
	div3.css({"background-color":"#D3D3D3","text-align":"center"});
	div3.html("<button onclick='_closeTipBox("+secend+")'>确定</button>")
	div.append(div1);
	div.append(div2);
	div.append(div3);
	$("body").append(div);
}
//关闭弹出框------------------------------------------------------------------------------
function _closeTipBox(id){
	$("#"+id).remove();
}
//确认框   ？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
function _alert(op){
	var content = op.content;
	var width = op.width || 260;
	var height = op.height || 50;
	var ti = new Date();
	var secend = ti.getTime();
	var div = $("<div></div>");
	div.attr("id",secend);
	div.css({"border":"1px solid","left":(winWidth-width)/2+"px","top":(winHeight-height)/3+"px",
			 "position":"absolute","z-index":"2"
			 });
	var div1 = $("<div></div>");
	div1.css({"background-color":"#D3D3D3","height":"20px"});
	var div2 = $("<div></div>");//弹出框内容
	div2.text(content);
	div2.css({"overflow":"hidden","word-wrap":"break-word","word-break":"break-all",
		"width":width,"height":height,"background-color":"#FFF0F5"});
	var div3 = $("<div></div>");//弹出框操作
	div3.css({"background-color":"#D3D3D3","text-align":"center"});
	div3.html("<button id="+secend+">是的</button><button onclick='_closeAlert("+secend+",1)'>取消</button>")
	div.append(div1);
	div.append(div2);
	div.append(div3);
	$("body").append(div);
}
function _closeAlert(id,sign){
	_closeTipBox(id);
	if(sign==1){
		return true;
	}else{
		return false;
	}
}