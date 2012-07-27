"""
Reads the contents of a ZIP archive as a OmegaEngine benchmark submission.
"""

class Data:
	pass

def parse_zip_submission(user_name, game_version, stream):
	def parse_submission(zip):
		def parse_hardware(zip):
			def parse_os(element):
				os = Data()
				os.platform = element.attrib['platform']
				os.is64bit = element.attrib['is64bit']
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

		def parse_statistics(zip):
			def parse_test_case(element):
				def parse_graphics_settings(element):
					text = element.text or ''
					graphics_settings = Data()
					graphics_settings.anisotropic = 'anisotropic' in text
					graphics_settings.double_sampling = 'double_sampling' in text
					graphics_settings.post_screen_effects = 'post_screen_effects' in text
					return graphics_settings

				test_case = Data()
				test_case.target_name = element.find('Target').attrib['Name']
				test_case.high_res = element.attrib['high-res']
				test_case.anti_aliasing = element.attrib['anti-aliasing']
				test_case.screenshot = element.attrib['screenshot']
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
			statistics.test_cases = []
			for i, element in enumerate(root.findall('test-case')):
				test_case = parse_test_case(element)
				try:
					test_case.result.frame_log = zip.open('test-case' + str(i) + '.xml').read()
				except KeyError:
					test_case.result.frame_log = None
				try:
					test_case.result.screenshot = zip.open('test-case' + str(i) + '.jpg').read()
				except KeyError:
					test_case.result.screenshot = None
				statistics.test_cases.append(test_case)
			return statistics

		submission = Data()
		submission.hardware = parse_hardware(zip)
		submission.statistics = parse_statistics(zip)
		submission.game_log = zip.open('log.txt').read()
		return submission

	import zipfile
	submission = parse_submission(zipfile.ZipFile(stream))
	submission.user_name = user_name
	submission.game_version = game_version
	return submission

def store_submission(submission):
	def add(cursor, table, values):
		#TODO
		return 0

	def add_or_get(cursor, table, values):
		#TODO
		return 0

	def add_or_get_by_key(cursor, table, key, values):
		#TODO
		return 0

	#import MySQLdb
	#db = MySQLdb.connect('localhost', 'testuser', 'test123', 'betabenchmark');
	#cursor = db.cursor()
	cursor = None

	os = submission.hardware.os
	os_id = add_or_get(cursor, 'os', {'platform': os.platform, 'is64bit': os.is64bit, 'version': os.version, 'service_pack': os.service_pack})

	cpu = submission.hardware.cpu
	cpu_id = add_or_get(cursor, 'cpu', {'manufacturer': cpu.manufacturer, 'name': cpu.name, 'speed': cpu.speed, 'cores': cpu.cores, 'logical': cpu.logical})

	gpu = submission.hardware.gpu	
	gpu_manufacturer_id = add_or_get_by_key(cursor, 'gpu_manufacturer', {'id': gpu.manufacturer}, {'name': gpu.manufacturer}) # Default GPU manufacturer name to manufacturer ID
	gpu_id = add_or_get(cursor, 'gpu', {'manufacturer_id': gpu_manufacturer_id, 'name': gpu.name, 'ram': gpu.ram, 'max_aa': gpu.max_aa})

	submission_id = add(cursor, 'submission', {'user_name': submission.user_name, 'game_version': submission.game_version, 'os_id': os_id, 'cpu_id': cpu_id, 'ram': submission.hardware.ram.size, 'gpu_id': gpu_id, 'game_log': submission.game_log})

	for test_case in submission.statistics.test_cases:
		water_effects_id = add_or_get(cursor, 'water_effects', {'name': test_case.water_effects})
		particle_system_quality_id = add_or_get(cursor, 'particle_system_quality', {'name': test_case.particle_system_quality})
		test_case_id = add_or_get(cursor, 'test_case', {'target_name': test_case.target_name, 'high_res': test_case.high_res, 'anti_aliasing': test_case.anti_aliasing, 'screenshot': test_case.screenshot, 'graphics_settings_anisotropic': test_case.graphics_settings.anisotropic, 'graphics_settings_double_sampling': test_case.graphics_settings.double_sampling, 'graphics_settings_post_screen_effects': test_case.graphics_settings.post_screen_effects, 'water_effects_id': water_effects_id, 'particle_system_quality_id': particle_system_quality_id})

		result = test_case.result
		add(cursor, 'test_case_result', {'submission_id': submission_id, 'test_case_id': test_case_id, 'average_fps': result.average_fps, 'average_frame_ms': result.average_frame_ms, 'frame_log': result.frame_log, 'screenshot': result.screenshot})

import sys
store_submission(parse_zip_submission(sys.argv[1], sys.argv[2], open(sys.argv[3], 'rb')))
