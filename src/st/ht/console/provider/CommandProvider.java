package st.ht.console.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.gen.Player;
import st.ht.console.entity.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Provider
public class CommandProvider extends LayerProvider {
	public long lastWaitTime = System.currentTimeMillis();
	
	public boolean isWait() {
		if (System.currentTimeMillis() - lastWaitTime < 250) {
			return true;
		} else {
			lastWaitTime = System.currentTimeMillis();
			return false;
		}
	}
	
	public HashMap<String, st.ht.console.entity.command> command = new HashMap();
	
	public static double calculateSimilarity(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		int[][] dp = new int[len1 + 1][len2 + 1];
		
		for (int i = 0; i <= len1; i++) {
			for (int j = 0; j <= len2; j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i][j - 1], dp[i - 1][j]));
				}
			}
		}
		
		int maxLen = Math.max(len1, len2);
		return 1 - (double) dp[len1][len2] / maxLen;
	}
	
	public String help(String cmd) {
		if (command.get(cmd) == null) return "";
		var c = command.get(cmd);
		var res = new ArrayList<String>();
		res.add("[yellow]cmd");
		res.add("[gray]" + c.desc);
		if (c.isAdmin) res.add("[red]#OP[white]");
		if (c.isDebug) res.add("[red]#DEBUG[white]");
		return String.join(" ", res.toArray(new String[0]));
	}
	
	public String help() {
		var c = new ArrayList<String>();
		for (var i : command.entrySet()) {
			c.add(help(i.getKey()));
		}
		return String.join("\n", c.toArray(new String[0]));
	}
	
	public String sim(String cmd) {
		String now = null;
		var sim = 0d;
		for (var i : command.entrySet()) {
			var key = i.getKey();
			var s = calculateSimilarity(key, cmd);
			if (sim <= s) {
				sim = s;
				now = key;
			}
		}
		if (sim < 0.3) {
			return null;
		}
		return now;
	}
	
	//public
	public CommandProvider set(String name, command c) {
		if (Objects.equals(name, "help")) return this;
		command.put(name, c);
		return this;
	}
	
	@Override
	public void run() {
		command.put("help", new command() {
			{
				desc = "帮助";
			}
			
			@Override
			public void call(Player player, String message) {
				player.sendMessage(help());
			}
		});
	}
}
