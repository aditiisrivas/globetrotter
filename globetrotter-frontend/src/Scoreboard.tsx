import React from 'react';

interface ScoreboardProps {
    score: { correct: number; incorrect: number };
}

function Scoreboard({ score }: ScoreboardProps) {
    return (
        <div className="mb-4">
            <p>Correct: {score.correct}</p>
            <p>Incorrect: {score.incorrect}</p>
        </div>
    );
}

export default Scoreboard;