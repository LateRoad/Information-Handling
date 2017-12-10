package com.lateroad.informationhandling.reader;

import java.io.File;
import java.io.IOException;

import com.lateroad.informationhandling.exception.TxtReaderException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class TxtReader {
    static {
        new DOMConfigurator().doConfigure("log4j2.xml", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = Logger.getLogger(TxtReader.class);


    public String readTxt(String filename) throws TxtReaderException {
        String text;
        try {
            LOGGER.info("Reading from " + filename + " was started.");
            text = FileUtils.readFileToString(new File(filename));
            LOGGER.info("Reading from " + filename + " was finished.");
        } catch (IOException e) {
            LOGGER.error("Wrong file name. File " + filename + " can not be open.", e);
            throw new TxtReaderException();
        }
        return text;
    }
}
