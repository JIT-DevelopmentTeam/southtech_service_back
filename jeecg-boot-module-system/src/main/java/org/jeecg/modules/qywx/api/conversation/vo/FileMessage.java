package org.jeecg.modules.qywx.api.conversation.vo;

import com.jeecg.qywx.api.conversation.vo.BaseMessage;
import com.jeecg.qywx.api.conversation.vo.Media;
import com.jeecg.qywx.api.conversation.vo.Receiver;

/**
 * 文件消息
 * @author SunHaiFeng
 *
 */
public class FileMessage extends BaseMessage {

	private Media file;

	public FileMessage() {
		super.msgtype = "file";
	}

	public FileMessage(Receiver receiver, String sender, Media file) {
		super(receiver, sender, "file");
		this.file = file;
	}

	public Media getFile() {
		return file;
	}
	
	public void setFile(Media file) {
		this.file = file;
	}
	
}
