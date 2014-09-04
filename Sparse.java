

import java.io.*;
import java.util.Scanner;

class Sparse {
  
    public static void main (String args[]) throws IOException {
        Scanner in = null;
        PrintWriter out = null;
        String line = null;
        int lineNumber = 0;
    
        if(args.length < 2) {
           System.out.printf("Usage: Sparse inputfile outputfile");
           System.exit(1);
        }
        // read and write
        in = new Scanner(new File(args[0]));
        out= new PrintWriter(new FileWriter(args[1]));
        // Create Matrix A and B
        Matrix A = new Matrix(1000);
        Matrix B = new Matrix(1000);
        String[] token = null;
    
        // while there is something in the file
        // increment
        while( in.hasNextLine() ) {
           lineNumber++;
           line = in.nextLine()+" "; 
           token = line.split("\\s+"); 
        }
    
    
    
        // Print the matrix to file
    
        // Print A
        out.printf("A has  non-zero entrie:\n");
        // Print B
        out.printf("B has  non-zero entries:\n");
        // (1.5)*A
        out.printf("1.5*A\n");
        // A+B
        out.printf("A+B =\n");
        // A+A
        out.printf("A+A =\n");
        // B-A
        out.printf("B-A =\n");
        // A-A
        out.printf("A-A =\n");
        // transpose(A)
        out.printf("Transpose(A) =\n");
        // A*B
        out.printf("A*B =\n");
        // B*B
        out.printf("B*B =\n");
    
        // close the input file and output file
        in.close();
        out.close();
    }
  
}
