// to use this example, uncomment the twitter4j dependency information in the project.clj,
// uncomment storm.starter.spout.TwitterSampleSpout, and uncomment this class

package storm.starter;

import storm.starter.spout.TwitterSampleSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.starter.bolt.PrinterBolt;


public class PrintSampleStream {        
    public static void main(String[] args) {
    	String consumerKey = args[0];
    	String consumerSecret = args[1];
    	String accessToken = args[2];
    	String accessTokenSecret = args[3];
    	String[] keyWords = Arrays.copyOfRange(arguments, 4, arguments.length);
    	
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("spout", new TwitterSampleSpout(consumerKey, 
        consumerSecret, accessToken, accessTokenSecret, keyWords));
        builder.setBolt("print", new PrinterBolt())
                .shuffleGrouping("spout");
                
        
        Config conf = new Config();
        
        
        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("test", conf, builder.createTopology());
        
        Utils.sleep(10000);
        cluster.shutdown();
    }
}
