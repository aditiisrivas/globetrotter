import React, { useState, FormEvent } from "react";
import Game from "./Game";
import Scoreboard from "./Scoreboard";
import ChallengeFriend from "./ChallengeFriend";
import axios from "axios";

interface Score {

    correct: number;
    incorrect: number;

}

function App() {
    const [score, setScore] = useState<Score>({ correct: 0, incorrect: 0 });
    const [username, setUsername] = useState<string>("");
    const [checkUser, setCheckUser] = useState<boolean>(false);
    const updateScore = (isCorrect: boolean) => {
        setScore((prevScore) => ({
            correct: isCorrect ? prevScore.correct + 1 : prevScore.correct,
            incorrect: isCorrect ? prevScore.incorrect : prevScore.incorrect + 1,
        }));
    };
    const handleRegister = async (event: FormEvent) => {
        event.preventDefault();
        try {
            const resp = await axios.get("/api/user/check-user", { params: { username } });
            setCheckUser(resp.data);
            if (resp.data === false) {
                await axios.post("/api/user/register", null, { params: { username } });
                setCheckUser(true);
            }
        } catch (error) {
            console.error("Error registering user:", error);
        }
    };

    return (
        <div className="min-h-screen flex flex-col items-center justify-center p-6 bg-gradient-to-br from-[#1a1a2e] via-[#16213e] to-[#0f3460] text-white font-sans">
            <h1 className="text-5xl font-extrabold mb-10 tracking-wide text-transparent bg-clip-text bg-gradient-to-r from-yellow-400 to-red-500 animate-pulse">
                Globetrotter Challenge
            </h1>

            {checkUser ? (
                <div className="w-full max-w-lg space-y-8 p-6 bg-white bg-opacity-10 backdrop-blur-lg rounded-2xl shadow-lg">
                    <Scoreboard score={score} />
                    <Game updateScore={updateScore} username={username} />
                    <ChallengeFriend username={username} score={score} />
                </div>
            ) : (
                <form
                    className="flex flex-col items-center p-8 rounded-2xl shadow-2xl w-full max-w-lg bg-white bg-opacity-10 backdrop-blur-lg"
                    onSubmit={handleRegister}
                >
                    <input
                        type="text"
                        placeholder="Enter Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="border p-4 rounded-xl mb-6 w-full bg-transparent text-white placeholder-gray-300 text-lg border-gray-400 focus:outline-none focus:ring-2 focus:ring-yellow-400"
                    />
                    <button
                        type="submit"
                        onClick={handleRegister}
                        className="w-full py-4 rounded-full text-lg font-semibold text-white bg-gradient-to-r from-blue-500 to-green-500 hover:from-green-500 hover:to-blue-500 transform transition duration-300 hover:scale-105 shadow-lg"
                    >
                        Register
                    </button>
                </form>
            )}
        </div>
    );
}

export default App;