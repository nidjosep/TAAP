package com.unb.taap.core.factory;

public class JSONGradeExportFactory extends GradeExportFactory {

  @Override
  public GradeExporter createGradeExporter() {
    return new JSONGradeExporter();
  }
}
