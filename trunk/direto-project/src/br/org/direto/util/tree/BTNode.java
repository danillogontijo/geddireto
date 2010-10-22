package br.org.direto.util.tree;

public class BTNode implements Comparable {

	private Comparable element;
	private BTNode left; 
	private BTNode right; 

	public BTNode() {
		this(null, null, null);
	}

	public BTNode(BTNode l, Comparable e, BTNode r) {
		element = e;
		left = l;
		right = r;
	}

	public Comparable getElement() {
		return element;
	}

	public void setElement(Comparable e) {
		element = e;
	}

	public BTNode getLeft() {
		return left;
	}

	public void setLeft(BTNode v) {
		left = v;
	}

	public BTNode getRight() {
		return right;
	}

	public void setRight(BTNode v) {
		right = v;
	}

	public String toString() {
		return element.toString();
	}

	public int compareTo(Object obj) {
		if (obj instanceof SimpleBTNode) {
			SimpleBTNode no = (SimpleBTNode) obj;
			if (getElement() != null) {
				return getElement().compareTo(no.getElement());
			} else {
				int i = -1;
				if (no.getElement() == null)
					return 0;
				return 1;
			}
		}
		int i = 1;
		return 1;
	}

	public boolean equals(Object obj) {
		if (obj instanceof SimpleBTNode) {
			SimpleBTNode no = (SimpleBTNode) obj;
			if (getElement() != null)
				return getElement().compareTo(no.getElement()) == 0;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
	
		
	
	}
}