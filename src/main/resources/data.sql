INSERT INTO `hotel` (`hotel_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `status`, `hotel_name`) VALUES 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'Marriott'), 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'ITC Royal Bengal');

INSERT INTO `room_type` (`roomtype_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `status`, `description`, `roomtype_name`, `hotel_id`) VALUES 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'AC Room', 'Delux', '1'), 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'Non AC Room', 'Semi-Delux', '1'),
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'AC Room', 'Delux', '2'), 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'Non AC Room', 'Semi-Delux', '2');

INSERT INTO `floor_details` (`floor_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `status`, `floor_name`, `hotel_id`) VALUES 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'First Floor', '1'), 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'Second Floor', '1'),
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'First Floor', '2'), 
(NULL, '1', '21-12-2021 13:13:35', NULL, NULL, '1', 'Second Floor', '2');