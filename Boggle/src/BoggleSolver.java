import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoggleSolver {
    private Node root;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        root = new Node();

        for(String word : dictionary) {
            Node currentNode = root;
            for (int i = 0; i < word.length(); i++) {
                currentNode = currentNode.getOrCreate(word.charAt(i));
            }
            currentNode.setWord(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int cols = board.cols();
        int rows = board.rows();

        Set<String> validWords = new HashSet<>();
        boolean[][] visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                validWords.addAll(getValidWords(board, root, visited, i, j));
            }
        }
        return validWords;
    }

    private Set<String> getValidWords(BoggleBoard board, Node letterNode, boolean[][] visited, int row, int col) {
        Set<String> validWords = new HashSet<>();
        if(row == -1 || col == -1 || row == board.rows() || col == board.cols()) return validWords;
        if(visited[row][col]) return validWords;

        Node nextLetterNode = getNextLetterNode(letterNode, board.getLetter(row, col));
        if(nextLetterNode == null) {
            return validWords;
        }

        String word = nextLetterNode.getWord();
        if(word != null && word.length() > 2) validWords.add(word);
        if(nextLetterNode.isLeaf()) return validWords;

        visited[row][col] = true;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                validWords.addAll(getValidWords(board, nextLetterNode, visited, row+i, col+j));
            }
        }

        visited[row][col] = false;
        return validWords;
    }

    private Node getNextLetterNode(Node letterNode, char letter) {
        Node nextLetterNode = letterNode.getLetterNode(letter);
        if(letter != 'Q') {
            return nextLetterNode;
        }
        if(nextLetterNode != null) {
            nextLetterNode = nextLetterNode.getLetterNode('U');
        }
        return nextLetterNode;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        Node currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            currentNode = currentNode.getLetterNode(word.charAt(i));
            if(currentNode == null) return 0;
        }
        if (currentNode.getWord() == null) return 0;

        int size = word.length();

        if (size < 3) return 0;
        else if (size < 5) return 1;
        else if (size < 6) return 2;
        else if (size < 7) return 3;
        else if (size < 8) return 5;
        else return 11;
    }

    private class Node {
        private Map<Character, Node> children;
        private String word;

        public Node() {
            this.children = new HashMap<>();
            this.word = null;
        }

        public Node getLetterNode(char letter) {
            return children.get(letter);
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public Node getOrCreate(char letter) {
            if (children.containsKey(letter)) return children.get(letter);

            Node letterNode = new Node();
            children.put(letter, letterNode);
            return letterNode;
        }

        public boolean isLeaf() {
            return children == null;
        }
    }
}