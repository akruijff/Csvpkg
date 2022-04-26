package org.akruijff.csvpkg.usercases.util;

import com.opencsv.*;
import com.opencsv.exceptions.*;
import org.akruijff.csvpkg.entities.*;
import org.akruijff.csvpkg.exceptions.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class IOUtil {
    public static Sheet read() {
        try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(System.in)))) {
            Headers headers = readHeaders(reader);
            if (headers.length() == 0)
                return new Sheet();

            Row[] rows = readRows(reader, headers.length());
            return new Sheet(headers, rows);
        } catch (IOException e) {
            throw new ReadError();
        } catch (CsvValidationException e) { // thrown when a user-defined validator fail
            throw new UnsupportedOperationException();
        }
    }

    private static Headers readHeaders(CSVReader reader) throws IOException, CsvValidationException {
        String[] arr = reader.readNext();
        if (arr == null)
            return new Headers(new String[0]);
        String[] trimmed = Arrays.stream(arr).map(String::trim).toArray(String[]::new);
        return new Headers(trimmed);
    }

    private static Row[] readRows(CSVReader reader, int columns) throws IOException, CsvValidationException {
        String[] line;
        List<Row> list = new ArrayList<>();
        int lineNumber = 1;
        while ((line = reader.readNext()) != null) {
            Object[] cells = Arrays.stream(line)
                    .map(String::trim)
                    .map(IOUtil::convert)
                    .toArray(Object[]::new);
            list.add(new Row(lineNumber, cells));
            ++lineNumber;
        }
        return list.toArray(new Row[list.size()]);
    }

    private static Object convert(String value) {
        if (is(value, Integer::parseInt))
            return Integer.parseInt(value);
        if (is(value, Double::parseDouble))
            return Double.parseDouble(value);
        String v = value.replace(',', '.');
        if (is(v, Double::parseDouble))
            return Double.parseDouble(v);
        return value;
    }

    private static boolean is(String value, Function<String, Object> convertor) {
        try {
            convertor.apply(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void write(Sheet input) {
        writeHeaders(input.headers());
        writeData(input);
        System.out.flush();
    }

    private static void writeHeaders(Headers headers) {
        writeln(headers::get, headers.length());
    }

    private static void writeData(Sheet sheet) {
        sheet.rows().forEach(row -> writeRow(row));
    }

    private static void writeRow(Row row) {
        writeln(row::column, row.length());
    }

    private static void writeln(IntFunction<Object> func, int n) {
        if (n == 0)
            return;
        --n;
        printStart();
        for (int i = 0; i < n; ++i)
            printNext(func, i);
        printLast(func, n);
    }

    private static void printStart() {
        System.out.print('"');
    }

    private static void printNext(IntFunction<Object> func, int i) {
        System.out.print(func.apply(i));
        System.out.print("\",\"");
    }

    private static void printLast(IntFunction<Object> func, int n) {
        System.out.print(func.apply(n));
        System.out.println('"');
    }
}
