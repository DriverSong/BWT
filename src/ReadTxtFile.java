import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadTxtFile {
	public List<String> readTxtFile(String filename) {
		try {
			List<String> inputStrings = new ArrayList<>();
			String encoding = "UTF-8";
			File file = new File(filename);
			if(file.exists() && file.isFile()) {
				FileInputStream in = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(in, encoding);
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
	
	public static void main(String args[]) {
		String filename = "home/qiujiawei/.../...";
		ReadTxtFile reader = new ReadTxtFile();
		reader.readTxtFile(filename);
	}
}
