// cc MaxTemperature Application to find the maximum temperature in the weather dataset
// vv MaxTemperature
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 实现MapReduce程序的三部分：
 * 1、实现map函数，见 MaxTemperatureMapper；
 * 2、实现reduce函数，见 MaxTemperatureReducer；
 * 3、运行job,见 MaxTemperature；
 * */
public class MaxTemperature {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: MaxTemperature <input path> <output path>");
      System.exit(-1);
    }
    
    Job job = new Job();
    // 1)在集群环境运行时，不必指明jar文件名称
    job.setJarByClass(MaxTemperature.class);
    job.setJobName("Max temperature");

    // 2)输入输出路径
    FileInputFormat.addInputPath(job, new Path(args[0]));
    // 3)输出输出路径
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    // 4)指定map和reduce类
    job.setMapperClass(MaxTemperatureMapper.class);
    job.setReducerClass(MaxTemperatureReducer.class);
    // 5)指定输出类型
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    // 6)是否生成详细输出
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
// ^^ MaxTemperature
