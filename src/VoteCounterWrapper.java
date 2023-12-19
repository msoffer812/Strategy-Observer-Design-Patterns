package assignment5;

public class VoteCounterWrapper {
	private String state;
	private int changeInVotes;
	private VoteChangeType voteType;
	
	public VoteCounterWrapper(String state, int amountOfVotes, VoteChangeType voteType)
	{
		this.state = state;
		this.changeInVotes = amountOfVotes;
		this.voteType = voteType;
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	public void setChangeInVotes(int changeInVotes)
	{
		this.changeInVotes = changeInVotes;
	}
	public void setParty(VoteChangeType voteType)
	{
		this.voteType = voteType;
	}
	
	public String getState()
	{
		return this.state;
	}
	public int getChangeInVotes()
	{
		return this.changeInVotes;
	}
	public VoteChangeType getPoliticalParty()
	{
		return this.voteType;
	}
}
