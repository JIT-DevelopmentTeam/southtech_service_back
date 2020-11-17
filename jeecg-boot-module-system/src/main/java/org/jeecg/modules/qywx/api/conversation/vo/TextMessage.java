package org.jeecg.modules.qywx.api.conversation.vo;

import com.jeecg.qywx.api.conversation.vo.BaseMessage;
import com.jeecg.qywx.api.conversation.vo.Receiver;
import com.jeecg.qywx.api.conversation.vo.Text;

/**
 * 会话文本消息视图
 * @author SunHaiFeng
 *
 */
public class TextMessage extends BaseMessage {
	/**文本内容*/
	private Text text;

	public TextMessage() {
		super.msgtype = "text";
	}

	public TextMessage(Receiver receiver, String sender, Text text) {
		super(receiver, sender, "text");
		this.text = text;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
}
