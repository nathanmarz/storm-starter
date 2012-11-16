require "./storm"

class SplitSentenceBolt < Storm::Bolt
  def process(tup)
    tup.values[0].split(" ").each do |word|
      emit([word])
    end
    ack(tup)
  end
end

SplitSentenceBolt.new.run
