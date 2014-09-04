

class Matrix {
  
  // private Entry class
  private class Entry {
     // entry fields
     int column;
     double colval;
     // Recieved TAs help to create this entry
     // to create a new entry
     Entry(int col, double cval) {
        this.column = col;
        this.colval = cval;
     }


     // toSting for the printing the Matrix's parentheis
     // and the coma
     public String toString() {
        String str;
        str = "( " + String.valueOf(this.column) + ", ";
        str = "( " + String.valueOf(this.colval) + " )";
        return str;
     }

     // end of private class Entry
  }

  // Fields 
  List[] mtx;
  private int size = 0;
  int NNZ = 0;

  

  // Constructor------------------------------------------------------
  

  // Makes a new n x n zero Matrix, pre: n>=1
  Matrix(int n) {
     // check pre condition
     if( n < 1) {
           throw new RuntimeException("Matrix Error: invalid matrix size");
     }
     // set n to be size     
     size = n;
     mtx = new List[n];
     // create new matrix based on the n size
     for( int i = 1; i < n; i++ ) {
        mtx[i] = new List();
     }
     // end of Matrix(int n) 
  } 
 

   
  // Access Functions-------------------------------------------------


  // Returns the number of rows and columns of this Matrix
     int getSize() {
        return size;
     }
   
  // Returns the number of non-zero entries in this Matrix
     int getNNZ() {
        return NNZ;
     }



  // Manipulation procedures 
  
  // sets this Matrix to the zero state
  // use clear() from List ADT to make matrix the zero state
  void makeZero() {
     // clear every element
     for ( int i = 1; i < this.getSize(); i++ ) {
        mtx[i].clear();
     }
     // remove NNZ entries
     NNZ = 0;
  }


  // object equals() method
  // Similar to the boolean in List ADT
  // Recieved help on this method from a TA
  // also revieced help from a student in class
  // and outside class
  public boolean equals(Object x) {
     if(this == x) return true;
     Matrix M = (Matrix)(x);
     
     boolean flag = true;
     // compare the lists
     // whether they are equals or not
     List M1;
     List M2;
  
     if ( this.getSize() == M.getSize() && this.getNNZ() == M.getSize() ) {
        int i = 0;
        M1 = this.mtx[i];
        M2 = M.mtx[i];
        while ( flag && M1 != null && i < this.getSize() ) {
           flag = M1.equals(M2);   
           i++;
        }
        return flag;
     }
     return false;
     // end boolean equals
  }

  // returns a new Matrix having the same entries as this Matrix
  Matrix copy() {
     Matrix tmpCpy = new Matrix(this.getSize());
     // variables
     Entry tmp1, tmp2;
     List M1;
     
     // loop through the matrix
     for ( int i = 1; i < this.getSize(); i++ ) {
        M1 = this.mtx[i];
        // check if the matrix is not empty
        if (M1.length() !=0) {
           M1.moveTo(0);
           // while the cursor is not undefined
           // create a new entry that takes values from 
           // the columns and appends the new matrix
           // and move to the next one
           while( M1.getIndex() != -1 ) {
              tmp1 = (Entry)(M1.getElement());
              tmp2 = new Entry (tmp1.column, tmp1.colval);
              tmpCpy.mtx[i].append(tmp2);
              M1.moveNext();
              // increment NNZ
              tmpCpy.NNZ++;
           }  
        } 
     }
  
     return tmpCpy;

    // end of Matrix copy()
  }

