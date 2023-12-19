package assignment5;
import java.util.*;

public class ElectionData extends Observable{
	private Map<String, Integer> statesRepublicanVotes;
	private Map<String, Integer> statesDemocratVotes;
	private Map<String, Integer> statesElectoralVotes;
	
	public ElectionData(Map<String, Integer> statesElectoralVotes)
	{
		statesRepublicanVotes = new HashMap<>();
		statesDemocratVotes = new HashMap<>();
		this.statesElectoralVotes = statesElectoralVotes;
		initializeMaps();
	}
	
	public Map<String, Integer> getStateElectoralVotes()
	{
		return new HashMap<>(this.statesElectoralVotes);
	}
	public Map<String, Integer> getStateDemocratVotes()
	{
		return new HashMap<>(this.statesDemocratVotes);
	}
	public Map<String, Integer> getStateRepublicanVotes()
	{
		return new HashMap<>(this.statesRepublicanVotes);
	}
	
	public int getSpecificStateElectoralVotes(String state)
	{
		if(statesElectoralVotes.containsKey(state))
		{
			return statesElectoralVotes.get(state);
		}
		return -1;
	}
	public int getSpecificStateDemocratVotes(String state)
	{
		if(statesDemocratVotes.containsKey(state))
		{
			return statesDemocratVotes.get(state);
		}
		return -1;
	}
	public int getSpecificStatesRepublicanVotes(String state)
	{
		if(statesRepublicanVotes.containsKey(state))
		{
			return statesRepublicanVotes.get(state);
		}
		return -1;
	}
	
	@SuppressWarnings("deprecation")
	public void setStateElectoralVotes(String state, int newAmountOfElectoralVotes)
	{
		if(statesElectoralVotes.containsKey(state))
		{
			int originalAmount = statesElectoralVotes.get(state);
			int change = newAmountOfElectoralVotes - originalAmount;
			statesElectoralVotes.put(state, newAmountOfElectoralVotes);
			setChanged();
			notifyObservers(new VoteCounterWrapper(state, change, VoteChangeType.ELECTORAL));
		}
	}
	@SuppressWarnings("deprecation")
	public void setStatesRepublicanVotes(String state, int newAmountOfRepublicanVotes)
	{
		if(statesRepublicanVotes.containsKey(state))
		{
			int originalVoteAmount = statesRepublicanVotes.get(state);
			int changeInVotes = newAmountOfRepublicanVotes - originalVoteAmount;
			statesRepublicanVotes.put(state, newAmountOfRepublicanVotes);
			setChanged();
			notifyObservers(new VoteCounterWrapper(state, changeInVotes, VoteChangeType.REPUBLICAN));
		}
	}
	@SuppressWarnings("deprecation")
	public void setStatesDemocratVotes(String state, int newAmountOfDemocratVotes)
	{
		if(statesDemocratVotes.containsKey(state))
		{
			int originalVoteAmount = statesDemocratVotes.get(state);
			int changeInVotes = newAmountOfDemocratVotes - originalVoteAmount;
			statesDemocratVotes.put(state, newAmountOfDemocratVotes);
			setChanged();
			notifyObservers(new VoteCounterWrapper(state, changeInVotes, VoteChangeType.DEMOCRAT));
		}
	}
	
	public void addDemocratVotes(String state, int addedVotes) 
	{
		if(statesDemocratVotes.containsKey(state))
		{
			int originalVoteAmount = statesDemocratVotes.get(state);
			statesDemocratVotes.put(state, addedVotes + originalVoteAmount);
			setChanged();
			notifyObservers(new VoteCounterWrapper(state, addedVotes, VoteChangeType.DEMOCRAT));
		}
	}
	
	public void addRepublicanVotes(String state, int addedVotes) 
	{
		if(statesRepublicanVotes.containsKey(state))
		{
			int originalVoteAmount = statesRepublicanVotes.get(state);
			statesRepublicanVotes.put(state, addedVotes + originalVoteAmount);
			setChanged();
			notifyObservers(new VoteCounterWrapper(state, addedVotes, VoteChangeType.REPUBLICAN));
		}
	}
	
	private void initializeMaps()
	{
		String[] states = {"California", "Texas", "New York", "Florida", "Illinois"};
		for(String state: states)
		{
			statesRepublicanVotes.put(state, 0);
			statesDemocratVotes.put(state, 0);
		}
	}
}
