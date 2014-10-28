/*

Cross-Browser XMLHttpRequest v1.1
=================================

Emulate Gecko 'XMLHttpRequest()' functionality in IE and Opera. Opera requires
the Sun Java Runtime Environment <http://www.java.com/>.

by Andrew Gregory
http://www.scss.com.au/family/andrew/webdesign/xmlhttprequest/

This work is licensed under the Creative Commons Attribution License. To view a
copy of this license, visit http://creativecommons.org/licenses/by/1.0/ or send
a letter to Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305,
USA.

Not Supported in Opera
----------------------
* user/password authentication
* responseXML data member

Not Fully Supported in Opera
----------------------------
* async requests
* abort()
* getAllResponseHeaders(), getAllResponseHeader(header)

*/
// IE support
<!--
if (window.ActiveXObject && !window.XMLHttpRequest) {
  window.XMLHttpRequest = function() {
    return new ActiveXObject((navigator.userAgent.toLowerCase().indexOf('msie 5') != -1) ? 'Microsoft.XMLHTTP' : 'Msxml2.XMLHTTP');
  };
}
// Gecko support
/* ;-) */
// Opera support
if (window.opera && !window.XMLHttpRequest) {
  window.XMLHttpRequest = function() {
    this.readyState = 0; // 0=uninitialized,1=loading,2=loaded,3=interactive,4=complete
    this.status = 0; // HTTP status codes
    this.statusText = '';
    this._headers = [];
    this._aborted = false;
    this._async = true;
    this.abort = function() {
      this._aborted = true;
    };
    this.getAllResponseHeaders = function() {
      return this.getAllResponseHeader('*');
    };
    this.getAllResponseHeader = function(header) {
      var ret = '';
      for (var i = 0; i < this._headers.length; i++) {
        if (header == '*' || this._headers[i].h == header) {
          ret += this._headers[i].h + ': ' + this._headers[i].v + '\n';
        }
      }
      return ret;
    };
    this.setRequestHeader = function(header, value) {
      this._headers[this._headers.length] = {h:header, v:value};
    };
    this.open = function(method, url, async, user, password) {
      this.method = method;
      this.url = url;
      this._async = true;
      this._aborted = false;
      if (arguments.length >= 3) {
        this._async = async;
      }
      if (arguments.length > 3) {
        // user/password support requires a custom Authenticator class
        opera.postError('XMLHttpRequest.open() - user/password not supported');
      }
      this._headers = [];
      this.readyState = 1;
      if (this.onreadystatechange) {
        this.onreadystatechange();
      }
    };
    this.send = function(data) {
      if (!navigator.javaEnabled()) {
        alert("XMLHttpRequest.send() - Java must be installed and enabled.");
        return;
      }
      if (this._async) {
        setTimeout(this._sendasync, 0, this, data);
        // this is not really asynchronous and won't execute until the current
        // execution context ends
      } else {
        this._sendsync(data);
      }
    }
    this._sendasync = function(req, data) {
      if (!req._aborted) {
        req._sendsync(data);
      }
    };
    this._sendsync = function(data) {
      this.readyState = 2;
      if (this.onreadystatechange) {
        this.onreadystatechange();
      }
      // open connection
      var url = new java.net.URL(new java.net.URL(window.location.href), this.url);
      var conn = url.openConnection();
      for (var i = 0; i < this._headers.length; i++) {
        conn.setRequestProperty(this._headers[i].h, this._headers[i].v);
      }
      this._headers = [];
      if (this.method == 'POST') {
        // POST data
        conn.setDoOutput(true);
        var wr = new java.io.OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
        wr.close();
      }
      // read response headers
      // NOTE: the getHeaderField() methods always return nulls for me :(
      var gotContentEncoding = false;
      var gotContentLength = false;
      var gotContentType = false;
      var gotDate = false;
      var gotExpiration = false;
      var gotLastModified = false;
      for (var i = 0; ; i++) {
        var hdrName = conn.getHeaderFieldKey(i);
        var hdrValue = conn.getHeaderField(i);
        if (hdrName == null && hdrValue == null) {
          break;
        }
        if (hdrName != null) {
          this._headers[this._headers.length] = {h:hdrName, v:hdrValue};
          switch (hdrName.toLowerCase()) {
            case 'content-encoding': gotContentEncoding = true; break;
            case 'content-length'  : gotContentLength   = true; break;
            case 'content-type'    : gotContentType     = true; break;
            case 'date'            : gotDate            = true; break;
            case 'expires'         : gotExpiration      = true; break;
            case 'last-modified'   : gotLastModified    = true; break;
          }
        }
      }
      // try to fill in any missing header information
      var val;
      val = conn.getContentEncoding();
      if (val != null && !gotContentEncoding) this._headers[this._headers.length] = {h:'Content-encoding', v:val};
      val = conn.getContentLength();
      if (val != -1 && !gotContentLength) this._headers[this._headers.length] = {h:'Content-length', v:val};
      val = conn.getContentType();
      if (val != null && !gotContentType) this._headers[this._headers.length] = {h:'Content-type', v:val};
      val = conn.getDate();
      if (val != 0 && !gotDate) this._headers[this._headers.length] = {h:'Date', v:(new Date(val)).toUTCString()};
      val = conn.getExpiration();
      if (val != 0 && !gotExpiration) this._headers[this._headers.length] = {h:'Expires', v:(new Date(val)).toUTCString()};
      val = conn.getLastModified();
      if (val != 0 && !gotLastModified) this._headers[this._headers.length] = {h:'Last-modified', v:(new Date(val)).toUTCString()};
      // read response data
      var reqdata = '';
      var stream = conn.getInputStream();
      if (stream) {
        var reader = new java.io.BufferedReader(new java.io.InputStreamReader(stream));
        var line;
        while ((line = reader.readLine()) != null) {
          if (this.readyState == 2) {
            this.readyState = 3;
            if (this.onreadystatechange) {
              this.onreadystatechange();
            }
          }
          reqdata += line + '\n';
        }
        reader.close();
        this.status = 200;
        this.statusText = 'OK';
        this.responseText = reqdata;
        this.readyState = 4;
        if (this.onreadystatechange) {
          this.onreadystatechange();
        }
        if (this.onload) {
          this.onload();
        }
      } else {
        // error
        this.status = 404;
        this.statusText = 'Not Found';
        this.responseText = '';
        this.readyState = 4;
        if (this.onreadystatechange) {
          this.onreadystatechange();
        }
        if (this.onerror) {
          this.onerror();
        }
      }
    };
  };
}
// ActiveXObject emulation
if (!window.ActiveXObject && window.XMLHttpRequest) {
  window.ActiveXObject = function(type) {
    switch (type.toLowerCase()) {
      case 'microsoft.xmlhttp':
      case 'msxml2.xmlhttp':
        return new XMLHttpRequest();
    }
    return null;
  };
}

