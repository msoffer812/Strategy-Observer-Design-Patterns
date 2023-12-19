/**
 * Favors Republican Electoral vote
 * by having Texas always turn republican no matter
 * the popular vote
 */
package assignment5;

import java.util.HashMap;
import java.util.Map;

public class RepublicanElectoralVoteFavorerReporter extends ElectoralVoteReporter{
	private static String stateThatWillAlwaysTurnRepublican = "Texas";
	
	public RepublicanElectoralVoteFavorerReporter(Map<String, Integer> statesRepublicanVotes,
			Map<String, Integer> statesDemocratVotes, Map<String, Integer> statesElectoralVotes)
	{
		super(statesRepublicanVotes, statesDemocratVotes, statesElectoralVotes);
	}
	
	@Override
	protected void calculateStateElectoralVoteWinner(String state, int repVote, int demVote, int electoralVotes){
		if(repVote > demVote || state.equals(stateThatWillAlwaysTurnRepublican)){
			adjustVoteToRepublican(state, electoralVotes);
		} else if(repVote < demVote) {
			adjustVoteToDemocrat(state, electoralVotes);
			} else {
				adjustVoteToTie(state, electoralVotes);
			}
	}
}

