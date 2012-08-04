def application(environ, start_response):
	from cgi import parse_qs, escape
	try:
		request_body_size = int(environ.get('CONTENT_LENGTH', 0))
	except (ValueError):
		request_body_size = 0
	request_data = parse_qs(environ['wsgi.input'].read(request_body_size))
	# TODO
	
	# Connect to the database
	import MySQLdb
	#Configuration:      Host,        Username,        Password,        Database
	db = MySQLdb.connect('localhost', 'betabenchmark', 'betabenchmark', 'betabenchmark')

	# Parse and store the data
	import submission
	store_submission(parse_submission(user_name, stream), db)

	start_response('200 OK', [('Content-Type', 'text/html')])
	return ["Done"]
