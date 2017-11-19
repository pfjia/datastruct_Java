package ch5;


import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author pfjia
 * @since 2017/11/18 16:24
 */
public class SeparateChainingHashTable<E> {
	public SeparateChainingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}


	/**
	 * Construct the hash table
	 * @param size size approximate table size.
	 */
	public SeparateChainingHashTable(int size) {
		table = new LinkedList[nextPrime(size)];
		for (int i = 0; i < table.length; i++) {
			table[i] = new LinkedList<>();
		}
	}


	/**
	 * Insert into the hash table.if the item is already present,the do nothing.
	 * @param x the item to insert
	 */
	public void insert(E x) {
		List<E> whichList = table[myHash(x)];
		if (!whichList.contains(x)) {
			whichList.add(x);
			if (++size > table.length) {
				rehash();
			}
		}
	}


	/**
	 * Remove from the hash table.
	 * @param x the item to remove
	 */
	public void remove(E x) {
		List<E> whichList = table[myHash(x)];
		if (whichList.contains(x)) {
			whichList.remove(x);
			size--;
		}

	}


	/**
	 * Find an item in the hash table.
	 * @param x the item /to search for.
	 * @return  true if x is  found
	 */
	public boolean contains(E x) {
		int index = myHash(x);
		return table[index].contains(x);
	}


	/**
	 * Make the hash table logically empty
	 */
	public void makeEmpty() {
		for (List<E> list : table) {
			list.clear();
		}
		size = 0;
	}


	private static final int DEFAULT_TABLE_SIZE = 101;

	private List<E>[] table;
	private int size;


    /**
     * Rehashing for separate chaining hash table.
     */
	private void rehash() {
	    List<E>[] oldTable=table;
        //create new double-size , empty table
	    table=new List[nextPrime(table.length*2)];
        for (int j=0;j<table.length;j++){
	        table[j]=new LinkedList<>();
        }
        // copy table over
	    size=0;
        for (List<E> list : oldTable) {
            for (E e : list) {
               insert(e);
            }
        }
    }


	private int myHash(E x) {
		int hashVal = x.hashCode();
		hashVal = hashVal % table.length;
		if (hashVal < 0) {
			hashVal = hashVal + table.length;
		}
		return hashVal;
	}


	private static int nextPrime(int n) {
		while (!isPrime(n)) {
			n++;
		}
		return n;
	}


	private static boolean isPrime(int n) {
		if (n <= 1) {
			return false;
		}
		if (n == 2) {
			return true;
		}
		boolean flag = true;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				flag = false;
				break;
			}
		}
		return flag;
	}


	public static void main(String[] args) {
        System.out.println(failure(0.9));
    }


	public static double failure(double load){
	    return 0.5*(1+1/Math.pow((1-load),2));
    }


}
