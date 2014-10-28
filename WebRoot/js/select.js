/**
* 全选
* chkName checkbox的name
*/
function choiceAll(chkName)
{
	$("[name='"+chkName+"']").attr("checked",true);//全选
}

/**
* 反选
* chkName checkbox的name
*/
function choiceReverse(chkName)
{
	$.each( $("[name='"+chkName+"']"), //循环每个checkbox
        function(){ 
						$(this).attr("checked",!$(this).attr("checked"));
        }
  ); 
}
function choiceOrReverse(clickName,chkName){
   if($("[name='"+clickName+"']").attr("checked")==true){
    $("[name='"+chkName+"']").attr("checked",true);//全选
    }else{
    $("[name='"+chkName+"']").attr("checked",false);//全选
    }
}

/**
* 得到id串
* chkName checkbox的name
*/
function getChoiceParam(chkName)
{
    var tempSel = "",checkboxIdStr = "";
    $.each( $("[name='"+chkName+"']"), //循环每个checkbox
        function(){ 
            if($(this).attr("checked")==true)
            {
                tempSel = tempSel + $(this).val() + ",";			    
                checkboxIdStr = tempSel.substring(0,tempSel.length-1);	
            }
        }
    ); 
    
    return checkboxIdStr;
}