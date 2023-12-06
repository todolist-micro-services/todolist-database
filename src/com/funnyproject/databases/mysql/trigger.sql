USE todolistMicroServices;

DROP TRIGGER IF EXISTS delete_user;

delimiter $$

 CREATE TRIGGER delete_user BEFORE DELETE ON User
	FOR EACH ROW
	BEGIN
		DELETE FROM Token WHERE user = OLD.user_id;
	END $$

delimiter ;

-- INSERT INTO User (firstname, lastname, email, password)
-- VALUES ("Pierre-Alexandre", "Delgado", "pdelgadoarevalo@sfsu.edu", "123456789"),
-- 		("Pierre-Alexandre", "Delgado", "delgadopierrealexandre@gmail.com", "123456789");
-- INSERT INTO Token (jwt_value, expiration_date, user)
-- VALUES ("token value 1", "1998-01-23 12:45:56", 1),
-- 		("token value 2", "1998-01-23 12:45:56", 2);
        
-- DELETE FROM User WHERE user_id = 1;
-- DELETE FROM Token WHERE token_id = 2;
-- SELECT * FROM User;
-- SELECT * FROM Token;