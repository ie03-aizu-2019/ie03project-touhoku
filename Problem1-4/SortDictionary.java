import java.util.ArrayList;
import java.util.Collections;

public class SortDictionary {
	public static void main(String[] args) {

		ArrayList<String> stateList = new ArrayList<String>();
		stateList.add("C1");
		stateList.add("1");
		stateList.add("4");
    stateList.add("　c1");
    stateList.add("○");
    stateList.add(",");
    stateList.add("()");
    stateList.add("%");
    stateList.add("oo");

		/*for(int i=0;i<stateList.size();i++){
			System.out.println(stateList.get(i));　　　
		}*/

		Collections.sort(stateList,Collections.reverseOrder());
		System.out.println("--- Sorted ---");

		for(int i=0;i<stateList.size();i++){
			System.out.println(stateList.get(i));
		}

	}
}
