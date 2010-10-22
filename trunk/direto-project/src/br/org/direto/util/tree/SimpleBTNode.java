package br.org.direto.util.tree;

public class SimpleBTNode implements Comparable {
	private Comparable element;
	private SimpleBTNode left; 
	private SimpleBTNode right; 

	public SimpleBTNode() {
		this(null, null, null);
	}

	public SimpleBTNode(SimpleBTNode l, Comparable e, SimpleBTNode r) {
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

	public SimpleBTNode getLeft() {
		return left;
	}

	public void setLeft(SimpleBTNode v) {
		left = v;
	}

	public SimpleBTNode getRight() {
		return right;
	}

	public void setRight(SimpleBTNode v) {
		right = v;
	}

	public String toString() {
		return element.toString();
	}

	public int compareTo(Object obj) {
		if (obj instanceof SimpleBTNode) {
			SimpleBTNode no = (SimpleBTNode) obj;
			Comparable tmp = getElement();
			
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
}
