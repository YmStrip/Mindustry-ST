package st.ht.console.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.game.EventType;
import st.ST;
import st.ht.console.provider.CommandProvider;

@Controller
public class CommandController extends LayerController {
	@Require(cls = CommandProvider.class)
	CommandProvider command;
	
	@Override
	public void run() {
		Events.on(EventType.PlayerChatEvent.class, e -> {
			if (command.isWait()) return;
			var str = e.message;
			if (!e.message.startsWith(".")) return;
			//不要相信机器
			//System.out.println("输入：" + e.message);
			str = e.message.substring(1, e.message.length());
			//System.out.println("处理后：" + str);
			var cmdAry = str.split("\\s+");
			//System.out.println("分割后：" + cmdAry);
			var cmd = cmdAry[0] == null ? "" : cmdAry[0];
			//System.out.println("处理后：" + cmd);
			System.out.println(cmd);
			if (command.command.get(cmd) == null) {
				//寻找最近的值
				var sim = command.sim(cmd == null ? "" : cmd);
				if (sim == null) {
					e.player.sendMessage("找不到命令 [red]" + cmd);
				} else {
					e.player.sendMessage("找不到命令 [red]" + cmd + " [white] 你想输入的是 [cyan]" + sim + "?");
				}
				return;
			}
			var c = command.command.get(cmd);
			if (c.isAdmin && !e.player.admin) {
				e.player.sendMessage("[red]没有权限直线当前命令");
				return;
			}
			if (c.isDebug && !ST.debug) {
				e.player.sendMessage("[red]仅在DEBUG模式才能");
				return;
			}
			c.call(e.player, e.message);
		});
	}
}
