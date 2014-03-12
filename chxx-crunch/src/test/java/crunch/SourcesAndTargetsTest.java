package crunch;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.Pipeline;
import org.apache.crunch.Source;
import org.apache.crunch.TableSource;
import org.apache.crunch.impl.mem.MemPipeline;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.io.From;
import org.apache.crunch.test.TemporaryPath;
import org.apache.crunch.types.avro.Avros;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SourcesAndTargetsTest {

  @Rule
  public transient TemporaryPath tmpDir = new TemporaryPath();

  @Test
  public void testReadTextFile() throws IOException {
    List<String> expectedContent = Lists.newArrayList("2", "3", "1", "5");
    String inputPath = tmpDir.copyResourceFileName("ints.txt");
    Pipeline pipeline = MemPipeline.getInstance();
    PCollection<String> lines = pipeline.readTextFile(inputPath);
    Iterable<String> materialized = lines.materialize();
    assertEquals(expectedContent, Lists.newArrayList(materialized));
    pipeline.done();
  }

  @Test
  public void testReadFromTextFile() throws IOException {
    List<String> expectedContent = Lists.newArrayList("2", "3", "1", "5");
    String inputPath = tmpDir.copyResourceFileName("ints.txt");
    Pipeline pipeline = MemPipeline.getInstance();
    PCollection<String> lines = pipeline.read(From.textFile(inputPath));
    Iterable<String> materialized = lines.materialize();
    assertEquals(expectedContent, Lists.newArrayList(materialized));
    pipeline.done();
  }

  @Test
  public void testReadFromTextFileAsAvro() throws IOException {
    List<String> expectedContent = Lists.newArrayList("2", "3", "1", "5");
    String inputPath = tmpDir.copyResourceFileName("ints.txt");
    Pipeline pipeline = MemPipeline.getInstance();
    PCollection<String> lines = pipeline.read(From.textFile(inputPath, Avros.strings()));
    Iterable<String> materialized = lines.materialize();
    assertEquals(expectedContent, Lists.newArrayList(materialized));

    pipeline.done();
  }

  @Test
  @Ignore("fails since Crunch (incorrectly) assumes key is NullWritable")
  public void testReadValuesFromSequenceFile() throws IOException {
    String inputPath = tmpDir.copyResourceFileName("numbers.seq");
    Pipeline pipeline = new MRPipeline(SourcesAndTargetsTest.class);
    PCollection<Text> lines = pipeline.read(From.sequenceFile(inputPath, Text.class));
    Iterable<Text> materialized = lines.materialize();
    assertTrue(Lists.newArrayList(materialized).contains(new Text("One, two, buckle my shoe")));
    pipeline.done();
  }

  @Test
  public void testReadPTableFromSequenceFile() throws IOException {
    String inputPath = tmpDir.copyResourceFileName("numbers.seq");
    Pipeline pipeline = new MRPipeline(SourcesAndTargetsTest.class);
    TableSource<IntWritable, Text> source =
        From.sequenceFile(inputPath, IntWritable.class, Text.class);
    PTable<IntWritable, Text> table = pipeline.read(source);
    Map<IntWritable, Text> map = table.materializeToMap();
    assertEquals(new Text("Nine, ten, a big fat hen"), map.get(new IntWritable(1)));
    pipeline.done();
  }

//  @Test
//  public void testReadFromAvroFile() throws IOException {
//    String inputPath = tmpDir.copyResourceFileName("weather.avro");
//    Pipeline pipeline = new MRPipeline(SourcesAndTargetsTest.class);
//    Source<WeatherRecord> source =
//        From.avroFile(inputPath, Avros.specifics(WeatherRecord.class));
//    PCollection<WeatherRecord> records = pipeline.read(source);
//    assertEquals((Long) 10L, records.length().getValue());
//    pipeline.done();
//  }

}
