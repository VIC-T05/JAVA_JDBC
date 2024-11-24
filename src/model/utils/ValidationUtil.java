package model.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import db.InvalidEntryException;

public class ValidationUtil {

	public static void ValidateSellerAge(Date birthDate) {
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

	public static void ValidateSellerEmail(String email) {
		// BUSINESS RULES: EMAIL MUST HAVE THE @companyname.com DOMAIN
		String regex = "^[\\w.-]+@companyname\\.com$";
		if (!email.matches(regex)) {
			throw new InvalidEntryException("Error. Email must contain '@companyname.com' domain");
		}
	}

	public static void ValidateSellerName(String name) {
		// BUSINESS RULE: SELLER'S NAME MUST START WITH AN UPPERCASE LETTER FOR BOTH
		// FIRST AND LAST NAMES,
		// BE SEPARATED BY A SINGLE SPACE, AND CONTAIN ONLY ALPHABETIC CHARACTERS.
		String regex = "^[A-Z][a-z]+ [A-Z][a-z]+$";
		if (!name.matches(regex)) {
			throw new InvalidEntryException("Error. It must start with uppercase letters and have two names.");
		}
	}

	public static Integer validateIntegerEntry(Scanner sc) {
		int id = 0;
		boolean valid = false;
		do {
			try {
				System.out.print("Enter a number: ");
				id = sc.nextInt();
				valid = true;

			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Error. Invalid Entry!");
			}
		} while (!valid);
		return id;

	}

	public static void validateDepartmentName(String name) {
		// BUSINESS RULE: DEPARTMENT'S NAME MUST STAR WITH AN UPPERCASE LETTER	
		String regex = "^[A-Z][a-zA-Z]*$";
		if (!name.matches(regex)) {
			throw new InvalidEntryException("Error. It must star with uppercase letters!");
		}
	}
}

