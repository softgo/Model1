/**
 * 2009-8-18
 */
package com.bruce.gogo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

/**
 * @author wangguan
 *
 */
public class MyProgressListener implements ProgressListener {

    private HttpSession session;

    public MyProgressListener(HttpServletRequest req) {
        session=req.getSession();
        FileUploadStatus status = new FileUploadStatus();
        session.setAttribute("status", status);
    }

    /* pBytesRead  到目前为止读取文件的比特数
     * pContentLength 文件总大小
     * pItems 目前正在读取第几个文件
     * 只要在session中实时保存文件上传的状态（这里我用fileUploadStatus类来封装）
     */
    public void update(long pBytesRead, long pContentLength, int pItems) {
        // TODO Auto-generated method stub
        FileUploadStatus status = (FileUploadStatus) session.getAttribute("status");
        status.setBytesRead(pBytesRead);
        status.setContentLength(pContentLength);
        status.setItems(pItems);
    }
}
