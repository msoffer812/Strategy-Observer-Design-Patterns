package assignment5;

public interface IVoteReporter {
	String getWinner();
	void updateWinner(VoteCounterWrapper changeInVotes);
}
