package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import db.InvalidEntryException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
import model.utils.ValidationUtil;

public class Program {
	private static final int EXIT_OPTION = 0;
	private static final int SELLER_OPTION = 1;
	private static final int DEPARTMENT_OPTION = 2;

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		int menuOption;

		do {
			menuOption = displayMainOptions(sc);

			switch (menuOption) {
			case SELLER_OPTION:
				sellerOptions(sc);
				break;
			case DEPARTMENT_OPTION:
				departmentOptions(sc);
				sc.nextLine();
				break;
			case EXIT_OPTION:
				System.out.println("EXITED");
				break;
			default:
				System.out.println("Invalid Option. Try again.");
				break;
			}
		} while (menuOption != EXIT_OPTION);

		sc.close();
	}

	private static int displayMainOptions(Scanner sc) {
		System.out.println("=== SELECT AN OPTION ===");
		System.out.println("1. Seller Options");
		System.out.println("2. Department Options");
		System.out.println("0. Exit");
		return sc.nextInt();
	}

	private static void sellerOptions(Scanner sc) {
		int sellerOption;
		do {
			System.out.println("=== SELLER OPTIONS ===");
			System.out.println("1. Find by ID");
			System.out.println("2. Find seller by department ID");
			System.out.println("3. Find all");
			System.out.println("4. Insert new seller");
			System.out.println("5. Update seller");
			System.out.println("6. Delete seller");
			System.out.println("0. Return");
			sellerOption = sc.nextInt();

			if (sellerOption != EXIT_OPTION) {
				initializeOption(SELLER_OPTION, sellerOption, sc);
			}
		} while (sellerOption != EXIT_OPTION);
	}

	private static void departmentOptions(Scanner sc) {
		System.out.println("=== DEPARTMENT OPTIONS ===");
		System.out.println("1. Find by ID");
		System.out.println("2. Find all");
		System.out.println("3. Insert department");
		System.out.println("0. Return");
		int departmentOption = sc.nextInt();

		if (departmentOption != EXIT_OPTION) {
			initializeOption(DEPARTMENT_OPTION, departmentOption, sc);
		}
	}

	private static void initializeOption(int menuOption, int options, Scanner sc) {
		switch (menuOption) {
		case 1:
			initializeSellerOptions(options, sc);
			break;
		case 2:
			initializeDepartmentOptions(options, sc);
			break;
		}
	}

	private static void initializeSellerOptions(int options, Scanner sc) {

		switch (options) {
		case 1:
			FindSellerByIdOption(sc);
			break;
		case 2:
			FindByDepartmentIdSellerOption(sc);
			break;
		case 3:
			FindAllSellerOption(sc);
			break;
		case 4:
			InsertSellerOption(sc);
			break;
		case 5:
			UptadeSellerOption(sc);
			break;
		case 6:
			DeleteSellerOption(sc);
		default:
			sellerOptions(sc);
			break;
		}

	}

	private static void UptadeSellerOption(Scanner sc) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("===UPDATE SELLER===");
		Seller seller = sellerDao.findById(ValidationUtil.validateIntegerEntry(sc));
		System.out.println(seller);
		System.out.println("UPTDATE:");
		System.out.println("1. Name");
		System.out.println("2. Email");
		System.out.println("3. BirthDate");
		System.out.println("4. Base salary");

		switch (ValidationUtil.validateIntegerEntry(sc)) {
		case 1:
			System.out.print("Update name: ");
			String name;
			sc.nextLine();
			try {
				ValidationUtil.ValidateSellerName(name = sc.nextLine());
				seller.setName(name);
			} catch (InvalidEntryException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Update completed!");
			break;
		case 2:
			System.out.print("Update email: ");
			String email;
			sc.nextLine();
			try {
				ValidationUtil.ValidateSellerEmail(email = sc.nextLine());
				seller.setName(email);
			} catch (InvalidEntryException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Update completed!");
			break;
		case 3:
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date birthDate = null;
			sc.nextLine();

			while (birthDate == null) {
				try {
					System.out.print("Birthdate (DD/MM/YYYY): ");
					ValidationUtil.ValidateSellerAge(birthDate = sdf.parse(sc.next()));
				} catch (InvalidEntryException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					birthDate = null;
				} catch (ParseException e) {
					System.out.println("Error: Invalid date! Please use the format DD/MM/YYYY.\n");
					birthDate = null;
				}
				seller.setBirthDate(birthDate);
			}
			System.out.println("Update completed!");
			break;
		case 4:
			System.out.print("Base salary: ");
			seller.setBaseSalary(sc.nextDouble());
			System.out.println("Update completed!");
			break;
		default:
			break;
		}

	}

	private static void DeleteSellerOption(Scanner sc) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("\n=== Delete Seller ===");
		int id = ValidationUtil.validateIntegerEntry(sc);
		Seller seller = sellerDao.findById(id);
		System.out.println("Delete seller:" + seller.getName() + "? (y/n)");
		char i = sc.next().charAt(0);
		if (i == 'y') {
			sellerDao.deleteById(1);
			System.out.println("\n--- SELLER DELETED ---\n");
		}

	}

	private static void FindByDepartmentIdSellerOption(Scanner sc) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("\n===FIND SELLER BY DEPARTMENT ID===");
		Department department = new Department(ValidationUtil.validateIntegerEntry(sc), null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}
		System.out.println();
	}

	private static void FindAllSellerOption(Scanner sc) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("\n===FIND All===");
		List<Seller> list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}

	}

	private static void FindSellerByIdOption(Scanner sc) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("\n===FIND SELLER BY ID===");
		Seller seller = sellerDao.findById(ValidationUtil.validateIntegerEntry(sc));
		System.out.println(seller + "\n");
	}

	private static void InsertSellerOption(Scanner sc) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		char check;
		do {
			sc.nextLine();
			System.out.println("\n===INSERT NEW SELLER===");
			String name = null;
			while (name == null) {
				try {
					System.out.println("Name: ");
					ValidationUtil.ValidateSellerName(name = sc.nextLine());
				} catch (InvalidEntryException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					name = null;
				}
			}
			String email = null;

			while (email == null) {
				try {
					System.out.println("Email: ");
					ValidationUtil.ValidateSellerEmail(email = sc.nextLine());
				} catch (InvalidEntryException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					email = null;
				}
			}
			Date birthDate = null;

			while (birthDate == null) {
				try {
					System.out.println("Birthdate (DD/MM/YYYY): ");
					ValidationUtil.ValidateSellerAge(birthDate = sdf.parse(sc.next()));
				} catch (InvalidEntryException e) {
					System.out.println("\n" + e.getMessage() + "\n");
					birthDate = null;
				} catch (ParseException e) {
					System.out.println("Error: Invalid date! Please use the format DD/MM/YYYY.\n");
					birthDate = null;
				}
			}
			System.out.println("Base salary: ");
			double baseSalary = sc.nextDouble();

			// TODO find a way to check if department id exist
			System.out.println("Department id: ");
			int depId = sc.nextInt();

			Department dep = departmentDao.findById(depId);
			System.out.println("\nName: " + name + "\nEmail: " + email + "\nBirthDate: " + sdf.format(birthDate)
					+ "\nBase salary: " + baseSalary + "\nDerpartment: " + dep.getName() + "\n\nCONFIRM? (y/n) ");
			check = sc.next().charAt(0);

			if (check == 'y') {
				dep = new Department(depId, null);
				Seller newSeller = new Seller(null, name, email, birthDate, baseSalary, dep);
				sellerDao.insert(newSeller);
				System.out.println("\n+++ SELLER ADDED SUCCESSFULLY! +++\n\n\n");

			}
			if (check == 'n') {
				System.out.println("DO YOU WANT TO INSERT AGAIN? (y/n)");
				char a = sc.next().charAt(0);
				if (a == 'n') {
					break;
				}
			}
		} while (check != 'y');

	}

	private static void initializeDepartmentOptions(int options, Scanner sc) {
		DepartmentDao department = DaoFactory.createDepartmentDao();
		switch (options) {
		case 1:
			FindDepartmentByIdOption(sc);
			break;
		case 2:
			FindAllDepartmentOption(sc);
			break;
		case 3:
			InsertDepartmentOption(sc);
			break;
		case 4:
			UpdateDepartmentOption(sc);
			break;
		case 5:
			DeleteDepartmentOption(sc);
			break;
		}

	}

	private static void DeleteDepartmentOption(Scanner sc) {
		// TODO Auto-generated method stub

	}

	private static void UpdateDepartmentOption(Scanner sc) {
		// TODO Auto-generated method stub

	}

	private static void InsertDepartmentOption(Scanner sc) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		System.out.println("\n===INSERT DEPARTMENT===");

		String name = null;
		sc.nextLine();
		while (name == null) {
			try {
				System.out.print("Insert new Department Name: ");
				ValidationUtil.validateDepartmentName(name = sc.nextLine());
			} catch (InvalidEntryException e) {
				name = null;
				System.out.println(e.getMessage());
			}
		}
		Department department = new Department(null, name);
		departmentDao.insert(department);
		System.out.println("\n+++ DEPARTMENT ADDED SUCCESSFULLY! +++\n");
		sc.next();
	}

	private static void FindAllDepartmentOption(Scanner sc) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		System.out.println("\n===SHOW All===");

		Set<Department> list = departmentDao.findAll();
		for (Department obj : list) {
			System.out.println(obj);
		}

	}

	private static void FindDepartmentByIdOption(Scanner sc) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		System.out.println("\n===FIND DEPARTMENT BY ID===");
		Department department = departmentDao.findById(ValidationUtil.validateIntegerEntry(sc));
		System.out.println(department + "\n");
	}

}

