import React, { useState, useEffect , useRef} from 'react';
import axios from 'axios';
import Feedback from './Feedback';
import mapboxgl from 'mapbox-gl';

mapboxgl.accessToken = 'pk.eyJ1IjoiYWRpdHRpaWkiLCJhIjoiY205MTY0eXhpMHBnMzJpc2FjbjcxbnZjNCJ9.6YxEjoHRrM4S3bTYAooI3Q';

interface Option {
    city: string;
    latitude: number;
    longitude: number;
}

interface Question {
    clues: string[];
    options: Option[];
    destinationId: string;
}

interface FeedbackData {
    correct: boolean;
    funFact: string;
}

interface GameProps {
    updateScore: (isCorrect: boolean) => void;
    username: string;
}

function Game({ updateScore, username }: GameProps) {
    const [question, setQuestion] = useState<Question | null>(null);
    const [guess, setGuess] = useState<string>('');
    const [feedback, setFeedback] = useState<FeedbackData | null>(null);
    const mapContainerRef = useRef<HTMLDivElement | null>(null);
    const mapInstance = useRef<mapboxgl.Map | null>(null);

    useEffect(() => {
        fetchQuestion();
    }, []);



    useEffect(() => {
        if (question && mapContainerRef.current) {

            if (mapInstance.current) {
                mapInstance.current.remove();
                mapInstance.current = null;
            }

            const validOptions = question.options.filter(option =>
                !isNaN(option.longitude) && !isNaN(option.latitude)
            );

            if (validOptions.length === 0) {
                console.warn("No valid coordinates found.");
                return;
            }

            const centerLngLat: [number, number] = [validOptions[0].longitude, validOptions[0].latitude];
            console.log("Map Center:", centerLngLat);

            // Create a new Mapbox instance
            mapInstance.current = new mapboxgl.Map({
                container: mapContainerRef.current,
                style: 'mapbox://styles/mapbox/streets-v11',
                center: centerLngLat,
                zoom: 4
            });

            mapInstance.current.on('load', () => {
                mapInstance.current?.resize();

                if (validOptions.length > 1) {
                    const bounds = new mapboxgl.LngLatBounds();
                    validOptions.forEach(option => bounds.extend([option.longitude, option.latitude]));
                    mapInstance.current?.fitBounds(bounds, { padding: 50 });
                }
            });

            validOptions.forEach(option => {
                console.log(`Placing marker at: ${option.city} (${option.longitude}, ${option.latitude})`);
                new mapboxgl.Marker()
                    .setLngLat([option.longitude, option.latitude])
                    .setPopup(new mapboxgl.Popup().setHTML(`<h3>${option.city}</h3>`))
                    .addTo(mapInstance.current as mapboxgl.Map);
            });
        }
    }, [question]);



    const fetchQuestion = async () => {
        try {
            const response = await axios.get('/api/game/next-question');
            setQuestion(response.data);
            setGuess('');
            setFeedback(null);
        } catch (error) {
            console.error('Error fetching question:', error);
        }
    };

    const handleGuess = async () => {
        try {
            if (!question) return;

            const response = await axios.post('/api/game/validate-answer',// Empty body
               {
                    destinationId: question.destinationId,
                    answer: guess,
                }
            );

            setFeedback(response.data);

            await axios.post('/api/game/track-score',
                {

                    username: username,
                    correct: Boolean(feedback?.correct),
                },
            );

            updateScore(response.data.correct);
        } catch (error) {
            console.error('Error validating answer:', error);
        }
    };

    return (
        <div className="p-4">
            <div ref={mapContainerRef} id="map-container"
                 className="w-full md:w-3/4 lg:w-1/2 h-96 mx-auto mb-4 rounded-lg shadow-lg"></div>

            {question && (
                <>
                    <p className="mb-4 text-lg font-semibold">{question.clues.join(', ')}</p>
                    <div className="flex flex-wrap gap-2 mb-4">
                        {question.options.map((option) => (
                            <button
                                key={option.city}
                                onClick={() => setGuess(option.city)}
                                //     className="bg-gray-200 hover:bg-gray-300 p-2 rounded"
                                // >
                                className={`p-2 rounded ${
                                    guess === option.city ? 'bg-blue-600 text-white' : 'bg-gray-200 hover:bg-gray-300'
                                }`}
                            >
                                {option.city}
                            </button>
                        ))}
                    </div>
                    <button onClick={handleGuess}
                            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mr-2">
                        Guess
                    </button>
                    <button onClick={fetchQuestion}
                            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                        Next Question
                    </button>
                    {feedback && <Feedback feedback={feedback}/>}
                </>
            )}
        </div>
    );
}

export default Game;