package st.addon.tech.entity;

import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;

public class STechBuilder {
	public TechTree.TechNode tech;
	public UnlockableContent content;
	public UnlockableContent parent;
	
	public STechBuilder parent(UnlockableContent item) {
		parent = item;
		new TechTree.TechNode(item.techNode, content, content.researchRequirements());
		tech = item.techNode;
		return this;
	}
	
	public STechBuilder(UnlockableContent item) {
		content = item;
	}
	
	
	//并行的
	public STechBuilder next(UnlockableContent item, child child) {
		var v = new STechBuilder(item);
		v.parent(parent);
		child.child(v);
		return this;
	}
	
	public STechBuilder next(UnlockableContent item) {
		return next(item, (t) -> {
		});
	}
	
	public STechBuilder req(ItemStack[] itemStack) {
		tech.requirements = itemStack;
		return this;
	}
	
	public STechBuilder req(UnlockableContent item) {
		return req(new Objectives.Research(item));
	}
	
	public STechBuilder req(Objectives.Objective o) {
		tech.objectives.add(o);
		return this;
	}
	
	@FunctionalInterface
	public interface child {
		void child(STechBuilder t);
	}
	
	public STechBuilder child(UnlockableContent item) {
		return child(item, (t) -> {
		});
	}
	
	public STechBuilder child(UnlockableContent item, child child) {
		var v = new STechBuilder(item);
		v.parent(content);
		child.child(v);
		return this;
	}
}
