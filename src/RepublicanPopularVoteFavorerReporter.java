/**
 * Favors the Republican popular vote by reporting 5% less of the democrat vote 
 */
package assignment5;

import java.util.Map;

public class RepublicanPopularVoteFavorerReporter extends PopularVoteReporter{
	
	public RepublicanPopularVoteFavorerReporter(Map<String, Integer> statesRepublicanVotes, 
			Map<String, Integer> statesDemocratVotes) {
		super(statesRepublicanVotes, statesDemocratVotes);
	}
	
	@Override
	protected void calculateTotalPopularVote()
	{
		for(int amountOfVotes: statesRepublicanVotes.values())
		{
			republicanPopularVote += amountOfVotes;
		}
		for(int amountOfVotes: statesDemocratVotes.values())
		{
			democratPopularVote += .95*amountOfVotes;	/* Republican-favoring strategy; report 5% less of democrat votes */
		}
		calculateWinningParty();
	}
	
	@Override
	public void updateWinner(VoteCounterWrapper changeInVotes)
	{
		VoteChangeType party = changeInVotes.getPoliticalParty();
		int voteChange = changeInVotes.getChangeInVotes();
		if(party == VoteChangeType.DEMOCRAT)
		{
			democratPopularVote += .95*voteChange; /* Republican-favoring strategy; report 5% less of democrat votes */
		}else
		{
			republicanPopularVote += voteChange;
		}
		calculateWinningParty();
	}
}