  // changes ith row, jth column of this Matrix to x
  // pre: 1<=i<=getSize(), 1<=j<=getSize()
  void changeEntry (int i, int j, double x) {
     // check pre condtions 
     if(( 1 <= i && i <= this.getSize()) && ( 1 <= j && j <= this.getSize())) {
        List M1 = mtx[i];
        Entry tmp1, tmp2, tmp3;
        if ( M1.length() != 0) {
           M1.moveTo(0);
           
           // use boolean to check
           boolean flag = false;
           while( flag == false && M1.getIndex() != -1) {
              tmp1 = (Entry)(M1.getElement());
              if( j > tmp1.column) { 
                 M1.moveNext();
              }
              else if( j == tmp1.column) {
                 if(x != 0) {
                    tmp1.colval = x;
                 }
                 else {
                     M1.delete();
                     flag = true;
                 }
              }
              else if( x != 0) {
                 tmp2 = new Entry(j,x);
                 M1.insertBefore(tmp2);
                 flag = true;
                 NNZ++;
              }
           }
      
           if(flag == false) {
              if(x != 0) {
                 tmp3 = new Entry(j, x);
                 M1.append(tmp3);
                 NNZ++;
              }
           }
        }
        else if (x != 0) {
           tmp3 = new Entry(j, x);
           M1.append(tmp3);
           NNZ++;
        }
     }
     else throw new RuntimeException("Matrix Error: changeEntry error");
  }
   

    // returns a new Matrix that is the scalar product of this Matrix with x 
    // no given pre codition
    //
    // 2   2  0     4 0
    //     1  1     2 2
    //
  Matrix scalarMult(double x) {
     // create new Matrix scal
     Matrix scal = copy();
     Entry tmp;
     List M1;
     double v;
     // loop through the row of the matrix
     for(int i = 1; i < this.getSize(); i++) {
        M1 = scal.mtx[i];
        // check if the length of the matrix is not zero
        if(!(M1.length() == 0)) {
           M1.moveTo(0);
           // while the cursor is not undefined,
           // do the scalar operation
           // mutiple the scalar vlaue x with the 
           // values of the matrix in each column
           // and move to the next
           while(M1.getIndex() != -1) {
              tmp = (Entry)(M1.getElement());
              v = tmp.colval * x;
              tmp.colval = v;
              M1.moveNext();
           }
        }
     }
    
     return scal;
 
     // end of scalarMult()
  }        
              
   
  // returns a new Matrix that is the sum of this Matrix with M 
  // pre: getSize()==M.getSize() 
  // I reiceved the most help for the ADD and SUB functions
  // I did not derive this code all by myself. I recieved 
  // help from TA and students in the class and outside
  // Still a little glithcy
  Matrix add(Matrix M) {
     Matrix tmpSum = new Matrix(this.getSize());
     List M1;
     Entry tmp1, tmp2, tmp3;
     // check pre condtion 
     if( this.getSize() == M.getSize() ) {
        for( int i = 1; i < this.getSize(); i++) {
           M1 = mtx[i];
           if(M1.length() != 0 && M.mtx[i].length() == 0) {
              M1.moveTo(0);
              while(M1.getIndex() != -1) {
                 tmp1 = (Entry)(M1.getElement());
                 tmp2 = new Entry(tmp1.column, tmp1.colval);
                 tmpSum.mtx[i].append(tmp2);
                 M1.moveNext();
                 NNZ++;
              }
           }
        else if(M1.length() == 0 && M.mtx[i].length() != 0) {
           M.mtx[i].moveTo(0);
           while(M.mtx[i].getIndex() != -1) {
              tmp3 = (Entry)(M.mtx[i].getElement());
              tmp2 = new Entry(tmp3.column, tmp3.colval);
              tmpSum.mtx[i].append(tmp2);
              M.mtx[i].moveNext();
              NNZ++;
           }
        }
       
        else if (M1.length() != 0 && M.mtx[i].length() != 0) {
           M1.moveTo(0);
           M.mtx[i].moveTo(0);
           while (M1.getIndex() != -1 && M.mtx[i].getIndex() != -1) {
              tmp1 = (Entry) M1.getElement();
              tmp3 = (Entry) M.mtx[i].getElement();
              // this matrix
              if (tmp1.column < tmp3.column) {
                 tmpSum.mtx[i].append(tmp1);
                 M1.moveNext();
                 NNZ++;
              }
              // M Matrix
              else if (tmp1.column > tmp3.column) {
                 tmpSum.mtx[i].append(tmp3);
                 M.mtx[i].moveNext();
                 NNZ++;
               }
              // same colval so add
              else {
                  Entry tmpE = new Entry (tmp3.column, tmp3.colval + tmp3.colval);
                  if (tmpE.colval != 0) {
                  tmpSum.mtx[i].append(tmpE);
                  }
                  if (this != M) {
                    mtx[i].moveNext();
                  }
                    M.mtx[i].moveNext();
                   
              }

           }
           // this matrix, append
              while (M1.getIndex() != -1) {
                 tmp1 = (Entry)M1.getElement();
                 tmpSum.mtx[i].append(tmp1);
                 mtx[i].moveNext();
              }
            // M matrix, append
            while (M.mtx[i].getIndex() != -1) {
              tmp3 = (Entry)M.mtx[i].getElement();
              tmpSum.mtx[i].append(tmp3);
              M.mtx[i].moveNext();
              NNZ++;
           }
        }
     }
  }
     else throw new RuntimeException ("Matrix Error: adding uneven matrices");
     return  tmpSum;
  }


  
 

