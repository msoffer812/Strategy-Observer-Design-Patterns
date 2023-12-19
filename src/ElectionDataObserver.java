package assignment5;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class ElectionDataObserver implements Observer{
	private IPopularVoteReporter popularVoteReporter;
	private IElectoralVoteReporter electoralCollegeReporter;
	
	public ElectionDataObserver(IPopularVoteReporter popularVoteReporter, IElectoralVoteReporter electoralCollegeReporter)
	{
		this.popularVoteReporter = popularVoteReporter;
		this.electoralCollegeReporter = electoralCollegeReporter;
	}
	public String getDisclaimer()
	{
		LocalDateTime currentTime = LocalDateTime.now(); 
		return "All reports are purely observational and not "
				+ "legally binding in any way. Current time: " + currentTime;
	}

	/**
	 * Implements push - the Observable sends the specifics of the change to the 
	 * Observer, who makes proper change, rather than the Observer finding out what it was 
	 */
	@Override
	public void update(@SuppressWarnings("deprecation") Observable electionData, Object changeInVotes) 
	{
		VoteCounterWrapper voteChange = (VoteCounterWrapper)changeInVotes;
		popularVoteReporter.updateWinner(voteChange);
		electoralCollegeReporter.updateWinner(voteChange);
	}
	
	public String getElectoralCollegeWinner()
	{
		return electoralCollegeReporter.getWinner();
	}
	
	public String getPopularVoteWinner()
	{
		return popularVoteReporter.getWinner();
	}
	
	
}
