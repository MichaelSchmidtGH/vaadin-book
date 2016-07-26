package com.example.myproject;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import com.example.myproject.utils.Utils;

/**
 * Umwandlung UTF-8 nach Latin-1 und zurück.
 * 
 * @author schmidt
 *
 */
public class StringCoding {
	
	public static void main(String[] args) {
		StringCoding c = new StringCoding();
		ByteBuffer result = c.encode("äöüÄÖÜß!§$%&/()=?\\}][{²@µ_-.:,;#'+*~´`|<>");
		c.decode(result);
		
	}
	
	/**
	 * Umwandlung eines UTF-8 Strings in einen Latin-1 ByteBuffer.
	 * 
	 * @param utf8String Der umzuwandelnde UTF-8-String.
	 * @return Ein ByteBuffer mit dem Latin-1-Inhalt.
	 */
	public ByteBuffer encode(String utf8String) {
		// Zu konvertierenden String loggen
		Utils.log("(StringCoding encode) utf8String = " + utf8String);
		
		// Länge des zu konvertierenden Strings ermitteln.
		int len = utf8String.length();
//		Utils.log("(StringCoding encode) len = " + len);

		// Encoder nach Latin-1 erstellen
		CharsetEncoder encoder = Charset.forName("ISO-8859-1").newEncoder();
		
		// Einen CharBuffer mit dem zu konvertierenden String füllen
		CharBuffer in  = CharBuffer.allocate(len);
		in.put(utf8String);
		in.rewind();
		
		// Einen ByteBuffer für das Ergebnis erstellen
		ByteBuffer out = ByteBuffer.allocate(len);
		
		// Konvertierung ausführen
		encoder.reset();
		encoder.encode(in, out, true);
		encoder.flush(out);
		
		// Ergebnis ausgeben
		logByteBuffer(out);
		
		// Ergebnis zurückgeben
		return out;
	}
	
	/**
	 * Umwandlung eines ByteBuffers mit Latin-1-Zeichen in einen UTF-8 String.
	 * 
	 * @param latin1Buffer Die umzuwandelnden Latin-1-Zeichen in einem ByteBuffer.
	 * @return Ein UTF-8-String.
	 */
	public String decode(ByteBuffer latin1Buffer) {
		// Zu konvertierenden String loggen
		logByteBuffer(latin1Buffer);
		
		// Länge des zu konvertierenden Strings ermitteln.
		latin1Buffer.rewind();
		int len = latin1Buffer.remaining();

		// Decoder erstellen
		CharsetDecoder decoder = Charset.forName("ISO-8859-1").newDecoder();
		

		// Einen ByteBuffer für das Ergebnis erstellen
		// Die Länge des UTF-8-Strings kann nicht größer sein als 4 * der Latin-1-kodierte String
		CharBuffer erg  = CharBuffer.allocate(len * 4);

		
		// Konvertierung ausführen
		decoder.reset();
		decoder.decode(latin1Buffer, erg, true);
		decoder.flush(erg);
		
		
		// Ergebnis ausgeben
		erg.rewind();
		Utils.log("(StringCoding decode) erg = " +  erg.toString());

		// Ergebnis zurückgeben
		return erg.toString();
	}
	
	
	private void logByteBuffer(ByteBuffer byteBuffer) {
		byteBuffer.rewind();
		byte[] dst = new byte[byteBuffer.remaining()];
		byteBuffer.get(dst);
		String outString = new String(dst);
		Utils.log("(StringCoding logByteBuffer) byteBuffer = " + outString);
	}


}
