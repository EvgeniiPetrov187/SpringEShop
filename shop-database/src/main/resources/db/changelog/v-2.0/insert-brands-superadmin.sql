INSERT INTO `users` (`username`, `password`, `age`)
    VALUE ('super', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq', '55');

GO

INSERT INTO `roles` (`name`) VALUE ('ROLE_SUPER_ADMIN');

GO

INSERT INTO `brands` (`title`) VALUES ('GReddy'), ('H&R');

GO

INSERT INTO `categories` (`title`) VALUES ('Wheels'), ('Turbochargers');

GO

INSERT INTO `users_roles`(`user_id`, `role_id`) SELECT
 (SELECT id FROM `users` WHERE `username` = 'super'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_SUPER_ADMIN');

GO