"""
Handles ZIP archives as a OmegaEngine benchmark submission.
"""

# Dummy class, fields are added dynamically
class Data:
	pass


def parse(user_name, game_name, stream):
	"""Parses a ZIP archive as a benchmark submission.
	@param user_name: the name of the user that submitted the data
	@param game_name: the name of the game that generated the data
	@param stream: a file-like object containing the ZIP data
	@return: the parsed benchmark data structure"""
	def parse_hardware(zip):
		def parse_os(element):
			os = Data()
			os.platform = element.attrib['platform']
			os.is64bit = (element.attrib['is64bit'] == "true")
			os.version = element.attrib['version']
			os.service_pack = element.attrib['service-pack']
			return os

		def parse_cpu(element):
			cpu = Data()
			cpu.manufacturer = element.attrib['manufacturer']
			cpu.name = element.attrib['name']
			cpu.speed = element.attrib['speed']
			cpu.cores = element.attrib['cores']
			cpu.logical = element.attrib['logical']
			return cpu

		def parse_ram(element):
			ram = Data()
			ram.size = element.attrib['size']
			return ram

		def parse_gpu(element):
			gpu = Data()
			gpu.manufacturer = element.attrib['manufacturer']
			gpu.name = element.attrib['name']
			gpu.ram = element.attrib['ram']
			gpu.max_aa = element.attrib['max-aa']
			return gpu

		import xml.etree.ElementTree as xml
		root = xml.parse(zip.open('hardware.xml')).getroot()

		hardware = Data()
		hardware.os = parse_os(root.find('os'))
		hardware.cpu = parse_cpu(root.find('cpu'))
		hardware.ram = parse_ram(root.find('ram'))
		hardware.gpu = parse_gpu(root.find('gpu'))
		return hardware

	def parse_statistics(zip, game_name):
		def parse_test_case(element):
			def parse_graphics_settings(element):
				text = element.text or ''
				graphics_settings = Data()
				graphics_settings.anisotropic = ('anisotropic' in text)
				graphics_settings.double_sampling = ('double_sampling' in text)
				graphics_settings.post_screen_effects = ('post_screen_effects' in text)
				return graphics_settings

			test_case = Data()
			test_case.target_name = element.find('Target').attrib['Name']
			test_case.high_res = (element.attrib['high-res'] == "true")
			test_case.anti_aliasing = (element.attrib['anti-aliasing'] == "true")
			test_case.screenshot = (element.attrib['screenshot'] == "true")
			test_case.graphics_settings = parse_graphics_settings(element.find('GraphicsSettings'))
			test_case.water_effects = element.find('WaterEffects').text
			test_case.particle_system_quality = element.find('ParticleSystemQuality').text
			test_case.result = Data()
			test_case.result.average_fps = element.attrib['average-fps']
			test_case.result.average_frame_ms = element.attrib['average-frame-ms']
			return test_case

		import xml.etree.ElementTree as xml
		root = xml.parse(zip.open('statistics.xml')).getroot()

		statistics = Data()

		statistics.game = Data()
		statistics.game.name = game_name
		statistics.game.version = root.attrib['game-version']
		statistics.game.engine_version = root.attrib['engine-version']

		statistics.test_cases = []
		for i, element in enumerate(root.findall('test-case')):
			test_case = parse_test_case(element)
			test_case.result.frame_log = zip.open('test-case' + str(i) + '.xml').read()
			test_case.result.screenshot = zip.open('test-case' + str(i) + '.jpg').read() if test_case.screenshot else None
			statistics.test_cases.append(test_case)
		return statistics

	submission = Data()

	submission.user = Data()
	submission.user.name = user_name

	import zipfile
	zip = zipfile.ZipFile(stream)
	submission.hardware = parse_hardware(zip)
	submission.statistics = parse_statistics(zip, game_name)
	submission.game_log = zip.open('log.txt').read()

	return submission


