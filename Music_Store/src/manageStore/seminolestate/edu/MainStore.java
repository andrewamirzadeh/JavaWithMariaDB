package manageStore.seminolestate.edu;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.sql.*;

public class MainStore {

	private static Scanner input = new Scanner(System.in);

	private static final int CUSTOMER_MENU = 1;
	private static final int VENDOR_MENU = 2;
	private static final int INSTRUMENT_MENU = 3;
	private static final int SALES_MENU = 4;
	private static final int EXIT = 5;

	@SuppressWarnings("deprecation")
	public static void main(String[] args)
			throws IllegalAccessException, ClassNotFoundException, SQLException, InstantiationException {

		String url = "jdbc:mysql://localhost:3306/";
		String user = "root";
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

							String a = getLastName("Enter the Customer's Last Name: ");
							String b = getFirstName("Enter the Customer's First Name: ");
							String c = getAddress("Enter the Customer's Address: ");
							String d = getCity("Enter the Customer's City: ");
							String e = getState("Enter the Customer's State: ");

							PreparedStatement prep = con.prepareStatement(
									"INSERT INTO customers (LastName, FirstName, Address, City, State) VALUES (?,?,?,?,?)");
							prep.setString(1, a);
							prep.setString(2, b);
							prep.setString(3, c);
							prep.setString(4, d);
							prep.setString(5, e);

							int row = prep.executeUpdate();
							System.out.print("The customer was entered succesfully.\n");
							selection = 0;

						} catch (Exception E) {
							System.out.print("There was an error somewhere");
						}
					} else if (selection == 2) {

						String query = "SELECT COUNT(*) FROM customers";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no customers to list.\n");
						} else {
							System.out.println("-----All Customers listed by their CustomerID------");
							System.out.println();
							ResultSet res = stt.executeQuery("SELECT * FROM customers ORDER BY CustomerID");
							while (res.next()) {
								System.out.println(res.getInt("CustomerID") + " " + res.getString("FirstName") + " "
										+ res.getString("LastName") + " " + res.getString("Address") + " "
										+ res.getString("City") + " " + res.getString("State"));
							}
							System.out.println("");

						}
						selection = 0;
					} else if (selection == 3) {
						String query = "SELECT COUNT(*) FROM customers";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no customers to search for.\n");
						}

						else {

							int r = getInt("Enter the CustomerID for who you would like to look up: ");
							// System.out.print("Enter the CustomerID for who you would like to look up: ");
							// int r = input.nextInt();
							// input.nextLine();

							PreparedStatement prep = con
									.prepareStatement("SELECT * FROM customers WHERE CustomerID = ?");
							prep.setInt(1, r);

							rs = prep.executeQuery();

							if (rs.next()) {
								System.out.println(rs.getInt("CustomerID") + " " + rs.getString("FirstName") + " "
										+ rs.getString("LastName") + " " + rs.getString("Address") + " "
										+ rs.getString("City") + " " + rs.getString("State"));
							} else {
								System.out.print("The Customer was not found.\n");
							}

						}
						selection = 0;
					}

					else if (selection == 4) {

						String query = "SELECT COUNT(*) FROM customers";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no customers to change.\n");
						}

						else if (count >= 1) {

							try {
								int r = getInt("Enter the CustomerID for who you would like to change: ");
								// System.out.print("Enter the CustomerID for who you would like to change: ");
								// int r = input.nextInt();
								// input.nextLine();

								PreparedStatement prep1 = con
										.prepareStatement("SELECT * FROM customers WHERE CustomerID = ?");
								prep1.setInt(1, r);

								rs = prep1.executeQuery();

								if (!rs.next()) {
									System.out.print("There is no customer with that ID.\n");
									// break;
								} else {

									rs = prep1.executeQuery();
									while (rs.next()) {
										System.out.println(rs.getInt("CustomerID") + " " + rs.getString("FirstName")
												+ " " + rs.getString("LastName") + " " + rs.getString("Address") + " "
												+ rs.getString("City") + " " + rs.getString("State"));
									}

									String a = getLastName("What would you like to change their Last Name to? ");
									String b = getFirstName("What would you like to change their First Name to? ");
									String c = getAddress("What would you like to change their Address to? ");
									String d = getCity("What would you like to change their City to? ");
									String e = getState("What would you like to change their State to? ");

									PreparedStatement prep = con.prepareStatement(
											"UPDATE customers SET LastName = ?, FirstName = ?, Address = ?, City = ?, State = ? WHERE CustomerID = ?");
									prep.setString(1, a);
									prep.setString(2, b);
									prep.setString(3, c);
									prep.setString(4, d);
									prep.setString(5, e);
									prep.setInt(6, r);

									int row = prep.executeUpdate();
									System.out.print("The customer's information has been updated.\n");
								}
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n");
							}
						}
						selection = 0;

					}

				} while (selection != 5);
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

							String a = getName("Enter the Vendor's Name: ");
							String b = getAddress("Enter the Vendor's Address: ");
							String c = getCity("Enter the Vendor's City: ");
							String d = getState("Enter the Vendor's State: ");
							String e = getLicensce("Enter a state licensce number for the Vendor: ");

							PreparedStatement vPrep = con.prepareStatement(
									"INSERT INTO vendor (Name, Address, City, State, LicensceNum) VALUES (?,?,?,?,?)");
							vPrep.setString(1, a);
							vPrep.setString(2, b);
							vPrep.setString(3, c);
							vPrep.setString(4, d);
							vPrep.setString(5, e);

							int row = vPrep.executeUpdate();

							System.out.print("The vendor was succesfully created.\n");

						} catch (Exception E) {
							System.out.print("There was an error somewhere.\n");
							E.printStackTrace();
						}
						vSelection = 0;
					} else if (vSelection == 2) {

						String query = "SELECT COUNT(*) FROM vendor";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to list.\n");
						} else {
							System.out.println("-----All Vendors listed by their VendorID------");
							System.out.println();
							ResultSet res = stt.executeQuery("SELECT * FROM vendor ORDER BY VendorID");
							while (res.next()) {
								System.out.println(res.getInt("VendorID") + " " + res.getString("Name") + " "
										+ res.getString("Address") + " " + res.getString("City") + " "
										+ res.getString("State") + " " + res.getString("LicensceNum"));
							}
							System.out.println("");

						}
						vSelection = 0;
					} else if (vSelection == 3) {
						String query = "SELECT COUNT(*) FROM vendor";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to search for.\n");
						}

						else {
							int r = getInt("Enter the VendorID for who you would like to look up: ");
							// System.out.print("Enter the VendorID for who you would like to look up: ");
							// int r = input.nextInt();
							// input.nextLine();

							PreparedStatement prep = con.prepareStatement("SELECT * FROM vendor WHERE VendorID = ?");
							prep.setInt(1, r);

							rs = prep.executeQuery();

							if (rs.next()) {
								System.out.println(rs.getInt("VendorID") + " " + rs.getString("Name") + " "
										+ rs.getString("Address") + " " + rs.getString("City") + " "
										+ rs.getString("State") + " " + rs.getString("LicensceNum"));
							} else {
								System.out.print("The Vendor was not found.\n");
							}

						}
						vSelection = 0;
					} else if (vSelection == 4) {

						String query = "SELECT COUNT(*) FROM vendor";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to change.\n");
						}

						else if (count >= 1) {

							try {

								int r = getInt("Enter the Vendor for who you would like to change: ");
								// System.out.print("Enter the VendorID for who you would like to change: ");
								// int r = input.nextInt();
								// input.nextLine();

								PreparedStatement prep1 = con
										.prepareStatement("SELECT * FROM vendor WHERE VendorID = ?");
								prep1.setInt(1, r);

								rs = prep1.executeQuery();

								if (!rs.next()) {
									System.out.print("There is no Vendor with that ID.\n");
									// break;
								}

								else {
									rs = prep1.executeQuery();
									while (rs.next()) {

										System.out.println(rs.getInt("VendorID") + " " + rs.getString("Name") + " "
												+ rs.getString("Address") + " " + rs.getString("City") + " "
												+ rs.getString("State") + " " + rs.getString("LicensceNum"));
									}

									String a = getName("What would you like to change the Vendor's name to? ");
									String b = getAddress("What would you like to change the Vendor's address to? ");
									String c = getCity("What would you like to change the Vendor's city to? ");
									String d = getState("What would you like to change the Vendor's state to? ");
									String e = getLicensce(
											"What would you like to change the Vendor's License number to? ");

									PreparedStatement prep = con.prepareStatement(
											"UPDATE vendor SET Name = ?, Address = ?, City = ?, State = ?, LicensceNum = ? WHERE VendorID = ?");
									prep.setString(1, a);
									prep.setString(2, b);
									prep.setString(3, c);
									prep.setString(4, d);
									prep.setString(5, e);
									prep.setInt(6, r);

									int row = prep.executeUpdate();
									System.out.print("The Vendor's information has been updated.\n");

								}
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n");
							}
						}
						vSelection = 0;
					} else if (vSelection == 5) {

						String query = "SELECT COUNT(*) FROM vendor";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Vendors to delete.\n");
						}

						else if (count >= 1) {

							try {

								int r = getInt("Enter the VendorID for who you would like to delete: ");
								// System.out.print("Enter the VendorID for who you would like to delete: ");
								// int r = input.nextInt();
								// input.nextLine();

								PreparedStatement prep1 = con
										.prepareStatement("SELECT * FROM vendor WHERE VendorID = ?");
								prep1.setInt(1, r);

								rs = prep1.executeQuery();

								if (!rs.next()) {
									System.out.print("There is no Vendor with that ID to delete.\n");
									// break;
								} else {
									rs = prep1.executeQuery();
									while (rs.next()) {
										System.out.println(rs.getInt("VendorID") + " " + rs.getString("Name") + " "
												+ rs.getString("Address") + " " + rs.getString("City") + " "
												+ rs.getString("State") + " " + rs.getString("LicensceNum"));

									}

									if (count == 0) {
										System.out.print("There are no Vendors to search for.\n");
									}
									String query2 = "SELECT COUNT(*) FROM instruments WHERE VendorID = " + r;
									// Executing the query
									ResultSet rs2 = stt.executeQuery(query2);
									// Retrieving the result
									rs2.next();
									int count2 = rs2.getInt(1);

									if (count2 >= 1) {
										System.out.println(
												"The Vendor can not be deleted because it has instruments in the database.");
									} else {
										System.out.print(
												"Are you sure you want to delete the Vendor listed above?\nEnter Y for yes or N for no:");
										String answer = input.nextLine();

										answer = answer.toUpperCase();
										char answer1 = answer.charAt(0);
										System.out.println(answer1);

										if (answer1 == 'N' || answer1 == 'n') {
											System.out.print("The vendor was not deleted.\n");
										} else if (answer1 == 'Y' || answer1 == 'y') {
											PreparedStatement dYPrep = con
													.prepareStatement("DELETE FROM vendor WHERE VendorID = ?");
											dYPrep.setInt(1, r);

											int row = dYPrep.executeUpdate();
											System.out.print("The Vendor's information has been deleted.\n");

										}

									}
								}
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n");
							}
						}

						vSelection = 0;
					}

				} while (vSelection != 6);
				System.out.print("Back to the Main Menu.\n");
				break;

			case INSTRUMENT_MENU:

				int iSelection = 0;
				do {
					try {
						System.out.print(
								"---Instrument Menu---\n\n(1). Add an Instrument. \n(2). List all Instruments. \n(3)."
										+ " Find Instrument by Serial Number. \n(4). Change Instrument information. \n(5). Delete an Instrument \n(6). Exit \n");
						iSelection = Integer.parseInt(input.nextLine());
						if (iSelection < 1 || iSelection > 6)
							System.out.print("Enter a value between 1 and 6. \n");
					} catch (Exception E) {
						System.out.print("Please enter a valid value. \n");
					}
					if (iSelection == 1) {

						try {

							int a = getInt("Enter the Instrument's Serial Number: ");
							String query = "SELECT COUNT(*) FROM instruments WHERE SerialNum = " + a;
							// Executing the query
							ResultSet rs = stt.executeQuery(query);
							// Retrieving the result
							rs.next();
							int countt = rs.getInt(1);

							if (countt >= 1) {
								System.out.println("That instrument already exists in the database.");
							}

							if (countt == 0) {

								String b = getMakeModelName("Enter the Instrument's Make: ");
								String c = getMakeModelName("Enter the Instrument's Model: ");
								double d = getDouble("Enter the Instrument's purchase price: ");
								double e = getDouble("Enter the Instrument's sell price: ");
								int f = getSoldOrNotSold(
										"Enter the 1 if the instrument has been sold or 0 if not sold: ");
								int g = getInt("Enter the vendor ID for the instrument: ");

								String query2 = "SELECT COUNT(*) FROM vendor WHERE VendorID = " + g;
								rs = stt.executeQuery(query2);
								rs.next();
								int count2 = rs.getInt(1);
								if (count2 != 1) {
									System.out.println("The ID for the vendor you entered does not exist. ");
								}
								if (count2 >= 1) {
									PreparedStatement iPrep = con.prepareStatement(
											"INSERT INTO instruments (SerialNum, Make, Model, PurchasePrice, SellPrice, Sold, VendorID) VALUES (?,?,?,?,?,?,?)");
									iPrep.setInt(1, a);
									iPrep.setString(2, b);
									iPrep.setString(3, c);
									iPrep.setDouble(4, d);
									iPrep.setDouble(5, e);
									iPrep.setInt(6, f);
									iPrep.setInt(7, g);

									int row = iPrep.executeUpdate();

									System.out.print("The instrument was succesfully inserted into the database.\n");

								}

							}

						} catch (Exception E) {
							System.out.print("There was an error somewhere.\n");
							E.printStackTrace();
						}
						iSelection = 0;
					} else if (iSelection == 2) {

						String query = "SELECT COUNT(*) FROM instruments";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Instruments to list.\n");
						} else {
							System.out.println("-----All Instruments listed by their SerialNum------");
							System.out.println();
							ResultSet res = stt.executeQuery("SELECT * FROM instruments ORDER BY SerialNum");
							while (res.next()) {
								System.out.println(res.getInt("SerialNum") + " " + res.getString("Make") + " "
										+ res.getString("Model") + " " + res.getDouble("PurchasePrice") + " "
										+ res.getDouble("SellPrice") + " " + res.getInt("Sold")
										+ res.getInt("VendorID"));
							}
							System.out.println("");

						}
						iSelection = 0;
					} else if (iSelection == 3) {
						String query = "SELECT COUNT(*) FROM instruments";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Instruments to search for.\n");
						}

						else {
							int r = getInt("Enter the Serial number of the instrument you would like to look up: ");
							// System.out.print("Enter the VendorID for who you would like to look up: ");
							// int r = input.nextInt();
							// input.nextLine();

							PreparedStatement prep = con
									.prepareStatement("SELECT * FROM instruments WHERE SerialNum = ?");
							prep.setInt(1, r);

							rs = prep.executeQuery();

							if (rs.next()) {
								System.out.println(rs.getInt("SerialNum") + " " + rs.getString("Make") + " "
										+ rs.getString("Model") + " " + rs.getDouble("PurchasePrice") + " "
										+ rs.getDouble("SellPrice") + " " + rs.getInt("Sold") + rs.getInt("VendorID"));
							} else {
								System.out.print("The Instrument was not found.\n");
							}

						}
						iSelection = 0;
					} else if (iSelection == 4) {

						String query = "SELECT COUNT(*) FROM instruments";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Instruments to change.\n");
						}

						else if (count >= 1) {

							try {

								int r = getInt("Enter the Instrument for who you would like to change: ");
								// System.out.print("Enter the VendorID for who you would like to change: ");
								// int r = input.nextInt();
								// input.nextLine();

								PreparedStatement prep1 = con
										.prepareStatement("SELECT * FROM instruments WHERE SerialNum = ?");
								prep1.setInt(1, r);

								rs = prep1.executeQuery();

								if (!rs.next()) {
									System.out.print("There is no Instrument with that Serial Number.\n");
									// break;
								}

								else {
									rs = prep1.executeQuery();
									while (rs.next()) {

										System.out.println(rs.getInt("SerialNum") + " " + rs.getString("Make") + " "
												+ rs.getString("Model") + " " + rs.getDouble("PurchasePrice") + " "
												+ rs.getDouble("SellPrice") + " " + rs.getInt("Sold")
												+ rs.getInt("VendorID"));
									}

									String a = getMakeModelName(
											"What would you like to change the name of the Make of the instrument to? ");
									String b = getMakeModelName(
											"What would you like to change the name of the Model of the instrument to? ");
									double c = getDouble(
											"What would you like to change the Purchase Price of the instrument to? ");
									double d = getDouble(
											"What would you like to change the Sell Price of the instrument to? ");
									int e = getSoldOrNotSold(
											"Enter 1 if the instrument is sold or 0 if the instrument is not sold.");

									PreparedStatement prep = con.prepareStatement(
											"UPDATE instruments SET Make = ?, Model = ?, PurchasePrice = ?, SellPrice = ?, Sold = ? WHERE SerialNum = ?");
									prep.setString(1, a);
									prep.setString(2, b);
									prep.setDouble(3, c);
									prep.setDouble(4, d);
									prep.setInt(5, e);
									prep.setInt(6, r);

									int row = prep.executeUpdate();
									System.out.print("The instrument's information has been updated.\n");

								}
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n");
							}
						}
						iSelection = 0;
					} else if (iSelection == 5) {

						String query = "SELECT COUNT(*) FROM instruments";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Instruments to delete.\n");
						}

						else if (count >= 1) {

							try {

								int r = getInt("Enter the Serial Number for the instrument you would like to delete: ");
								// System.out.print("Enter the VendorID for who you would like to delete: ");
								// int r = input.nextInt();
								// input.nextLine();

								PreparedStatement prep1 = con
										.prepareStatement("SELECT * FROM instruments WHERE SerialNum = ?");
								prep1.setInt(1, r);

								rs = prep1.executeQuery();

								if (!rs.next()) {
									System.out.print("There is no Instrument with that Serial Number to delete.\n");
									// break;
								} else {
									rs = prep1.executeQuery();
									while (rs.next()) {
										System.out.println(rs.getInt("SerialNum") + " " + rs.getString("Make") + " "
												+ rs.getString("Model") + " " + rs.getDouble("PurchasePrice") + " "
												+ rs.getDouble("SellPrice") + " " + rs.getInt("Sold")
												+ rs.getInt("VendorID"));

									}

									System.out.print(
											"Are you sure you want to delete the Instrument listed above?\nEnter Y for yes or N for no:");
									String answer = input.nextLine();

									answer = answer.toUpperCase();
									char answer1 = answer.charAt(0);
									System.out.println(answer1);

									if (answer1 == 'N' || answer1 == 'n') {
										System.out.print("The instrument was not deleted.\n");
									} else if (answer1 == 'Y' || answer1 == 'y') {
										PreparedStatement dYPrep = con
												.prepareStatement("DELETE FROM instruments WHERE SerialNum = ?");
										dYPrep.setInt(1, r);

										int row = dYPrep.executeUpdate();
										System.out.print("The Instrument's information has been deleted.\n");

									}

								}
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n");
							}
						}

						iSelection = 0;
					}

				} while (iSelection != 6);
				System.out.print("Back to the Main Menu.\n");

				break;
			case SALES_MENU:
				int sSelection = 0;
				do {
					try {
						System.out.print("---Sales Menu---\n\n(1). Add a sale. \n(2). List all Sales. \n(3)."
								+ " Find Sale by ID. \n(4). Change Sale information. \n(5). Delete a Sale \n(6). Exit \n");
						sSelection = Integer.parseInt(input.nextLine());
						if (sSelection < 1 || sSelection > 6)
							System.out.print("Enter a value between 1 and 6. \n");
					} catch (Exception E) {
						System.out.print("Please enter a valid value. \n");
					}
					if (sSelection == 1) {

						try {

							int b = getInt("Enter the Customer ID: ");

							String query2 = "SELECT COUNT(*) FROM customers WHERE CustomerID = " + b;
							ResultSet rs = stt.executeQuery(query2);
							rs.next();
							int count2 = rs.getInt(1);
							if (count2 == 0) {
								System.out.println("The ID for the customer you entered does not exist.");
							} else {
								int c = getInt("Enter the serial number for the Instrument: ");
								String query3 = "SELECT COUNT(*) FROM instruments WHERE SerialNum = " + c;
								rs = stt.executeQuery(query3);
								rs.next();
								int count3 = rs.getInt(1);

								if (count3 == 0) {
									System.out.println("There are no instruments with that serial number.");
								} else {
									int d = getInt("Enter the Vendor's ID: ");

									String query4 = "SELECT COUNT(*) FROM vendor WHERE VendorID =" + d;
									rs = stt.executeQuery(query4);
									rs.next();
									int count4 = rs.getInt(1);

									if (count4 == 0) {
										System.out.println("There are no vendors associated with that ID.");
									} else {

										String e = getDate("Enter the date sold: ");
										double f = getDouble("Enter the sale amount: ");

										PreparedStatement iPrep = con.prepareStatement(
												"INSERT INTO sales (CustomerID, SerialNum, VendorID, DateSold, SaleAmount) VALUES (?,?,?,?,?)");
										iPrep.setInt(1, b);
										iPrep.setInt(2, c);
										iPrep.setInt(3, d);
										iPrep.setString(4, e);
										iPrep.setDouble(5, f);

										int row = iPrep.executeUpdate();

										System.out.print("The sale was succesfully inserted into the database.\n");
									}
								}

							}

						} catch (Exception E) {
							System.out.print("There was an error somewhere.\n");
							E.printStackTrace();
						}
						sSelection = 0;
					} else if (sSelection == 2) {

						String query = "SELECT COUNT(*) FROM sales";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Sales to list.\n");
						} else {
							System.out.println("-----All Sales listed by their SaleID------");
							System.out.println();
							ResultSet res = stt.executeQuery("SELECT * FROM sales ORDER BY SaleID");
							while (res.next()) {
								System.out.println(res.getInt("SaleID") + " " + res.getInt("CustomerID") + " "
										+ res.getInt("SerialNum") + " " + res.getInt("VendorID") + " " + res.getString("DateSold") + " "
										+ res.getDouble("SaleAmount"));
							}
							System.out.println("");

						}
						sSelection = 0;
					} else if (sSelection == 3) {
						String query = "SELECT COUNT(*) FROM sales";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Sales to search for.\n");
						}

						else {
							int r = getInt("Enter the ID of the Sale you would like to look up: ");
							// System.out.print("Enter the VendorID for who you would like to look up: ");
							// int r = input.nextInt();
							// input.nextLine();

							PreparedStatement prep = con
									.prepareStatement("SELECT * FROM sales WHERE SaleID = ?");
							prep.setInt(1, r);

							rs = prep.executeQuery();

							if (rs.next()) {
								System.out.println(rs.getInt("SaleID") + " " + rs.getInt("CustomerID") + " "
										+ rs.getInt("SerialNum") + " " + rs.getInt("VendorID") + " " + rs.getString("DateSold") + " "
										+ rs.getDouble("SaleAmount"));
							} else {
								System.out.print("The Instrument was not found.\n");
							}

						}
						sSelection = 0;
					} else if (sSelection == 4) {

						String query = "SELECT COUNT(*) FROM sales";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Sales to change.\n");
						}

						else if (count >= 1) {

							try {

								int r = getInt("Enter the ID of the Sale you would like to change: ");
								// System.out.print("Enter the VendorID for who you would like to change: ");
								// int r = input.nextInt();
								// input.nextLine();

								PreparedStatement prep1 = con
										.prepareStatement("SELECT * FROM sales WHERE SaleID = ?");
								prep1.setInt(1, r);

								rs = prep1.executeQuery();

								if (!rs.next()) {
									System.out.print("There is no sales with that Sale ID.\n");
									// break;
								}

								else {
									rs = prep1.executeQuery();
									while (rs.next()) {

										System.out.println(rs.getInt("SaleID") + " " + rs.getInt("CustomerID") + " "
												+ rs.getInt("SerialNum") + " " + rs.getInt("VendorID") + " " + rs.getString("DateSold") + " "
												+ rs.getDouble("SaleAmount"));
									}

									String a = getDate(
											"What would you like to change the date sold of the instrument? ");
									double b = getDouble(
											"What would you like to change the sale amount of the instrument to?");
									

									PreparedStatement prep = con.prepareStatement(
											"UPDATE sales SET DateSold = ?, SaleAmount = ? WHERE SaleID = ?");
									prep.setString(1, a);
									prep.setDouble(2, b);
									prep.setInt(3, r);

									int row = prep.executeUpdate();
									System.out.print("The information of the sale has been updated.\n");

								}
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n");
							}
						}
						sSelection = 0;
					} else if (sSelection == 5) {

						String query = "SELECT COUNT(*) FROM sales";
						// Executing the query
						ResultSet rs = stt.executeQuery(query);
						// Retrieving the result
						rs.next();
						int count = rs.getInt(1);
						if (count == 0) {
							System.out.print("There are no Sales to delete.\n");
						}

						else if (count >= 1) {

							try {

								int r = getInt("Enter the ID of the sale you would like to delete: ");
								// System.out.print("Enter the VendorID for who you would like to delete: ");
								// int r = input.nextInt();
								// input.nextLine();

								PreparedStatement prep1 = con
										.prepareStatement("SELECT * FROM sales WHERE SaleID = ?");
								prep1.setInt(1, r);

								rs = prep1.executeQuery();

								if (!rs.next()) {
									System.out.print("There is no Sale with that ID to delete.\n");
									// break;
								} else {
									rs = prep1.executeQuery();
									while (rs.next()) {
										System.out.println(rs.getInt("SaleID") + " " + rs.getInt("CustomerID") + " "
												+ rs.getInt("SerialNum") + " " + rs.getInt("VendorID") + " " + rs.getString("DateSold") + " "
												+ rs.getDouble("SaleAmount"));

									}

									System.out.print(
											"Are you sure you want to delete the Sale listed above?\nEnter Y for yes or N for no:");
									String answer = input.nextLine();

									answer = answer.toUpperCase();
									char answer1 = answer.charAt(0);
									System.out.println(answer1);

									if (answer1 == 'N' || answer1 == 'n') {
										System.out.print("The Sale was not deleted.\n");
									} else if (answer1 == 'Y' || answer1 == 'y') {
										PreparedStatement dYPrep = con
												.prepareStatement("DELETE FROM sales WHERE SaleID = ?");
										dYPrep.setInt(1, r);

										int row = dYPrep.executeUpdate();
										System.out.print("The Sale's information has been deleted.\n");

									}

								}
							} catch (Exception e) {
								System.out.print("There was an error somewhere.\n");
							}
						}

						sSelection = 0;
					}

				} while (sSelection != 6);
				System.out.print("Back to the Main Menu.\n");

				break;
			case EXIT:

			}
		} while (response != EXIT);

	}

	private static String getDate(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 10) {
				System.out.print("The date can not be null and must be entered in the format of yyyy-mm-dd. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 10);
		return r;
	}

	private static int getInt(String prompt) {
		int r = -1;
		do {
			try {
				System.out.print(prompt);
				r = Integer.parseInt(input.nextLine());

				if (r < 0)
					System.out.print("Enter a value greater than 0! Re-enter value. \n");
			} catch (Exception E) {
				System.out.print(
						"Enter a valid value. It must be a number greater than 0 and not contain any letters or special characters. \n");
			}

		} while (r < 0);
		return r;
	}

	private static double getDouble(String prompt) {
		double r = -1;
		do {
			try {
				System.out.print(prompt);
				r = Double.parseDouble(input.nextLine());

				if (r < 0)
					System.out.print("Enter a value greater than 0! Re-enter value. \n");
			} catch (Exception E) {
				System.out.print(
						"Enter a valid value. It must be a number greater than 0 and not contain any letters or special characters. \n");
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

	private static String getMakeModelName(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 11) {
				System.out.print(
						"The make and model name can not be null, less than 1 or greater than 11. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 11);
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
				System.out
						.print("The field can not be null, more than 30 characters or less than 1. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 30);
		return r;
	}

	public static int getSoldOrNotSold(String prompt) {
		int r = -1;
		do {
			try {
				System.out.print(prompt);
				r = Integer.parseInt(input.nextLine());

				if (r < 0 || r > 1)
					System.out.print(
							"Enter a value of either 1 for sold or 0 for not sold! Please re-enter the value. \n");
			} catch (Exception E) {
				System.out.print("Enter a valid value. It must be a number of either 1 or 0. \n");
			}

		} while (r < 0 || r > 1);
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
