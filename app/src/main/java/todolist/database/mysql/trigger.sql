USE todolistMicroServices;

DROP TRIGGER IF EXISTS delete_user;

-- INSERT INTO User (firstname, lastname, email, password)
-- VALUES ("Pierre-Alexandre", "Delgado", "pdelgadoarevalo@sfsu.edu", "123456789"),
-- 		("Pierre-Alexandre", "Delgado", "delgadopierrealexandre@gmail.com", "123456789");
-- INSERT INTO Token (jwt_value, expiration_date, user)
-- VALUES ("token value 1", "1998-01-23 12:45:56", 1),
-- 		("token value 2", "1998-01-23 12:45:56", 2);

-- UPDATE Token SET jwt_value = "", expiration_date = "1998-01-23 12:45:56" WHERE token_id = 3;
        
-- DELETE FROM User WHERE user_id = 1;
-- DELETE FROM Token WHERE token_id = 2;
-- SELECT * FROM User WHERE email = "newemail@example.com" AND password = "123456789";
DELETE FROM User WHERE user_id = 9;
DELETE user, token FROM Token token JOIN User user ON token.user = user.user_id WHERE token.jwt_value = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZWxnYWRvcGllcnJlYWxleGFuZHJlQGdtYWlsLmNvbSIsImp0aSI6ImQ3NmI2M2I0LTE0OTAtNDAzYi1hNWY3LTc2ZTRhZjZhYmJiOSIsImlhdCI6MTcwMjE5NTEyMCwiZXhwIjoxNzAyMjgxNTIwfQ.k5wFI_8PMMSWLceRj7helf0UnUIMpZCuYNiG5Ei9NMA";
SELECT user.user_id, user.firstname, user.lastname, user.email, user.password FROM Token token JOIN User user ON token.user = user.user_id WHERE token.jwt_value = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZWxnYWRvcGllcnJlYWxleGFuZHJlQGdtYWlsLmNvbSIsImp0aSI6ImQ3NmI2M2I0LTE0OTAtNDAzYi1hNWY3LTc2ZTRhZjZhYmJiOSIsImlhdCI6MTcwMjE5NTEyMCwiZXhwIjoxNzAyMjgxNTIwfQ.k5wFI_8PMMSWLceRj7helf0UnUIMpZCuYNiG5Ei9NMA";
SELECT * FROM User;
SELECT * FROM Token;


