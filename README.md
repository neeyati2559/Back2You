# Back2You: Campus Lost & Found Matching System

A terminal-based Core Java project (OOP) that records lost and found items on campus
and suggests likely matches.

## Features
- Report lost and found items (with owner/finder contact)
- Search by category, location or keyword
- Automatic match score out of 100 (category, location, date, description)
- Data saved to items.txt
- Input validation with a custom exception

## OOP concepts
Encapsulation, inheritance (Item -> LostItem, FoundItem), abstraction (abstract class
and interface), polymorphism (method overriding), enum, collections, exception
handling, file handling.

## How to run
1. Open the project in IntelliJ IDEA (JDK 17 or higher).
2. Run `back2you.Main`.