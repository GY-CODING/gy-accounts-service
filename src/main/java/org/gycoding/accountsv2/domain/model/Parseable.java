package org.gycoding.accountsv2.domain.model;

public interface Parseable {
    public final static String DELIM = ";";
    
    String toJSON();
    String toXML();
    String toTXT();
}
