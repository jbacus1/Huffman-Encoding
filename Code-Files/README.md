## Files

### Huffman Encoding files

- BufferedBitWriter.java: Handles bit-level file writing by accumulating bits into bytes before writing to a file. Ensures correct handling of partial bytes and writes metadata to enable accurate reading with a bit reader.

- BufferedBitReader.java: Provides bit-level file reading, allowing retrieval of individual bits from a file. It ensures correct handling of partially filled bytes and includes a hasNext() method to check for remaining bits.

- CodeTreeElement.java: Represents a (frequency, character) pair used in the Huffman Code Tree, providing methods to access the frequency and character values.

- HuffmanEncoding.java: Implements Huffman encoding and decoding for file compression. It uses a TreeMap for frequency analysis, a PriorityQueue for building the Huffman tree, and recursive tree traversal to generate prefix-free binary codes. The class also handles file compression with custom bit-writing and decompression by traversing the Huffman tree to reconstruct the original content.

- TreeComparator.java: Defines a comparator for BinaryTree objects based on frequency values, enabling priority-based operations in the Huffman tree construction.

### Text files

- USConstitution.txt/compressed/decompressed: Holds the US Constitution, a compressed version made by Huffman Encoding, and another file containing the US Consitution generated from the compressed file respectively

- WarAndPeace.txt/compressed/decompressed: Same as US Constitution, but the text is War and Peace by Leo Tolstoy
