package manageStore.seminolestate.edu;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

import store.seminole.state.edu.Customer;

public class MainStore {

	private static Scanner input = new Scanner(System.in);

	private static final int CUSTOMER_MENU = 1;
	private static final int VENDOR_MENU = 2;
	private static final int INSTRUMENT_MENU = 3;
	private static final int SALES_MENU = 4;
	private static final int EXIT = 5;


	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, SQLException, InstantiationException {

		
		String url      = "jdbc:mysql://localhost:3306/";
		String user     = "root";
		String password = "";
		
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection(url, user, password);
		
		Statement stt = con.createStatement();
		stt.execute("USE music_store");

	
		int response;

		do {
			response = menu();
			switch (response) {

			case CUSTOMER_MENU:
				int selection = 0;
				do {
					try {
						System.out.print("---Customer Menu---\n\n(1). Add a Customer. \n(2). List all Customers. \n(3)."
								+ " Find Customer by ID. \n(4). Change Customer information. \n(5). Exit \n");
						selection = Integer.parseInt(input.nextLine());
						if (selection < 1 || selection > 5)
							System.out.print("Enter a value between 1 and 5. \n");
					} catch (Exception E) {
						System.out.print("Please enter a valid value. \n");
					}
					if (selection == 1) {
						
						try {
							
								String a =	getLastName("Enter the Customer's Last Name: ");
								String b =	getFirstName("Enter the Customer's First Name: ");
								String c =	getAddress("Enter the Customer's Address: ");
								String d =	getCity("Enter the Customer's City: ");
								String e =	getState("Enter the Customer's State: ");
									
								PreparedStatement prep = con.prepareStatement("INSERT INTO customers (FirstName, LastName, Address, City, State) VALUES (?,?,?,?,?)");
								prep.setString(1, a);
								prep.setString(2, b);
								prep.setString(3, c);
								prep.setString(4, d);
								prep.setString(5, e);
									
								int row = prep.executeUpdate();
								
								
						
							
						} catch (Exception E) {
							System.out.print("There was an error somewhere");
						}
					} else if (selection == 2) {
						
						String query = "SELECT COUNT(*) FROM customers";
					      //Executing the query
					      ResultSet rs = stt.executeQuery(query);
					      //Retrieving the result
					      rs.next();
					      int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no customers to list.\n");
						}
						else {
							System.out.println("-----All Customers listed by their CustomerID------");
							System.out.println();
							ResultSet res = stt.executeQuery("SELECT * FROM customers ORDER BY CustomerID");
							while (res.next()) {
								System.out.println(res.getInt("CustomerID") + " " + res.getString("FirstName") + " " + res.getString("LastName") + " " +
							res.getString("Address") + " " + res.getString("City") + " " + res.getString("State"));
							}
							System.out.println("");
							
							
							}
						}
					 else if (selection == 3) {
						 String query = "SELECT COUNT(*) FROM customers";
					      //Executing the query
					      ResultSet rs = stt.executeQuery(query);
					      //Retrieving the result
					      rs.next();
					      int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no customers to search for.\n");
						}
						
						else {
							System.out.print("Enter the CustomerID for who you would like to look up: ");
							int r = input.nextInt();
							input.nextLine();
							
							PreparedStatement prep = con.prepareStatement("SELECT * FROM customers WHERE CustomerID = ?");
							prep.setInt(1, r);
							
							rs = prep.executeQuery();
							
							if (rs.next()) {
								System.out.println(rs.getInt("CustomerID") + " " + rs.getString("FirstName") + " " + rs.getString("LastName") + " " +
							rs.getString("Address") + " " + rs.getString("City") + " " + rs.getString("State"));
							}
							else {
								System.out.print("The Customer was not found.\n");
							}
							
						}
					} 
					
					else if (selection == 4) {
						
						 String query = "SELECT COUNT(*) FROM customers";
					      //Executing the query
					      ResultSet rs = stt.executeQuery(query);
					      //Retrieving the result
					      rs.next();
					      int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no customers to change.\n");
						} 
						
						else if (count >= 1){
							
							try {
							System.out.print("Enter the CustomerID for who you would like to change: ");
							int r = input.nextInt();
							input.nextLine();
							
							PreparedStatement prep1 = con.prepareStatement("SELECT * FROM customers WHERE CustomerID = ?");
							prep1.setInt(1, r);
							
							rs = prep1.executeQuery(); 
							
							while (rs.next()) {
								System.out.println(rs.getInt("CustomerID") + " " + rs.getString("FirstName") + " " + rs.getString("LastName") + " " +
							rs.getString("Address") + " " + rs.getString("City") + " " + rs.getString("State"));
							}
							
							
							
							
							String a =	getLastName("What would you like to change their Last Name to? ");
							String b =	getFirstName("What would you like to change their First Name to? ");
							String c =	getAddress("What would you like to change their Address to? ");
							String d =	getCity("What would you like to change their City to? ");
							String e =	getState("What would you like to change their State to? ");
								
							PreparedStatement prep = con.prepareStatement("UPDATE customers SET LastName = ?, FirstName = ?, Address = ?, City = ?, State = ? WHERE CustomerID = ?");
							prep.setString(1, a);
							prep.setString(2, b);
							prep.setString(3, c);
							prep.setString(4, d);
							prep.setString(5, e);
							prep.setInt(6, r);
								
							int row = prep.executeUpdate();
							System.out.print("The customer's information has been updated.\n");
							
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n" );
							}
							}
						
						}
					
				
					
				}while (selection != 5);
				System.out.print("Back to the Main Menu.\n");
				break;

