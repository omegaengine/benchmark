def db_connect():
	import MySQLdb
	#Configuration:        Host,        Username,        Password,        Database
	return MySQLdb.connect('localhost', 'betabenchmark', 'betabenchmark', 'betabenchmark')

def application(environ, start_response):
	# Locate modules in same directory
	import site, os
	site.addsitedir(os.path.dirname(__file__))

	try:
		# Parse and store data received via CGI
		import cgi, submission
		data = cgi.FieldStorage(fp=environ['wsgi.input'], environ=environ)
		submission.store(submission.parse(user_name=data['user'].value, game_name=data['game'].value, stream=data['file'].file), db_connect())

		# Send HTTP response
		start_response('200 OK', [('Content-Type', 'text/html')])
		return ["OK"]
	except:
		# Send HTTP response
		start_response('400 Bad Request', [('Content-Type', 'text/html')])
		return ["Error"]
