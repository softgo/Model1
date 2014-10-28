//--
function changeStyle(elementID,toStyle) {
  document.getElementById(elementID).className=toStyle;
}

function getRadioValue(formName,radioName) {
  var radios = eval("document."+formName+"."+radioName);
  for (var i = 0; i < radios.length; i++) {
    if (radios[i].checked) {
      return radios[i].value;
    }
  }
}

function getRadioValueByName(radioName) {
   var radioValues = document.getElementsByName(radioName);
   for (var i = 0; i < radioValues.length; i++) {
    if (radioValues[i].checked) {
      return radioValues[i].value;
    }
  }
}

function getCheckBoxValue(checkBoxName) {
  var checkBox = document.getElementById(checkBoxName);
  if (checkBox.checked) {
    return checkBox.value;
  }
  return "";
}

function getCheckBoxSingleValue(checkBoxId) {
  var checkBoxs = document.getElementsByName(checkBoxId);
  for (var i = 0; i < checkBoxs.length; i++) {
    if (checkBoxs[i].checked) {
      return checkBoxs[i].value;
    }
  }
  return "";
}

function getResponseXMLMsgs(responseXML) {
  return responseXML.getElementsByTagName("messages")[0];
}

function getResponseXMLMsgsCodeid(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  var x = messages.childNodes;
  for (i = 0; i < x.length; i++) {
    if (x[i].nodeName == "codeid") {
      return x[i].childNodes[0].nodeValue;
    }
  }
  return "E000";
}

function getResponseXMLMsgsMessage(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  var x = messages.childNodes;
  for (i = 0; i < x.length; i++) {
    if (x[i].nodeName == "message") {
      return x[i].childNodes[0].nodeValue;
    }
  }
  return "Error";
}

function getResponseXMLMsgsText(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  var x = messages.childNodes;
  for (i = 0; i < x.length; i++) {
    if (x[i].nodeName == "text") {
      return x[i].childNodes[0].nodeValue;
    }
  }
  return "";
}

function hiddenElement(elementName) {
  document.getElementById(elementName).style.display = "none";
}

function displayElement(elementName) {
  document.getElementById(elementName).style.display = "block";
}

function filterString(txt) {
  var re = /\+/;
  return txt.replace(re,"%2B");
}

function getElementValue(elementName) {
  return $(elementName).value;
}

function getElementFilterValue(elementName) {
  return filterString($(elementName).value);
}

function handleEnter(field, event) {
  var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
  if (keyCode == 13) {
    return false;
  }
  return true;
}

function startsWith(txt,tar) {
  if (txt.indexOf(tar) == 0) {
    return true;
  }
  else {
    return false;
  }
}

function endsWith(txt,tar) {
  var num = txt.lastIndexOf(tar);
  if ((num != -1) && (tar == txt.substring(num,txt.length))) {
    return true;
  }
  else {
    return false;
  }
}

function getActionMappingName(action) {
  var value = action;
  var question = action.indexOf("?");
  if (question >= 0) {
    value = value.substring(0, question);
  }

  var slash = value.lastIndexOf("/");
  var period = value.lastIndexOf(".");
  if ((period >= 0) && (period > slash)) {
    value = value.substring(0, period);
  }

  return startsWith(value,"/") ? value : ("/" + value);
}

function getActionMappingURL(action) {
  var value = contextPath;
  var servletMapping = servletMappingStr;
  var queryString;
  var question = action.indexOf("?");
  if (question >= 0) {
    queryString = action.substring(question);
  }

  var actionMapping = getActionMappingName(action);
  if (startsWith(servletMapping,"*.")) {
    value += actionMapping;
    value += servletMapping.substring(1);
  }
  else if (endsWith(servletMapping,"/*")) {
    value += servletMapping.substring(0, servletMapping.length - 2);
    value += actionMapping;
  }
  else if (servletMapping == "/") {
    value += actionMapping;
  }
  if (queryString != undefined) {
    value += queryString;
  }
  return value;
}

function getActionName(url) {
	var question = url.indexOf("?");
	if (question > 0) {
		return url.substring(0, question);
	}
	else {
		return url;
	}
}

function getActionPars(url) {
	var question = url.indexOf("?");
	if (question > 0) {
		return url.substring(question+1, url.length);
	}
	else {
		var d = new Date();
		var t = d.getTime();
		return "timestamp="+t;
	}
}

function getExtName(fileName){
    if(fileName.lastIndexOf(".")<0) return "";
    return fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
}

function showExeMsg() {
  var loade = document.getElementById("exeingdiv");
  if (loade == null) {
    var el = document.createElement('DIV');
    el.setAttribute("id","exeingdiv");
    el.className = "exeMsg";
    el.innerHTML = exeing;
    document.body.appendChild(el);
    loade = el;
  }

  loade.style.display = "block";
  loade.style.top = document.documentElement.scrollTop + 1 + "px";
}

function hiddenExeMsg() {
  var loade = document.getElementById("exeingdiv");
  if (loade != null) {
    loade.style.display = "none";
  }
}

function getResponseJsonMsgsCodeid(responseText) {
  var jsonObj = eval('(' + responseText + ')');
  return jsonObj.codeid;
}

var JsonMsgObj = function(responseText) {
  this.json = eval('(' + responseText + ')');
}

JsonMsgObj.prototype.getCodeid = function() {
  return this.json.codeid;
}

JsonMsgObj.prototype.getMessage = function() {
  return this.json.message;
}

JsonMsgObj.prototype.getText = function() {
  return this.json.text;
}

String.prototype.format = function() {
    var str = this;
    for(var i=0;i<arguments.length;i++) {
        var re = new RegExp('\\{' + (i) + '\\}','gm');
        str = str.replace(re, arguments[i]);
    }
    return str;
}
//-->
