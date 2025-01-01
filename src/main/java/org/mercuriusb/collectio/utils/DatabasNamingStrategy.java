package org.mercuriusb.collectio.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class DatabasNamingStrategy extends PhysicalNamingStrategyStandardImpl {
  private static final String TABLE_PREFIX = "col_";

  @Override
  public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
    if (name == null) {
      return null;
    }
    final String newName = TABLE_PREFIX + camelCaseToUnderscore(name.getText());
    return Identifier.toIdentifier(newName);
  }

  private String camelCaseToUnderscore(String camelCase) {
    return camelCase
        .replaceAll("(.)(\\p{Upper})", "$1_$2")
        .toLowerCase();
  }
}
