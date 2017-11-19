package ch5;


import java.util.Random;


/**
 * @author pfjia
 * @since 2017/11/19 9:21
 */
public class CuckooHashTable<E> {
	private static final double MAX_LOAD = 0.4;
	private static final int ALLOWED_REHASHES = 1;
	private static final int DEFAULT_TABLE_SIZE = 101;
	private final HashFamily<? super E> hashFunctions;
	private final int numHashFunctions;
	private E[] array;
	private int size;
	/**跟踪已经为这次插入尝试了多少次的再散列*/
	private int rehashes = 0;
	private Random r = new Random();


	public CuckooHashTable(HashFamily<? super E> hf) {
		this(hf, DEFAULT_TABLE_SIZE);
	}


	public CuckooHashTable(HashFamily<? super E> hf, int size) {
		allocateArray(nextPrime(size));
		doClear();
		hashFunctions = hf;
		numHashFunctions = hf.getNumberOfFunctions();
	}


	public void makeEmpty() {

	}


	/**
	 * Find an item in the hash table.
	 * @param x the item to search for.
	 * @return true if item is found
	 */
	public boolean contains(E x) {
		return findPos(x) != -1;
	}


	/**
	 * Compute the hash code for x using specified hash fuction
	 * @param x the item 
	 * @param which the hash function
	 * @return the hash code
	 */
	private int myHash(E x, int which) {
		int hashVal = hashFunctions.hash(x, which);
		hashVal %= array.length;
		while (hashVal < 0) {
			hashVal += array.length;
		}
		return hashVal;
	}


	/**
	 * Method that searches all hash function places
	 * @param x the item to search for
	 * @return the position where the search terminates , or -1 if not found
	 */
	private int findPos(E x) {
		for (int i = 0; i < numHashFunctions; i++) {
			int pos = myHash(x, i);
			if (array[pos] != null && array[pos].equals(x)) {
				return pos;
			}
		}
		return -1;
	}


	/**
	 * Remove from the hash table.
	 * @param x the itme to remove.
	 * @return true if item was found and removed
	 */
	public boolean remove(E x) {
		int pos = findPos(x);
		if (pos != -1) {
			array[pos] = null;
			size--;
		}
		return pos != -1;
	}


	/**
	 * Insert into the hash table. If the item is already present, return false.
	 * @param x the item to insert
	 * @return
	 */
	public boolean insert(E x) {
		if (contains(x)) {
			return false;
		}
		if (size >= array.length * MAX_LOAD) {
			expand();
		}
		return insertHelper1(x);
	}


	private boolean insertHelper1(E x) {
		// 1.调用此方法时已经确定x在array中不存在
		final int countLimit = 100;
		while (true) {
			int lastPos = -1;
			int pos;
			for (int count = 0; count < countLimit; count++) {
				// 2.检查是否有任何有效位置是空着的
				for (int i = 0; i < numHashFunctions; i++) {
					pos = myHash(x, i);
					// 是则将该项放入到第一个可以用的位置
					if (array[pos] == null) {
						array[pos] = x;
						size++;
						return true;
					}
				}

				// 替换掉其中一个已经存在的项
				// 问题:
				// 替换第一项效果不好
				// 替换最后一项效果不好
				// 按序列替换项效果不好
				// 随机替换在散列函数较少时往往会产生循环
				// 解决方法:维护被替换的最后一个位置
				// none of the spots are available.Evict out a random one
				int i = 0;
				do {
					pos = myHash(x, r.nextInt(numHashFunctions));
				} while (pos == lastPos && ++i < 5);

				E tmp = array[lastPos = pos];
				array[pos] = x;
				x = tmp;
			}

			if (++rehashes > ALLOWED_REHASHES) {
				// Make the table bigger
				expand();
				// Reset the # of rehashes
				rehashes = 0;
			} else {
				// Same table size,new hash functions
				rehash();
			}
		}
	}


	private void expand() {
		rehash((int) (array.length / MAX_LOAD));
	}


	private void rehash() {
		hashFunctions.generateNewFunctions();
		rehash(array.length);
	}


	private void rehash(int newLength) {
		E[] oldArray = array;
		allocateArray(nextPrime(newLength));
		size = 0;
		// copy table over
		for (E e : oldArray) {
			if (e != null) {
				insert(e);
			}
		}
    }


	private void doClear() {
		size = 0;
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
	}


	private void allocateArray(int arraySize) {

	}


	private int myHash(E x) {
		int hashVal = x.hashCode();
		hashVal = hashVal % array.length;
		if (hashVal < 0) {
			hashVal = hashVal + array.length;
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


}

