import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || '/api';

// For debugging only:
export const getRandomDestination = () => axios.get(`${API_BASE_URL}/destinations/random`);

export const getRandomQuestion = () => axios.get(`${API_BASE_URL}/destinations/randomQuestion`);

export const getAllDestinations = () => axios.get(`${API_BASE_URL}/destinations/all`);

export const submitAnswer = (destinationId, userGuess, username = '') =>
  axios.post(`${API_BASE_URL}/destinations/answer`, { destinationId, userGuess, username });

export const registerUser = (userData) =>
  axios.post(`${API_BASE_URL}/users/register`, userData);

export const getInvitationLink = (username) =>
  axios.get(`${API_BASE_URL}/users/invite`, { params: { username } });

export const getUserDetails = (username) =>
  axios.get(`${API_BASE_URL}/users/${username}`);
