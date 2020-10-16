/* 
	This file when run using command "node server.js" will create a node server and runs in 8082 localhost.
	NodeJs server is required in this project to manipulate the Status file because, we can't handle local files directly from browser.
*/

var http = require('http');  
var url = require('url');  
var fs = require('fs'); 

var server = http.createServer(function(request, response) {
    var path = url.parse(request.url).pathname;  
    switch (path) {
        case '/off':		
            fs.writeFile(__dirname + '/Status.txt', 'off', function (err, data) {
                if(err) {
                    console.log(err);
                    response.writeHead(404);  
                    response.write("Error");
                    response.end();
                    console.log('Error while writing in file');
                }
                response.writeHead(200, {  
                    'Content-Type': 'text/html'  
                });  
                response.write("Done");  
                response.end();
            });
            break;
        case '/on':
            fs.writeFile(__dirname + '/Status.txt', 'on', function (err, data) {
                if(err) {
                    console.log(err);
                    response.writeHead(404);  
                    response.write("Error");
                    response.end();
                    console.log('Error while writing in file');
                }
                response.writeHead(200, {  
                    'Content-Type': 'text/html'  
                });  
                response.write("Done");  
                response.end();
            });
            break;
    }
});  
server.listen(8082);
