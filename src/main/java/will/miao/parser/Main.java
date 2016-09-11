package will.miao.parser;

/**
 * Created by WillMiao on 16/9/10.
 */
public class Main {
	private static final String[] PREDICATES = {
			"host IN ('host1', 'host3', 'host4')",
			"host = 'host2' OR time >= 400 && price <= 3.14",
			"host = 'host2' AND time >= 400 || price <= 3.14",
			"(host = 'host2' AND time >= 400) || price <= 3.14",
			"(host = 'host2' OR time >= 400) && price <= 3.14",
			"host = 'host2' OR (time >= 400 && price <= 3.14)"
	};
	public static void main(String[] args) throws ParseException {
		// TODO:
		String[] fieldNames = {"host", "content", "time", "price"};
		int[] fieldTypes = {3, 3, 1, 2};

		QueryParser queryParser = new QueryParser(fieldNames, fieldTypes);

		for (String predicate : PREDICATES) {
			System.out.println(predicate);
			System.out.println(queryParser.parse(predicate));
			System.out.println("*************************************************************");
		}
	}
}