  // returns a new Matrix that is the difference of this Matrix with M 
  // pre: getSize()==M.getSize() 
  // Recived the most for the SUB and ADD fucntions
  // From TA and students. 
  // sub is same as the add except for minor changes, still glitchy
  // colval + calval   changes to colval - colval
  Matrix sub(Matrix M) {
      Matrix tmpSub = new Matrix(this.getSize());
      List M1;
      Entry tmp1, tmp2, tmp3;
      // check pre condtion 
      if( this.getSize() == M.getSize() ) {
        for( int i = 1; i < this.getSize(); i++) {
           M1 = mtx[i];
           if(M1.length() != 0 && M.mtx[i].length() == 0) {
              M1.moveTo(0);
              while(M1.getIndex() != -1) {
                 tmp1 = (Entry)(M1.getElement());
                 tmp2 = new Entry(tmp1.column, tmp1.colval);
                 tmpSub.mtx[i].append(tmp2);
                 M1.moveNext();
                 NNZ++;
              }
           }
           else if(M1.length() == 0 && M.mtx[i].length() != 0) {
              M.mtx[i].moveTo(0);
              while(M.mtx[i].getIndex() != -1) {
                 tmp3 = (Entry)(M.mtx[i].getElement()); 
                 tmp2 = new Entry(tmp3.column, tmp3.colval);
                 tmpSub.mtx[i].append(tmp2);
                 M.mtx[i].moveNext();
                 NNZ++;
              }
           }
       
           else if (M1.length() != 0 && M.mtx[i].length() != 0) {
              M1.moveTo(0);
              M.mtx[i].moveTo(0);
              while (mtx[i].getIndex() != -1 && M.mtx[i].getIndex() != -1) {
                 tmp1 = (Entry) M1.getElement();
                 tmp3 = (Entry) M.mtx[i].getElement();
                 if (tmp1.column < tmp3.column) {
                    tmpSub.mtx[i].append(tmp1);
                    M1.moveNext();
                    NNZ++;
                 }
                   // this Matrix and M matrix
                 else if (tmp1.column > tmp3.column) {
                    tmpSub.mtx[i].append(tmp3);
                    M.mtx[i].moveNext();
                    NNZ++;
                 }
                   // add if columns are same
                 else {
                    Entry tmpE = new Entry (tmp3.column, tmp3.colval - tmp3.colval);
                    if (tmpE.colval != 0) {
                       tmpSub.mtx[i].append(tmpE);
                    }
                    if (this != M) {
                       mtx[i].moveNext();
                    }
                   M.mtx[i].moveNext();
                 }
              }
                // this Matrix, appeding
              while (M1.getIndex() != -1) { 
                 tmp1 = (Entry)M1.getElement();
                 tmpSub.mtx[i].append(tmp1);
                 mtx[i].moveNext();
              }
           // Matrix M, appending
              while (M.mtx[i].getIndex() != -1) {
                 tmp3 = (Entry)M.mtx[i].getElement();
                 tmpSub.mtx[i].append(tmp3);
                 M.mtx[i].moveNext();
                 NNZ++;
              }
           }
        }
     }
     else {
        throw new RuntimeException ("Matrix Error: subtracting uneven matrices");
     }
     return  tmpSub;
  }


   
  // returns a new Matrix that is the transpose of this Matrix 
  // no pre condtions
  // Recieved help adn pseudocode from a student outside of class
  // Transpose: place the coluns to row
  // Ex. Transpose
  //           1 2
  //  1 3 5    3 4
  //  2 4 6    5 6
  //  
  //
  Matrix transpose() {
     Matrix trans = new Matrix(this.getSize());
     Entry tmp;
     List M1;
     int c;
     double v;
     // go through the matrix
        for(int i = 1; i < this.getSize(); i++) {
           M1 = this.mtx[i];
           M1.moveTo(0);
           // as long as the cursor does not  
           // go off the matrix
           // change the entry of Matrix 1 at  
           // that element and move to the next one
           while(M1.getIndex() != -1) {
              tmp = (Entry)(M1.getElement());
              c = tmp.column;
              v = tmp.colval;
              // make the change of the values in 
              // the columns
              trans.changeEntry(c, i, v);
              M1.moveNext();
           } 

        }

     return trans;
   
     // end of transpose() 
   
  }


