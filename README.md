# PassphraseHasher
plain text to SHA256 passphrase hasher

Console menu that emulates the Forgot Password? feature of websites. When a user is created, 
the hashed output of the provided password is stored. When a user decides to reset their password,
a login password attempt is submitted, hashed, and compared to the stored hash.
