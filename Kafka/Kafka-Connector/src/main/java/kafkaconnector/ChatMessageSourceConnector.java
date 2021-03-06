package kafkaconnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.pircbotx.PircBotX;

import chatmessage.BotClient;

public class ChatMessageSourceConnector extends SourceConnector{

	private String kafkaTopic;
	private String username;
	private String oauth;
	private String channel;
	private String channelUsername;
	
	public String version() {
		return "1";
	}
	
	@Override
	public void start(Map<String, String> props) {
		kafkaTopic = props.get("topic");
		username = props.get("channel.username");
		oauth = props.get("oauth");
		channel = props.get("channel");
		channelUsername = props.get("target.channel.username");
	}
	@Override
	public Class<? extends Task> taskClass() {
		return ChatMessageSourceTask.class;
	}
	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		List<Map<String, String>> configs = new ArrayList<Map<String, String>>();
		Map<String, String> config = new HashMap<String, String>();
		config.put("topic", kafkaTopic);
		config.put("username", username);
		config.put("oauth", oauth);
		config.put("channel", channel);
		config.put("channelUsername", channelUsername);
		configs.add(config);
		return configs;
	}
	@Override
	public void stop() {
		
	}
	
	@Override
	public ConfigDef config() {
		return new ConfigDef()
				.define("topic", Type.STRING, Importance.HIGH, "The Kafka topic")
				.define("channel.username", Type.STRING, Importance.HIGH, "Channel Username")
				.define("oauth", Type.STRING, Importance.HIGH, "Oauth token")
				.define("channel", Type.STRING, Importance.HIGH, "Channel to observe")
				.define("target.channel.username", Type.STRING, Importance.HIGH, "Channel Username to observe");
	}	
}
