package assignment5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

public class test {

	public static void main(String[] args) {
		Map<String, Integer> electoralVotesByState = fillUpElectoralVotesByState();
		ElectionData electionDataObservable = new ElectionData(electoralVotesByState);
		Map<String, Integer> statesRepublicanVotes = electionDataObservable.getStateDemocratVotes();
		Map<String, Integer> statesDemocratVotes = electionDataObservable.getStateRepublicanVotes();
		Map<String, Integer> statesElectoralVotes = electionDataObservable.getStateElectoralVotes();
		
		/*
		 * Use a treeMap because predictable order to print out the stats
		 */
		Map<String, ElectionDataObserver> observers = new TreeMap<>();
		
		ElectionDataObserver honestElectionDataReporter = 
				new ElectionDataObserver(
						new PopularVoteReporter(statesRepublicanVotes, 
								statesDemocratVotes), 
						new ElectoralVoteReporter(statesRepublicanVotes, 
								statesDemocratVotes, statesElectoralVotes));
		observers.put("Honest Election Data Reporter", honestElectionDataReporter);
		
		ElectionDataObserver republicanFavoringElectionDataReporter = 
				new ElectionDataObserver(
						new RepublicanPopularVoteFavorerReporter(statesRepublicanVotes, 
								statesDemocratVotes), 
						new RepublicanElectoralVoteFavorerReporter(statesRepublicanVotes, 
								statesDemocratVotes, statesElectoralVotes));
		observers.put("Republican-Favoring Election Data Reporter", 
				republicanFavoringElectionDataReporter);
		
		ElectionDataObserver democratFavoringElectionDataReporter = 
				new ElectionDataObserver(
						new DemocratPopularVoteFavorerReporter(statesRepublicanVotes, 
								statesDemocratVotes), 
						new DemocratElectoralVoteFavorerReporter(statesRepublicanVotes, 
								statesDemocratVotes, statesElectoralVotes));
		observers.put("Democrat-Favoring Election Data Reporter", 
				democratFavoringElectionDataReporter);
		
		ElectionDataObserver honestElectoralDemocratPopularFavorerElectionDataReporter = 
				new ElectionDataObserver(
						new DemocratPopularVoteFavorerReporter(statesRepublicanVotes, 
								statesDemocratVotes), 
						new ElectoralVoteReporter(statesRepublicanVotes, 
								statesDemocratVotes, statesElectoralVotes));
		observers.put("Honest Electoral and Democrat-Favoring for the Popular Election Data Reporter",
				honestElectoralDemocratPopularFavorerElectionDataReporter);
		
		ElectionDataObserver democratPopularRepublicanElectoralFavorerElectionDataReporter = 
				new ElectionDataObserver(
						new DemocratPopularVoteFavorerReporter(statesRepublicanVotes, 
								statesDemocratVotes), 
						new RepublicanElectoralVoteFavorerReporter(statesRepublicanVotes, 
								statesDemocratVotes, statesElectoralVotes));
		observers.put("Republican-Favoring for the Electoral and Democrat-Favoring for the Popular Election Data Reporter", 
				democratPopularRepublicanElectoralFavorerElectionDataReporter);
		//electionDataObservable.addObserver(honestElectionDataReporter);
		initializeObserversAsObserver(observers, electionDataObservable);
		
		displayData(observers);
		
		electionDataObservable.setStatesRepublicanVotes("Texas", 70000);
		electionDataObservable.setStatesDemocratVotes("Texas", 500);
		electionDataObservable.setStatesRepublicanVotes("California", 234124);
		electionDataObservable.setStatesDemocratVotes("California", 4665);
		electionDataObservable.setStatesRepublicanVotes("New York", 3422);
		electionDataObservable.setStatesDemocratVotes("New York", 234);
		electionDataObservable.setStatesRepublicanVotes("Florida", 678468);
		electionDataObservable.setStatesDemocratVotes("Florida", 345);
		electionDataObservable.setStatesRepublicanVotes("Illinois", 5645);
		electionDataObservable.setStatesDemocratVotes("Illinois", 132423);
		
		System.out.println("Votes Adjusted");
		System.out.println("");
		displayData(observers);
	
		electionDataObservable.addDemocratVotes("Texas", 5000);
		electionDataObservable.addRepublicanVotes("Texas", 4700);
		electionDataObservable.addDemocratVotes("California", 2700);
		electionDataObservable.addRepublicanVotes("California", 6300);
		electionDataObservable.addDemocratVotes("New York", 6700);
		electionDataObservable.addRepublicanVotes("New York", 670);
		electionDataObservable.addDemocratVotes("Florida", 20);
		electionDataObservable.addRepublicanVotes("Florida", 230);
		electionDataObservable.addDemocratVotes("Illinois", 450);
		electionDataObservable.addRepublicanVotes("Illinois", 500);
		
		System.out.println("Votes Adjusted");
		System.out.println("");
		displayData(observers);
		
		electionDataObservable.setStateElectoralVotes("New York", 50);
		
		System.out.println("Votes Adjusted");
		System.out.println("");
		displayData(observers);
	}
	
	public static void initializeObserversAsObserver(Map<String, ElectionDataObserver> observers, ElectionData observable) {
		for(ElectionDataObserver observer:observers.values()) {
			observable.addObserver(observer);
		}
	}
	public static void displayData(Map<String, ElectionDataObserver> observers) {
		for(String observerType: observers.keySet()) {
			ElectionDataObserver observer = observers.get(observerType);
			System.out.println("Observer type: " + observerType);
			System.out.println(observer.getDisclaimer());
			printOutWinner(observer.getElectoralCollegeWinner(), "Electoral College");
			printOutWinner(observer.getPopularVoteWinner(), "Popular Vote");
			System.out.println("");
		}
	}
	
	public static void printOutWinner(String winner, String voteType) {
		if(winner.equals("Tie")) {
			System.out.println("The " + voteType + " was a tie.");
		}else {
			System.out.println("The " + winner + "s won the " + voteType + ".");
		}
	}
	/**
	 * Declare the states, votes, put into HashMap
	 * @return the map
	 */
	public static Map<String, Integer> fillUpElectoralVotesByState(){
		String[] states = {"California", "Texas", "New York", "Florida", "Illinois"};
		int[] votes = {5, 3, 4, 1, 5};
		Map<String, Integer> electoralVotesByState = new HashMap<>();
		/*
		 * Fill it up
		 */
		for(int i=0;i<states.length;i++){
			electoralVotesByState.put(states[i], votes[i]);
		}
		return electoralVotesByState;
	}

}
