# darwin
A simple Spring Boot web application that performs:

[post] /user
Saving a user with the following mandatory details in the request's body:
email, firstName, lastName, password, phoneNumber, department, roleTitle
* If the user's email is already saved, his details will be updated.

[get]  /user?email={email address}
Getting user's details by his email.

[delete]  /user?email={email address}
Deleting a user by his email.
