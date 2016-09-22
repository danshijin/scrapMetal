$(function(){
	//初始化页面数据
	(function(){
		if(company.name.length > 9){
			$("#companyNameTitle").text(company.name.substr(0,9) + "...");
		} else {
			$("#companyNameTitle").text(company.name);
		}
		
		$("#createdAt").text(company.createdAt);
		$("#createdBy").text(company.createdBy);
		//负责人$("#").text(company.);
		$("#categorySource").attr("category",company.categorySource);
		$("#categorySource").text(company.source);
		$("#name").text(company.name);
		$("#salesProducts").text(company.salesProducts);
		$("#salesProducts").text(company.salesProducts);
		$("#categoryCoo").attr("category", company.coo);
		$("#categoryCoo").text(company.coo);
		$("#province").text(company.province);
		
		
		var html = "";
		for(var i = 0, arr=selItems.企业类型; i < arr.length; i++){
			if(company.entTypesText != null && company.entTypesText != "" && company.entTypesText.indexOf(arr[i].name) >-1){
				html += '<label><input type="checkbox" value="'+arr[i].id+'" txt="'+arr[i].name+'" checked="checked" />'+arr[i].name+'&nbsp;</label>';
			} else {
				html += '<label><input type="checkbox" value="'+arr[i].id+'" txt="'+arr[i].name+'"  />'+arr[i].name+'&nbsp;</label>';
			}
		}
		
		$("#entTypes").html(html);
		
		$("#entTypes input:checkbox").each(function(index, element){
			$(element).prop("disabled", true);
		});
		
		$("#address").text(company.address);
		$("#salesVolume").text(company.salesVolume);
		$("#categoryEmployee").attr("category",company.categoryEmployee);
		$("#categoryEmployee").text(company.employee);
		
		$("#corporate").text(company.corporate);
		$("#registerDate").text(company.registerDate);
		$("#companyPhone").text(company.companyPhone);
		$("#companyProperty").attr("category", company.companyProperty);
		$("#companyProperty").text(company.property);
		$("#companySite").html('<a href = "'+company.companySite+'" target="_blank">'+company.companySite+"</a>");
		
		$("#pic").text(company.pic);
		
		var contacters = company.contacters;
		var ctText = "";
		for(var i =0; i < contacters.length; i++){
			if(i >0){
				ctText +=",";
			}
			ctText += contacters[i].name;
		}
		$("#contacters").text(ctText); //联系人
		$("#companyAss").text(company.companyAss);
		if(company.primaryPurchaseContacts != null){
			$("#primaryPurchaseContacter").text(company.primaryPurchaseContacts.name);//首要采购联系人
		}
		$("#buyProducts").text(company.buyProducts);
		$("#categoryFreq").attr("category", company.categoryFreq);
		$("#categoryFreq").text(company.freq);
		//上次采购时间$("#").text(company.);
		$("#commands").text(company.commands);
	})();
	
	//将页面选项变为可编辑状态
	$("#infoUpdate").click(function(){
		//来源
		/*var html = "";
		html += '<select id="source">';
		html += '<option value="1">网站注册</option>';
		html += '<option value="2">QQ群</option>';
		html += '<option value="3">网络营销</option>';
		html += '<option value="4">公开媒体</option>';
		html += '<option value="5">合作伙伴</option>';
		html += '</select>';
		$("#categorySource").html(html);
		$("#source").val(company.categorySource);*/
		//公司名
		$("#name").html('<input type="text" value="'+company.name+'" />');
		$("#salesProducts").html('<input type="text" value="'+company.salesProducts+'" />');
		//客户级别
		/*html = "";
		html += '<select id="coo">';
		html += '<option value="14">无</option>';
		html += '<option value="15">未合作</option>';
		html += '<option value="16">有过合作</option>';
		html += '<option value="17">长期合作</option>';
		html += '<option value="18">意愿强烈</option>';
		html += '</select>';
		$("#categoryCoo").html(html);
		$("#coo").val(company.categoryCoo);*/
		
		$("#entTypes input:checkbox").each(function(index, element){
			$(element).prop("disabled", false);
		});
		
		$("#address").html('<input type="text" value="'+company.address+'" />');
		$("#salesVolume").html('<input type="text" class="number" value="'+company.salesVolume+'" />');
		//员工人数
		html = "";
		html += '<select id="employee">';
		for(var i = 0, arr=selItems.员工人数; i < arr.length; i++){
			html += '<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
		}
		html += '</select>';
		$("#categoryEmployee").html(html);
		$("#employee").val(company.categoryEmployee);
		$("#corporate").html('<input type="text" value="'+company.corporate+'" />');
		$("#companyPhone").html('<input type="text" value="'+company.companyPhone+'" />');
		
		//企业性质
		html = "";
		html += '<select id="property">';
		for(var i = 0, arr=selItems.企业性质; i < arr.length; i++){
			html += '<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
		}
		html += '</select>';
		$("#companyProperty").html(html);
		$("#property").val(company.companyProperty);
		
		$("#companySite").html('<input type="text" value="'+company.companySite+'" />');
		$("#companyAss").html('<input type="text" value="'+company.companyAss+'" />');
		$("#buyProducts").html('<input type="text" value="'+company.buyProducts+'" />');
		//采购周期
		html = "";
		html += '<select id="freq">';
		for(var i = 0, arr=selItems.采购周期; i < arr.length; i++){
			html += '<option value="'+arr[i].id+'">'+arr[i].name+'</option>';
		}
		html += '</select>';
		$("#categoryFreq").html(html);
		$("#freq").val(company.categoryFreq);
		$("#commands").html('<textarea type="text" cols =130 rows=3>'+company.commands+'</textarea>');
		
		$("#infoUpdate").css("display","none");
		$("#infoSave").css("display", "inline-block");
		$("#frm1").validate();
	});
	
	//提交修改的数据
	$("#infoSave").click(function(){
		
		var uCompany = {};
		
		uCompany.id = company.id;
		
		//uCompany.categorySource = $("#categorySource select").val();
		
		uCompany.name = $("#name input").val();
		
		uCompany.salesProducts = $("#salesProducts input").val();
		
		//uCompany.categoryCoo = $("#categoryCoo select").val();
		
		//TODO 省份
		
		var entVal="";
		$("#entTypes :checkbox:checked").each(function(index, element){
			if(index >0){
				entVal += ",";
			}
			entVal += $(element).val();
		});
		
		uCompany.entTypes = entVal;
		
		uCompany.address = $("#address input").val();
		
		uCompany.salesVolume = $("#salesVolume input").val();
		
		uCompany.categoryEmployee = $("#categoryEmployee select").val();
		
		uCompany.corporate = $("#corporate input").val();
		
		uCompany.companyPhone = $("#companyPhone input").val();
		
		uCompany.companyProperty = $("#companyProperty select").val();
		
		uCompany.companySite = $("#companySite input").val();
		
		uCompany.companyAss = $("#companyAss input").val();
		
		uCompany.buyProducts = $("#buyProducts input").val();
		
		uCompany.categoryFreq = $("#categoryFreq select").val();
		
		uCompany.commands = $("#commands textarea").val();
		
		$.post("updateCompanyInfo.do", uCompany, function(data){
			if(data =="success"){
				alert("保存成功");
				$("#infoSave").css("display","none");
				$("#infoUpdate").css("display", "inline-block");
				$("#companyInfoA").click();
			}
		});
	});
	
	
	$("#infoDel").click(function(){
		CommonUtils.confirm("确认","将删除该企业信息", "delCompany()");
	});
});
function delCompany(){
	$.get("deleteCompany.do",{"id":company.id},function(data){
		if(data == "success"){
			alert("删除成功");
		}
	});
}