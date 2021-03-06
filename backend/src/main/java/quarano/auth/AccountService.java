package quarano.auth;

import lombok.RequiredArgsConstructor;
import quarano.department.Department.DepartmentIdentifier;
import quarano.tracking.TrackedPerson.TrackedPersonIdentifier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountService {

	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accounts;
	private final RoleRepository roles;

	private final Log logger = LogFactory.getLog(AccountService.class);

	/**
	 * creates a new account, encrypts the password and stores it
	 * 
	 * @param username
	 * @param unencryptedPassword
	 * @param firstname
	 * @param lastename
	 * @param departmentId
	 * @param clientId
	 * @param roleType
	 * @return
	 */
	public Account createAndStoreAccount(String username, String unencryptedPassword, String firstname, String lastename,
			DepartmentIdentifier departmentId, TrackedPersonIdentifier trackedPersonIdentifier, RoleType roleType) {

		String encryptedPassword = passwordEncoder.encode(unencryptedPassword);

		Role role = roles.findByName(roleType.toString());
		Account account = new Account(username, encryptedPassword, firstname, lastename, departmentId,
				trackedPersonIdentifier, role);
		account = accounts.save(account);

		logger.info("Created account for client " + trackedPersonIdentifier + " with username " + username);

		return account;
	}

	public boolean isUsernameAvailable(String userName) {
		return accounts.findByUsername(userName).isEmpty();
	}

}
