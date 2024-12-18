# Huffman File Compression

## Overview

This project implements Huffman encoding for lossless file compression and decompression. The program uses character frequency analysis to construct a binary tree, generating optimal prefix-free codes for efficient encoding. Files can be compressed and decompressed while maintaining the original content.

---

## Key Features

### Encoding
- **Frequency Table Generation:** Reads a file and counts character frequencies using a `TreeMap` for sorted storage.
- **Code Tree Construction:** Builds a Huffman tree using a `PriorityQueue` to combine nodes based on character frequency.
- **Code Map Creation:** Generates prefix-free binary codes for each character through a single tree traversal.

### Compression
- **Bitwise Encoding:** Encodes file data into a compressed binary format using a character-to-code map.
- **Custom Bit Writer:** Writes compressed binary data to a file efficiently.

### Decompression
- **Tree-Based Decoding:** Reads compressed bits, traverses the Huffman tree, and reconstructs the original file.
- **Custom Bit Reader:** Processes binary data to retrieve the encoded content.

---

## How It Works

1. **Frequency Analysis:**
   - Input: A plain text file (e.g., `USConstitution.txt`).
   - Output: A frequency map of characters in the file.

2. **Tree Construction:**
   - Constructs a binary Huffman tree based on character frequencies.
   - Nodes with lower frequencies are placed deeper in the tree, ensuring efficient encoding.

3. **Compression:**
   - Encodes the file using the Huffman tree, translating characters into binary codes.
   - Writes compressed data to an output file (e.g., `USConstitution_compressed.txt`).

4. **Decompression:**
   - Reads compressed binary data and uses the Huffman tree to decode it.
   - Outputs the reconstructed file (e.g., `USConstitution_decompressed.txt`) identical to the original.

---

## Acknowledgments

Developed as part of the Dartmouth CS curriculum, Fall 2024.

---

## License

This project is distributed under the MIT License. Refer to the LICENSE file for detailed terms and conditions.

