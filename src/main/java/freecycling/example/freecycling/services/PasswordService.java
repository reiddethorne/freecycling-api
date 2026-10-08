package freecycling.example.freecycling.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

@Service
public class PasswordService {

	private final String secret;

	public PasswordService(@Value("${app.security.password-pepper}") String secret) {
		this.secret = secret;
	}
	
	public String hashPassword(String password) {
		BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

		Hash hash = Password.hash(password)
		                    .addPepper(secret)
		                    .with(bcrypt);

		return hash.getResult();
	}
	
	public boolean verifyHash(String password,String hash) {
		BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

		return Password.check(password, hash)
		               .addPepper(secret)
		               .with(bcrypt);
	}
}
