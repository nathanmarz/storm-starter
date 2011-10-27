# Example Storm topologies

This is the easiest way to try out [Storm](https://github.com/nathanmarz/storm).

## Dependencies

You need `java` and `git` in your path.  Python and Ruby are required for two of the examples.

## Copy and paste into terminal

    # Download `lein` to a directory on your PATH (assuming $HOME/bin):
    curl https://raw.github.com/technomancy/leiningen/stable/bin/lein -o ~/bin/lein

    # Clone storm-starter
    git clone git://github.com/nathanmarz/storm-starter.git && cd storm-starter

    # Download all dependencies
    lein deps

    # Compile
    lein compile

    # Run:
    java -cp classes:multilang`find lib -name '*.jar' | perl -pe 'chomp; s/^/:/'` storm.starter.ExclamationTopology

    java -cp classes:multilang`find lib -name '*.jar' | perl -pe 'chomp; s/^/:/'` storm.starter.WordCountTopology

    # For extra points, use Ruby instead of Python:
    cat <<EOT > multilang/resources/splitsentence.rb
    require './storm'
    class SplitSentenceBolt < Storm::Bolt
      def process(tup)
        tup.values[0].split(" ").each { |word| emit([word]) }
      end
    end
    SplitSentenceBolt.new.run
    EOT

    perl -pi -e 's/python/ruby/;s/\.py/.rb/' src/jvm/storm/starter/WordCountTopology.java

    lein compile

    java -cp classes:multilang`find lib -name '*.jar' | perl -pe 'chomp; s/^/:/'` storm.starter.WordCountTopology
