import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
        BoardNode[][] boardGraph = getBoardNodes(board, cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                validWords.addAll(getValidWords(boardGraph[i][j], root));
            }
        }
        return validWords;
    }

    private BoardNode[][] getBoardNodes(BoggleBoard board, int cols, int rows) {
        BoardNode[][] boardGraph = new BoardNode[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardGraph[i][j] = new BoardNode(board.getLetter(i, j));
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int row = i - 1; row <= i + 1; row++) {
                    for (int col = j - 1; col <= j + 1; col++) {
                        if (row < 0 || row >= rows || col < 0 || col >= cols || (row == i && col == j)) {
                            continue;
                        }
                        boardGraph[i][j].addAdj(boardGraph[row][col]);
                    }
                }
            }
        }
        return boardGraph;
    }

    private List<String> getValidWords(BoardNode boardNode, Node letterNode) {
        List<String> validWords = new LinkedList<>();
        if(boardNode.isVisited()) return validWords;

        Node nextLetterNode = getNextLetterNode(letterNode, boardNode.getLetter());
        if(nextLetterNode == null) {
            return validWords;
        }

        String word = nextLetterNode.getWord();
        if(word != null && word.length() > 2) validWords.add(word);
        if(nextLetterNode.isLeaf()) return validWords;

        boardNode.setVisited(true);
        for (BoardNode adjNode : boardNode.getAdjs()) {
            validWords.addAll(getValidWords(adjNode, nextLetterNode));
        }
        boardNode.setVisited(false);
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
        private Node[] children;
        private String word;
        private boolean leaf;

        public Node() {
            this.children = new Node[26];
            this.word = null;
            this.leaf = true;
        }

        public Node getLetterNode(char letter) {
            return children[letter - 65];
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public Node getOrCreate(char letter) {
            Node letterNode = children[letter - 65];
            if (letterNode == null) {
                letterNode = new Node();
                children[letter-65] = letterNode;
                leaf = false;
            }
            return letterNode;
        }

        public boolean isLeaf() {
            return leaf;
        }
    }

    private class BoardNode {
        private List<BoardNode> adjs;
        private char letter;
        private boolean visited;

        public BoardNode(char letter) {
            this.letter = letter;
            adjs = new ArrayList<>();
        }

        public void addAdj(BoardNode adj) {
            adjs.add(adj);
        }

        public List<BoardNode> getAdjs() {
            return adjs;
        }

        public char getLetter() {
            return letter;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }
}