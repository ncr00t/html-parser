import parser.Parser;

import java.io.IOException;

/**
 *Class allows you to run the parser
 */
public class LaunchParser {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        parser.parsing();
    }
}
