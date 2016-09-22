var Chats = {
	wsGlobal : {},
	
	init : function(employeeId){
		Chats.wsGlobal = Chats.connect(employeeId);
	},
	getEmployeeMessageInstance : function(customerId, chatId, type, subType, content, lastestTime, len){
		var message = {};
		message.customerId=customerId;
		message.chatId=chatId;
		message.type=type;
		message.subType=subType;
		message.content=content;
		message.lastestTime=lastestTime;
		message.len=len;
		return message;
	},
	connect : function(employeeId) {
		urlPath = BASE_URL + "/chatConnect.do?chatInitiator=U&employeeId="+employeeId;
		url = 'ws://' + window.location.host + urlPath;
		console.log("url : "+url);
		
	    ws = new WebSocket(url);
	    
	    ws.onopen = function () {
	    	Chats.setConnected(true);
	    	Chats.log('Info: connection opened.');
	    };
	    ws.onmessage = function (event) {
	    	var mainPageFrame = window.parent.frames['mainPageFrame'];
	    	//var chatId = $(mainPageFrame.document).find("#chatIdForCurrChat");
	    	if(mainPageFrame){
	    		var jsonMsg = JSON.parse(event.data);
	    		if(Chats.isArray(jsonMsg)){ // 历史消息
	    			if(jsonMsg.length){
	    				mainPageFrame.prependHistoryMessage(jsonMsg);
	    			}
	    		} else {
	    			if(jsonMsg.type == 0 && jsonMsg.subType == 0){ // 普通消息
	    				mainPageFrame.appendMessage('C', jsonMsg.content);
	    			} else if (jsonMsg.type = 1 && jsonMsg.subType == 2){ // 未读提醒消息
	    				var notReadList = JSON.parse(jsonMsg.content);
	    				var totalNotRead = 0;
	    				for(var i =0;i < notReadList.length; i++){
	    					totalNotRead += notReadList[i]['num'];
	    				}
	    				var headerNotifiBar =$(mainPageFrame.document).find("#header_notification_bar"); 
	    				headerNotifiBar.find("span.badge").text(totalNotRead);
	    				headerNotifiBar.find(".external strong").text(totalNotRead);
	    				if(totalNotRead > 0){
	    					headerNotifiBar.find("#chatRemindImg").attr("src",BASE_URL + "/Public/images/chats/chatRemindBlink.gif");
	    				} else {
	    					headerNotifiBar.find("#chatRemindImg").attr("src",BASE_URL +  "/Public/images/chats/msgRemind.png");
	    				}
	    			} else if(jsonMsg.type = 1 && jsonMsg.subType == 3) {
	    				var orderMsg = JSON.parse(jsonMsg.content);
	    				mainPageFrame.appendMessage('U', "<a style=\"text-decoration: underline;\" href=\"javascript:orders_details("+orderMsg.id+")\">订单已生成</a>");
	    			} else if (jsonMsg.type = 2 && jsonMsg.subType == 0) {
	    				$(mainPageFrame.document).find("#chatIdForCurrChat").val(jsonMsg.chatId);
	    				var infoTitleMsg = JSON.parse(jsonMsg.content);
	    				mainPageFrame.appendInfoTitle(infoTitleMsg.source, infoTitleMsg.sourceId, infoTitleMsg.infoTitle);
	    			}
	    		}
	    	}
	    	Chats.log('Received: ' + event.data);
	    };
	    ws.onclose = function (event) {
	         Chats.setConnected(false);
	         Chats.log('Info: connection closed.');
	         Chats.log(event);
	    };
	    ws.onerror = function(event) {
	    	Chats.setConnected(false);
	    	Chats.log('Info: connection error.');
	    	Chats.log(event);
        };
        
	    return ws;
	},
	sendPlainTextMessage : function(customerId, chatId, message){
		if(Chats.wsGlobal && Chats.wsGlobal.readyState == Chats.wsGlobal.OPEN){
			Chats.wsGlobal.send(JSON.stringify(Chats.getEmployeeMessageInstance(customerId, chatId, 0, 0, message)));
			return true;
		} else {
			return false;
		}
	},
	sendImgMessage : function(customerId, chatId, message){
		if(Chats.wsGlobal && Chats.wsGlobal.readyState == Chats.wsGlobal.OPEN){
			Chats.wsGlobal.send(JSON.stringify(Chats.getEmployeeMessageInstance(customerId, chatId, 0, 2, message)));
			return true;
		} else {
			return false;
		}
	},
	sendOrderRemindMessage : function(customerId, chatId, content){
		debugger;
		Chats.wsGlobal.send(JSON.stringify(Chats.getEmployeeMessageInstance(customerId, chatId, 1, 3, content)));
	},
	sendOpenWinMessage : function(customerId, chatId){
		Chats.wsGlobal.send(JSON.stringify(Chats.getEmployeeMessageInstance(customerId, chatId, 1, 0)));
	},
	sendCloseWinMessage : function(customerId, chatId){
		Chats.wsGlobal.send(JSON.stringify(Chats.getEmployeeMessageInstance(customerId, chatId, 1, 1)));
	},
	getHistoryMessage : function(customerId, chatId, lastestTime, len){
		Chats.wsGlobal.send(JSON.stringify(Chats.getEmployeeMessageInstance(customerId, chatId, 0, 1, '', lastestTime, len)));
	},
	setConnected : function(connected) {
		console.log("connected : " + connected);
	},
	disconnect : function() {
    	wsGlobal.close();
    	wsGlobal = null;
	    Chats.setConnected(false);
	},
	
	log : function(message) {
	    console.log(message);
	    //console.scrollTop = console.scrollHeight;
	},
	isArray : function(o) {
	    return Object.prototype.toString.call(o) === '[object Array]';
	}
};
