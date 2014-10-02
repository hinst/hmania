package hmania.script;

class ActionMap
{
	
	public static var map: Map < String, Void -> Action > = null;
	
	public static function prepareMap()
	{
		map = new Map();
		map.set("lp", function() { return new Login(); } );
	}
	
	public static function mapToDebugText(): String
	{
		var string = new StringBuf();
		var count = 0;
		for (key in map.keys())
		{
			var value = map.get(key);
			string.add(key + " = " + value + "\n");
			count++;
		}
		string.add("count of items: " + count + "\n");
		return string.toString();
	}
	
	public static function create(action: String): Action
	{
		var result: Action = null;
		if (null == map)
			prepareMap();
		var actionConstructor = map.get(action);
		if (actionConstructor != null)
		{
			result = actionConstructor();
			result.action = action;
		}
		return result;
	}
	
}