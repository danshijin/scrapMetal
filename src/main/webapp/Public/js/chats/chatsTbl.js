var start = 0;
var type=0;
var len=10;
var total=0;
var strWhere = "";
var titleArr = [];
var nameArr = [];
var delTd=false;

$(document).ready(function() {
	initData();
});

function sortArray(data){
	
	var head = [];
	var body = [];
	var foot = [];
	for(var i = 0 ; i < data.length; i++){
		if(data[i]['isClose'] == 1){
			foot.push(data[i]);
		} else if (data[i]["isConfirm"]==1) {
			head.push(data[i]);
		} else {
			body.push(data[i]);
		}
	}
	return head.concat(body).concat(foot);
}

function initData(){
	var searchParam = {};
	searchParam.start = start;
	searchParam.len = len;
	searchParam.strWhere = strWhere;
	searchParam.attribute = $("#attributeHidden").val();
	searchParam.operator = $("#operatorHidden").val();
	//alert($("#contentHidden").val());
	searchParam.content = $("#contentHidden").val();   
	searchParam.type=type;
	$.getJSON(actionUrl, searchParam,function(data){
		total= data.total;
		if(total <= 0){
			$("#grid").html('');
			$(".dataDiv").before('<div class="ajaxNoData" style="width:'+$(".dataDiv").width()+'px; height:'+$(".dataDiv").height()+'px; position:absolute; background-color:white;"><div style="text-align: center;">没有数据</div></div>');
			return;
		} else {
			$('.ajaxNoData').remove();
		}
		//alert(data.type);
		$("#type").val(data.type);
		titleArr = initTitleArr.concat();
		nameArr = initNameArr.concat();
		
		generateTable(sortArray(data.rows));
		if(typeof callback == 'function'){
			callback();
		}
		setTimeout("initData()", 15 * 1000);
	});
}

function generateTable(data){
	var html = "<thead>";
	html += "<th class='table-checkbox'><input type='checkbox' id='allChk' class='group-checkable' data-set='#myTable .checkboxes' /></th>";
	for(var i =0;i < titleArr.length; i++){
		html+="<th>"+titleArr[i]+"</th>";
	}
	if(delTd==true){
		html+="<th>&nbsp;</th>";
	}
	html+="</thead><tbody>";
	for(var i = 0 ; i < data.length; i++){
		var id = data[i].id;
		html+="<tr data_id="+id+" data_lastChatId="+data[i]['lastChatId']+" data_customerId="+data[i]['customerId'];
		if(data[i]['notReadNum'] > 0){
			html += " style='font-weight: 600;'";
		}
		html += ">";
		html+="<td><input type='checkbox' class='checkboxes' name='checkboxes' value="+data[i]['id']+" /></td>";
		for(var j = 0; j < nameArr.length; j++){
			
			if (nameArr[j].indexOf("fun_") == 0){
				var value = "";
				if(nameArr[j] == "fun_name"){
					value = getCustomerName(data[i]['name'], data[i]["nickName"], data[i]["customerId"]);
				}else if(nameArr[j] == "fun_lastestMsg"){
					value = getLastestMsg(data[i]['lastChatId'], data[i]["lastestMsg"]);
				}
				html+=value;
			} else if (nameArr[j].indexOf("time_") == 0){
				var value = "";
				value = getFormatTime(data[i][nameArr[j].substr(5)]);
				html+="<td>"+value+"</td>";
			} else if(data[i][nameArr[j]]==undefined){
				html+="<td>&nbsp;</td>";
			} else{
				html+="<td>"+data[i][nameArr[j]]+"</td>";
			}
		}
		
		html += "</td></tr>";
		
		if(delTd==true){
			html+="<td><a class='btn_red50'  href='javascript:deleteZB("+id+")'>删除</a></td></tr>";
		}
	}
	$("#grid").html(html);
	$("#allChk").click(function(){
		
		if(this.checked){
			$("#grid td input[type='checkbox']").each(function(){
				$(this).prop("checked", true);
			});
		} else {
			$("#grid td input[type='checkbox']").each(function(){
				$(this).prop("checked", false);
			});
		}
	});
	var pageNum = Math.floor(total / len);
	if(total % len > 0){
		pageNum +=1;
	}
	var selectHtml = "";
	for(var i = 1 ; i <= pageNum; i++){
		selectHtml+="<option value='"+i+"'>"+i+"</option>";
	}
	$("#toPageNum").html(selectHtml);
	var currentPage = (start / len)+1;
	$("#toPageNum").val(currentPage);
	$("#CPofAP").text("页次："+currentPage+"/"+pageNum+"页");
	$("#DataCount").text("共"+total+"条数据");
	$("#perPageNum").text("每页"+len+"条");
	if(currentPage==1){
		$("#prePageBtn").css("color","#A0A0A4");
	}
	if(currentPage>=pageNum){
		$("#nextPageBtn").css("color","#A0A0A4");
	} else if(currentPage<pageNum) {
		$("#nextPageBtn").css("color","#0000FF");
	}
	$("#grid tr").click(function(){
		$("#grid tr.companyClickColor").removeClass("companyClickColor");
		$(this).addClass("companyClickColor");
	});
}
function prePage(){
	if(start <= 0){
		alert("已经是第一页");
		$("#prePageBtn").css("color","#A0A0A4");
		return;
	}
	start-=len;
	initData();
	$("#nextPageBtn").css("color","#0000FF");
}
function nextPage(){
	if(start +len >= total){
		alert("已经是最后一页");
		$("#nextPageBtn").css("color","#A0A0A4");
		return;
	}
	start+=len;
	initData();
	$("#prePageBtn").css("color","#0000FF");
}
function toPage(){
	var newPage = $("#toPageNum").val();
	start = (newPage - 1) *len;
	initData();
	$("#toPageNum").val(newPage);
}

