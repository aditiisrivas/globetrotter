import React from 'react';
import Confetti from 'react-confetti';

interface FeedbackProps {
    feedback: { correct: boolean; funFact: string };
}

function Feedback({ feedback }: FeedbackProps) {
    return (
        <div className="mt-4">
            {feedback.correct ? (
                <>
                    <Confetti width={window.innerWidth} height={window.innerHeight} />
                    <p className="text-green-600 font-semibold">🎉 Correct! {feedback.funFact}</p>
                </>
            ) : (
                <p className="text-red-600 font-semibold">😢 Incorrect! {feedback.funFact}</p>
            )}
        </div>
    );
}

export default Feedback;