import java.io.*;
import java.util.*;

/**
 * A class to create a huffman tree
 * Author: Jacob Bacus
 */
public class HuffmanEncoding {

    /**
     * Read file provided in pathName and count how many times each character appears
     *
     * @param pathName - path to a file to read
     * @return - Map with a character as a key and the number of times the character appears in the file as value
     * @throws IOException
     */
    public Map<Character, Long> countFrequencies(String pathName) throws IOException {
        Map<Character, Long> map = new TreeMap<>();
        try {
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            int readValue = input.read();
            if (readValue == -1) {
                input.close();
                return null;
            }
            // generates value map and increases value if it is a repeat
            while (readValue != -1) {
                char currChar = (char)readValue;
                if (!map.containsKey(currChar)) {
                    map.put(currChar, 1L);
                } else {
                    Long currValue = map.get(currChar);
                    currValue++;
                    map.put(currChar, currValue);
                }

                readValue = input.read();
            }
            input.close();
            return map;

        } catch (IOException e) {
            System.out.println(e);
            return map;
        }
    }

    /**
     * Construct a code tree from a map of frequency counts. Note: this code should handle the special
     * cases of empty files or files with a single character.
     *
     * @param frequencies a map of Characters with their frequency counts from countFrequencies
     * @return the code tree.
     */
    public BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies) {
        TreeComparator treeCompare = new TreeComparator();
        PriorityQueue<BinaryTree<CodeTreeElement>> queue = new PriorityQueue<>(treeCompare);
        // empty tree exception
        if(frequencies == null) {
            return null;
        }
        // single tree exception
        if(frequencies.size() == 1) {
            Set<Character> keys = frequencies.keySet();
            Character key = keys.iterator().next();
            BinaryTree<CodeTreeElement> leftTree = new BinaryTree<>(new CodeTreeElement(frequencies.get(key), key));
            return new BinaryTree<>(new CodeTreeElement((frequencies.get(key)), null), leftTree, leftTree);

        }
        //loops through all characters in map and generates tree
        Set<Character> keys = frequencies.keySet();
        for(Character key : keys) {
            BinaryTree<CodeTreeElement> currentTree = new BinaryTree<>(new CodeTreeElement(frequencies.get(key), key));
            queue.add(currentTree);
        }
        //creates a new addition to generate tree
        while(queue.size() > 1) {
            BinaryTree<CodeTreeElement> t1 = queue.remove();
            BinaryTree<CodeTreeElement> t2 = queue.remove();
            BinaryTree<CodeTreeElement> addition = new BinaryTree<>(new CodeTreeElement((t1.data.getFrequency() + t2.data.getFrequency()), null), t1, t2);
            queue.add(addition);
        }
        if (queue.size() != 1) {
            System.out.println("Unexpected Map Structure (likely missing file path)");
        }
        return queue.remove();
    }

    /**
     * Computes the code for all characters in the tree and enters them
     * into a map where the key is a character and the value is the code of 1's and 0's representing
     * that character.
     *
     * @param codeTree the tree for encoding characters produced by makeCodeTree
     * @return the map from characters to codes
     */
    public Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree) {
        return treeTraversal("", codeTree, new HashMap<>());
    }

    /**
     *
     * @param currString the string of letters from navigation
     * @param codeTree the tree comrpsied of characters and their frequency values
     * @param map map that has currently been made
     * @return completed map of characters to their string equivalent
     */
    public Map<Character, String> treeTraversal(String currString, BinaryTree<CodeTreeElement> codeTree, Map<Character, String> map){
        if (codeTree == null) {
            return null;
        }
        if (codeTree.isLeaf()) {
            map.put(codeTree.data.getChar(), currString);
            return map;
        }

        if (codeTree.hasLeft()) {
            treeTraversal((currString + "0"), codeTree.getLeft(), map);
        }
        if (codeTree.hasRight()) {
            treeTraversal((currString + "1"), codeTree.getRight(), map);
        }
        return map;
    }

    /**
     * Compress the file pathName and store compressed representation in compressedPathName.
     *
     * @param codeMap            - Map of characters to codes produced by computeCodes
     * @param pathName           - File to compress
     * @param compressedPathName - Store the compressed data in this file
     * @throws IOException
     */
    public void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException {
        try {
            BufferedReader input = new BufferedReader(new FileReader(pathName));
            BufferedBitWriter bitOutput = new BufferedBitWriter(compressedPathName);
            try {
                int next;
                // loops through every character, turns character into bits which are put into compressed file
                while ((next = input.read()) != -1) {
                    String curr = codeMap.get((char)next);
                    for (int i = 0; i < curr.length(); i++) {
                        boolean nextBit;
                        if (curr.charAt(i) == '0') {
                            nextBit = false;
                        } else {
                            nextBit = true;
                        }
                        bitOutput.writeBit(nextBit);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                input.close();
                bitOutput.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Decompress file compressedPathName and store plain text in decompressedPathName.
     *
     * @param compressedPathName   - file created by compressFile
     * @param decompressedPathName - store the decompressed text in this file, contents should match the original file before compressFile
     * @param codeTree             - Tree mapping compressed data to characters
     * @throws IOException
     */
    public void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException {
        try {
            BufferedBitReader bitInput = new BufferedBitReader(compressedPathName);
            try (BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName))) {
                BinaryTree<CodeTreeElement> currTree = codeTree;

                // pulls bits from compressed file, navigates tree to turn them into strings which correspond to characters
                // characters are then put into file
                while (bitInput.hasNext()) {
                    boolean bit = bitInput.readBit();
                    if (!bit) {
                        currTree = currTree.getLeft();
                    } else {
                        currTree = currTree.getRight();
                    }
                    if (currTree.isLeaf()) {
                        output.write(currTree.data.getChar());
                        currTree = codeTree;
                    }

                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                bitInput.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        HuffmanEncoding myEncoder = new HuffmanEncoding();
        // no .txt
        String file = "USConstitution";
        Map<Character, Long> myMap = myEncoder.countFrequencies(file + ".txt");
        BinaryTree<CodeTreeElement> myTree = myEncoder.makeCodeTree(myMap);

        System.out.println("This is the frequency of each character: ");
        System.out.println(myMap);
        System.out.println("\n\nThis is the tree made from frequencies: ");
        System.out.println(myTree);
        System.out.println("\n\nThis is the codes for each character: ");
        Map<Character, String> codes = myEncoder.computeCodes(myTree);
        System.out.println(codes);
        myEncoder.compressFile(codes, file + ".txt", file + "_compressed.txt");
        myEncoder.decompressFile(file + "_compressed.txt", file + "_decompressed.txt", myTree);

    }
}
