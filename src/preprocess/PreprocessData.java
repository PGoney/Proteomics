package proteomics.preprocess;

public class PreprocessData {

	private final PREPROCESS_METHOD method;
	private final String[] args;
	
	public PreprocessData(PREPROCESS_METHOD method, String[] args) {
		this.method = method;
		this.args = args;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("( ");
		sb.append(method.name());
		sb.append(", { ");
		for (String arg : args) {
			sb.append(arg);
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append(" } )");
		
		return sb.toString();
	}
	
	public PREPROCESS_METHOD getMethod() {
		return method;
	}
	
	public String[] getArgument() {
		return args;
	}
}