function getLastestMsg(chatId, lastestMsg){
	var html = '<td onclick="popChatWin('+chatId+')" style="cursor:pointer;">'+(lastestMsg?lastestMsg:'')+'</td>';
	return html;
}
var beforeId;
function getCustomerName(name, nickName,customerId){
	 var html = '<td >';
	 html=html+'<div id='+customerId+' style="display: none;width: 200px;background-color: #d1eeee;position: absolute; text-align: left; padding: .2em .5em; overflow: hidden; max-width: 300px;">';
	 html=html+'</div>';
	 html=html+'<span onmouseover="openCustomerDetail(this,'+customerId+')" onmouseout="hiddenCustomerDetail('+customerId+')">'+ (name? name : nickName)  +'</span>';
	 html=html+'</td>';
	 return html;
}
function popChatWin(chatId){
	CommonUtils.editModal(BASE_URL + "/chats/showChatWin.do?chatId="+chatId, 600, chatWinCallBackWhenOpen, chatWinCallBackWhenClose);
}
function openCustomerDetail(thisObj,customerId){
	this.hidden(beforeId);
	beforeId=customerId;
	var objDiv=$("#"+customerId);
	loadCus(customerId,objDiv);
	$(objDiv).css("display","block");
	$(objDiv).css("left",event.clientX+50);
	$(objDiv).css("top",event.clientY-190);
}
function loadCus(customerId,objDiv){
	$.ajax({  
        url : BASE_URL + "/chats/showCustomerDetail.do?customerId="+customerId,   
        dataType : 'json', 
		success : function(result) {
			 if(result.info=='success'){
				 var html = '<div >';
				 html=html+'<div class="popCustomerDetail"><img src='+result.customer.weChatAvatar+' width="50" height="50" >';
				 html=html+' 昵称：'+result.customer.nickName+'</div>';
				 html=html+'<div class="popCustomerDetail">姓名：'+result.customer.name+'</div>';
				 if(result.customer.goodsProvName!=null&&result.customer.goodsProvName!=""&&result.customer.goodsCityName!=null&&result.customer.goodsCityName!=""){
					 html=html+'<div class="popCustomerDetail">地区：'+result.customer.goodsProvName+'/'+result.customer.goodsCityName+'</div>'; 
				 }else if(result.customer.goodsProvName!=null&&result.customer.goodsProvName!=""&&(result.customer.goodsCityName==null||result.customer.goodsCityName=="")){
					 html=html+'<div class="popCustomerDetail">地区：'+result.customer.goodsProvName+'</div>'; 
				 }else if((result.customer.goodsProvName==null||result.customer.goodsProvName=="")&&(result.customer.goodsCityName==null||result.customer.goodsCityName=="")){
					 html=html+'<div class="popCustomerDetail">地区：</div>'; 
				 }
				 html=html+'<div class="popCustomerDetail">公司名：'+result.customer.companyName+'</div>';
				 html=html+'<div class="popCustomerDetail">手机：'+result.customer.phone+'</div>';
				
				 if(result.customer.itemName!=null&&result.customer.itemName!=''){
					 html=html+'<div class="popCustomerDetail">废品品种：'+ result.customer.itemName +'</div>';
				 }else{
					 html=html+'<div class="popCustomerDetail">废品品种：</div>';
				 }
				if(result.customer.categorybusiness==1){
					 html=html+'<div class="popCustomerDetail">交易类型：采购方</div>';
				}else if(result.customer.categorybusiness==2){
					 html=html+'<div class="popCustomerDetail">交易类型：供应商</div>';
				}else if(result.customer.categorybusiness==3){
					 html=html+'<div class="popCustomerDetail">交易类型：采购方,供应商</div>';
				}
				 html=html+'</div>';
				 $(objDiv).html(html);
			 }else{
				 CommonUtils.succModal("系统提示", "获取失败");
			 }
        },  
        error : function(data)//服务器响应失败处理函数  
        {  
        	console.log("请求失败");
        }  
    }); 
}
function hiddenCustomerDetail(customerId){
	setTimeout('hidden('+customerId+')',15000);
}
function hidden(id){
	$("#"+id).hide();
}
function chatWinCallBackWhenOpen(){
	var winChatId = $("#chatIdForCurrChat").val();
	var winCustomerId = $("#customerIdForCurrChat").val();
	Chats.sendOpenWinMessage(winCustomerId, winChatId);
	
	$("#grid tr").each(function(index, element){
		if($(this).attr("data_lastChatId") == winChatId){
			$(this).css("font-weight", "400");
		}
	});
	
	$("#xb_page_body").scrollTop($("#xb_page_body")[0].scrollHeight);
}
function chatWinCallBackWhenClose(){
	var chatId = $("#chatIdForCurrChat").val();
	var winCustomerId = $("#customerIdForCurrChat").val();
	Chats.sendCloseWinMessage(winCustomerId, chatId);
}

function getFormatTime(time){
	if(time == null || time == ""){
		return "";
	}
	return new Date(time).format("yyyy-MM-dd hh:mm:ss");
}
