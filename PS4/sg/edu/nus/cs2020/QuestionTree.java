package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class QuestionTree extends QuestionTreeBase
{
	private static final boolean LEFT = true;
	private static final boolean RIGHT = false;
	
	/**
	 * Helper function that iterates through the properties and returns the first not yet encountered property
	 * @param encounteredProps
	 * @param iter
	 * @return First property that has not been encountered
	 */
	private String getNextProp(ArrayList<String> encounteredProps, Iterator<String> iter)
	{
		while(iter.hasNext())
		{
			String prop = iter.next();
			if(!encounteredProps.contains(prop)) return prop;
		}
		
		throw new IllegalArgumentException("Error: No more unencountered properties.");
	}
	
	/**
	 * Helper function to add a tree node and set parent pointer.
	 * @param side LEFT (true) or RIGHT (false).
	 * @param property
	 * @param parent
	 * @return Pointer to the newly added node.
	 */
	private TreeNode<String> addTreeNode(boolean side, String property, TreeNode<String> parent)
	{
		if(side == LEFT)
		{
			parent.setLeft(new TreeNode<String>(property));
			parent.getLeft().setParent(parent);
			return parent.getLeft();
		}
		else
		{
			parent.setRight(new TreeNode<String>(property));
			parent.getRight().setParent(parent);
			return parent.getRight();
		}
	}
	
	/**
	 * Helper function to add a leaf node and set parent pointer.
	 * @param object
	 * @param parent
	 */
	private void addLeafNode(boolean side, QuestionObject object, TreeNode<String> parent)
	{
		if(side == LEFT)
		{
			parent.setLeft(new LeafNode<String>(object.getName(), object));
			parent.getLeft().setParent(parent);
		}
		else
		{
			parent.setRight(new LeafNode<String>(object.getName(), object));
			parent.getRight().setParent(parent);
		}
	}
	
	/**
	 * Helper function to build a subtree of properties along the right edge. Object is added as a leaf node at the end.
	 * @param object
	 * @param parent
	 * @param iter Provides the properties nodes to add
	 */
	private void buildSubTree(QuestionObject object, TreeNode<String> parent, ArrayList<String> encounteredProps, Iterator<String> iter)
	{
		TreeNode<String> currNode = parent;
		
		//Add all remaining properties to right edge
		while(iter.hasNext())
		{
			String prop = iter.next();
			if(!encounteredProps.contains(prop)) currNode = addTreeNode(RIGHT, prop, currNode);
		}
		
		//Add object as leaf node
		addLeafNode(RIGHT, object, currNode);
	}
	
	@Override
	public void buildTree(ArrayList<QuestionObject> objects)
	{
		//Sort the objects by number of properties in descending order
		Collections.sort(objects);
		
		TreeNode<String> currNode = null;
		
		//Iterate and add every object
		for(int x = 0; x < objects.size(); x++)
		{
			//For easier reference
			QuestionObject object = objects.get(x);
			Iterator<String> iter = object.propertyIterator();
			
			//Keep track of already encountered properties
			ArrayList<String> encounteredProps = new ArrayList<String>();
			
			//If root is null then we instantiate it and build subtree from first object
			if(m_root == null)
			{
				m_root = new TreeNode<String>(iter.next());
				buildSubTree(object, m_root, encounteredProps, iter);
			}
			else
			{
				//Start tree walk from root
				currNode = m_root;
				
				while(true)
				{
					//If object has this property then take the right edge
					if(object.containsProperty(currNode.m_value))
					{
						encounteredProps.add(currNode.m_value);
						currNode = currNode.getRight();
					}
					else
					{
						//If the left edge is empty then we build a subtree of the remaining objects there
						if(currNode.getLeft() == null)
						{
							//Special case where object's properties have been exhausted, add leaf to left edge
							if(encounteredProps.size() == object.getPropCount())
							{
								addLeafNode(LEFT, object, currNode);
								break;
							}
							else
							{
								//Find the next property that has not been encountered and add to left edge
								currNode = addTreeNode(LEFT, getNextProp(encounteredProps, iter), currNode);
								
								//Build subtree of all remaining properties and place object as leaf
								buildSubTree(object, currNode, encounteredProps, iter);
								break;
							}
						}
						else currNode = currNode.getLeft();
					}
				}
			}
		}
	}
	
	@Override
	public Query findQuery()
	{
		//Start the tree traversal
		TreeNode<String> currNode = m_root;
		
		//Calculate the limits n/3 < count(v) <= 2n/3
		//NOTE: the left compare may not be strict due to float
		double totalObjects = countObjects();
		double oneThird = totalObjects / 3;
		double twoThird = totalObjects / 3 * 2;
		
		//We're confident it will return
		while(true)
		{
			double nodeCount = countObjects(currNode);
			double rightCount = countObjects(currNode.getRight());
			
			//Check if the current node count satisfies the bounds
			if(nodeCount >= oneThird && nodeCount <= twoThird) break;
			//Case 1 where right subtree count is < n/3
			//Discard right subtree and follow left subtree
			else if(rightCount < oneThird)
			{
				currNode = currNode.getLeft();
			}
			//Case 2 where right subtree count is > n/3
			//Discard left subtree and follow right subtree
			//If the count is also <= 2n/3 it will satisfy check next loop
			else
			{
				currNode = currNode.getRight();
			}
		}
		
		return constructQuery(currNode);
	}
}