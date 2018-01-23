import java.util.*;

public class Parser {
	public static void main(String[]args) {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		Start test = new Start();
		ArrayList<String> value = new ArrayList<String>();
		
		map = test.doParse(args);
		test.printAll(map);
		value = test.getValue(map, "test");
	}
}

class Start {
	public HashMap<String, ArrayList<String>> doParse (String[]args) {
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
		Iterator<Map.Entry<Integer, String>> iteratorParam = allParam.entrySet().iterator();
		
		while (iteratorParam.hasNext()) {
			ArrayList<String> values = new ArrayList<String>();
			Map.Entry<Integer, String> pairPar = iteratorParam.next();
			Integer keyPar = pairPar.getKey();
			String valuePar = pairPar.getValue();
			Iterator<Map.Entry<String, String>> iteratorValues = allValues.entrySet().iterator();
			
			while (iteratorValues.hasNext()) {
				Map.Entry<String, String> pairVal = iteratorValues.next();
				String keyVal = pairVal.getKey();
				String valueVal = pairVal.getValue();
				if(keyVal.indexOf(keyPar + "_") != -1)
					values.add(valueVal);
			}
			parameters.put(valuePar, values);
		}
		return parameters;
	}
	
	
	public ArrayList<String> getValue (HashMap<String, ArrayList<String>> parameters, String paramName) {
		return parameters.get(paramName);
	}
	
	public void printAll (HashMap<String, ArrayList<String>> parameters) {
		Iterator<Map.Entry<String, ArrayList<String>>> iterator = parameters.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = iterator.next();
			String param = pair.getKey();
			ArrayList<String> values = pair.getValue();
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
