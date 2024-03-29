package com.example.demo.Validator;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		LocalDate today = LocalDate.now().plusDays(1);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}

		if (!user.getEmail().equals(user.getEmailConfirm())) {
			errors.rejectValue("emailConfirm", "Diff.userForm.emailConfirm");
		}
		
		System.out.println(user.getDateNaissance().plusYears(18).isBefore(today));
		if (!user.getDateNaissance().plusYears(18).isBefore(today)) {
			errors.rejectValue("dateNaissance", "Diff.userForm.dateConfirm");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateNaissance", "NotEmpty");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adresse", "NotEmpty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ville", "NotEmpty");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codePostal", "NotEmpty");

		//        if (user.getEmail())

		// INSERER MESSAGE ERREUR //
	}
}
