package will.miao.parser;

import org.apache.lucene.search.Query;

/**
 * Created by WillMiao on 16/9/10.
 */
public class Main {
	public static void main(String[] args) throws ParseException {
		// TODO:
		String[] fieldNames = {"host", "content", "time", "price"};
		int[] fieldTypes = {3, 3, 1, 2};

		QueryParser queryParser = new QueryParser(fieldNames, fieldTypes);

		Query query = queryParser.parse("price>3.414");

		System.out.println(query);
	}
}
