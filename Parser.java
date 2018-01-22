import java.util.*;

public class Parser {
	public static void main(String[]args) {
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList values = new ArrayList();
		Start test = new Start();
		
		map = test.doParse(args);
		test.printAll(map);
	}
}

class Start {
	public HashMap<String, ArrayList<String>> doParse (String[]args) {
		HashMap<String, ArrayList<String>> parameters = new HashMap<String, ArrayList<String>>();
		HashMap<Integer,String> allParam = new HashMap<Integer,String>();
		HashMap<String,String> allValues = new HashMap<String,String>();
		String param = new String();
		int firstChar = 0, endChar, paramCount = 0, valuesCount = 0;
		
		for(int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--") || args[i].startsWith("-") || args[i].startsWith("/")) {
				paramCount++;
				valuesCount = 0;
				
				param = args[i].replace("-", "");
				param = param.replace("/", "");
				allParam.put(paramCount, param);
				
				if(param.indexOf(":") != -1 || param.indexOf("=") != -1) {
					endChar = param.indexOf(":");
					if(endChar == -1)
						endChar = param.indexOf("=");
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
			ArrayList values = new ArrayList();
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
		if(parameters.get(paramName) != null)
			return parameters.get(paramName);
		else
			return null;
	}
	
	public void printAll(HashMap<String, ArrayList<String>> parameters) {
		Iterator<Map.Entry<String, ArrayList<String>>> iterator = parameters.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, ArrayList<String>> pair = iterator.next();
			String param = pair.getKey();
			ArrayList<String> values = pair.getValue();
			System.out.println("Parameter: " + param);
			System.out.println("Values:");
			
			for(String str : values)
				System.out.println(str);
		}
	}
}