def store(submission, db):
	"""Stores a benchmark submission in a database.
	@param submission: the parsed benchmark data structure
	@param db: the database to store the data in"""
	def _dict_value_pad(key):
		return '%(' + str(key) + ')s'
	def _dict_value_pair(key):
		return str(key) + ' = %(' + str(key) + ')s'

	def get(cursor, table, dict):
		"""Tries to find the ID of an existing database table entry.
		@param cursor: the cursor used to access the database
		@param table: the name of the table to read from
		@param dict: the key-value pairs to search for
		@return: the ID of the existing entry or None"""
		sql = 'SELECT id FROM ' + table + ' WHERE '
		sql += ' AND '.join(map(_dict_value_pair, dict))
		sql += ' LIMIT 1;'
		cursor.execute(sql, dict)
		return cursor.fetchone()[0] if cursor.rowcount > 0 else None

	def add(cursor, table, dict):
		"""Tries to add a new database table entry.
		@param cursor: the cursor used to access the database
		@param table: the name of the table to write to
		@param dict: the key-value pairs to write
		@return: the ID of the newly created entry or None"""
		sql = 'INSERT IGNORE INTO ' + table + ' ('
		sql += ', '.join(dict)
		sql += ') VALUES ('
		sql += ', '.join(map(_dict_value_pad, dict))
		sql += ');'
		cursor.execute(sql, dict)
		return cursor.lastrowid if cursor.rowcount > 0 else None

	def get_or_add(cursor, table, dict):
		"""Tries to find the ID of an existing database table entry and adds it if it is missing.
		@param cursor: the cursor used to access the database
		@param table: the name of the table to read from and write to
		@param dict: the key-value pairs to search for or write
		@return: the ID of the existing or the newly created entry"""
		result = get(cursor, table, dict)	# Try to find exisiting
		if result is None:
			result = add(cursor, table, dict) # Try to add new
			if result is None:
				result = get(cursor, table, dict) # Handle race condition
				if result is None:
					raise Exception("Failed to insert into table '%s'" % table)
		return result

	cursor = db.cursor()

	user = submission.user
	user_id = get_or_add(cursor, 'user', dict(name=user.name))

	game = submission.statistics.game
	game_id = get_or_add(cursor, 'game', dict(name=game.name, version=game.version, engine_version=game.engine_version))

	os = submission.hardware.os
	os_id = get_or_add(cursor, 'os', dict(platform=os.platform, is64bit=os.is64bit, version=os.version, service_pack=os.service_pack))

	cpu = submission.hardware.cpu
	cpu_id = get_or_add(cursor, 'cpu', dict(manufacturer=cpu.manufacturer, name=cpu.name, speed=cpu.speed, cores=cpu.cores, logical=cpu.logical))

	gpu = submission.hardware.gpu
	add(cursor, 'gpu_manufacturer', dict(id=gpu.manufacturer, name='Manufacturer #' + str(gpu.manufacturer)))
	gpu_id = get_or_add(cursor, 'gpu', dict(manufacturer_id=gpu.manufacturer, name=gpu.name, ram=gpu.ram, max_aa=gpu.max_aa))

	statistics = submission.statistics
	submission_id = add(cursor, 'submission', dict(user_id=user_id, game_id=game_id, os_id=os_id, cpu_id=cpu_id, ram=submission.hardware.ram.size, gpu_id=gpu_id, game_log=submission.game_log))
	for test_case in statistics.test_cases:
		water_effects_id = get_or_add(cursor, 'water_effects', dict(name=test_case.water_effects))
		particle_system_quality_id = get_or_add(cursor, 'particle_system_quality', dict(name=test_case.particle_system_quality))
		test_case_id = get_or_add(cursor, 'test_case', dict(target_name=test_case.target_name, high_res=test_case.high_res, anti_aliasing=test_case.anti_aliasing, graphics_settings_anisotropic=test_case.graphics_settings.anisotropic, graphics_settings_double_sampling=test_case.graphics_settings.double_sampling, graphics_settings_post_screen_effects=test_case.graphics_settings.post_screen_effects, water_effects_id=water_effects_id, particle_system_quality_id=particle_system_quality_id))

		result = test_case.result
		add(cursor, 'test_case_result', dict(submission_id=submission_id, test_case_id=test_case_id, average_fps=result.average_fps, average_frame_ms=result.average_frame_ms, frame_log=result.frame_log, screenshot=result.screenshot))

	db.commit()
