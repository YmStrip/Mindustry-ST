package st.provider.command.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.ctype.UnlockableContent;
import mindustry.game.EventType;
import st.ST;
import st.provider.command.provider.CommandProvider;
import st.quamtum.provider.Quantum;
import st.quamtum.provider.provider.QuantumMapTile;
import st.quamtum.provider.provider.QuantumWorld;

import java.util.Objects;

@Controller
public class CommandController extends LayerController {
	@Require(cls = CommandProvider.class)
	CommandProvider commandProvider;
	@Require(cls = QuantumMapTile.class)
	@Import(cls = Quantum.class)
	QuantumMapTile quantumMapTile;
	@Require(cls = QuantumWorld.class)
	@Import(cls = Quantum.class)
	QuantumWorld quantumWorld;
	public long lastWaitTime = System.currentTimeMillis();
	
	public boolean isWait() {
		if (System.currentTimeMillis() - lastWaitTime < 250) {
			return false;
		} else {
			lastWaitTime = System.currentTimeMillis();
			return true;
		}
	}
	
	@Require
	Logger logger;
	
	@Override
	public void run() {
		Events.on(EventType.PlayerChatEvent.class, e -> {
			if (!isWait()) return;
			if (!ST.debug) return;
			if (Objects.equals(e.message, "sandbox")) {
				if (!Vars.state.rules.infiniteResources) {
					e.player.sendMessage("开启沙盒模式");
					logger.info("开启沙盒模式");
				} else {
					e.player.sendMessage("关闭沙盒模式");
					logger.info("关闭沙盒模式");
				}
				Vars.state.rules.infiniteResources = !Vars.state.rules.infiniteResources;
			}
			if (Objects.equals(e.message, "research")) {
				e.player.sendMessage("科技全开");
				logger.info("科技树全开");
				Vars.content.each(ce -> {
					if (!(ce instanceof UnlockableContent unc)) return;
					unc.quietUnlock();
				});
			}
			if (Objects.equals(e.message, "unResearch")) {
				e.player.sendMessage("科技全关");
				logger.info("科技树全关");
				Vars.content.each(ce -> {
					if (!(ce instanceof UnlockableContent unc)) return;
					unc.clearUnlock();
				});
			}
			if (Objects.equals(e.message, "get")) {
				e.player.sendMessage("区块占领");
				logger.info("区块占领");
				Vars.state.rules.waves = false;
			}
			if (Objects.equals(e.message, "team")) {
				var t = quantumMapTile.team(e.player.team().id);
				e.player.sendMessage(String.format("队伍类名:%s\n队伍id:%s\n队伍建筑:%s",t, e.player.team().id, t.buildCount + " <- " + t.builds.size()));
			}
			if (Objects.equals(e.message, "buildingList")) {
				var t = quantumMapTile.team(e.player.team().id);
				t.builds.forEach(d -> {
					e.player.sendMessage(d.toString());
				});
			}
			if (Objects.equals(e.message, "itemList")) {
				var t = quantumMapTile.team(e.player.team().id);
				t.items.data.forEach((name, data) -> {
					e.player.sendMessage(name + ": " + data);
				});
			}
			//eval代码
			if (e.message.startsWith("eval")) {
				var code = e.message.substring(4);
			}
		});
	}
}
