Battle Arena project complete with servers.
==
Battle Arena login server design
Server waits for login requests containing username and password.
Takes credentials and sends the to website. If website returns true means user is login.
Server generates and MD5 hash of the current timestamp and sends it to the client as a salt key.
Then stores user in the user array.
Any further loggin requests without password will require that the username is sent with a security key generated with MD5(username+salt_key)
Login server then validates with its generated security key and returns if its valid or not.
