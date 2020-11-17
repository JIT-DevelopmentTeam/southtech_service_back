package org.jeecg.modules.qywx.api.conversation.vo;

import com.jeecg.qywx.api.conversation.vo.BaseMessage;
import com.jeecg.qywx.api.conversation.vo.Link;
import com.jeecg.qywx.api.conversation.vo.Receiver;

public class LinkMessage extends BaseMessage {

	private Link link;

	public LinkMessage() {
		// TODO Auto-generated constructor stub
	}

	public LinkMessage(Receiver receiver, String sender, Link link) {
		super(receiver, sender, "link");
		this.link= link;
	}
	
	public Link getLink() {
		return link;
	}
	
	public void setLink(Link link) {
		this.link = link;
	}
}
