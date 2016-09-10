package will.miao.parser;

import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WillMiao on 16/9/10.
 */
public abstract class QueryParserBase {

	private Map<String, Integer> fieldTypes = new HashMap<String, Integer>();

	public abstract void ReInit(Reader stream);
	public abstract Query TopLevelQuery() throws ParseException;

	protected void init(String[] fieldNames, int[] fieldTypes) {
		for (int i = 0; i < fieldNames.length; ++i) {
			this.fieldTypes.put(fieldNames[i], fieldTypes[i]);
		}
	}

	public Query parse(String predicate) throws ParseException {
		ReInit(new StringReader(predicate));

		Query query = TopLevelQuery();

		if (query == null) {
			query = new BooleanQuery.Builder().build();
		}

		return query;
	}

	protected Query newExactQuery(String field, String value) {
		// TODO:
		switch (fieldTypes.get(field)) {
			case 1:
				return LongPoint.newExactQuery(field, Long.parseLong(value));
			case 2:
				return DoublePoint.newExactQuery(field, Double.parseDouble(value));
			case 3:
				return new TermQuery(new Term(field, value));
			default:
				return null;
		}
	}

	protected Query newRangeQuery(String field, String lowerValue, String upperValue, boolean startInclusive, boolean endInclusive) {
		// TODO:
		switch (fieldTypes.get(field)) {
			case 1: {
				long lowerBound = Long.MIN_VALUE;
				long upperBound = Long.MAX_VALUE;

				if (lowerValue != null) {
					long value = Long.parseLong(lowerValue);
					lowerBound = startInclusive ? value : Math.addExact(value, 1);
				}

				if (upperValue != null) {
					long value = Long.parseLong(upperValue);
					upperBound = endInclusive ? value : Math.addExact(value, -1);
				}

				return LongPoint.newRangeQuery(field, lowerBound, upperBound);
			}
			case 2: {
				double lowerBound = Double.MIN_VALUE;
				double upperBound = Double.MAX_VALUE;

				if (lowerValue != null) {
					double value = Double.parseDouble(lowerValue);
					lowerBound = startInclusive ? value : Math.nextUp(value);
				}

				if (upperValue != null) {
					double value = Double.parseDouble(upperValue);
					upperBound = endInclusive ? value : Math.nextDown(value);
				}

				return DoublePoint.newRangeQuery(field, lowerBound, upperBound);
			}
			case 3: {
				BytesRef start = lowerValue == null ? null : new BytesRef(lowerValue);
				BytesRef end = upperValue == null ? null : new BytesRef(upperValue);

				return new TermRangeQuery(field, start, end, startInclusive, endInclusive);
			}
			default:
				return null;
		}

	}

	protected Query newInQuery(String field, List<String> values) {
		// TODO:
		return null;
	}
}
