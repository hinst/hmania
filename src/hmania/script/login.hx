package hmania.script;

import js.*;
import js.html.*;

class Login extends Action
{
	
	var nameField: InputElement;

	public function new()
	{	
		super();
		nameField = cast(Browser.document.getElementById("name"), InputElement);
	}
	
	public override function run()
	{
		super.run();
		nameField.focus();
	}
		
}