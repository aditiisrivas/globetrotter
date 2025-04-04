import React, {JSX, useState} from 'react';
import axios from 'axios';
import { FaShareAlt } from 'react-icons/fa';
import { FaLink } from 'react-icons/fa';
import { FaImage } from 'react-icons/fa';

interface ChallengeFriendProps {
    username: string;
    score: { correct: number; incorrect: number };
}

function ChallengeFriend({ username, score }: ChallengeFriendProps) {
    const [inviteDetails, setInviteDetails] = useState<{ inviteLink: string; inviteImage: string } | null>(null);
    const [showPopup, setShowPopup] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleChallenge = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get('/api/user/invite-details', { params: { username: username } });
            setInviteDetails(response.data);
            setShowPopup(true);
        } catch (err) {
            setError('Failed to fetch invite details. Please try again.');
            console.error('Error getting invite details:', err);
        } finally {
            setLoading(false);
        }
    };

    const closePopup = () => {
        setShowPopup(false);
        setInviteDetails(null);
    };

    return (
        <div className="mb-6">
            <button
                onClick={handleChallenge}
                className="bg-gradient-to-r from-purple-500 to-indigo-600 hover:from-indigo-600 hover:to-purple-500 text-white font-semibold py-3 px-6 rounded-full transition duration-300 ease-in-out flex items-center"
                disabled={loading}
            >
                {(FaShareAlt as unknown as JSX.Element)}
                {loading ? 'Loading...' : 'Challenge a Friend'}
            </button>

            {showPopup && inviteDetails && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white rounded-xl shadow-lg p-8 w-full max-w-md">
                        <h2 className="text-2xl font-semibold mb-6 text-gray-800">Share Your Invite</h2>
                        {error && <p className="text-red-500 mb-4">{error}</p>}
                        <div className="mb-4 flex items-center">
                            {(FaLink as unknown as JSX.Element)}
                            <input
                                type="text"
                                value={inviteDetails.inviteLink || ''}
                                readOnly
                                className="border p-2 rounded w-full text-sm text-gray-700"
                            />
                        </div>
                        <div className="mb-4 flex items-center">
                            {(FaImage as unknown as JSX.Element)}
                            <a
                                href={inviteDetails.inviteImage || ''}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="text-blue-600 hover:underline text-sm"
                            >
                                View Invite Image
                            </a>
                        </div>
                        <div className="flex justify-end">
                            <button
                                onClick={closePopup}
                                className="bg-gray-300 hover:bg-gray-400 text-gray-700 font-semibold py-2 px-4 rounded"
                            >
                                Close
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default ChallengeFriend;