package com.opreaalex.processor.parser;

import com.opreaalex.processor.parser.exception.StreamLineParserException;

public interface StreamLineParser {

    Object parseLine(String line) throws StreamLineParserException;
}
