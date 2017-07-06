package com.hodor.corejava.jdbc.launcher;

import java.sql.SQLException;
import java.util.Scanner;

import com.hodor.corejava.jdbc.common.DatabaseUtils;

/**
 * 
 * @author sham
 *
 */
public class ApplicationLauncherUtils {

	/**
	 * printMenu() is used to print the menu with some predefined option on the
	 * console
	 */
	private static void printMenu() {
		System.out.println(
				"**************************Persons Database Application*********************************************************");
		System.out.println("1. Create  a Database/Table");
		System.out.println("2. Display a Data");
		System.out.println("3. Insert  a Record");
		System.out.println("4. Delete  a Record");
		System.out.println("5. Update  a Record");
		System.out.println("6. Batch Update");
		System.out.println("Enter your choice:");
	}

	public static void launchApplication() {
		Integer ch;
		Scanner scanner = null;
		try {
			do {
				scanner = new Scanner(System.in);
				printMenu();
				ch = scanner.nextInt();
				switch (ch) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				default:
					System.out.println("You have entered the wrong choice");
					break;
				}
			} while (ch != 'n' || ch != 'N');
		} catch (Throwable ex) {
			if (ex instanceof SQLException) {
				DatabaseUtils.alternatePrintSQLException((SQLException) ex);
			}
		} finally {
			scanner.close();
		}
	}
}
