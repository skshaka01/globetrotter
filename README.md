# Globetrotter Challenge

🧩 The Globetrotter Challenge is a full-stack web application that challenges users to guess a famous travel destination from cryptic clues. Once a user guesses correctly, they unlock fun facts about that destination. The application also includes a "Challenge a Friend" feature, allowing users to register, share their scores, and invite friends to play.

## Table of Contents

- [Overview](#overview)
- [Tech Stack & Choices](#tech-stack--choices)
- [Features](#features)

## Overview

Globetrotter Challenge is designed to provide a fun, engaging, and secure gaming experience by combining a Spring Boot backend with a React frontend. The backend is built using Java, Spring Boot, Maven, and MySQL (hosted on Railway), while the frontend is a React app styled with Material-UI and deployed on Netlify.

## Tech Stack & Choices

- **Backend:**
  - **Spring Boot:** Provides a robust and scalable framework for building RESTful APIs.
  - **Maven:** Manages dependencies and builds the Java application.
  - **MySQL:** Stores game data, user scores, and destination details. Deployed using a free service like Railway.
  - **Hibernate/JPA:** Handles object-relational mapping and database operations.
  - **ClearDB/Railway MySQL:** Free database hosting solution.

- **Frontend:**
  - **React:** Creates a dynamic, single-page application (SPA) for a responsive user experience.
  - **Material-UI:** Provides pre-built UI components and consistent styling.
  - **Axios:** Handles API requests from the frontend to the backend.
  - **Netlify:** Free hosting for static sites, ideal for deploying React apps.

## Features

- **Random Destination Guessing Game:**
  - Fetches random clues and multiple-choice options from the backend.
  - Provides immediate feedback with fun animations (e.g., confetti on correct answers).
  - Tracks and displays user scores.

- **Challenge a Friend:**
  - Users can register with a unique username.
  - The registration merges anonymous game scores.
  - Generates a dynamic invitation link that can be shared via WhatsApp.
  - Invited friends can see the inviter's score and join the game.