package hmania.AH

import java.util.HashMap

object ActionHandlerMap: HashMap<String, () -> ActionHandler>() {
	{
		put("", { Default() })
		put("hd", { HttpDiagnostics() })
		put("lp", { LoginPage() })
		put("l", { Login() })
	}

}