function $() {
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }

  return elements;
}

var BBSXml = function(){}

BBSXml.prototype.GetHttpRequest = function()
{
  return new XMLHttpRequest();
}

BBSXml.prototype.GetUrlText = function( urlToCall, asyncFunctionPointer )
{
  var oBBSXml = this ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "GET", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
	{
	  if ( oXmlHttp.readyState == 4 ) {
	    if ( oXmlHttp.status == 200 ) {
		  asyncFunctionPointer( oXmlHttp.responseText ) ;
		}
		else {
		  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
		}
	  }
	}
  }
  oXmlHttp.send( null ) ;
  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}

BBSXml.prototype.GetUrlText = function( urlToCall, asyncFunctionPointer, asyncErrorFunctionPointer )
{
  var oBBSXml = this ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "GET", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
	{
	  if ( oXmlHttp.readyState == 4 ) {
	    if ( oXmlHttp.status == 200 ) {
		  asyncFunctionPointer( oXmlHttp.responseText ) ;
		}
		else {
		  asyncErrorFunctionPointer() ;
		}
	  }
	}
  }
  oXmlHttp.send( null ) ;
  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  asyncErrorFunctionPointer() ;
	}
  }
}

BBSXml.prototype.PostUrlText = function( urlToCall, data, asyncFunctionPointer )
{
  var oBBSXml = this ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "POST", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
	{
	  if ( oXmlHttp.readyState == 4 ) {
	    if ( oXmlHttp.status == 200 ) {
		  asyncFunctionPointer( oXmlHttp.responseText ) ;
		}
		else {
		  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
		}
	  }
	}
  }
  oXmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  oXmlHttp.send( data ) ;
  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}

BBSXml.prototype.GetUrlXml = function( urlToCall, asyncFunctionPointer )
{
  var oBBSXml = this ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "GET", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
    {
      if ( oXmlHttp.readyState == 4 ) {
        if ( oXmlHttp.status == 200 ) {
          asyncFunctionPointer( oXmlHttp.responseXML ) ;
        }
        else {
          alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
        }
      }
    }
  }
  oXmlHttp.send( null ) ;

  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}

