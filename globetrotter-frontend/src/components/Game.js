import React, { useEffect, useState } from 'react';
import { getRandomQuestion, submitAnswer } from '../api';
import Confetti from 'react-confetti';
import { Box, Button, Typography, CircularProgress, Grid } from '@mui/material';

const Game = () => {
  const [question, setQuestion] = useState(null);
  const [selectedAnswer, setSelectedAnswer] = useState('');
  const [feedback, setFeedback] = useState('');
  const [funFactText, setFunFactText] = useState('');
  const [score, setScore] = useState({ correct: 0, incorrect: 0 });
  const [loading, setLoading] = useState(false);
  const [showConfetti, setShowConfetti] = useState(false);

  useEffect(() => {
    fetchRandomQuestion();
  }, []);

  useEffect(() => {
    localStorage.setItem("anonymousScore", JSON.stringify(score));
  }, [score]);

  const fetchRandomQuestion = async () => {
    setLoading(true);
    setFeedback('');
    setSelectedAnswer('');
    setFunFactText('');
    setShowConfetti(false);
    try {
      const response = await getRandomQuestion();
      const data = response.data;
      console.log("Fetched question:", data);
      setQuestion(data);
    } catch (error) {
      console.error("Error fetching question:", error);
    }
    setLoading(false);
  };

  const handleAnswerClick = async (option) => {
    setSelectedAnswer(option);
    try {
      const response = await submitAnswer(question.id, option);
      const isCorrect = response.data.correct;
      console.log("Fetched Response:", response.data);
      setFeedback(response.data.message);
      const funFact = response.data.funFact || response.data.fun_fact;
      setFunFactText(funFact ? funFact : 'No fun fact available.');
      if (isCorrect) {
        setScore(prev => ({ ...prev, correct: prev.correct + 1 }));
        setShowConfetti(true);
      } else {
        setScore(prev => ({ ...prev, incorrect: prev.incorrect + 1 }));
      }
    } catch (error) {
      console.error("Error submitting answer:", error);
    }
  };

  const handleNext = () => {
    fetchRandomQuestion();
  };

  return (
    <Box sx={{ p: 2 }}>
      <Typography variant="h4" gutterBottom>Guess The Destination</Typography>
      {loading ? (
        <CircularProgress />
      ) : question ? (
        <Box>
          <Typography variant="h6">Clues:</Typography>
          {question.clues.map((clue, index) => (
            <Typography key={index} variant="body1">{clue}</Typography>
          ))}
          <Typography variant="h6" sx={{ mt: 2 }}>Select the correct destination:</Typography>
          <Grid container spacing={2} sx={{ mt: 1 }}>
            {question.options.map((option, index) => (
              <Grid item key={index}>
                <Button
                  variant="contained"
                  color={selectedAnswer === option ? 'secondary' : 'primary'}
                  onClick={() => handleAnswerClick(option)}
                  disabled={selectedAnswer !== ''}
                >
                  {option}
                </Button>
              </Grid>
            ))}
          </Grid>
          {feedback && (
            <Box sx={{ mt: 2 }}>
              <Typography variant="body1">{feedback}</Typography>
              <Typography variant="body2" sx={{ fontStyle: 'italic', mt: 1 }}>
                Fun Fact: {funFactText}
              </Typography>
            </Box>
          )}
          <Button variant="outlined" onClick={handleNext} sx={{ mt: 3 }}>
            Next
          </Button>
          <Box sx={{ mt: 3 }}>
            <Typography variant="h6">Score:</Typography>
            <Typography variant="body1">Correct: {score.correct}</Typography>
            <Typography variant="body1">Incorrect: {score.incorrect}</Typography>
          </Box>
          {showConfetti && <Confetti />}
        </Box>
      ) : (
        <Typography>No question available.</Typography>
      )}
    </Box>
  );
};

export default Game;