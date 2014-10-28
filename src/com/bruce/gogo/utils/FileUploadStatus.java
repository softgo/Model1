/**
 * 2009-8-18
 */
package com.bruce.gogo.utils;

/**
 * @author wangguan
 *
 */
public class FileUploadStatus {
	private long bytesRead;
	private long contentLength;
	private int items;
	public long getBytesRead() {
		return bytesRead;
	}
	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}
	public long getContentLength() {
		return contentLength;
	}
	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}
	public int getItems() {
		return items;
	}
	public void setItems(int items) {
		this.items = items;
	}

}
