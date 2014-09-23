package hinst;

import haxe.Int64;
import js.node.http.Server;
import js.node.Http;
import js.node.http.ServerResponse;

class App
{
	
	public static function main() 
	{
		var app = new App();
		app.run();
	}
	
	public function new()
	{
		countOfProcessedRequests = Int64.ofInt(0);
	}
	
	private var server: Server;
	public var countOfProcessedRequests(default, null): Int64;
	
	public function run()
	{
		server = Http.createServer(processRequest);
		server.listen(80);
	}
	
	public function processTestRequest(request: HttpServerReq, response: ServerResponse)
	{
		response.writeHead(200, { "Content-Type": MimeType.TEXT_UTF8 } );
		response.write('LOL');
		response.end();		
	}
	
	public function processRequest(request: HttpServerReq, response: ServerResponse)
	{
		countOfProcessedRequests = Int64.add(countOfProcessedRequests, Int64.ofInt(1));
		trace('process request #'  + countOfProcessedRequests + '...');
		processTestRequest(request, response);
	}
	
}