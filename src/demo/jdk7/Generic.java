package demo.jdk7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Generic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//ʹ�÷���ǰ 
		List strList = new ArrayList(); 
		List<String> strList4 = new ArrayList<String>(); 
		List<Map<String, List<String>>> strList5 =  new ArrayList<Map<String, List<String>>>();
		 
		 
		//������ʹ�ü����� (<>) �ƶ����� 
		List<String> strList0 = new ArrayList<String>(); 
		List<Map<String, List<String>>> strList1 =  new ArrayList<Map<String, List<String>>>(); 
		List<String> strList2 = new ArrayList<>(); 
		List<Map<String, List<String>>> strList3 = new ArrayList<>();
		
		
		List<String> list = new ArrayList<>();
		list.add("A");
		  // The following statement should fail since addAll expects
		  // Collection<? extends String>
		//list.addAll(new ArrayList<>());
	}
}
