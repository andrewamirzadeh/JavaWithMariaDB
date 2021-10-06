package store.seminole.state.edu;

public class Customer implements Comparable<Customer>{

	private int CustomerID;
	private String LastName;
	private String FirstName;
	private String Address;
	private String City;
	private String State;
	
	
	Customer(int ID){
		CustomerID = ID;
	}
	
	public Customer(int ID, String LName, String FName, String Address, String City, String State){
		setCustomerID(ID);
		setLastName(LName);
		setFirstName(FName);
		setAddress(Address);
		setCity(City);
		setState(State);
		
	}

	public int getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}
	
	public String toString() {
		return "[Customer Id: " + this.CustomerID + " Last Name: " + this.LastName + " First Name: " + this.FirstName + " Address: " + this.Address 
				+ " City: " + this.City + " State: " + this.State + " ]"
						+ "\n";
	}

	@Override
	public int compareTo(Customer o) {
		// TODO Auto-generated method stub
		return CustomerID - o.getCustomerID();
	}
	
}


