package st.addon.content.entity;

import mindustry.content.SectorPresets;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.SectorPreset;
import mindustry.world.Block;

public class STechBuilder {
	public TechTree.TechNode tech;
	public UnlockableContent content;
	public UnlockableContent parent;
	
	public STechBuilder parent(UnlockableContent item) {
		if (content instanceof Block b) {
			if (b.researchCostMultiplier <= 0) b.researchCostMultiplier = 1;
			/*b.researchCost = null;
			System.out.println(content + " " + content.getClass() + " " + b.requirements.length + " " + b.researchCostMultipliers + " " + b.researchRequirements());*/
		}
		/*for (var i : content.researchRequirements()) {
			//System.out.println(content + " " + i.toString());
		}*/
		
		parent = item;
		new TechTree.TechNode(item.techNode, content, content.researchRequirements());
		tech = item.techNode;
		if ((content instanceof Item) || (content instanceof Liquid)) {
			req(new Objectives.Produce(content));
		}
		if ((content instanceof SectorPreset) && (item instanceof SectorPreset s)) {
			req(new Objectives.SectorComplete(s));
		}
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
	
	public STechBuilder inChild(UnlockableContent item) {
		var v = new STechBuilder(item);
		v.parent(content);
		return v;
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
