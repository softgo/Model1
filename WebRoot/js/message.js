function message_check(){
	var name = document.getElementById("name");
	var title = document.getElementById("title");
	var content = document.getElementById("content");
	if(!isEmpty(name.value)){
		alert("请填写姓名");
		name.focus();
		return false;
	}
	if(!isEmpty(title.value)){
		alert("请填写留言主题");
		title.focus();
		return false;
	}
	if(!isEmpty(content.value)){
		alert("请填写留言内容");
		content.focus();
		return false;
	}
	if(content.value.length > 3000){
		alert("留言内容长度最大为3000");
		content.focus();
		return false;
	}
	return true;
}
function changeimage(){
	document.message.showimages.src = document.message.image.value;
}