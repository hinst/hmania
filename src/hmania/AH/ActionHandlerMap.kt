package hmania.AH

import java.util.HashMap
import sun.plugin.com.JavaClass

object ActionHandlerMap: HashMap<String, () -> ActionHandler>() {

	val actionConstructors: Array<() -> ActionHandler>
	{
		actionConstructors = array({Default()}, {HttpDiagnostics()},  {LoginPage()}, {Login()})
		for (actionConstructor in actionConstructors) {
			val action = actionConstructor()
			this.put(action.actionName, actionConstructor)
		}
	}


}