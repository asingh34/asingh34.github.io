// Import any package as required
import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.Comparable;
//code taken from huffman lecture, found on blackboard 
public class HuffmanSubmit implements Huffman {
 
	//Taken from huffman.java
    /** A bunch of utility functions. */
    private static class DSutil<E> {

      /** Swap two Objects in an array
          @param A The array
          @param p1 Index of one Object in A
          @param p2 Index of another Object A
      */
      public static <E> void swap(E[] A, int p1, int p2) {
        E temp = A[p1];
        A[p1] = A[p2];
        A[p2] = temp;
      }

      /** Randomly permute the Objects in an array.
          @param A The array
      */

    // int version
    // Randomly permute the values of array "A"
    static void permute(int[] A) {
      for (int i = A.length; i > 0; i--) // for each i
        swap(A, i-1, DSutil.random(i));  //   swap A[i-1] with
    }                                    //   a random element

      public static void swap(int[] A, int p1, int p2) {
        int temp = A[p1];
        A[p1] = A[p2];
        A[p2] = temp;
      }

    /** Randomly permute the values in array A */
    static <E> void permute(E[] A) {
      for (int i = A.length; i > 0; i--) // for each i
        swap(A, i-1, DSutil.random(i));  //   swap A[i-1] with
    }                                    //   a random element

      /** Initialize the random variable */
      static private Random value = new Random(); // Hold the Random class object

      /** Create a random number function from the standard Java Random
          class. Turn it into a uniformly distributed value within the
          range 0 to n-1 by taking the value mod n.
          @param n The upper bound for the range.
          @return A value in the range 0 to n-1.
      */
      static int random(int n) {
        return Math.abs(value.nextInt()) % n;
      }

    }
    private static class Code { // One code stored in a code table
      private char data;
      private String code;

      public Code(char d, String c)
      { data = d;  code = c; }

      public char data() { return data; }
      public String code() { return code; }
    }
    //lecture code 
    /** Huffman tree node implementation: Base class */
    public interface HuffBaseNode<E> {
      public boolean isLeaf();
      public int weight();
    }
    private static class HuffInternalNode<E> implements HuffBaseNode<E> {
      private int weight;            // Weight (sum of children)
      private HuffBaseNode<E> left;  // Pointer to left child
      private HuffBaseNode<E> right; // Pointer to right child

      /** Constructor */
      public HuffInternalNode(HuffBaseNode<E> l,
                              HuffBaseNode<E> r, int wt)
        { left = l; right = r; weight = wt; }

      /** @return The left child */
      public HuffBaseNode<E> left() { return left; }

      /** @return The right child */
      public HuffBaseNode<E> right() { return right; }

      /** @return The weight */
      public int weight() { return weight; }

      /** Return false */
      public boolean isLeaf() { return false; }
    }

    private static class HuffLeafNode<E> implements HuffBaseNode<E> {
      private E element;         // Element for this node
      private int weight;        // Weight for this node

      /** Constructor */
      public HuffLeafNode(E el, int wt)
        { element = el; weight = wt; }

      /** @return The element value */
      public E element() { return element; }

      /** @return The weight */
      public int weight() { return weight; }

      /** Return true */
      public boolean isLeaf() { return true; }
    }

    /** A Huffman coding tree */
    private static class HuffTree<E> implements Comparable<HuffTree<E>>{
      private HuffBaseNode<E> root;  // Root of the tree

      /** Constructors */
      public HuffTree(E el, int wt)
        { root = new HuffLeafNode<E>(el, wt); }
      public HuffTree(HuffBaseNode<E> l,
                      HuffBaseNode<E> r, int wt)
        { root = new HuffInternalNode<E>(l, r, wt); }

      public HuffBaseNode<E> root() { return root; }
      public int weight() // Weight of tree is weight of root
        { return root.weight(); }
      public int compareTo(HuffTree<E> that) {
        if (root.weight() < that.weight()) return -1;
        else if (root.weight() == that.weight()) return 0;
        else return 1;
      }
    }

