package edu.ben.project.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ben.project.Node;

public class NodeTest {

	@Test
	public void testParentNodeFunctionality() {
		Node n = new Node(2, 5);
		Node n2 = new Node(2, 3);
		n.setParent(n2);
		assertEquals(n2, n.getParent());
	}

	@Test
	public void testEquals() {
		Node n1 = new Node(3, 5);
		Node n2 = new Node(3, 5);
		Node n3 = new Node(4, 4);
		n2.setParent(n3);
		assertTrue(n1.equals(n2));
	}

	@Test
	public void testEqualsFalse() {
		Node n1 = new Node(0, 5);
		Node n2 = new Node(4, 7);
		assertFalse(n1.equals(n2));
	}

}
