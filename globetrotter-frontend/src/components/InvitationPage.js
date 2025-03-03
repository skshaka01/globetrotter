import React, { useEffect, useState } from 'react';
import { getUserDetails } from '../api';
import { Box, Typography, CircularProgress, Button } from '@mui/material';
import { useSearchParams, useNavigate } from 'react-router-dom';

const InvitationPage = ({ inviterUsername: inviterProp }) => {
  const [searchParams] = useSearchParams();
  // Use the prop if available, otherwise extract from URL.
  const inviterUsername = inviterProp || searchParams.get('user');
  const [inviterDetails, setInviterDetails] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchInviterDetails = async () => {
      if (inviterUsername) {
        setLoading(true);
        try {
          const response = await getUserDetails(inviterUsername);
          setInviterDetails(response.data);
        } catch (err) {
          console.error('Error fetching inviter details:', err);
          setError('Unable to fetch inviter details.');
        }
        setLoading(false);
      }
    };

    fetchInviterDetails();
  }, [inviterUsername]);

  const handleJoinGame = () => {
    // Navigate to the game page ("/")
    navigate('/');
  };

  if (loading) {
    return <CircularProgress />;
  }

  if (error) {
    return <Typography variant="body1">{error}</Typography>;
  }

  return (
    <Box sx={{ p: 2 }}>
      <Typography variant="h4" gutterBottom>
      🧩Welcome to Globetrotter Challenge! - The Ultimate Travel Guessing Game!
      </Typography>
      {inviterDetails ? (
        <Box>
          <Typography variant="h6">
            {inviterDetails.username} has invited you to play!
          </Typography>
          <Typography variant="body1">
            Their current score is:
          </Typography>
          <Typography variant="body1">
            Correct: {inviterDetails.scoreCorrect} | Incorrect: {inviterDetails.scoreIncorrect}
          </Typography>
          <Typography variant="body2" sx={{ mt: 2 }}>
            Join the game now and try to beat their score!
          </Typography>
          <Button variant="contained" color="primary" sx={{ mt: 2 }} onClick={handleJoinGame}>
            Join Game
          </Button>
        </Box>
      ) : (
        <Typography variant="body1">
          No inviter details available.
        </Typography>
      )}
    </Box>
  );
};

export default InvitationPage;
