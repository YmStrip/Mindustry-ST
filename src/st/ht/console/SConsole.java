package st.ht.console;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.ht.console.controller.CommandController;
import st.ht.console.controller.ConsoleController;
import st.ht.console.provider.CommandProvider;
import st.ht.console.provider.ConsoleTabUI;

@Module(name = "console")
public class SConsole extends LayerModule {
	public SConsole() {
		imports(
			SContent.class
		);
		provider(
			new CommandProvider(),
			new ConsoleTabUI()
		);
		controller(
			new CommandController(),
			new ConsoleController()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		f.instance("logger", t -> t
			.implement(new Logger())
			.config("name", "console"));
	}
}
