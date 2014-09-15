/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.metamug.scrapper.entity;

/**
 *
 * @author deepak
 */
public interface MetaData {

    public static final String HTML = "HTML";
    public static final String AUDIO = "AUDIO";
    public static final String PRODUCT = "PRODUCT";
    public static final String PDF = "PDF";
    public static final String VIDEO = "VIDEO";
    public static final String MAP = "MAP";
    public static final String SERVICE = "SERVICE";
    public static final String WIKI = "WIKI";
    public static final String IMAGE = "IMAGE";
    public static final String QnA = "Q&A";
    public static final String ARTICLE = "ARTICLE";

    public static final String SYMBOL_PDF = "http://metamug.com/assets/img/symbols/pdf_symbol.png";
    public static final String SYMBOL_MUSIC = "http://metamug.com/assets/img/music-icon.png";
    public static final int MAX_DESC_LENGTH = 400;
    public static final int MIN_DESC_LENGTH = 80;
}
