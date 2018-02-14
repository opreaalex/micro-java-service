package com.opreaalex.parser;

import com.opreaalex.parser.exception.StreamLineParserException;

public interface StreamLineParser {

    Object parseLine(String line) throws StreamLineParserException;
}
