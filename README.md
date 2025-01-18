# Software Quality Engineering - System Testing
This is a repository for the system-testing assignment of the Software Quality Engineering course at the [Ben-Gurion University](https://in.bgu.ac.il/), Israel.

## Assignment Description
In this assignment, we tested an open-source software called "opencart" (https://github.com/opencart/opencart).


## Installation
Install Selenium, Provengo and Cucamber and run them according to the readmes.

## What we tested
We tested adding to cart two items, while an admin changes stock quantity to 1. We chose to test the following user stories: 

*User story:* A user adds two items two cart

*Preconditions:* The item has at least 2 in stock

*Expected outcome:* Two items will be in cart

*User story:* An admin changes quantity of an item to 1

*Preconditions:* The item exists beforehand

*Expected outcome:* The quantity will be 1


## How we tested
We used two different testing methods:
1. [Cucumber](https://cucumber.io/), a behavior-driven testing framework.
2. [Provengo](https://provengo.tech/), a story-based testing framework.

Each of the testing methods is elaborated in its own directory. 

