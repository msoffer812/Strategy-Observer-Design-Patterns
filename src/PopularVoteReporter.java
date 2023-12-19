/**
 * Calculates and reports the Popular Vote results
 * completely honestly. Serves as a parent class to the 
 * classes that report but skew the vote, since most of the code 
 * is really the same
 */
package assignment5;

import java.util.HashMap;
import java.util.Map;

public class PopularVoteReporter implements IPopularVoteReporter{
	protected int republicanPopularVote;
	protected int democratPopularVote;
	protected String popularVoteWinner;
	protected Map<String, Integer> statesRepublicanVotes;
	protected Map<String, Integer> statesDemocratVotes;
	
	public PopularVoteReporter(Map<String, Integer> statesRepublicanVotes, 
			Map<String, Integer> statesDemocratVotes) {
		this.statesRepublicanVotes = new HashMap<String, Integer>(statesRepublicanVotes);
		this.statesDemocratVotes = new HashMap<String, Integer>(statesDemocratVotes);
		republicanPopularVote = 0;
		democratPopularVote = 0;
		calculateTotalPopularVote();
	}
	
	protected void calculateTotalPopularVote()
	{
		for(String state: statesRepublicanVotes.keySet()) {
			republicanPopularVote += statesRepublicanVotes.get(state);
			democratPopularVote += statesDemocratVotes.get(state);
		}
		calculateWinningParty();
	}
	
	protected void calculateWinningParty()
	{
		if(republicanPopularVote > democratPopularVote) {
			this.popularVoteWinner = "Republican";
		} else if(republicanPopularVote < democratPopularVote) {
			this.popularVoteWinner = "Democrat";
		} else
		{
			this.popularVoteWinner = "Tie";
		}
	}
	@Override
	public String getWinner()
	{
		return this.popularVoteWinner;
	}
	
	@Override
	public void updateWinner(VoteCounterWrapper changeInVotes)
	{
		VoteChangeType party = changeInVotes.getPoliticalParty();
		int voteChange = changeInVotes.getChangeInVotes();
		if(party == VoteChangeType.DEMOCRAT)
		{
			democratPopularVote+=voteChange;
		}else
		{
			republicanPopularVote+=voteChange;
		}
		calculateWinningParty();
	}
}