			case VENDOR_MENU:
				
				int vSelection = 0;
				do {
					try {
						System.out.print("---Vendor Menu---\n\n(1). Add a Vendor. \n(2). List all Vendors. \n(3)."
								+ " Find Vendor by ID. \n(4). Change Vendor information. \n(5). Delete a vendor \n(6). Exit \n");
						vSelection = Integer.parseInt(input.nextLine());
						if (vSelection < 1 || vSelection > 6)
							System.out.print("Enter a value between 1 and 6. \n");
					} catch (Exception E) {
						System.out.print("Please enter a valid value. \n");
					}
					if (vSelection == 1) {
						
						try {
							
								String a =	getName("Enter the Vendor's Name: ");
								String b =	getAddress("Enter the Vendor's Address: ");
								String c =	getCity("Enter the Vendor's City: ");
								String d =	getState("Enter the Vendor's State: ");
								String e = getLicensce("Enter a state licensce number for the Vendor: ");
									
								PreparedStatement vPrep = con.prepareStatement("INSERT INTO vendor (Name, Address, City, State, LicensceNum) VALUES (?,?,?,?,?)");
								vPrep.setString(1, a);
								vPrep.setString(2, b);
								vPrep.setString(3, c);
								vPrep.setString(4, d);
								vPrep.setString(5, e);
									
								int row = vPrep.executeUpdate();
								
								
						
							
						} catch (Exception E) {
							System.out.print("There was an error somewhere.\n");
							E.printStackTrace();
						}
					}
					else if (vSelection == 2) {
						
						String query = "SELECT COUNT(*) FROM vendor";
					      //Executing the query
					      ResultSet rs = stt.executeQuery(query);
					      //Retrieving the result
					      rs.next();
					      int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to list.\n");
						}
						else {
							System.out.println("-----All Vendors listed by their VendorID------");
							System.out.println();
							ResultSet res = stt.executeQuery("SELECT * FROM vendor ORDER BY VendorID");
							while (res.next()) {
								System.out.println(res.getInt("VendorID") + " " + res.getString("Name") + " " + res.getString("Address") + " " +
							res.getString("City") + " " + res.getString("State") + " " + res.getString("LicensceNum"));
							}
							System.out.println("");
							
							
							}
						}
					else if (vSelection == 3) {
						 String query = "SELECT COUNT(*) FROM vendor";
					      //Executing the query
					      ResultSet rs = stt.executeQuery(query);
					      //Retrieving the result
					      rs.next();
					      int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to search for.\n");
						}
						
