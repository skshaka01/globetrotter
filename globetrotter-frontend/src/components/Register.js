import React, { useState } from 'react';
import { registerUser, getInvitationLink } from '../api';
import { Box, Button, TextField, Typography, Link } from '@mui/material';

const Register = () => {
  const [username, setUsername] = useState('');
  const [registrationFeedback, setRegistrationFeedback] = useState('');
  const [inviteLink, setInviteLink] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    // Retrieve anonymous scores from local storage
    const storedScore = localStorage.getItem("anonymousScore");
    const score = storedScore ? JSON.parse(storedScore) : { correct: 0, incorrect: 0 };

    const payload = {
      username: username,
      scoreCorrect: score.correct,
      scoreIncorrect: score.incorrect,
    };

    console.log("Registration payload:", payload);

    try {
      const response = await registerUser(payload);
      setRegistrationFeedback(`User ${response.data.username} registered successfully!`);
    } catch (error) {
      console.error('Registration error:', error);
      // Check if backend returned a message for duplicate username
      if (error.response && error.response.data && error.response.data.message) {
        setRegistrationFeedback(error.response.data.message);
      } else {
        setRegistrationFeedback('Registration failed. Please try again.');
      }
    }
  };

  const handleChallenge = async () => {
    try {
      const response = await getInvitationLink(username);
      setInviteLink(response.data.invitationLink);
      const whatsappMessage = `Can you beat my score? let's find out: ${response.data.invitationLink}`;
      const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(whatsappMessage)}`;
      window.open(whatsappUrl, '_blank');
    } catch (error) {
      console.error('Error generating invitation link:', error);
    }
  };

  return (
    <Box sx={{ p: 2, mt: 4, borderTop: '1px solid #ccc' }}>
      <Typography variant="h5" gutterBottom>Challenge a Friend</Typography>
      <form onSubmit={handleRegister}>
        <TextField
          label="Enter a unique username"
          variant="outlined"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          inputProps={{ minLength: 3 }}
          sx={{ mr: 2 }}
        />
        <Button type="submit" variant="contained" color="primary">
          Register
        </Button>
      </form>
      {registrationFeedback && (
        <Typography variant="body1" sx={{ mt: 2 }}>
          {registrationFeedback}
        </Typography>
      )}
      {registrationFeedback && (
        <Button variant="outlined" onClick={handleChallenge} sx={{ mt: 2 }}>
          Invite Friend
        </Button>
      )}
      {inviteLink && (
        <Typography variant="body2" sx={{ mt: 1 }}>
          Invitation Link: <Link href={inviteLink} target="_blank" rel="noopener noreferrer">{inviteLink}</Link>
        </Typography>
      )}
    </Box>
  );
};

export default Register;