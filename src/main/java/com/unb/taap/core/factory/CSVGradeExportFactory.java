package com.unb.taap.core.factory;

public class CSVGradeExportFactory extends GradeExportFactory {
    @Override
    public GradeExporter createGradeExporter() {
        return new CSVGradeExporter();
    }
}