  // returns a new Matrix that is the product of this Matrix with M 
  // pre: getSize()==M.getSize()
  // use transpose and dot fucntions to do multiplication
  Matrix mult(Matrix M) {
     // pre condtion for Matrix M 
     if(getSize() == M.getSize()) { 
        double dotP;
        Matrix tmpMul = new Matrix(this.getSize());
        Matrix trans = M.transpose();
 
        // loop through the row the first matrix
        for(int i = 1; i< this.getSize(); i++) {
           List M1 = mtx[i];
           // if its not empty
           if(M1.length() != 0) {
              for(int j = 1; j < this.getSize(); j++) {
              // transpose the matrix
                 List M2 = trans.mtx[j];
                 if(M2.length() !=  0) {
                 // compute the dot product
                    dotP = dot(M1, M2);
                    tmpMul.changeEntry(i, j, dotP);
                 }
              }
           }
        }
        return tmpMul;
 
     }
     else {
        throw new RuntimeException("Matrix Error: unequal Matrices");
     }
 
     // end of mult()
  }
    

  // computes the vector dot product
  // Ex.
  //  1 2  x  1 2   5 5   
  //  1 2     2 1   5 5
  //
  // Recieved help from a student outside of class
  // to finish the function
 
  private static double dot(List P, List Q) {
     double tmpDot = 0.0;
     Entry tmp1, tmp2;
     // start at 0
     P.moveTo(0);
     Q.moveTo(0);
   
     // while the cursor is not undefined
     // create new entries
     while(P.getIndex() != -1 && Q.getIndex() != -1) {
        tmp1 = (Entry)(P.getElement());
        tmp2 = (Entry)(Q.getElement());
        // if entry 1 has value less than entry 2
        // then move to the next one in list P
        // and vice versa for list Q
        if(tmp1.column < tmp2.column) {
        P.moveNext();
        }
        else if(tmp1.column > tmp2.column) {
           Q.moveNext();
        }
        else {
           tmpDot += (tmp1.colval * tmp2.colval);
           P.moveNext();
           Q.moveNext();
        }
     }
    
     return tmpDot;
     // end of doouble dot
  } 


  // override Object's toString() method
  // based of Tantalo's example 
  // similar to the example of toString  
  public String toString() {
     String str = new String("");
     for(int i = 1; i < this.getSize(); i++) {
        if(this.mtx[i].length() != 0) {
        str +=  String.valueOf(i) + ":" ;
        str +=  mtx[i].toString() + "\n";
        }
     } 
     return str;
        // end of String
  }      
   

// end of Matrix ADT 
}

  


