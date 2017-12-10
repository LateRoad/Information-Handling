package com.lateroad.informationhandling.action;

import com.lateroad.informationhandling.composite.Component;
import com.lateroad.informationhandling.handler.impl.LexemeHandler;
import com.lateroad.informationhandling.handler.impl.ParagraphHandler;
import com.lateroad.informationhandling.handler.impl.SentenceHandler;
import com.lateroad.informationhandling.handler.impl.TextHandler;
import org.apache.log4j.Logger;

public class TaskAction {
    private static final Logger LOGGER = Logger.getLogger(TaskAction.class);


    private static final String REGEX_CHECK_SPACE_BEFORE = "(?=\\s)";
    private static final String REGEX_SPACE = "\\s";
    private static final String REGEX_ALL_CHARACTERS = "\\p{all}*";

    public static String exchangeLexeme(String text) {
        LexemeHandler lexemeHandler = new LexemeHandler();
        SentenceHandler sentenceHandler = new SentenceHandler(lexemeHandler);
        ParagraphHandler paragraphHandler = new ParagraphHandler(sentenceHandler);
        TextHandler textHandler = new TextHandler(paragraphHandler);

        Component paragraphs = textHandler.handleRequest(text);
        for (int i = 0; i < paragraphs.childrenCount(); ++i) {
            Component sentences = null;

            sentences = paragraphs.getChild(i);
            for (int j = 0; j < sentences.childrenCount(); ++j) {
                Component sentence = sentences.getChild(j);
                Component firstLexeme = sentence.getChild(0);
                Component lastLexeme = sentence.getChild(sentence.childrenCount() - 1);
                sentence.setChild(0, lastLexeme);
                sentence.setChild(sentence.childrenCount() - 1, firstLexeme);

            }
        }
        return paragraphs.toString();
    }

    public static String deleteLexeme(String text, int length, char firstCharacter) {
        String resultText = text;
        LexemeHandler lexemeHandler = new LexemeHandler();
        SentenceHandler handler = new SentenceHandler(lexemeHandler);

        Component lexemes = handler.handleRequest(text);
        for (int i = 0; i < lexemes.childrenCount(); ++i) {
            String lexemeForDelete = null;
            lexemeForDelete = lexemes.getChild(i).getValue();

            if (lexemeForDelete.length() == length) {
                if (lexemeForDelete.matches(firstCharacter + REGEX_ALL_CHARACTERS)) {
                    resultText = resultText.replaceFirst(REGEX_SPACE + lexemeForDelete + REGEX_CHECK_SPACE_BEFORE, "");
                }
            }
        }
        return resultText;
    }

    public static String sortSentenceByLexeme(String text) {
        LexemeHandler lexemeHandler = new LexemeHandler();
        SentenceHandler sentenceHandler = new SentenceHandler(lexemeHandler);
        ParagraphHandler paragraphHandler = new ParagraphHandler(sentenceHandler);

        Component sentences = paragraphHandler.handleRequest(text);
        for (int i = 1; i < sentences.childrenCount(); ++i) {
            Component temp = null;
            temp = sentences.getChild(i);
            int j = i;
            while (j > 0 && sentences.getChild(j - 1).childrenCount() > temp.childrenCount()) {
                sentences.setChild(j, sentences.getChild(j - 1));
                j--;
            }
            sentences.setChild(j, temp);

        }
        return sentences.toString();
    }
}
