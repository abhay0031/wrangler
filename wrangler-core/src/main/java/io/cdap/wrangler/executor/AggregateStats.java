import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.Arguments;
import io.cdap.wrangler.api.DirectiveContext;
import io.cdap.wrangler.api.DirectiveExecutionException;
import io.cdap.wrangler.api.DirectiveParseException;
import io.cdap.wrangler.api.ErrorRowException;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.ReportErrorAndProceed;
import io.cdap.wrangler.api.annotations.Categories;
import io.cdap.wrangler.api.parser.Token;
import io.cdap.wrangler.api.parser.UsageDefinition;

import java.util.ArrayList;
import java.util.List;

@Name("aggregate-stats")
@Categories(categories = { "statistics" })
@Description("Aggregates fields representing byte sizes and time durations across all rows.")
public class AggregateStats implements Directive {

  private Object byteSizeField;
  private Object timeDurationField;

  @Override
  public void initialize(Arguments arguments, List<Token> tokens, DirectiveContext context) throws Exception {
    if (tokens.size() != 2) {
      throw new IllegalArgumentException("Usage: aggregate-stats <byteSizeField> <timeDurationField>");
    }
    byteSizeField = tokens.get(0).value();
    timeDurationField = tokens.get(1).value();
  }

  @Override
  public List<Row> execute(List<Row> rows, DirectiveContext context) throws Exception {
    long totalBytes = 0;
    long totalDuration = 0;

    for (Row row : rows) {
      Object sizeVal = row.getValue((int) byteSizeField);
      Object durationVal = row.getValue((int) timeDurationField);

      if (sizeVal instanceof Number) {
        totalBytes += ((Number) sizeVal).longValue();
      }

      if (durationVal instanceof Number) {
        totalDuration += ((Number) durationVal).longValue();
      }
    }

    List<Row> result = new ArrayList<>();
    Row output = new Row();
    output.add("total_bytes", totalBytes);
    output.add("total_duration_ms", totalDuration);
    result.add(output);
    return result;
  }

  @Override
  public void initialize(Arguments args) throws DirectiveParseException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'initialize'");
  }

  @Override
  public List<Row> execute(List<Row> rows, ExecutorContext context)
        throws DirectiveExecutionException, ErrorRowException, ReportErrorAndProceed {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'execute'");
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'destroy'");
  }

  @Override
  public UsageDefinition define() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'define'");
  }
}
