public class Outcast {

    private WordNet wordNet;
    private Iterable<String> nouns;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
        nouns = wordnet.nouns();
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDistance = Integer.MIN_VALUE;
        String outcast = null;
        for (String noun : nouns) {
            int dist = calculateDistance(noun);
            if (dist > maxDistance) {
                maxDistance = dist;
                outcast = noun;
            }
        }
        return outcast;
    }

    private int calculateDistance(String entry) {
        int totalDistance = 0;
        for (String noun : nouns) {
            totalDistance += wordNet.distance(entry, noun);
        }
        return totalDistance;
    }

    // see test client below
    public static void main(String[] args) {

    }

}