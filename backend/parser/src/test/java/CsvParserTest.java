import com.cwienczek.csvparser.parser.CsvParser;
import com.cwienczek.csvparser.parser.CsvPropertyObject;
import io.reactivex.Observable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.stream.Stream;

/**
 * Created by Michal Cwienczek on 09/04/2017.
 */
public class CsvParserTest {

    @Test
    void givenSingleRowReaderWhenParseReturnsOneRowObservable() {
        CsvParser parser = new CsvParser();
        Stream<CsvPropertyObject> parsingResultObservable = parser.parseCsvToPropertyObject(new StringReader("1.5;2.5;3.5;4.5;5.5;att6;att7;att8"));
        parsingResultObservable.forEach(parsedRow -> {
            assertEquals(1.5, parsedRow.getAtt1(), 0.01);
            assertEquals(2.5, parsedRow.getAtt2(), 0.01);
            assertEquals(3.5, parsedRow.getAtt3(), 0.01);
            assertEquals(4.5, parsedRow.getAtt4(), 0.01);
            assertEquals(5.5, parsedRow.getAtt5(), 0.01);
            assertEquals("att6", parsedRow.getAtt6());
            assertEquals("att7", parsedRow.getAtt7());
            assertEquals("att8", parsedRow.getLabel());
        });
    }
}
