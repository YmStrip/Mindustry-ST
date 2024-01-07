package st.ht.console.controller;

import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Player;
import st.ht.console.entity.command;
import st.ht.console.provider.CommandProvider;
import st.ht.console.provider.ConsoleTabUI;

@Controller
public class ConsoleController extends LayerController {
	@Require(cls = ConsoleTabUI.class)
	ConsoleTabUI consoleTabUI;
	@Require(cls = CommandProvider.class)
	CommandProvider command;
	@Require
	Logger logger;
	
	@Override
	public void run() {
		//命令区
		{
			//沙盒模式
			command.set("sandbox", new command() {
				{
					isDebug = true;
					isAdmin = true;
					desc = "沙盒模式";
				}
				
				@Override
				public void call(Player player, String message) {
					if (!Vars.state.rules.infiniteResources) {
						player.sendMessage("开启沙盒模式");
						logger.info("开启沙盒模式");
					} else {
						player.sendMessage("关闭沙盒模式");
						logger.info("关闭沙盒模式");
					}
					Vars.state.rules.infiniteResources = !Vars.state.rules.infiniteResources;
				}
			});
			command.set("research", new command() {
				{
					isDebug = true;
					isAdmin = true;
					desc = "科技全开";
				}
				
				@Override
				public void call(Player player, String message) {
					player.sendMessage("科技全开");
					logger.info("科技树全开");
					Vars.content.each(ce -> {
						if (!(ce instanceof UnlockableContent unc)) return;
						unc.quietUnlock();
					});
				}
			});
			command.set("unResearch", new command() {
				{
					isDebug = true;
					isAdmin = true;
					desc = "科技全关";
				}
				
				@Override
				public void call(Player player, String message) {
					player.sendMessage("科技全关");
					logger.info("科技树全关");
					Vars.content.each(ce -> {
						if (!(ce instanceof UnlockableContent unc)) return;
						unc.clearUnlock();
					});
				}
			});
			command.set("get", new command() {
				{
					isDebug = true;
					isAdmin = true;
					desc = "区块占领";
				}
				
				@Override
				public void call(Player player, String message) {
					player.sendMessage("success");
					Vars.state.rules.waves = false;
				}
			});
			command.set("res", new command() {
				{
					isDebug = true;
					isAdmin = true;
					desc = "无线资源";
				}
				
				@Override
				public void call(Player player, String message) {
					var core = player.team().core();
					if (core != null) {
						Vars.content.items().forEach(d -> {
							core.items.add(d, 100 * 1000 * 1000);
						});
					}
					
				}
			});
		}
	}
}
