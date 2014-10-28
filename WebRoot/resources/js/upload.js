/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/
function refreshProgress()
{
    UploadMonitor.getUploadInfo(updateProgress);
}

function updateProgress(FileUploadStatus)
{
/*    if (FileUploadStatus.inProgress)
    {
*/
        document.getElementById('uploadbutton').disabled = true;
        document.getElementById('videoimage').disabled = true;
        document.getElementById('videofile').disabled = true;
//        document.getElementById('file3').disabled = true;
//        document.getElementById('file4').disabled = true;

        //var fileIndex = FileUploadStatus.fileIndex;
        //alert(FileUploadStatus);
        //alert(FileUploadStatus.pContentLength == undefined);
        //alert(FileUploadStatus.pBytesRead);
        var progressPercent = 0;
		if(FileUploadStatus.contentLength != undefined && FileUploadStatus.contentLength != 0){
	        progressPercent = Math.ceil((FileUploadStatus.bytesRead / FileUploadStatus.contentLength) * 100);
	
	        document.getElementById('progressBarText').innerHTML = '上传进度: ' + progressPercent + '%';
	
	        document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px'; 		
		}

		if(progressPercent != 100){
			window.setTimeout('refreshProgress()', 1000);
		}
	       		

/*    }
    else
    {
        document.getElementById('uploadbutton').disabled = false;
        document.getElementById('videoimage').disabled = false;
        document.getElementById('videofile').disabled = false;
        document.getElementById('file3').disabled = false;
        document.getElementById('file4').disabled = false;
    }
*/
    return true;
}

function startProgress()
{
    document.getElementById('progressBar').style.display = 'block';
    document.getElementById('progressBarText').innerHTML = '上传进度: 0%';
    document.getElementById('uploadbutton').disabled = true;

    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress()", 1500);
    return true;
}
