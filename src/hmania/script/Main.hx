package hmania.script;

class Main
{
	
	static function main()
	{
		trace("Now starting...");
		var main = new Main();
		main.run();
	}
	
	var actionName: String;
	
	public function new() 
	{
		actionName = "$action$";
		trace("actionName is: '" + actionName + "'");
	}
	
	public function run()
	{
		var action: Action = ActionMap.create(actionName);
		if (action != null)
		{
			trace("found action handler for '" + actionName + "', now running...");
			action.run();
		}
		else
		{
			trace("action handler not found");
		}
	}
	
}