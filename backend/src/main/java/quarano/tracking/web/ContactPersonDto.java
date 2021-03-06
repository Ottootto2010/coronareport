package quarano.tracking.web;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

import lombok.Data;
import lombok.Getter;
import quarano.tracking.ContactPerson.ContactPersonIdentifier;
import quarano.tracking.EmailAddress;
import quarano.tracking.PhoneNumber;
import quarano.tracking.ZipCode;

import java.util.Collections;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.springframework.validation.Errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
class ContactPersonDto {

	@Getter(onMethod = @__(@JsonProperty(access = JsonProperty.Access.READ_ONLY))) //
	private ContactPersonIdentifier id;

	private String lastName, firstName;
	private String street, houseNumber, city;
	private @Pattern(regexp = ZipCode.PATTERN) String zipCode;
	private @Pattern(regexp = PhoneNumber.PATTERN) String phone;
	private @Pattern(regexp = PhoneNumber.PATTERN) String mobilePhone;
	private @Pattern(regexp = EmailAddress.PATTERN) String email;
	private String remark;
	private String identificationHint;
	private Boolean isHealthStaff;
	private Boolean isSenior;
	private Boolean hasPreExistingConditions;

	@JsonProperty("_links")
	@JsonInclude(Include.NON_EMPTY)
	public Map<String, Object> getLinks() {

		if (id == null) {
			return Collections.emptyMap();
		}

		var contactResource = on(ContactPersonController.class).getContact(null, id);

		return Map.of("self", Map.of("href", fromMethodCall(contactResource).toUriString()));
	}

	Errors validate(Errors errors) {

		if (phone == null && email == null && mobilePhone == null) {
			errors.rejectValue("phone", "Invalid.contactWays");
			errors.rejectValue("mobilePhone", "Invalid.contactWays");
			errors.rejectValue("email", "Invalid.contactWays");
		}

		return errors;
	}
}
