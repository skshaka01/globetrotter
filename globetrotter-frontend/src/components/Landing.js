import React from 'react';
import { Container, Typography, Box } from '@mui/material';
import Game from './Game';
import Register from './Register';

const Landing = () => {
  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Typography variant="h3" gutterBottom align="center">
        🧩 The Globetrotter Challenge - The Ultimate Travel Guessing Game!
      </Typography>
      <Box sx={{ mb: 4 }}>
        <Game />
      </Box>
      <Box sx={{ mb: 4 }}>
        <Register />
      </Box>
    </Container>
  );
};

export default Landing;
