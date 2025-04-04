
# Globetrotter

## Overview

Globetrotter is more than just a quiz game – it’s an exciting way to test your geography knowledge, challenge friends, and learn fun facts about amazing places! Whether you're a seasoned traveler or just curious about the world, this game keeps you engaged by presenting clues about famous destinations and letting you guess the right one.

## Features

- **Join & Invite Friends**: Create an account, get a unique invite code, and challenge friends to see who knows the world best!
- **Trivia Fun**: Get a random destination, read the clues, and choose the correct city from multiple options.
- **Score & Compete**: Earn points for correct answers and track your progress over time.
- **Dynamic & Engaging**: Each question is generated dynamically with unique clues and randomized answer choices.
- **Discover New Places**: Even if you answer wrong, you still get to learn a fun fact about the location!

## How It Works

### User Management

When you sign up, our system:

1. Creates your profile with a unique username and a starting score of 0.
2. Generates an invite code so you can bring your friends into the challenge.
3. Stores your details securely so you can keep playing anytime.

When you invite a friend, we generate a special shareable link along with a custom image using Cloudinary, showcasing your current score. It’s a cool way to show off and encourage some friendly competition!

### The Quiz Experience

Our `GameService` makes sure every round feels fresh and challenging. Here’s what happens:

- We randomly pick a destination from our database.
- You get clues about the location (e.g., famous landmarks, history, or culture).
- Four answer choices appear – one correct and three carefully chosen incorrect options.
- The system shuffles them to keep things unpredictable!

### Answering & Scoring

- If you get it **right**, you earn 10 points and move up in rankings.
- If you get it **wrong**, no worries! You’ll still get a fun fact about the place.
- Scores are saved instantly, so you can keep track of your progress.

## Building This Game – The Thought Process

Creating Globetrotter wasn’t just about coding; it was about crafting an engaging experience. Here’s what went into it:

- **Smart Data Handling**: Using Spring Boot’s JPA repositories, we efficiently manage user and game data.
- **Ensuring Fair Play**: Randomization ensures that questions are unique, and multiple-choice options are shuffled dynamically.
- **Security Matters**: We validate all inputs to prevent users from manipulating scores or tampering with invite codes.
- **Performance Optimization**: The API responses are structured for speed and efficiency, ensuring smooth gameplay.
- **Future-Proofing**: The modular structure makes it easy to add features like leaderboards, achievements, or even new types of questions in the future!

## Running the Game Locally

### What You’ll Need

- Java 17+
- Maven
  -MongoDb
  -React

### Getting Started

1. Clone the repository:
   ```sh
   git clone https://github.com/aditiisrivas/globetrotter.git
