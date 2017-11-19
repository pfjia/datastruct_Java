package ch5;


/**
 * @author pfjia
 * @since 2017/11/18 17:19
 */
public class QuadraticProbingHashTable<E> {
	private static final int DEFAULT_TABLE_SIZE = 101;
	/**The array of elements*/
	private HashEntry<E>[] table;
	/**The number of occupied cells*/
	private int size;


	/**
	 * Contrust the hash table
	 */
	public QuadraticProbingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}


	/**
	 * Construct the hash table.
	 * @param size the approximate initial size
	 */
	public QuadraticProbingHashTable(int size) {
		allocateArray(size);
		makeEmpty();
	}


	/**
	 * Make the table logically empty.
	 */
	public void makeEmpty() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
	}


	/**
	 * Find an item in the hash table.
	 * @param x the item to search for.
	 * @return the matching item.
	 */
	public boolean contains(E x) {
		int pos = findPos(x);
		return isActive(pos);
	}

    /**
     * Insert into the hash table. if the item is already present, do nothing.
     * @param x the item to insert.
     */
	public void insert(E x) {
		int currentPos = findPos(x);
		if (isActive(currentPos)) {
		    return;
		}
		table[currentPos]=new HashEntry<E>(x);
		if(++size>table.length/2){
		    rehash();
        }
	}


    /**
     * Remove from the hash table.
     * @param x the item to remove
     */
	public void remove(E x) {
	    int currentPos=findPos(x);
	    if (isActive(currentPos)){
	        table[currentPos].isActive=false;
        }
	}


	/**
	 * Internal method to allocate array.
	 * @param arraySize the size of the array.
	 */
	private void allocateArray(int arraySize) {
		table = new HashEntry[nextPrime(arraySize)];
	}


	/**
	 * Return true if currentPos exists and is active.
	 * @param currentPos the result of a call to findPos.
	 * @return true if currentPos is active
	 *
	 */
	private boolean isActive(int currentPos) {
		return table[currentPos] != null && table[currentPos].isActive;
	}


	/**
	 * Method that performs quadratic probing resolution in half-empty table.
	 * @param x the item to search for
	 * @return the position where the search terminates
	 */
	private int findPos(E x) {
		// n^2-(n-1)^2=2*n-1
		int offset = 1;
		int currentPos = myHash(x);
		while (table[currentPos] != null
				&& !table[currentPos].element.equals(x)) {
			// 平方探测
			currentPos += offset;
			offset += 2;
			while (currentPos >= table.length) {
				currentPos -= table.length;
			}
		}
		return currentPos;
	}


    /**
     * Rehashing for quadratic probing hash table
     */
	private void rehash() {
	    HashEntry<E>[]oldTable=table;
	    // create a new double-sized,empty table
	    allocateArray(table.length*2);
	    size=0;
	    // copy table over
        for (HashEntry<E> entry : oldTable) {
            if (entry != null && entry.isActive) {
                insert(entry.element);
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


	private static class HashEntry<E> {
		/**the element*/
		public E element;
		/**false if marked deleted*/
		public boolean isActive;


		public HashEntry(E element) {
			this(element, true);
		}


		public HashEntry(E element, boolean isActive) {
			this.element = element;
			this.isActive = isActive;
		}
	}
}
