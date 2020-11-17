package org.jeecg.modules.qywx.api.conversation.vo;

import com.jeecg.qywx.api.conversation.vo.BaseMessage;
import com.jeecg.qywx.api.conversation.vo.Media;
import com.jeecg.qywx.api.conversation.vo.Receiver;

public class VoiceMessage extends BaseMessage {

	private Media voice;

	public VoiceMessage() {
		// TODO Auto-generated constructor stub
	}

	public VoiceMessage(Receiver receiver, String sender, Media voice) {
		super(receiver, sender, "voice");
		this.voice= voice;
	}



	public Media getVoice() {
		return voice;
	}
	
	public void setVoice(Media voice) {
		this.voice = voice;
	}
	
	
	
}
