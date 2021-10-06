package store.seminole.state.edu;

import java.util.Comparator;

public class CustomerIdComparator implements Comparator<Customer>{

	@Override
	public int compare(Customer o1, Customer o2) {
		
		return o1.getCustomerID() - o2.getCustomerID();
	}

}