BBSXml.prototype.PostUrlXml = function( urlToCall, data, asyncFunctionPointer )
{
  var oBBSXml = this ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "POST", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
	{
	  if ( oXmlHttp.readyState == 4 ) {
	    if ( oXmlHttp.status == 200 ) {
		  asyncFunctionPointer( oXmlHttp.responseXML ) ;
		}
		else {
		  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
		}
	  }
	}
  }
  oXmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  oXmlHttp.send( data ) ;
  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}

//--BBSXml with Object
BBSXml.prototype.GetUrlTextInObj = function( urlToCall, asyncFunctionPointer )
{
  var oBBSXml = this ;
  //var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'object' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "GET", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
	{
	  if ( oXmlHttp.readyState == 4 ) {
	    if ( oXmlHttp.status == 200 ) {
		  //asyncFunctionPointer( oXmlHttp.responseText ) ;
                  asyncFunctionPointer.execute( oXmlHttp.responseText ) ;
		}
		else {
		  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
		}
	  }
	}
  }
  oXmlHttp.send( null ) ;
  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}

BBSXml.prototype.PostUrlTextInObj = function( urlToCall, data, asyncFunctionPointer )
{
  var oBBSXml = this ;
  //var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'object' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "POST", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
	{
	  if ( oXmlHttp.readyState == 4 ) {
	    if ( oXmlHttp.status == 200 ) {
		  //asyncFunctionPointer( oXmlHttp.responseText ) ;
                  asyncFunctionPointer.execute( oXmlHttp.responseText ) ;
		}
		else {
		  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
		}
	  }
	}
  }
  oXmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  oXmlHttp.send( data ) ;
  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}

BBSXml.prototype.GetUrlXmlInObj = function( urlToCall, asyncFunctionPointer )
{
  var oBBSXml = this ;
  //var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'object' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "GET", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
    {
      if ( oXmlHttp.readyState == 4 ) {
        if ( oXmlHttp.status == 200 ) {
          //asyncFunctionPointer( oXmlHttp.responseXML ) ;
          asyncFunctionPointer.execute( oXmlHttp.responseXML ) ;
        }
        else {
          alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
        }
      }
    }
  }
  oXmlHttp.send( null ) ;

  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}

BBSXml.prototype.PostUrlXmlInObj = function( urlToCall, data, asyncFunctionPointer )
{
  var oBBSXml = this ;
  //var bAsync = ( typeof(asyncFunctionPointer) == 'function' ) ;
  var bAsync = ( typeof(asyncFunctionPointer) == 'object' ) ;
  var oXmlHttp = this.GetHttpRequest() ;
  oXmlHttp.open( "POST", urlToCall, bAsync ) ;
  if ( bAsync ) {
    oXmlHttp.onreadystatechange = function()
	{
	  if ( oXmlHttp.readyState == 4 ) {
	    if ( oXmlHttp.status == 200 ) {
                  asyncFunctionPointer.execute( oXmlHttp.responseXML ) ;
		}
		else {
		  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
		}
	  }
	}
  }
  oXmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  oXmlHttp.send( data ) ;
  if ( ! bAsync ) {
    if ( oXmlHttp.status == 200 ) {
	  this.DOMDocument = oXmlHttp.responseXML ;
	}
	else {
	  alert( 'XML request error: ' + oXmlHttp.statusText + ' (' + oXmlHttp.status + ')' ) ;
	}
  }
}
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

var req;

function executeXhrOfPost(callback, url,data) {
  req = new XMLHttpRequest();
  if (req) {
    req.onreadystatechange = callback;
    req.open("POST", url, true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(data);
  }
}

function executeXhrOfGet(callback, url) {
  req = new XMLHttpRequest();
  if (req) {
    req.onreadystatechange = callback;
    req.open("GET", url, true);
    req.send();
  }
}

function getResponseXMLMsgs(responseXML) {
  return responseXML.getElementsByTagName("messages")[0];
}

function getResponseXMLMsgsCodeid(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  //alert(messages);
  return messages.childNodes[0].childNodes[0].nodeValue;
}

function getResponseXMLMsgsMessage(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  //alert(messages);
  return messages.childNodes[1].childNodes[0].nodeValue;
}

function getResponseXMLMsgsText(responseXML) {
  var messages = getResponseXMLMsgs(responseXML);
  //alert(messages);
  return messages.childNodes[2].childNodes[0].nodeValue;
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

function getExtName(fileName){
    if(fileName.lastIndexOf(".")<0) return "";
    return fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
}
//-->
