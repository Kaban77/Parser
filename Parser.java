import java.util.*;

public class Parser {
                public static void main(String[]args) {
					HashMap<String, String> map = new HashMap<String, String>();
				        Start test = new Start();
					
					map = test.doParse(args);
					System.out.println(test.getValue(map, "test"));
                }
}

class Start {
	public HashMap<String, String> doParse (String[]args) {
		HashMap<String, String> map = new HashMap<String, String>();
		int firstChar, endChar;
				
		for(int i=0; i < args.length; i+=2) {
			firstChar = 0;
			endChar = args[i].length();
			
			if(i == 0) {
				for(int j = 0; j < 2; j++) {
					if(args[i].charAt(j) == '/') {
						firstChar = j + 1;
						break;
					}
					else if (args[i].charAt(j) == '-')
						firstChar ++;	
				}
			
			}
			
			map.put(args[i].substring(firstChar, endChar), args[i + 1]);
		}
		return map;
	}
	
	public String getValue (HashMap<String, String> parameters, String paramName) {
		if(parameters.get(paramName) != null)
			return parameters.get(paramName);
		else 
			return null; 
	}
}
