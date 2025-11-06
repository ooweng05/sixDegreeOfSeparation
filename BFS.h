#pragma once
#include <vector>
#include <list>
#include <unordered_map>
#include <queue>
#include <unordered_set>
#include <string>
#include <iostream>

class SocialGraph {
private:
	std::unordered_map<int, std::list<int>> adjacencyList;
	int totalUser;

public:
	SocialGraph();

	int getDegreeOfSeparation(int startUser, int targetUser);
	std::vector<int> getShortestPath(int startUser, int targetUser);

	void addFriendship(int user1, int user2);
	void printGraphInfo()const;

private:
	bool bfsTraversal(int startUser, int targetUser, std::unordered_map<int, int>& parent, std::unordered_map<int, int>& distance);

}; 
