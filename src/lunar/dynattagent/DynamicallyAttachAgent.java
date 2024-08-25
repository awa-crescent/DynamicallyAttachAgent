package lunar.dynattagent;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import lib.crescent.Agent;
import lib.crescent.VMEntry;

public class DynamicallyAttachAgent extends JavaPlugin {
	private FileConfiguration config;
	private List<String> agent_configs;

	public void onEnable() {
		File config_file = new File(this.getDataFolder() + File.separator + "config.yml");
		if (!config_file.exists())
			saveDefaultConfig();
		loadConfig();
		for (String agent_with_args : agent_configs) {
			String agent_path = null;
			String agent_args = null;
			String[] options = agent_with_args.split(" ");
			for (String option : options) {
				int dim = -1;
				if (option.startsWith("-D")) {// 系统属性
					option = option.substring(2);
					dim = option.indexOf('=');
					if (dim == -1) {
						VMEntry.setSystemProperty(option, "true");
						continue;
					} else {
						VMEntry.setSystemProperty(option.substring(0, dim), option.substring(dim + 1));
						continue;
					}
				} else if (option.startsWith("-javaagent:")) {// agent参数
					option = option.substring(11);
					dim = option.indexOf('=');
					if (dim == -1) {
						agent_path = option;
					} else {
						agent_path = option.substring(0, dim);
						agent_args = option.substring(dim + 1);
					}
				}
			}
			Agent.attach(this.getDataFolder() + File.separator + agent_path, agent_args);
		}
	}

	public void loadConfig() {
		config = this.getConfig();
		agent_configs = config.getStringList("agent-configs");
	}
}
