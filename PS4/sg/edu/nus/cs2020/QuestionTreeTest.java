package sg.edu.nus.cs2020;

import org.junit.Test;

public class QuestionTreeTest
{
	@Test
	public void buildTreeTest()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/SmallExample");
		//Comment out restartGame() in QuestionGameBase
		QuestionTree tree = new QuestionTree();
		tree.buildTree(game.m_objects);
		tree.printTree();
		
		/*
		 * Expected Output:
		 * F:  -1 -2 -4 5
		 * H:  -1 -2 4 5 6
		 * E:  -1 2 -4 -3
		 * D:  -1 2 -4 3 5
		 * B:  -1 2 4 -5 3
		 * G:  -1 2 4 5 6
		 * C:  1 -2 3 5 6
		 * A:  1 2 3 4 5
		 */
	}
	
	@Test
	public void findQueryTest()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/SmallExample");
		//Comment out restartGame() in QuestionGameBase
		QuestionTree tree = new QuestionTree();
		tree.buildTree(game.m_objects);
		System.out.println(tree.findQuery());
		
		/*
		 * Expected Output:
		 * -1,2
		 * *Contains 4 leaves
		 */
	}
}
