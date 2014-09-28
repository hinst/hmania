import java.util.HashMap

object ActionHandlerMap: HashMap<String, () -> ActionHandler>() {
	{
		put("", {DefaultAH()})
	}

}