/*
 * SellerDao sellerDao = DaoFactory.createSellerDao();
 * 
 * System.out.println("=== TEST 1: seller findById ====="); Seller seller =
 * sellerDao.findById(3); System.out.println(seller);
 * 
 * System.out.println("\n=== TEST 2: seller findByDepartmentId =====");
 * Department department = new Department(2, null); List<Seller> list =
 * sellerDao.findByDepartment(department); for (Seller obj : list) {
 * System.out.println(obj); }
 * System.out.println("\n=== TEST 3: seller findAll ====="); list =
 * sellerDao.findAll(); for (Seller obj : list) { System.out.println(obj); }
 * 
 * System.out.println("\n=== TEST 4: seller insert ====="); Seller newSeller =
 * new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.00, department);
 * sellerDao.insert(newSeller); System.out.println("Inserted! New id = " +
 * newSeller.getId());
 * 
 * System.out.println("\n=== TEST 5: seller update ====="); seller =
 * sellerDao.findById(1); seller.setName("Marthe Waine");
 * sellerDao.update(seller); System.out.println("Update completed!");
 * 
 * System.out.println("\n=== TEST 6: seller delete =====");
 * System.out.println("Enter id for delete test:"); int id = sc.nextInt();
 * sellerDao.deleteById(id); System.out.println("Delete completed");
 * 
 * sc.close();
 */