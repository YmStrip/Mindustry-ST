package st.provider.command;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.provider.command.controller.CommandController;
import st.provider.command.provider.CommandProvider;
import st.quamtum.provider.Quantum;

@Module
public class Command extends LayerModule {
	public Command() {
		imports(
			Quantum.class
		);
		provider(
			new CommandProvider()
		);
		controller(
			new CommandController()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		f.instance("logger", t -> t
			.implement(new Logger())
			.config("name", "command"));
	}
}
