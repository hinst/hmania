package hmania.AH

import java.util.HashMap
import sun.plugin.com.JavaClass

object ActionHandlerMap: HashMap<String, () -> ActionHandler>() {

	val actionConstructors: Array<() -> ActionHandler>
	{
		actionConstructors = array(
			{ // function
				Default()
			},
			{
				HttpDiagnostics()
			},
			{
				LoginPage()
			},
			{
				Login()
			},
			{
				CurrentUserPage()
			},
			{
				Logout()
			}
		)
		for (actionConstructor in actionConstructors) {
			val action = actionConstructor()
			this.put(action.actionName, actionConstructor)
		}
	}

}