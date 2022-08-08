# AutoCompleteApi

<h2>Overview</h2>

The objective of this repository is to create an <b> autoComplete API </b> that given a user input query, provides suggested queries, based on the search history stored in a csv file and the number of times the queries were typed.

The project was implemented using two different methods. The programming language used is Java with the framework SpringBoot to build the api.

The first one consists in storing all the queries in a Map (text : count). Then, for each input query, we loop over the Map and determine all the queries that starts with the input. The result Map will be sorted according to the values of the count which allows extract the most typed queries. (<b> branch main </b> of the code)

The second method consists in using a prefix tree (trie). After storing the different queries in a Map (text : count), we loop over the Map to insert each query sentence in the tree.
The root node is empty and each node is defined by a <em> hashMap children </em> and an <em> integer endOfSentence </em> to tell whether we have already a complete sentence at this node or not. The insertion is done character by character. Once it is inserted, we set the <em> endOfSentence </em> attribute of the last node to the number of times the query was typed (count Value).
Given an input query (prefix), the search is done letter by letter accross the tree which speeds up the search and allows you to eliminate certain branches each time you move forward. (<b> branch tree-search </b> of the code)

<h2>Complexity:</h2>  
N : max query length
M : size of query list data


|               | Time  | Memory |  
|---------------|-------|--------|
| naive         | O(M)  | O(MN)  |   
| tree (insert) | O(MN) | O(MN)  | 
| tree (search) | O(N)  |  cte   | 

<h2>Results: </h2>

![image](https://user-images.githubusercontent.com/71329302/183301379-4d8897cc-c626-48b5-a05c-3c3a0e8759f0.png)

To get the request input in the url, i use the @GetMapping annotation.

<h2>Methods comparaison </h2>

Then, I compared the two methods performances by sending a certain number of requests and calculating each time the time to execute all of them

The experience is repeated using 5, 10, 100, 200, 500, 1000, 2000, 3000, 5000 Samples.

First method --> naive          Second method --> Tree

The time is displayed by seconds.

![image](https://user-images.githubusercontent.com/71329302/183301686-bcca549f-854f-4238-942d-8d4c4d7a922e.png)

![image](https://user-images.githubusercontent.com/71329302/183301710-fdd94c42-2747-4b4f-8cd0-e9a781884807.png)

Example with 5000 input query: time to execute all the queries:

* first method (naive) : ~184s

* Second method (tree) : ~20s

The second method may be improved by storing the different sentences in the tree decreasingly according to the count value which allows get the 10 most typed queries without having to loop over all the queries starting with the prefix input.

We can also define a new rule to prioritize and manage the case where our result list of suggested queries contains queries with the same number of time typed.

To implement an <em> approximate suggestion </em> logic by measuring the similarity between the input query and the queries stored and defining a threshold to determine whether the words can be considered as similar or not 
