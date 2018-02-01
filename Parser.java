import java.util.*;

public class Parser {
	public static void main(String[]args) {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		Start test = new Start();
		ArrayList<String> value = new ArrayList<String>();
		
		map = test.Parse(args);
		test.printAll(map);
		value = test.getValue(map, "test");
	}
}

class Start {
	public HashMap<String, ArrayList<String>> Parse (String[]args) {
		HashMap<String, ArrayList<String>> parameters = new HashMap<String, ArrayList<String>>();
		HashMap<Integer,String> allParam = new HashMap<Integer,String>();
		HashMap<String,String> allValues = new HashMap<String,String>();
		int paramCount = 0, valuesCount = 0;
		
		for(int i = 0; i < args.length; i++) {
			if (args[i].startsWith("-") || args[i].startsWith("/")) {
				int firstChar = 0, endChar;
				paramCount++;
				valuesCount = 0;
				
				String param = args[i].replace("-", "");
				param = param.replace("/", "");
				
				if(param.indexOf(":") != -1 || param.indexOf("=") != -1) {
					endChar = getEndOfParamStr(param);
					valuesCount++;
					allValues.put(paramCount + "_" + valuesCount, param.substring(endChar + 1, param.length()));
					param = param.substring(firstChar, endChar); 
				}
				allParam.put(paramCount, param);
			}
			else {
				valuesCount++;
				allValues.put(paramCount + "_" + valuesCount, args[i]);
			}
		}

		for(Integer objParam : allParam.keySet()) {
			ArrayList<String> values = new ArrayList<String>();
			
			for(String objValues : allValues.keySet()) {
				if(objValues.indexOf(objParam + "_") != -1)
					values.add(allValues.get(objValues));
			}
			parameters.put(allParam.get(objParam), values);
		}
		return parameters;
	}
	
	
	public ArrayList<String> getValue (HashMap<String, ArrayList<String>> parameters, String paramName) {
		return parameters.get(paramName);
	}
	
	public void printAll (HashMap<String, ArrayList<String>> parameters) {
		for(Object param : parameters.keySet()) {
			ArrayList<String> values = parameters.get(param);
			System.out.println("Parameter: " + param);
			System.out.println("Values:");
			
			for(String s : values)
				System.out.println(s);
		}
	}
	private int getEndOfParamStr (String param) {
		int endChar = param.indexOf(":");
		if(endChar == -1)
			endChar = param.indexOf("=");
		return endChar;
	}
}
