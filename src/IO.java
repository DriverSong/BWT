import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IO {
	public List<String> readTxtFile(String filename) {
		try {
			List<String> inputStrings = new ArrayList<>();
			String encoding = "UTF-8";
			File file = new File(filename);
			if(file.exists() && file.isFile()) {
				/*
				 * FileReader, BufferReader, InputStreamReader以及FileInputStream的区别：
				 * http://www.cnblogs.com/lianghui66/p/3303546.html
				 */
//				FileInputStream in = new FileInputStream(file);
//				InputStreamReader reader = new InputStreamReader(in, encoding);
				FileReader reader = new FileReader(filename);
				BufferedReader buffer = new BufferedReader(reader);
				String line = null;
				while((line = buffer.readLine()) != null) {
					inputStrings.add(line);
				}
				return inputStrings;
			}else {
				System.out.println("File Does Not Exsit !!!");
			}
		}catch (Exception e) {
			System.out.println("System Error !!!");
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeTxtFile(String content, String txtPath, boolean isNextWriter) {
		try {
			File file = new File(txtPath);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(txtPath, isNextWriter);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write(content);
			bw.newLine();//newLine()要在后
			fileWriter.flush();
			bw.close();
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String args[]) {
//		String filename = "/home/qiujiawei/eclipse-workspace/BWT/in.txt";
//		IO io = new IO();
//		List<String> inputStrings  = new ArrayList<>();
//		inputStrings = io.readTxtFile(filename);
//		for(int i = 0; i < inputStrings.size(); i++) {
//			io.writeTxtFile(inputStrings.get(i), "/home/qiujiawei/eclipse-workspace/BWT/out.txt", true);
//		}
//	}
}
