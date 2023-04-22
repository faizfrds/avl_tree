package structures;

public class AVLTree <T extends Comparable<T>> implements BSTInterface<T> {
	protected BSTNode<T> root;
	private int size;

	public AVLTree() {
		root = null;
		size = 0;
	}

	public boolean isEmpty() {
		// DO NOT MODIFY
		return root == null;
	}

	public int size() {
		// DO NOT MODIFY
		return size;
	}

	public BSTNode<T> getRoot() {
		// DO NOT MODIFY
		return root;
	}
	
	public void printTree() {
		System.out.println("------------------------");
		if (root != null) root.printSubtree(0);
	}

	public boolean remove(T element) {
		// Do not need to implement this method.
		return false;
	}

	public T get(T element) {
		// Do not need to implement this method.
		return null;
	}

	public int height() {
		return height(this.root);
	}

	public int height(BSTNode<T> node) {
		// return the node height
		return node != null ? node.getHeight() : -1;
	}
	
	public void updateHeight(BSTNode<T> node) {
		// TODO: update node height to 1 + the maximal height between left and right subtree

		int rightHeight = height(node.getRight());
		int leftHeight = height(node.getLeft());
		
		node.setHeight(1 + Math.max(rightHeight, leftHeight));
	}
	
	public int balanceFactor(BSTNode<T> node) {
		// TODO: compute the balance factor by substracting left subtree height by right subtree height

		int rightHeight = height(node.getRight());
		int leftHeight = height(node.getLeft());

		return rightHeight - leftHeight;
	}

	public BSTNode<T> rotateLeft(BSTNode<T> node) {
		// TODO: implement left rotation algorithm

        BSTNode<T> rightNode = node.getRight();
        BSTNode<T> subB = rightNode.getLeft(); 

        rightNode.setLeft(node);
        node.setRight(subB);

        if (subB != null) {
            subB.setParent(node);
        }

        rightNode.setParent(node.getParent());
        node.setParent(rightNode);

        if (rightNode.getParent() != null) { //if node's parent is not root
            if (rightNode.getParent().getRight() == node) {
                rightNode.getParent().setRight(rightNode); //changing node parent's right child
            } 
			else {
                rightNode.getParent().setLeft(rightNode); //changing node parent's left child
            }
        } 
		else {
            root = rightNode;
        }

        updateHeight(node);
        updateHeight(rightNode);

        return rightNode;
	}
	
	public BSTNode<T> rotateRight(BSTNode<T> node) {
        BSTNode<T> leftNode = node.getLeft();
        BSTNode<T> subB = leftNode.getRight(); 

        leftNode.setRight(node);
        node.setLeft(subB);

        if (subB != null) {
            subB.setParent(node);
        }

        leftNode.setParent(node.getParent());
        node.setParent(leftNode);

        if (leftNode.getParent() != null) {
            if (leftNode.getParent().getRight() == node) {
                leftNode.getParent().setRight(leftNode);
            } 
			else 
			{
                leftNode.getParent().setLeft(leftNode);
            }
        } 
		else {
            root = leftNode;
        }

        updateHeight(node);
        updateHeight(leftNode);

        return leftNode;
	}
		// When inserting a new node, updating the height of each node in the path from root to the new node.
		// Check the balance factor of each updated height and run rebalance algorithm if the balance factor
		// is less than -1 or larger than 1 with following algorithm
		// 1. if the balance factor is less than -1
		//    1a. if the balance factor of left child is less than or equal to 0, apply right rotation
	    //    1b. if the balance factor of left child is larger than 0, apply left rotation on the left child,
		//        then apply right rotation
		// 2. if the balance factor is larger than 1
		//    2a. if the balance factor of right child is larger than or equal to 0, apply left rotation
	    //    2b. if the balance factor of right child is less than 0, apply right rotation on the right child,
		//        then apply left rotation
		public void add(T t) {
			// TODO

			BSTNode<T> newChild = new BSTNode<T>(t, null, null);
			
			if (isEmpty()){ //setting new node as root if empty
				root = newChild;
				updateHeight(newChild);
				size++;
				return;
			}

			BSTNode<T> parentNode = null;
			BSTNode<T> curNode = root;

			while (curNode != null){
				parentNode = curNode;
				if (t.compareTo(curNode.getData()) > 0){ //move to right subtree if bigger
					curNode = curNode.getRight();
				}
				else if (t.compareTo(curNode.getData()) < 0){ //move to left subtree if smaller
					curNode = curNode.getLeft();
				}
				else{
					return; //exit if t is curNode; already exists
				}
			}

			newChild.setParent(parentNode);

			if (t.compareTo(parentNode.getData()) > 0){
				parentNode.setRight(newChild);	
			} 
			else{ 
				parentNode.setLeft(newChild);
			}

			updateHeight(newChild);
			updateHeight(parentNode);
			if (parentNode != root) updateHeight(root); //updating root height
			size++;
				
			BSTNode<T> newNode = newChild.getParent();

			while (newNode != null) {
				
				updateHeight(newNode);

				if (balanceFactor(newNode) < -1) { //left subtree has more nodes than right
					if (balanceFactor(newNode.getLeft()) <= -1){ //left subtree has imbalance subtree, requiring left-left rotation
						newNode = rotateRight(newNode);
					} 
					else {
						newNode.setLeft(rotateLeft(newNode.getLeft()));
						newNode = rotateRight(newNode);
					}
				} 
				else if (balanceFactor(newNode) > 1) {
					if (balanceFactor(newNode.getRight()) >= 1) { //right subtree has imbalance subtree, requiring right-right rotation
						newNode = rotateLeft(newNode);
					} 
					else {
						newNode.setRight(rotateRight(newNode.getRight()));
						newNode = rotateLeft(newNode);
					}
				}
				newNode = newNode.getParent();	
			}
		}
	}
	
	
