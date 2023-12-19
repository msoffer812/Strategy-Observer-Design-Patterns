/**
 * Calculates and reports the Electoral College results
 * completely honestly. Serves as a parent class to the 
 * classes that report but skew the vote, since most of the code 
 * is really the same
 */
package assignment5;

import java.util.HashMap;
import java.util.Map;

public class ElectoralVoteReporter implements IElectoralVoteReporter{
	protected Integer totalRepublicanElectoralVotes;
	protected Integer totalDemocratElectoralVotes;
	protected String electoralVoteWinner;
	protected Map<String, Integer> statesRepublicanVotes;
	protected Map<String, Integer> statesDemocratVotes;
	protected Map<String, Integer> statesElectoralVotes;
	protected Map<String, String> electoralVoteWinnersByState;
	
	public ElectoralVoteReporter(Map<String, Integer> statesRepublicanVotes,
			Map<String, Integer> statesDemocratVotes, Map<String, Integer> statesElectoralVotes)
	{
		this.totalRepublicanElectoralVotes = 0;
		this.totalDemocratElectoralVotes = 0;
		this.statesRepublicanVotes = new HashMap<String, Integer>(statesRepublicanVotes);
		this.statesDemocratVotes = new HashMap<String, Integer>(statesDemocratVotes);
		this.statesElectoralVotes = new HashMap<String, Integer>(statesElectoralVotes);
		this.electoralVoteWinnersByState = new HashMap<>();
		initializeTotalElectoralVotes();
	}
	
	@Override
	public void updateWinner(VoteCounterWrapper changeInVotes) {
		VoteChangeType voteType = changeInVotes.getPoliticalParty();
		String state = changeInVotes.getState();
		int voteChange = changeInVotes.getChangeInVotes();
		if(voteType == VoteChangeType.ELECTORAL) {
			adjustElectoralVotes(state, voteChange);
		}else {
			if(voteType == VoteChangeType.DEMOCRAT) {
				Integer voteCount = statesDemocratVotes.get(state);
				voteCount += voteChange;
				statesDemocratVotes.put(state, voteCount);
			} else if(voteType == VoteChangeType.REPUBLICAN){
				Integer voteCount = statesRepublicanVotes.get(state);
				voteCount += voteChange;
				statesRepublicanVotes.put(state, voteCount);
			}
			updateStats(state);
		}
	}
	
	protected void updateStats(String state) {
		calculateStateElectoralVoteWinner(state, statesRepublicanVotes.get(state), 
				statesDemocratVotes.get(state), statesElectoralVotes.get(state));
		calculateOverallWinner();
	}
	
	
	protected void adjustElectoralVotes(String state, int voteChange) {
		int originalAmountOfVotes = statesElectoralVotes.get(state);
		int newAmount = originalAmountOfVotes + voteChange;
		statesElectoralVotes.put(state, newAmount);
		calculateStateElectoralVoteWinner(state, statesRepublicanVotes.get(state), 
				statesDemocratVotes.get(state), originalAmountOfVotes);
		calculateOverallWinner();
	}
	
	protected void initializeTotalElectoralVotes()
	{
		for(String state: statesRepublicanVotes.keySet())
		{
			int repVote = statesRepublicanVotes.get(state);
			int demVote = statesDemocratVotes.get(state);
			int amountOfElectoralVotes = statesElectoralVotes.get(state);
			calculateStateElectoralVoteWinner(state, repVote, demVote, amountOfElectoralVotes);
		}
		calculateOverallWinner();
	}
	
	protected void calculateStateElectoralVoteWinner(String state, int repVote, int demVote, int electoralVotes){
		if(repVote > demVote){
			adjustVoteToRepublican(state, electoralVotes);
		} else if(repVote < demVote) {
			adjustVoteToDemocrat(state, electoralVotes);
			} else {
				adjustVoteToTie(state, electoralVotes);
			}
	}
	
	/**
	 * Contains a parameter of electoralvotes in case they were adjusted
	 * so we subtract the old amount before adding the new amount
	 * @param state
	 * @param electoralVotes
	 */
	protected void adjustVoteToRepublican(String state, int electoralVotes) {
		if(!electoralVoteWinnersByState.containsKey(state) ||
				!electoralVoteWinnersByState.get(state).equals("Republican")) {
			if(electoralVoteWinnersByState.containsKey(state) && 
					electoralVoteWinnersByState.get(state).equals("Democrat")) {
				totalDemocratElectoralVotes -= electoralVotes;
			}
			totalRepublicanElectoralVotes += statesElectoralVotes.get(state);
			electoralVoteWinnersByState.put(state, "Republican");
		}else {
			totalRepublicanElectoralVotes -= electoralVotes;
			totalRepublicanElectoralVotes += statesElectoralVotes.get(state);
		}
	}
	
	/**
	 * Contains a parameter of electoralvotes in case they were adjusted
	 * so we subtract the old amount before adding the new amount
	 * @param state
	 * @param electoralVotes
	 */
	protected void adjustVoteToDemocrat(String state, int electoralVotes) {
		if(!electoralVoteWinnersByState.containsKey(state) ||
				!electoralVoteWinnersByState.get(state).equals("Democrat")) {
			if(electoralVoteWinnersByState.containsKey(state) &&
					electoralVoteWinnersByState.get(state).equals("Republican")) {
				totalRepublicanElectoralVotes -= electoralVotes;
			}
			totalDemocratElectoralVotes += statesElectoralVotes.get(state);
			electoralVoteWinnersByState.put(state, "Democrat");
		}else {
			totalDemocratElectoralVotes -= electoralVotes;
			totalDemocratElectoralVotes += statesElectoralVotes.get(state);
		}
	}
	
	/**
	 * Contains a parameter of electoralvotes in case they were adjusted
	 * so we subtract the old amount before adding the new amount
	 * @param state
	 * @param electoralVotes
	 */
	protected void adjustVoteToTie(String state, int electoralVotes) {
		if(electoralVoteWinnersByState.containsKey(state) && electoralVoteWinnersByState.get(state) != null) {
			if(electoralVoteWinnersByState.containsKey(state) &&
					electoralVoteWinnersByState.get(state).equals("Republican")) {
				totalRepublicanElectoralVotes -= electoralVotes;
			}else if(electoralVoteWinnersByState.get(state).equals("Democrat")) {
				totalDemocratElectoralVotes -= electoralVotes;
			}
			electoralVoteWinnersByState.put(state, "Tie");
		}
	}
	protected void calculateOverallWinner() {
		if(totalRepublicanElectoralVotes > totalDemocratElectoralVotes) {
			this.electoralVoteWinner = "Republican";
		} else if(totalRepublicanElectoralVotes < totalDemocratElectoralVotes) {
			this.electoralVoteWinner = "Democrat";
		} else
		{
			this.electoralVoteWinner = "Tie";
		}
	}
	@Override
	public String getWinner() {
		return this.electoralVoteWinner;
	}
	
}
