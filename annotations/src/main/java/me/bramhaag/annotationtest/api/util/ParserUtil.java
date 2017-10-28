package me.bramhaag.annotationtest.api.util;

import com.sun.tools.javac.parser.JavacParser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.util.Context;

public class ParserUtil {

    public static JavacParser newParser(Context context, String code) {
        return ParserFactory.instance(context).newParser(code, true, true, true, true);
    }
}