						else {
							System.out.print("Enter the VendorID for who you would like to look up: ");
							int r = input.nextInt();
							input.nextLine();
							
							PreparedStatement prep = con.prepareStatement("SELECT * FROM vendor WHERE VendorID = ?");
							prep.setInt(1, r);
							
							rs = prep.executeQuery();
							
							if (rs.next()) {
								System.out.println(rs.getInt("VendorID") + " " + rs.getString("Name") + " " + rs.getString("Address") + " " +
							rs.getString("City") + " " + rs.getString("State") + " " + rs.getString("LicensceNum"));
							}
							else {
								System.out.print("The Vendor was not found.\n");
							}
							
						}
					}
					else if (vSelection == 4) {
						
						 String query = "SELECT COUNT(*) FROM vendor";
					      //Executing the query
					      ResultSet rs = stt.executeQuery(query);
					      //Retrieving the result
					      rs.next();
					      int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to change.\n");
						} 
						
						else if (count >= 1){
							
							try {
							System.out.print("Enter the VendorID for who you would like to change: ");
							int r = input.nextInt();
							input.nextLine();
							
							PreparedStatement prep1 = con.prepareStatement("SELECT * FROM vendor WHERE VendorID = ?");
							prep1.setInt(1, r);
							
							rs = prep1.executeQuery(); 
							
							while (rs.next()) {
								System.out.println(rs.getInt("VendorID") + " " + rs.getString("Name") + " " + rs.getString("Address") + " " +
							rs.getString("City") + " " + rs.getString("State") + " " + rs.getString("LicensceNum"));
							}
							
							
							
							
							String a =	getName("What would you like to change the Vendor's name to? ");
							String b =	getAddress("What would you like to change the Vendor's address to? ");
							String c =	getCity("What would you like to change the Vendor's city to? ");
							String d =	getState("What would you like to change the Vendor's state to? ");
							String e =	getLicensce("What would you like to change the Vendor's License number to? ");
								
							PreparedStatement prep = con.prepareStatement("UPDATE vendor SET Name = ?, Address = ?, City = ?, State = ?, LicensceNum = ? WHERE VendorID = ?");
							prep.setString(1, a);
							prep.setString(2, b);
							prep.setString(3, c);
							prep.setString(4, d);
							prep.setString(5, e);
							prep.setInt(6, r);
								
							int row = prep.executeUpdate();
							System.out.print("The Vendor's information has been updated.\n");
							
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n" );
							}
							}
						
						}
					else if (vSelection == 5) {
						
						String query = "SELECT COUNT(*) FROM vendor";
					      //Executing the query
					      ResultSet rs = stt.executeQuery(query);
					      //Retrieving the result
					      rs.next();
					      int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to delete.\n");
						} 
						
						else if (count >= 1){
							
							try {
							System.out.print("Enter the VendorID for who you would like to delete: ");
							int r = input.nextInt();
							input.nextLine();
							
							PreparedStatement prep1 = con.prepareStatement("SELECT * FROM vendor WHERE VendorID = ?");
							prep1.setInt(1, r);
							
							rs = prep1.executeQuery(); 
							
							while (rs.next()) {
								System.out.println(rs.getInt("VendorID") + " " + rs.getString("Name") + " " + rs.getString("Address") + " " +
							rs.getString("City") + " " + rs.getString("State") + " " + rs.getString("LicensceNum"));
								
							}
							
							
								
								System.out.print("Are you sure you want to delete the Vendor listed above?\nEnter Y for yes or N for no:");
								String answer = input.nextLine();
								answer = answer.toUpperCase();
								char answer1 = answer.charAt(0);
								System.out.println(answer1);
								
								if (answer1 == 'N') {
									System.out.print("The customer was not deleted.\n");
								}
								else if (answer1 == 'Y'){
									PreparedStatement dYPrep = con.prepareStatement("DELETE FROM vendor WHERE VendorID = ?");
									dYPrep.setInt(1, r);
									
									int row = dYPrep.executeUpdate();
									System.out.print("The Vendor's information has been deleted.\n");
									
								}
								
							
							
							
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n" );
							}
							}
						
						
					}
				
					
					
				}while (vSelection != 6);
				System.out.print("Back to the Main Menu.\n");
				break;
				

			case INSTRUMENT_MENU:
				break;
			case SALES_MENU:
				break;
			case EXIT:

			}
		} while (response != EXIT);
	
		
	}

	private static int getInt(String prompt) {
		int r = -1;
		do {
			try {
				System.out.print(prompt);
				r = Integer.parseInt(input.nextLine());

				if (r <= 0)
					System.out.print("Enter a value greater than 0! Re-enter value. \n");
			} catch (Exception E) {
				System.out.print("Enter a valid value. \n");
			}

		} while (r < 0);
		return r;
	}
	private static String getName(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 30) {
				System.out.print("The last name can not be null, less than 1 or greater than 30. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 30);
		return r;
	}
	
	private static String getLastName(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 13) {
				System.out.print("The last name can not be null, less than 1 or greater than 13. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 13);
		return r;
	}

	private static String getFirstName(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 13) {
				System.out.print("The first name can not be null, less than 1 or greater than 13. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 13);
		return r;
	}

	private static String getAddress(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 30) {
				System.out.print("The address can not be null, less than 1 or greater than 30. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 30);
		return r;
	}

	private static String getCity(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 15) {
				System.out.print("The city can not be null, less than 1 or greater than 15. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 15);
		return r;
	}

	private static String getState(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r.length() <= 1 || r.length() > 2) {
				System.out.print("The state can not be null or greater than 2 characters. Re-Enter value. \n");
			}

		} while (r.length() <= 1 || r.length() > 2);
		return r;
	}

	private static String getString(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1) {
				System.out.print("The field can not be null or less than 1. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1);
		return r;
	}
	
	private static String getLicensce(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 30) {
				System.out.print("The field can not be null, more than 30 characters or less than 1. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 30);
		return r;
	}

	
	
	public static int menu() {
		int userResponse = 0;
		do {
			try {
				System.out.print("----Main Menu---- \n\n(1). Customer Menu. \n(2). Vendor Menu \n"
						+ "(3). Instrument Menu \n(4). Sales Menu \n(5). Exit.\n");
				userResponse = Integer.parseInt(input.nextLine());
				if (userResponse < 1 || userResponse > 5) {
					System.out.print("Enter a value of 1 to 5. \n");
				}
			} catch (Exception E) {
				System.out.print("Please enter a valid value. \n");
			}
		} while (userResponse < 1 || userResponse > 5);

		return userResponse;

	}

	
}



