package model.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import db.InvalidEntryException;

public class ValidationUtil {

	public static void verifyAge(Date birthDate) {
		LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate currentDate = LocalDate.now();

		int age = java.time.Period.between(birthLocalDate, currentDate).getYears();

		if (age < 18) {
			throw new InvalidEntryException("Error. Seller age must be at least 18 years");
		}
		if (age > 110) {
			throw new InvalidEntryException("Error. Invalid Date");
		}
	}

	public static void verifyEmail(String email) {
		// BUSSINES RULES: EMAIL MUST HAVE THE @companyname.com DOMAIN
		String regex = "^[\\w.-]+@companyname\\.com$";
		if (!email.matches(regex)) {
			throw new InvalidEntryException("Error. Email must contain '@companyname.com' domain");
		}			
		}

}
