package storm.starter.scala;

import scala.util._
import collection.JavaConversions._

import backtype.storm._
import backtype.storm.Config._
import backtype.storm.spout._
import backtype.storm.task._
import backtype.storm.topology._
import backtype.storm.topology.base._
import backtype.storm.tuple._

class RandomSentenceSpout extends BaseRichSpout {

  var collector: SpoutOutputCollector = null
  var rand: Random = null

  override def open(conf: java.util.Map[_, _], context: TopologyContext, collector: SpoutOutputCollector) {
    this.collector = collector
    rand = new Random
  }

  override def nextTuple {
    Thread.sleep(100)
    val sentences = Array(
      "the cow jumped over the moon",
      "an apple a day keeps the doctor away",
      "four score and seven years ago",
      "snow white and the seven dwarfs",
      "i am at two with nature");

    val sentence = sentences(rand.nextInt(sentences.length));
    collector.emit(new Values(sentence));
  }

  override def ack(id: AnyRef) {}

  override def fail(id: AnyRef) {}

  override def declareOutputFields(declarer: OutputFieldsDeclarer) =
    declarer.declare(new Fields("word"))

}

class SplitSentence extends BaseRichBolt {

  var collector: OutputCollector = null

  override def prepare(stormConf: java.util.Map[_, _], context: TopologyContext, collector: OutputCollector) {
    this.collector = collector
  }

  override def execute(tuple: Tuple) {
    tuple.getString(0).split(" ").foreach(
      word => collector.emit(tuple, new Values(word)))
    collector.ack(tuple)
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) =
    declarer.declare(new Fields("word"))

}

class WordCount extends BaseBasicBolt {

  val counts = collection.mutable.Map[String, Int]()

  override def execute(tuple: Tuple, collector: BasicOutputCollector) {
    val word = tuple.getString(0)
    val count = counts.getOrElseUpdate(word, 0)
    counts(word) = count + 1
    collector.emit(new Values(word, count: java.lang.Integer))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) =
    declarer.declare(new Fields("word", "count"))

}

object WordCountTopology {

  def makeTopoLogy = {
    val builder = new TopologyBuilder;
    builder.setSpout("spout", new RandomSentenceSpout, 5)
    builder.setBolt("split", new SplitSentence, 8)
      .shuffleGrouping("spout")
    builder.setBolt("count", new WordCount, 12)
      .fieldsGrouping("split", new Fields("word"))
    builder.createTopology
  }

  def runInLocal() {
    val cluster = new LocalCluster
    cluster.submitTopology("word-count",
      Map[String, Any](
        TOPOLOGY_DEBUG -> true,
        TOPOLOGY_WORKERS -> 3), makeTopoLogy)
    Thread.sleep(10000)
    cluster.shutdown()
  }

  def runInCluster(name: String) {
    StormSubmitter.submitTopology(
      name,
      Map[String, Any](
        TOPOLOGY_DEBUG -> true,
        TOPOLOGY_MAX_TASK_PARALLELISM -> 3),
      makeTopoLogy)
  }

  def main(args: Array[String]) {
    args match {
      case Array(name) => runInCluster(name)
      case Array() => runInLocal
      case _ => System.err.println("Invalid args: " + args.mkString(" "))
    }
  }

}
