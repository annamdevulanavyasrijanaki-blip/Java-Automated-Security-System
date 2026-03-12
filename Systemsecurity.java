/**
 * Project: Automated Security System (Phase 1)
 * Goal: Check user eligibility based on multiple criteria.
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;   // Needed to open files
import java.io.PrintWriter;  // Needed to write text to files
import java.util.Scanner;

public class Systemsecurity {
	// Configuration Settings
	private static final int MINIMUM_AGE=18;
	private static final String[] AUTHORIZED_PASSWORDS = { "Admin123", "Staff456" , "Google2026"};
	// System State
	private static int failedAttempts = 0; // Tracks consecutive failures
	
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		boolean keepRunning= true;
		
		System.out.println("--- WELCOME TO GOOGLE SECURITY 2026 ---");
		
		while(keepRunning) {
			System.out.println("\\n[MAIN MENU]");
			System.out.println("1. Login to Dashboard");
	        System.out.println("2. View Security Info");
	        System.out.println("3. Exit System");
	        System.out.println("4. View Logs");
	        System.out.print("Select an option: ");
	        
	        String choice = sc.next();
	        
	        switch (choice) {
            case "1":
                runSecurityCheck(sc); // We moved your login logic to its own method
                break;
            case "2":
                System.out.println("Security Level: HIGH. Minimum Age: " + MINIMUM_AGE);
                break;
            case "3":
                System.out.println("Shutting down... Goodbye.");
                keepRunning = false; // This breaks the loop!
                break;
            case "4":
            	viewLogs();
            	break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
		}
		sc.close();// Clean up resources only once at the very end
	}
	
	/**
     * Handles the user interaction for a login attempt.
     */
	public static void runSecurityCheck(Scanner sc) {
		// Check if they are already locked out
	    if (failedAttempts >= 3) {
	        System.out.println("\n[SECURITY ALERT] System is LOCKED due to 3 failed attempts.");
	        System.out.println("Please contact an administrator.");
	        return; // This exits the method immediately
	    }
	    
		try {
		System.out.print("Enter your Age:");
		int Age = sc.nextInt();
		
		System.out.print("Do you have a premium account?(true/false)");
		boolean hasPremium = sc.nextBoolean();
		
		sc.nextLine();// Clear buffer before reading string
		System.out.print("Enter your password:");
		String inputPassword = sc.next();
		
		boolean isAllowed=checkAccess(Age,hasPremium,inputPassword);
		// LOGGING THE RESULT
        // We pass the age and the result to our log file
		logAttempt(Age,isAllowed);
		
		if (isAllowed) {
			System.out.println("\n[SUCCESS] Access Granted. Welcome to the dashboard.");
			failedAttempts = 0; // RESET the counter 
		}
		else {
			System.out.println("\n[DENIED] You do not meet the security requirements.");
			failedAttempts++; // Add a strike
            int remaining = 3 - failedAttempts;
            if (remaining > 0) {
                System.out.println("Attempts remaining: " + remaining);
            }
        }
	}
		catch (Exception e){
			System.out.println("\n[ERROR] Invalid input. Please enter numbers for age and true/false for premium.");
			sc.nextLine();
		}
	}
	
	
	/**
     * Logic engine: Validates age, premium status, and password.
     */
	public static boolean checkAccess(int Age, boolean hasPremium , String inputPassword) {
		if(Age < MINIMUM_AGE || !hasPremium) {
			return false;
		}
		for (String authorized : AUTHORIZED_PASSWORDS) {
			if (inputPassword.equalsIgnoreCase(authorized)) {
				return true;
			}
		}
		return false;
	}
	
	/**
     * Writes attempt details to 'security_logs.txt' with a timestamp.
     */
	public static void logAttempt(int Age, boolean isAllowed) {
		// 1. Get the current time
	    LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter Formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String FormattedTime = now.format(Formatter);
		try(PrintWriter out = new PrintWriter(new FileWriter("security_logs.txt", true))){
			String status=isAllowed? "Granted" : "Denied";
			out.println(" [" + FormattedTime + "]Attempt - Age: " + Age + " | Status: " + status);
			System.out.println("...Log updated in security_logs.txt");
		}catch (Exception e) {
            System.out.println("Could not write to log file.");
        }
	}
	/**
     * Reads and displays the entire history from the log file.
     */
	public static void viewLogs() {
	    System.out.println("\n--- SECURITY LOG HISTORY ---");
	    // We use BufferedReader to "Read" the file
	    try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("security_logs.txt"))) {
	        String line;
	        // This loop says: "Keep reading lines until there are no more (null)"
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line);
	        }
	    } catch (java.io.FileNotFoundException e) {
	        System.out.println("No logs found yet. Try logging in first!");
	    } catch (Exception e) {
	        System.out.println("Error reading log file.");
	    }
	    System.out.println("----------------------------");
	}

}
