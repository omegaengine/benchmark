-- DROP DATABASE omegaengine_benchmark;
-- CREATE DATABASE omegaengine_benchmark CHARACTER SET utf8;
-- USE omegaengine_benchmark;


-- Hardware --

CREATE TABLE os
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	platform VARCHAR(255) NOT NULL, -- operating system family, e.g. Win32NT
	is64bit BOOLEAN NOT NULL, -- 64bit operating system yes/no
	version VARCHAR(255) NOT NULL, -- internal operating system version number, e.g. 6.1 for Windows 7
	service_pack VARCHAR(255) NOT NULL -- service pack, e.g. Service Pack 1
) CHARSET=utf8 ENGINE=InnoDB;

CREATE TABLE cpu 
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	manufacturer VARCHAR(255) NOT NULL, -- CPU manufacturer, e.g. GenuineIntel
	name VARCHAR(255) NOT NULL, -- CPU full name, e.g. "Intel(R) Core(TM)2 Duo CPU     P8400  @ 2.26GHz"
	speed INT UNSIGNED NOT NULL, -- CPU speed in MHz
	cores INT UNSIGNED NOT NULL, -- number of CPU cores
	logical INT UNSIGNED NOT NULL -- number of logical threads
) CHARSET=utf8 ENGINE=InnoDB;

CREATE TABLE gpu_manufacturer (
  id INT PRIMARY KEY, -- DirectX graphics card manufacturer ID, e.g. 4318 for NVidia
  name varchar(255) NOT NULL -- graphics card manufacturer name
) CHARSET=utf8 ENGINE=InnoDB;

CREATE TABLE gpu 
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	manufacturer_id INT NOT NULL, -- DirectX graphics card manufacturer ID, e.g. 4318 for NVidia
	name VARCHAR(255) NOT NULL, -- graphics card name, e.g. NVIDIA GeForce 9200M GS
	ram INT UNSIGNED NOT NULL, -- graphics card RAM size in MB
	max_aa INT UNSIGNED NOT NULL, -- maximum anti-aliasing level
	FOREIGN KEY (manufacturer_id) REFERENCES gpu_manufacturer(id)
) CHARSET=utf8 ENGINE=InnoDB;


-- Test cases --

CREATE TABLE submission
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	submission_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	user_name VARCHAR(128) NOT NULL, -- the name of the user that submitted these benchmark test_case_results
	game_version VARCHAR(5) NOT NULL, -- version number of the game, e.g. 0.5.0
	os_id INT UNSIGNED NOT NULL,
	cpu_id INT UNSIGNED NOT NULL,
	ram INT UNSIGNED NOT NULL, -- system RAM size in MB
	gpu_id INT UNSIGNED NOT NULL,
	game_log BLOB, -- content of the log.txt file
	FOREIGN KEY (os_id) REFERENCES os(id),
	FOREIGN KEY (cpu_id) REFERENCES cpu(id),
	FOREIGN KEY (gpu_id) REFERENCES gpu(id)
) CHARSET=utf8 ENGINE=InnoDB;

CREATE TABLE water_effects
(
	id INT PRIMARY KEY,
	name VARCHAR(255) NOT NULL
) CHARSET=utf8 ENGINE=InnoDB;
INSERT INTO water_effects VALUES (0, 'None'), (1, 'RefractionOnly'), (2, 'ReflectTerrain'), (3, 'ReflectAll');

CREATE TABLE particle_system_quality
(
	id INT PRIMARY KEY,
	name VARCHAR(255) NOT NULL
) CHARSET=utf8 ENGINE=InnoDB;
INSERT INTO particle_system_quality VALUES (0, 'Low'), (1, 'Medium'), (2, 'High');

CREATE TABLE test_case
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	target_name VARCHAR(255) NOT NULL, -- name of the object target in the scene, e.g. House1
	water_effects_id INT NOT NULL,
	particle_system_quality_id INT NOT NULL,
	graphics_settings_anisotropic BOOLEAN NOT NULL, -- was anisotropic filtering on?
	graphics_settings_double_sampling BOOLEAN NOT NULL, -- was double sampling on?
	graphics_settings_post_screen_effects BOOLEAN NOT NULL, -- were post-screen effects on on?
	high_res BOOLEAN NOT NULL, -- was the scene rendered with high resolution?
	anti_aliasing BOOLEAN NOT NULL, -- was the scene rendered with anti aliasing?
	screenshot BOOLEAN NOT NULL, -- was a screenshot created?
	FOREIGN KEY (water_effects_id) REFERENCES water_effects(id),
	FOREIGN KEY (particle_system_quality_id) REFERENCES particle_system_quality(id)
) CHARSET=utf8 ENGINE=InnoDB;

CREATE TABLE test_case_result
(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	submission_id INT UNSIGNED NOT NULL,
	test_case_id INT UNSIGNED NOT NULL,
	average_fps FLOAT UNSIGNED NOT NULL, -- average number of frames that were rendered per second
	average_frame_ms FLOAT UNSIGNED NOT NULL, -- average number of milliseconds a frame took to render
	frame_log BLOB, -- content of the test-case##.xml file
	screenshot BLOB, -- content of the test-case##.jpg file
	FOREIGN KEY (submission_id) REFERENCES submission(id) ON DELETE CASCADE,
	FOREIGN KEY (test_case_id) REFERENCES test_case(id)
) CHARSET=utf8 ENGINE=InnoDB;
