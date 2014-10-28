/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/
package com.bruce.gogo.utils;

import uk.ltd.getahead.dwr.WebContextFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Original : plosson on 06-janv.-2006 12:19:08 - Last modified  by $Author: cvsuser $ on $Date: 2009/08/25 07:02:02 $
 * @version 1.0 - Rev. $Revision: 1.1 $
 */
public class UploadMonitor
{
    public FileUploadStatus getUploadInfo()
    {
        HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();

        if (req.getSession().getAttribute("status") != null)
            return (FileUploadStatus) req.getSession().getAttribute("status");
        else
            return new FileUploadStatus();
    }
}
