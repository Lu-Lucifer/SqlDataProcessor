package com.zwc.sqldataprocessor.core.exporter;

import java.io.IOException;

import java.util.List;

import com.zwc.sqldataprocessor.Global;
import com.zwc.sqldataprocessor.core.entity.DataList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvExporter {

    public byte[] export(DataList table, boolean exportNulls) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            CSVPrinter csvPrinter = new CSVPrinter(stringBuilder, CSVFormat.DEFAULT);
            csvPrinter.printRecord(table.columns);
            String[] csvValues = new String[table.columns.size()];
            for (List<String> values : table.rows) {
                for (int columnIndex = 0; columnIndex < csvValues.length; ++columnIndex) {
                    String value = values.get(columnIndex);
                    csvValues[columnIndex] = value == null && exportNulls ? "<null>" : value;
                }
                csvPrinter.printRecord(csvValues);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString().getBytes();
    }

}
