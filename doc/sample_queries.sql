-- Show all submissions
SELECT
submission.submission_time, submission.user_name, submission.game_version,
os.platform as os_platform, os.is64bit as os_is64bit, os.version as os_version, os.service_pack as os_service_pack,
cpu.manufacturer as cpu_manufacturer, cpu.name as cpu_name, cpu.speed as cpu_speed, cpu.cores as cpu_cores, cpu.logical as cpu_logical,
submission.ram,
gpu_manufacturer.name as gpu_manufacturer, gpu.name as gpu_name, gpu.ram as gpu_ram, gpu.max_aa as gpu_max_aa
FROM (os, cpu, (gpu_manufacturer JOIN gpu ON (gpu.manufacturer_id = gpu_manufacturer.id))) JOIN submission ON (submission.os_id = os.id AND submission.cpu_id = cpu.id AND submission.gpu_id = gpu.id);

-- Show all test cases
SELECT
test_case.target_name, test_case.high_res, test_case.anti_aliasing, test_case.screenshot, test_case.graphics_settings_anisotropic, test_case.graphics_settings_double_sampling, test_case.graphics_settings_post_screen_effects,
water_effects.name as water_effects, particle_system_quality.name as particle_system_quality
FROM (water_effects, particle_system_quality) JOIN test_case ON (test_case.water_effects_id = water_effects.id AND test_case.particle_system_quality_id = particle_system_quality.id);

-- Show all test case results
SELECT
submission.submission_time, submission.user_name, submission.game_version,
os.platform as os_platform, os.is64bit as os_is64bit, os.version as os_version, os.service_pack as os_service_pack,
cpu.manufacturer as cpu_manufacturer, cpu.name as cpu_name, cpu.speed as cpu_speed, cpu.cores as cpu_cores, cpu.logical as cpu_logical,
submission.ram,
gpu_manufacturer.name as gpu_manufacturer, gpu.name as gpu_name, gpu.ram as gpu_ram, gpu.max_aa as gpu_max_aa,
test_case.target_name, test_case.high_res, test_case.anti_aliasing, test_case.screenshot, test_case.graphics_settings_anisotropic, test_case.graphics_settings_double_sampling, test_case.graphics_settings_post_screen_effects,
water_effects.name as water_effects, particle_system_quality.name as particle_system_quality,
test_case_result.average_fps, test_case_result.average_frame_ms
FROM (
  ((os, cpu, (gpu_manufacturer JOIN gpu ON (gpu.manufacturer_id = gpu_manufacturer.id))) JOIN submission ON (submission.os_id = os.id AND submission.cpu_id = cpu.id AND submission.gpu_id = gpu.id)),
  ((water_effects, particle_system_quality) JOIN test_case ON (test_case.water_effects_id = water_effects.id AND test_case.particle_system_quality_id = particle_system_quality.id))
) JOIN test_case_result ON (test_case_result.submission_id = submission.id AND test_case_result.test_case_id = test_case.id);
