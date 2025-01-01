package org.mercuriusb.collectio.utils;

@SuppressWarnings("ALL")
public class LtreeUtils{

  public static String escape(String value){
    StringBuffer buffer = new StringBuffer();
    for(int i=0; i < value.length(); i++){
      char c = value.charAt(i);
      if(c == '/'){
        buffer.append(".");
        continue;
      }else if(!(c >= 'a' && c <= 'z') && !(c >= 'A' && c <= 'Z') && !(c >= '0' && c <= '9')){
        String ustr = String.format("\\u%04x", (int) c);
        buffer.append("_").append(ustr.substring(2));
        continue;
      }
      buffer.append(c);
    }
    return buffer.toString();
  }

  public static String unescape(String value){
    StringBuffer buffer = new StringBuffer();
    for(int i=0; i < value.length(); i++){
      char c = value.charAt(i);
      try{
        if(c == '.'){
          c = '/';
        }else if(c == '_' && i + 5 < value.length()){
          String ustr = value.substring(i + 1, i + 5);
          int code = Integer.parseInt(ustr, 16);
          c = (char) code;
          i += 4;
        }
      }catch(NumberFormatException ex){
      }
      buffer.append(c);
    }
    return buffer.toString();
  }


}
