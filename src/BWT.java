import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class BWT {
	private int[] FCount;
	private int[] LCount;
	private String[] FStrings;
	private String[] LStrings;
	
	//加密
	public String encode(String rawStr) {
		String[][] matrix = toMatrix(rawStr);
		int length = matrix.length;
		List<String> BWM = new ArrayList<>();
		for(int i = 0; i < length; i++) {
			String[] tempArray = new String[length];
			for(int j = 0; j < length; j++) {
				tempArray[j] = matrix[i][j];
			}
			BWM.add(String.join("", tempArray));
		}
		BWM.sort(null);
		String[] BWTArray = new String[length];
		for(int i = 0; i < length; i++) {
			BWTArray[i] = BWM.get(i).split("")[length - 1];
		}
		String BWTString = String.join("", BWTArray);
		return BWTString;
	}
	
	//解密
	public String decode(String BWTStr) {
		preProcess(BWTStr);
		int length = this.FStrings.length;
		String[] rawStrings = new String[length];
		int index = 0;
		rawStrings[0] = this.LStrings[index];
		for(int i = 1; i < length; i++) {
			index = findF(this.LStrings[index], this.LCount[index]);
			if(index == -1) {
				System.out.println("Error !!!");
				return null;
			}
			rawStrings[i] = this.LStrings[index];
		}
		String rawStr = String.join("", rawStrings);
		return rawStr;
	}
	
	//搜索
	public int search(String str, String BWTStr) {
		preProcess(BWTStr);
		String[] strs = str.split("");
		int length1 = strs.length;
		if(length1 == 0) {
			System.out.println("The Input String Cannot Be Null !!!");
			return 0;
		}
		int length2 = this.FStrings.length;
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i < length2; i++) {
//			问题：string明明一样但是比较为False
//			System.out.println(this.FStrings[i]);
//			System.out.println(strs[length1 - 1]);
//			System.out.println(this.FStrings[i] == strs[length1 - 1]);
//			System.out.println("---------------");
//			if(this.FStrings[i] == strs[length1 - 1]) {
			if(this.FStrings[i].equals(strs[length1 - 1])) {
				list.add(i);
			}
		}
		if(length1 == 1) return list.size();
		for(int i = length1 - 2; i >= 0; i--) {
			int size = list.size();
			if(size == 0) return 0;
			List<Integer> tempList = new ArrayList<>();
			for(int j = 0; j < size; j++) {
				if(this.LStrings[list.get(j)].equals(strs[i])) {
					int index = findF(this.LStrings[list.get(j)], this.LCount[list.get(j)]);
					tempList.add(index);
				}
			}
			list = tempList;
		}
		return list.size();
	}

	//将输入Str转化为BWM
	private String[][] toMatrix(String rawStr) {
		String[] rawStrArray = rawStr.split("");
		int length = rawStrArray.length;
		String[] tempArray = rawStrArray;
		String[][] matrix = new String[length][length];
		matrix[length - 1] = rawStrArray;
		for(int i = 1; i < length; i++) {
			String[] shiftArray = new String[length];
			for(int j = 0; j < length - 1; j++) {
				shiftArray[j] = tempArray[j + 1];
			}
			shiftArray[length - 1] = tempArray[0];
			matrix[length - 1 - i] = shiftArray;
			tempArray = shiftArray;
		}
		return matrix;
	}
	
	
	
	//根据LStrings中元素与其对应count，转到对应FStrings中的元素
	private int findF(String tempString, int count) {
		int length = this.FStrings.length;
		for(int j = 0; j < length; j++) {
			if(this.FStrings[j] == tempString && this.FCount[j] == count) {
				return j;
			}
		}
		return -1;
	}
	
	//预处理，根据带解密的BWTStr，生成LStrings，FStrings以及其对应的LCount，FCount
	private void preProcess(String BWTStr) {
		this.LStrings = BWTStr.split("");
		int length = this.LStrings.length;
		List<String> BWTList = new ArrayList<>(Arrays.asList(this.LStrings));
//		List<String> BWTList = new ArrayList<>();
//		Collections.addAll(BWTList, this.LStrings);
		BWTList.sort(null);
		this.FStrings = (String[]) BWTList.toArray(new String[length]);
		this.LCount = new int[length];
		this.FCount = new int[length];
		Map<String, Integer> LMap = new HashMap<>();
		Map<String, Integer> FMap = new HashMap<>();
		for(int i = 0; i < length; i++) {
			if(LMap.containsKey(this.LStrings[i])) {
				int count = LMap.get(this.LStrings[i])  + 1;
				this.LCount[i] = count;
				LMap.put(this.LStrings[i], count);
			}else {
				int count = 1;
				this.LCount[i] = count;
				LMap.put(this.LStrings[i], count);
			}
			if(FMap.containsKey(this.FStrings[i])) {
				int count = FMap.get(this.FStrings[i])  + 1;
				this.FCount[i] = count;
				FMap.put(this.FStrings[i], count);
			}else {
				int count = 1;
				this.FCount[i] = count;
				FMap.put(this.FStrings[i], count);
			}
		}
	}
	
	@Test
	public void test() {
//		String aString = "123456";
//		String[] aStringArray = aString.split("");
//		System.out.println(Arrays.toString(aStringArray));
		String bString = "";
		String[] bStrings = bString.split("");
		System.out.println(Arrays.toString(bStrings));
	}
	
	public static void main(String args[]) {
		BWT bwt = new BWT();
		String BWTString = bwt.encode("abaaba$");
		System.out.println(BWTString);
//		String rawStr = bwt.decode(BWTString);
		int count = bwt.search("aba", BWTString);
		System.out.println(count);
		
	}
}