    /** Min-heap implementation */
    private static class MinHeap<E extends Comparable<? super E>> {
        private E[] Heap;   // Pointer to the heap array
        private int size;   // Maximum size of the heap
        private int n;      // Number of things in heap

        public MinHeap(E[] h, int num, int max)
        { Heap = h;  n = num;  size = max;  buildheap(); }

        /** Return current size of the heap */
        public int heapsize() { return n; }

        /** Is pos a leaf position? */
        public boolean isLeaf(int pos)
        { return (pos >= n/2) && (pos < n); }

        /** Return position for left child of pos */
        public int leftchild(int pos) {
          assert pos < n/2 : "Position has no left child";
          return 2*pos + 1;
        }

        /** Return position for right child of pos */
        public int rightchild(int pos) {
          assert pos < (n-1)/2 : "Position has no right child";
          return 2*pos + 2;
        }

        /** Return position for parent */
        public int parent(int pos) {
          assert pos > 0 : "Position has no parent";
          return (pos-1)/2;
        }

        /** Heapify contents of Heap */
        public void buildheap()
          { for (int i=n/2-1; i>=0; i--) siftdown(i); }

        /** Insert into heap */
        public void insert(E val) {
          assert n < size : "Heap is full";
          int curr = n++;
          Heap[curr] = val;                 // Start at end of heap
          // Now sift up until curr's parent's key > curr's key
          while ((curr != 0)  &&
                 (Heap[curr].compareTo(Heap[parent(curr)]) < 0)) {
            DSutil.swap(Heap, curr, parent(curr));
            curr = parent(curr);
          }
        }
        /** Put element in its correct place */
        private void siftdown(int pos) {
          assert (pos >= 0) && (pos < n) : "Illegal heap position";
          while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j<(n-1)) && (Heap[j].compareTo(Heap[j+1]) > 0)) 
              j++; // j is now index of child with greater value
            if (Heap[pos].compareTo(Heap[j]) <= 0)
              return;
            DSutil.swap(Heap, pos, j);
            pos = j;  // Move down
          }
        }

        public E removemin() {     // Remove minimum value
          assert n > 0 : "Removing from empty heap";
          DSutil.swap(Heap, 0, --n); // Swap minimum with last value
          if (n != 0)      // Not on last element
            siftdown(0);   // Put new heap root val in correct place
          return Heap[n];
        }

        /** Remove element at specified position */
        public E remove(int pos) {
          assert (pos >= 0) && (pos < n) : "Illegal heap position";
          if (pos == (n-1)) n--; // Last element, no work to be done
          else
          {
            DSutil.swap(Heap, pos, --n); // Swap with last value
            // If we just swapped in a small value, push it up
            while ((pos > 0) && (Heap[pos].compareTo(Heap[parent(pos)]) < 0)) {
              DSutil.swap(Heap, pos, parent(pos));
              pos = parent(pos);
            }
            if (n != 0) siftdown(pos);   // If it is big, push down
          }
          return Heap[n];
        }
    }
    @SuppressWarnings("unchecked")  // Allow the generic array allocation 
    private static Vector<Code> codeTable; // The code lookup table
    private static MinHeap<HuffTree<Character>> Hheap;
    private static HuffTree<Character>[] TreeArray;
   
    /* constructor */
    private static int totalChars;
    HuffmanSubmit() {
        totalChars = 0;
        codeTable = new Vector<Code>();
    }

    /* count frequency of characters in the binary stream 
     */
    static Map <Character, Integer> countFreqs(BinaryIn b) throws IOException {
        char symbol;
        Map<Character, Integer> freqTable =new HashMap<>();
        while ( !b.isEmpty()) {
            symbol = b.readChar();
            totalChars++;
            if (!freqTable.containsKey(symbol)) { 
                freqTable.put(symbol, 1);
            }
            else {
                int count=freqTable.get(symbol);
                freqTable.put(symbol, count + 1);
            }
        }
        return freqTable;
    }

    static Map <Character, Integer> parseFreqs(String fileName) throws IOException {
        Map<Character, Integer> freqTable =new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            line = reader.readLine();
            System.out.println("First line: " + line.substring(0,8) + ":" + line.substring(9));
            totalChars = Integer.parseInt(line.substring(9), 10);
            while ((line = reader.readLine()) != null) {
                //System.out.println("Processing: " + line.substring(0,8) + ":" + line.substring(9));
                char symbol = (char)Integer.parseInt(line.substring(0,8), 2);
                int count = Integer.parseInt(line.substring(9),10);
                freqTable.put(symbol, count);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return freqTable;
    }
    static void writeFreqTable(Map <Character, Integer> freqTable, String fileName) {
        try {
            FileWriter fr = new FileWriter(fileName);
            //maintains total original to make sure we dont read extraneous characters introduced by flush
            fr.write("total   :" + totalChars + '\n');
            for (Map.Entry<Character, Integer> entry : freqTable.entrySet()) {
                fr.write(String.format("%8s", Integer.toBinaryString(entry.getKey())).replace(' ', '0')+ ":" + entry.getValue() + '\n');
            }
            fr.close();
           
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    static HuffTree<Character> buildTree(Map <Character, Integer> freqTable) {
        int i = 0;
        TreeArray = (HuffTree<Character>[])new HuffTree[freqTable.size()];
        for (Map.Entry<Character, Integer> entry : freqTable.entrySet()) {
            TreeArray[i++] = new HuffTree<Character>(entry.getKey(), entry.getValue());
        }
        //System.out.println(TreeArray);
        //System.out.println(freqTable.size());
        Hheap = new MinHeap<HuffTree<Character>>(TreeArray, freqTable.size(), freqTable.size());

        HuffTree tmp1, tmp2, tmp3 = null;

        while (Hheap.heapsize() > 1) { // While two items left
            tmp1 = Hheap.removemin();
            tmp2 = Hheap.removemin();
            tmp3 = new HuffTree<Character>(tmp1.root(), tmp2.root(),
                         tmp1.weight() + tmp2.weight());
            Hheap.insert(tmp3);   // Return new tree to heap
        }
        return tmp3;            // Return the tree
    }

    // make CodeTable
    static void outputTree(HuffBaseNode<Character> node, String prefix) {
      assert node != null : "Bad input tree";
      // This is a full binary tree so must not be null subtree
      if (node.isLeaf()) {
        //System.out.println(((HuffLeafNode<Character>)node).element() + "\t" + prefix);
        char temp  = ((HuffLeafNode<Character>)node).element();
        codeTable.addElement(new Code(temp, prefix));
        //total += prefix.length() * node.weight();
      }
      else {
        outputTree(((HuffInternalNode)node).left(), prefix + "0");
        outputTree(((HuffInternalNode)node).right(), prefix + "1");
      }

    }
    
    /* dump codetable for debug */
    static void dumpCodeTable() {
      int i;
      System.out.println("codeTable size: " + codeTable.size());
      for (i=0; i<codeTable.size(); i++) {
        System.out.print(i);
        System.out.println(" : " + codeTable.elementAt(i).data() + " > " +  codeTable.elementAt(i).code());
      }
    }

    // Find the index in CodeTable for the given codeword
    static int getindex(char codeword)
    {
      int i;

      for (i=0; i<codeTable.size(); i++)
        if (codeword == codeTable.elementAt(i).data())
          return i;
      return i; // Not found
    }

    static void encodeWrite(BinaryIn b, String outputFile) {
        char symbol;
        int index;
        BinaryOut f = new BinaryOut(outputFile);
        while ( ! b.isEmpty() ) {
            symbol = b.readChar();
            //System.out.println("ouputting for " + symbol);
            index = getindex(symbol);
            assert index < codeTable.size() :
              "Tried to find code of bad character|" +
              symbol + "|";
            String code = codeTable.elementAt(index).code();
            for (int i=0; i < code.length(); i++) {
                //System.out.println ("Writing : " + code.charAt(i));
                f.write(code.charAt(i) == '1' ? true : false);
            }
        }
        f.flush();
        f.close();
    }


    static void decodeWrite(String inputFile, HuffTree<Character> tree, String outputFile) {
        boolean bit;
        int countWritten = 0;
        HuffBaseNode<Character> temp;
        try {
            BinaryIn b = new BinaryIn(inputFile);
            BinaryOut d = new BinaryOut(outputFile);
            temp = tree.root();
            while (! b.isEmpty() ) {
                bit = b.readBoolean();
                //System.out.println ("read bit:" + bit);
                if (bit == false) {
                  temp = ((HuffInternalNode)temp).left();
                }
                else {
                  temp = ((HuffInternalNode)temp).right();
                }

                //System.out.println (temp);
                if (temp.isLeaf()) {
                  //System.out.println (">>>>>>>leaf element:" + (char)((HuffLeafNode)temp).element());
                  d.write((char)((HuffLeafNode)temp).element());
                  countWritten++;
                  //discarding flushed bits 
                  if (countWritten >= totalChars) {
                      break;
                  }
                  temp = tree.root(); // reset at root
                }
            }
            d.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void encode(String inputFile, String outputFile, String freqFile) {
        BinaryIn b = new BinaryIn (inputFile);
        //System.out.println("Read frequencies");
        Map <Character, Integer> freqTable;
        try {
            System.out.println("Output frequency table");
            freqTable = countFreqs(b);
            writeFreqTable(freqTable, freqFile);
            //System.out.println(freqTable);
            System.out.println("Build the tree");
            HuffTree<Character> codes = buildTree(freqTable);
            System.out.println("Output the tree");
            outputTree(codes.root(), "");
            //dumpCodeTable();
            System.out.println("Encode and write outputFile");
            encodeWrite(new BinaryIn(inputFile), outputFile);
        }
        catch ( Exception e) {
            System.out.println (e);
        }

    }


    public void decode(String inputFile, String outputFile, String freqFile){
        BinaryIn b = new BinaryIn (inputFile);
        //System.out.println("Read frequencies");
        Map <Character, Integer> freqTable;
        try {
            System.out.println("parse freqs file" + freqFile);
            freqTable = parseFreqs(freqFile);
            //System.out.println(freqTable);
            System.out.println("Build the tree");
            HuffTree<Character> codes = buildTree(freqTable);
            System.out.println("Output the tree");
            outputTree(codes.root(), "");
            System.out.println("Decode and write outputFile");
            decodeWrite(inputFile, codes, outputFile);
        } 
        catch (Exception e) {
            System.out.println (e);
        }
       
    }




   public static void main(String[] args) {
      Huffman  huffman; 
        /* a small test file */
        huffman= new HuffmanSubmit();
        huffman.encode("mytest.txt", "mytest.enc", "mytest.freq.txt");
        
        /* given test case 1 */
        huffman= new HuffmanSubmit();
        huffman.encode("alice30.txt", "alice30.enc", "alice30.freq.txt");

        /* given test case 2*/
        huffman= new HuffmanSubmit();
		huffman.encode("ur.jpg", "ur.enc", "ur.freq.txt");

        huffman= new HuffmanSubmit();
		huffman.decode("ur.enc", "ur_dec.jpg", "ur.freq.txt");

		huffman= new HuffmanSubmit();
        huffman.decode("mytest.enc", "mytest.dec", "mytest.freq.txt");

        huffman= new HuffmanSubmit();
        huffman.decode("alice30.enc", "alice30.dec", "alice30.freq.txt");

   }

}

