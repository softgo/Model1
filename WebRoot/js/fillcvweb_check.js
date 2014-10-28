   function fillcv_check(){
       var name = document.getElementById("name");
       var origin = document.getElementById("origin");
       var school = document.getElementById("school");
       var professional = document.getElementById("professional");
       var political = document.getElementById("political");
       var birth = document.getElementById("birth");
       var phone = document.getElementById("phone");
       var address = document.getElementById("address");
       var email = document.getElementById("email");
       var oicq = document.getElementById("oicq");
       var homepage = document.getElementById("homepage");
       var description = document.getElementById("description");
       var evaluation = document.getElementById("evaluation");
     
       if(!isEmpty(name.value)){
	    alert("请填写姓名");
		name.focus();
		 return false;
	   }  
	   if(!isEmpty(origin.value)){
	    alert("请填写籍贯");
		origin.focus();
		 return false;
	   }
	  if(!isEmpty(political.value)){
	    alert("请填写政治面貌");
		political.focus();
		 return false;
	   }
	   if(!isEmpty(school.value)){
	    alert("请填写毕业院校");
		school.focus();
		 return false;
	   }
	   if(!isEmpty(professional.value)){
	    alert("请填写专业");
		professional.focus();
		 return false;
	   }	
	  if(!isEmpty(email.value)){
	    alert("请填写E-mail");
		name.focus();
		 return false;
	   }
	return true;
  }
  
  function isEmail( str ){  
	   	 myReg = "^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$"; 

	   	 var re = new RegExp(myReg);  
	   	 if(re.test(str))
	   	 { 
	   	 	 return true;
	   	 }
	   	 else
	   	 {
	   	 	return false;
	   	 }
	 }
/**   
    判断输入框中输入的日期格式为yyyy-mm-dd和正确的日期   
  */   
  function   IsDate(mystring)   {      
      var   reg   =   /^(\d{4})-(\d{2})-(\d{2})$/;   
      var   str   =   mystring;   
      var   arr   =   reg.exec(str);   
      if   (str=="")   return   true;   
      if   (!reg.test(str)&&RegExp.$2<=12&&RegExp.$3<=31){    
       // alert("请输入的日期格式为yyyy-mm-dd的日期!");          
        return   false;  
        }  
        return   true;  
  
    }   



  