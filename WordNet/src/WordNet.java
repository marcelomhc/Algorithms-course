import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WordNet {

    private HashMap<String, List<Integer>> wordNet;
    private ArrayList<String> synonims;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        wordNet = new HashMap<>();
        synonims = new ArrayList<>();
        int size = 0;

        In synIn = new In(synsets);
        while (synIn.hasNextLine()) {
            String line = synIn.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            String[] nouns = tokens[1].split(" ");
            synonims.add(id, tokens[1]);
            for (String noun : nouns) {
                List<Integer> vertexes = wordNet.get(noun);
                if(vertexes == null) {
                    vertexes = new ArrayList<>();
                }
                vertexes.add(id);
                wordNet.put(noun, vertexes);
            }
            size++;
        }

        In hyperIn = new In(hypernyms);
        Digraph graph = new Digraph(size);
        while (hyperIn.hasNextLine()) {
            String line = hyperIn.readLine();
            String[] edges = line.split(",");
            for (int i=1; i<edges.length; i++) {
                graph.addEdge(Integer.parseInt(edges[0]), Integer.parseInt(edges[i]));
            }
        }
        for(int i=0; i<graph.V(); i++) {
            if (graph.outdegree(i) == 0) {
                sap = new SAP(graph);
                return;
            }
        }
        throw new IllegalArgumentException("WordNet must be a DAG");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordNet.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word cant be null");
        }
        return wordNet.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Argument not member of WordNet");
        }
        return sap.length(wordNet.get(nounA), wordNet.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Argument not member of WordNet");
        }
        return synonims.get(sap.ancestor(wordNet.get(nounA), wordNet.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}