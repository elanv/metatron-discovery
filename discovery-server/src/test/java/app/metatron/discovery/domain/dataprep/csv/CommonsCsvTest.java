package app.metatron.discovery.domain.dataprep.csv;

import app.metatron.discovery.domain.dataprep.teddy.DataFrame;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonsCsvTest {

  // utility functions for building URIs, paths
  public static String buildPathToTestOutputDir(String relpath) {
    return String.format("%s/src/test/resources/test_output/%s", System.getProperty("user.dir"), relpath);
  }

  public static String buildStrUrlFromResourceDir(String relpath) {
    return String.format("file://%s/src/test/resources/%s", System.getProperty("user.dir"), relpath);
  }


  @Test
  public void test_basic() {
    FileWriter writer = null;
    CSVPrinter printer = null;

    try {
      writer = new FileWriter(buildPathToTestOutputDir("CommonsCsvTest.csv"), false);    // append = false
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      printer = new CSVPrinter(writer, CSVFormat.RFC4180);
    } catch (IOException e) {
      e.printStackTrace();
    }

    List<Object> rows = new ArrayList();
    rows.add(new ArrayList(Arrays.asList("id", "userName", "firstName", "lastName", "birthday")));
    rows.add(new ArrayList(Arrays.asList(1, "john73", "John", "Doe", "1973-09-15")));
    rows.add(new ArrayList(Arrays.asList(2, "mary", "Mary", "Meyer", "1985-03-29")));

    try {
      for (Object row : rows) {
        printer.printRecord((List<Object>) row);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      printer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void test_with_df() {
    String strUri = buildStrUrlFromResourceDir("teddy/sale.csv");
    PrepCsvParseResult result = PrepCsvUtil.parse(strUri, ",", 30, null, false);
    DataFrame df = new DataFrame();
    df.setByGrid(result.grid, null);
    df.show();
  }

  @Test
  public void test_bom() {
    String strUri = buildStrUrlFromResourceDir("teddy/sale_bom16.csv");
    PrepCsvParseResult result = PrepCsvUtil.parse(strUri, ",", 30, null, false);
    DataFrame df = new DataFrame();
    df.setByGrid(result.grid, null);
    df.show();
  }

  @Test
  public void test_header() {
    String strUri = buildStrUrlFromResourceDir("teddy/sale_bom16.csv");
    PrepCsvParseResult result = PrepCsvUtil.parse(strUri, ",", 30, null, false);
    DataFrame df = new DataFrame();
    df.setByGrid(result.grid, null);
    df.show();
  }
}
