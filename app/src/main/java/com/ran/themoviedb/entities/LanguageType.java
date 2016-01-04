package com.ran.themoviedb.entities;

/**
 * Created by ranjith.suda on 12/31/2015.
 *
 * Different Language Options Available in the App
 */
public enum LanguageType {

  ENGLISH("en"),
  GERMAN("de"),
  SPANISH("es"),
  FRENCH("fr"),
  ITALIAN("it"),
  RUSSIAN("ru"),
  PORTUGUESE("pt"),
  HUNGARIAN("hu"),
  CHINESE("zh"),
  CZECH("cs");

  String code;

  LanguageType(String code) {
    this.code = code;
  }

  /**
   * To return the LanguageType based on the Code passed
   *
   * @param code -- String code
   * @return -- LanguageType
   */
  public static LanguageType getLangType(String code) {
    for (LanguageType languageType : values()) {
      if (languageType.code.equalsIgnoreCase(code)) {
        return languageType;
      }
    }
    return ENGLISH;
  }

  /**
   * To Return the Language String based on LanguageType
   *
   * @param languageType -- LanguageType
   * @return -- String
   */

  public static String getLangString(LanguageType languageType) {
    for (LanguageType lgType : values()) {
      if (lgType == languageType) {
        return languageType.code;
      }
    }
    return ENGLISH.code;
  }
}
