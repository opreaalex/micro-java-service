package com.opreaalex.parser;

public interface StreamLineParser {

    <T> T parseLine(String line);
}
