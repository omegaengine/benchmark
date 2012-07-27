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

import sys
submission = parse_zip_submission(sys.argv[1], sys.argv[2], open(sys.argv[3], 'rb'))
# TODO: Store in database
