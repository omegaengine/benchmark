def db_connect():
	import MySQLdb
	#Configuration:        Host,        Username,        Password,        Database
	return MySQLdb.connect('localhost', 'betabenchmark', 'betabenchmark', 'betabenchmark')

def application(environ, start_response):
	# Locate modules in same directory
	import site, os
	site.addsitedir(os.path.dirname(__file__))

	# Parse and store data received via CGI
	import cgi, submission
	data = cgi.FieldStorage(fp=environ['wsgi.input'], environ=environ)
	submission.store(submission.parse(data['user-name'].value, data['file'].file), db_connect())

	# Send HTTP response
	start_response('200 OK', [('Content-Type', 'text/html')])
	return ["Done"]
