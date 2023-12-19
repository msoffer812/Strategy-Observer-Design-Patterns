/**
 * Extends the honest reporter because there's some code reuse,
 * but favors the Democrat party by calculating the state with 
 * the most republican votes and ignoring it
 */
package assignment5;

import java.util.Map;

public class DemocratPopularVoteFavorerReporter extends PopularVoteReporter{
	private String stateWithMostRepublicanVotes = null;
	
	public DemocratPopularVoteFavorerReporter(Map<String, Integer> statesRepublicanVotes, 
			Map<String, Integer> statesDemocratVotes) {
		super(statesRepublicanVotes, statesDemocratVotes);
	}
	
	@Override
	protected void calculateTotalPopularVote()
	{
		for(String state : statesRepublicanVotes.keySet())
		{
			calculateStateWithMostRepublicanVotes(state);
			republicanPopularVote += statesRepublicanVotes.get(state);
			democratPopularVote += statesDemocratVotes.get(state);
		}
		calculateWinningParty();
	}
	
	/**
	 * implemented with the democrat-popular vote favoring strategy 
	 * by ignoring the state with the most amount of republican votes
	 * @param state
	 */
	private void calculateStateWithMostRepublicanVotes(String state) {
		if(stateWithMostRepublicanVotes == null) {
			stateWithMostRepublicanVotes = "Texas";
		}
		if(statesRepublicanVotes.get(state) > statesRepublicanVotes.get(this.stateWithMostRepublicanVotes)) {
			this.stateWithMostRepublicanVotes = state;
		}
	}
	
	@Override
	protected void calculateWinningParty()
	{
		int adjustedRepublicanPopularVote = republicanPopularVote -			/* Adjust the amount of republican popular vote by state */
				statesRepublicanVotes.get(stateWithMostRepublicanVotes);		/* with most republican votes so to favor democrat party*/
		
		if(adjustedRepublicanPopularVote > democratPopularVote) {
			this.popularVoteWinner = "Republican";
		} else if(republicanPopularVote < democratPopularVote) {
			this.popularVoteWinner = "Democrat";
		} else
		{
			this.popularVoteWinner = "Tie";
		}
	}
	
	private void adjustVotesToDemocrat(int voteChange, String state) {
		int voteCount = statesDemocratVotes.get(state);
		voteCount += voteChange;
		statesDemocratVotes.put(state, voteCount);
		democratPopularVote+=voteChange;
	}
	
	private void adjustVotesToRepublican(int voteChange, String state) {
		int voteCount = statesRepublicanVotes.get(state);
		voteCount += voteChange;
		statesRepublicanVotes.put(state, voteCount);
		republicanPopularVote+=voteChange;
	}
	
	/*
	 * Unlike in the parent class, we want to store voting data by state
	 * to figure out which is the current state with the most republican votes
	 * so it can swing to democrat
	 */
	@Override
	public void updateWinner(VoteCounterWrapper changeInVotes)
	{
		VoteChangeType party = changeInVotes.getPoliticalParty();
		int voteChange = changeInVotes.getChangeInVotes();
		String state = changeInVotes.getState();
		if(party == VoteChangeType.DEMOCRAT)
		{
			adjustVotesToDemocrat(voteChange, state);
		}else
		{
			adjustVotesToRepublican(voteChange, state);
		}
		calculateStateWithMostRepublicanVotes(state);
		calculateWinningParty();
	}
}
