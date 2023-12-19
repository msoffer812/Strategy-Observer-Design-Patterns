/**
 * Extends honest electoral vote reporter
 * to maximize code reuse
 */
package assignment5;

import java.util.HashMap;
import java.util.Map;

public class DemocratElectoralVoteFavorerReporter extends ElectoralVoteReporter{
	private String stateWithMostElectoralVotes = null;
	
	public DemocratElectoralVoteFavorerReporter(Map<String, Integer> statesRepublicanVotes,
			Map<String, Integer> statesDemocratVotes, Map<String, Integer> statesElectoralVotes)
	{
		super(statesRepublicanVotes, statesDemocratVotes, statesElectoralVotes);
	}
	
	private void findAndAssignStateWithMostElectoralVotes() {
		int maxVote = 0;
		for(String state:statesElectoralVotes.keySet()) {
			if(statesElectoralVotes.get(state) > maxVote) {
				stateWithMostElectoralVotes = state;
				maxVote = statesElectoralVotes.get(state);
			}
		}
	}
	
	@Override
	protected void adjustElectoralVotes(String state, int voteChange) {
		int originalAmountOfVotes = statesElectoralVotes.get(state);
		int newAmount = originalAmountOfVotes + voteChange;
		statesElectoralVotes.put(state, newAmount);
		adjustStatsForChangedState(state, originalAmountOfVotes);
		calculateOverallWinner();
	}
	
	private void adjustStatsForChangedState(String state, int originalAmountOfElectoralVotes) {
		if(statesElectoralVotes.get(stateWithMostElectoralVotes)<statesElectoralVotes.get(state)) {
			String oldStateWithMostElectoralVotes = stateWithMostElectoralVotes;
			stateWithMostElectoralVotes = state;
			/*
			 * Do it twice, first adjust the winner of the old ignored 
			 * state in case it changes, then adjust the votes of the new ignored state
			 */
			calculateStateElectoralVoteWinner(oldStateWithMostElectoralVotes, 
					statesRepublicanVotes.get(oldStateWithMostElectoralVotes), 
						statesDemocratVotes.get(oldStateWithMostElectoralVotes), 
							statesElectoralVotes.get(oldStateWithMostElectoralVotes));
			calculateStateElectoralVoteWinner(stateWithMostElectoralVotes, 
					statesRepublicanVotes.get(stateWithMostElectoralVotes), 
						statesDemocratVotes.get(stateWithMostElectoralVotes), 
							originalAmountOfElectoralVotes);
		}else {
			/*
			 * If it's not the new state to ignore, just adjust for the new amounts of votes
			 */
			calculateStateElectoralVoteWinner(state, statesRepublicanVotes.get(state), 
						statesDemocratVotes.get(state), originalAmountOfElectoralVotes);
		}
	}
	
	@Override
	protected void calculateStateElectoralVoteWinner(String state, int repVote, int demVote, int electoralVotes){
		if(stateWithMostElectoralVotes == null) {
			findAndAssignStateWithMostElectoralVotes();
		}
		if(repVote < demVote || state.equals(stateWithMostElectoralVotes)) {
			adjustVoteToDemocrat(state, electoralVotes);
		} else if(repVote > demVote){
			adjustVoteToRepublican(state, electoralVotes);
		} else{
			adjustVoteToTie(state, electoralVotes);
		}
	}
	
	
}
