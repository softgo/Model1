function copyToList(from,to) //from??????:????????????????????????select???????????? to??????:????????????????????????select???????????? //???????????????????????????????????????
{
  fromList = document.getElementById(from);//$("[name='" + from + "']");//eval('document.' + from);
  toList = document.getElementById(to);//$("[name='" + to + "']");
  if (toList.options.length > 0 && toList.options[0].value == 'temp')
  {
    toList.options.length = 0;
  }
  //var sel = false;
  for (i=0;i<fromList.options.length;i++)
  {
    var current = fromList.options[i];
    if (current.selected)
    {
      //sel = true;
      if (current.value == 'temp')
      {
        alert ('你不能选择这个选项！');
        return;
      }
      txt = current.text;
      val = current.value;
      
      //?????????????????????????????????????????????id???????????????
      var ret = true;
      for(j=0;j<toList.options.length;j++)
      {
      	
      	if(val == toList.options[j].value)
      		ret = false;
      }
      
      
      if(ret)
      {
      	toList.options[toList.length] = new Option(txt,val);      
      }


      fromList.options[i] = null;
      i--;
    }
  }
}


function copyAll(from,to)
{
  //fromList = eval('document.' + from);
  //toList = eval('document.' + to);
  fromList = document.getElementById(from);
  toList = document.getElementById(to);
  if (toList.options.length > 0 && toList.options[0].value == 'temp')
  {
    toList.options.length = 0;
  }
  //var sel = false;
  for (i=0;i<fromList.options.length;i++)
  {
    var current = fromList.options[i];

      //sel = true;
      if (current.value == 'temp')
      {
        alert ('你不能选择这个选项！');
        return;
      }
      txt = current.text;
      val = current.value;
      
      var ret = true;
      for(j=0;j<toList.options.length;j++)
      {
      	if(val == toList.options[j].value)
      	{
      		ret = false;
      	}
      }


      if(ret)
      {
      	toList.options[toList.length] = new Option(txt,val);      
      }
      
      fromList.options[i] = null;
      i--;
  }
}

/*function allSelect(zheForm) //?????????????????????????????????????????????????????????select???????????????????????????????????????????????????????????????????????????
{
  List = eval('document.' + zheForm);
  if (List.length && List.options[0].value == 'temp') return;
  for (i=0;i<List.length;i++)
  {
     List.options[i].selected = true;
  }
}


  function getChose(company, depart, position, user, usergroup, role, type, formName, name, hiddenName, choseType, companyid)
  {
  	var ret = "";
  	if(company == '1')
  		ret = ret + "&companyRet=1"
  	else
  		ret = ret + "&companyRet=0"
  	
  	if(depart == '1')
  		ret = ret + "&departRet=1"
  	else
  		ret = ret + "&departRet=0"
  		
  	if(position == '1')
  		ret = ret + "&positionRet=1" 
  	else
  	  	ret = ret + "&positionRet=0"  
  	  		 		  
  	if(user == '1')
  		ret = ret + "&userRet=1" 
  	else
  		ret = ret + "&userRet=0"   	
  		
  	if(usergroup == '1')
  		ret = ret + "&usergroupRet=1"  
  	else
  		ret = ret + "&usergroupRet=0" 	
  		  		
  	if(role == '1')
  		ret = ret + "&roleRet=1"  	
  	else
  		ret = ret + "&roleRet=0"  	  	
	  		

  	var hidden = document.forms[formName].elements[hiddenName].value;
  	var choseTypeValue = document.forms[formName].elements[choseType].value;
  	window.open("choseuaction!getChose.xml?name="+name+"&hidden="+hidden+"&type="+type+"&hiddenName="+hiddenName+"&formName="+formName+"&choseType="+choseType+"&companyid="+companyid+"&choseTypeValue="+choseTypeValue+ret,"getChose","width=800,height=600,scrollbars=yes,resizable=yes");
  } */

