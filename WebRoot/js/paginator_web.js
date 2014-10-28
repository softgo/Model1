var pageBean = Class.create();
pageBean.prototype = {
	_url:null,
	_formName:null,
	//_pageSize:null,
	_dyndiv:null,
	initialize:function(url, formName, pageSize, navigationItemCount,dynamicSize,dyndiv){
		this._url = url;
		this._formName = formName;
		//this._pageSize = pageSize;
		this._dyndiv = dyndiv;
		$('pageSize').value = pageSize;	
		$('navigationItemCount').value = navigationItemCount;
		$('dynsize').value = dynamicSize;
	},
	
	first_page:function(){
		$('actType').value = "first_page";
		new Ajax.Updater(this._dyndiv, this._url,{
			evalScripts:true,		
			parameters:$(this._formName).serialize().toQueryParams()
		});
	},
	
	yest_page:function(){
		$('actType').value = "yest_page";
		new Ajax.Updater(this._dyndiv, this._url,{
			evalScripts:true,		
			parameters:$(this._formName).serialize().toQueryParams()
		});
	},
	
	next_page:function(){
		$('actType').value = "next_page";
		new Ajax.Updater(this._dyndiv, this._url,{
			evalScripts:true,		
			parameters:$(this._formName).serialize().toQueryParams()
		});
	},
	
	last_page:function(){
		$('actType').value = "last_page";
		new Ajax.Updater(this._dyndiv, this._url,{
			evalScripts:true,		
			parameters:$(this._formName).serialize().toQueryParams()
		});
	},
	
	goto_page:function(pageNo,pageCount){
		if(!is_int(pageNo))
		{
			alert('Input error!');
			$("pageNo").value = 1;
			return false;
		}
					
		if(!(pageNo >= 1))
		{
			alert('Input error!');
			$("pageNo").value = 1;
			return false;
		}
	
		pageNo = eval(pageNo);
	
		if(pageNo > pageCount)
		{
			alert('Input error!');
			$("pageNo").value = 1;
			return false;
		}
		$("pageNo").value = pageNo;
		$('actType').value = "goto_page";
		new Ajax.Updater(this._dyndiv, this._url,{
			evalScripts:true,		
			parameters:$(this._formName).serialize().toQueryParams()
		});
	},
	
	search_page:function(){
		$('actType').value = "search_page";
		$('actionType').value = "none";
		$('listSQL').value = "none";
		new Ajax.Updater(this._dyndiv, this._url,{
			evalScripts:true,		
			parameters:$(this._formName).serialize().toQueryParams()
		});
	}, 

	research_page:function(){
		$('actType').value = "search_page";
		$('actionType').value = "count";
		new Ajax.Updater(this._dyndiv, this._url,{
			evalScripts:true,		
			parameters:$(this._formName).serialize().toQueryParams()
		});
	}
}
