package sg.edu.nus.cs2020;

public class QuestionGame extends QuestionGameBase
{
	public QuestionGame(String objectFileName)
	{
		super(objectFileName);
	}

	@Override
	protected QuestionTreeBase CreateTree()
	{
		return new QuestionTree();
	}
	
	public void RestartGame(int chosen)
	{
		m_chosenObject = m_objects.get(chosen);
		
		// Initialize the player
		m_player = new QuestionPlayer(m_objects, CreateTree());
	}
}
