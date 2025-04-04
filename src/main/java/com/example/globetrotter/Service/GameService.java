//package com.example.globetrotter.Service;
//
//import com.example.globetrotter.Model.User;
//import com.example.globetrotter.repository.DestinationRepository;
//import com.example.globetrotter.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import com.example.globetrotter.Model.Destination;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class GameService {
//    private final DestinationRepository destinationRepository;
//    private final UserRepository userRepository;
//
//    public Map<String, Object> getNextQuestion() {
//        List<Destination> destinations = destinationRepository.findAll();
//        if (destinations.isEmpty()) {
//            throw new RuntimeException("No destinations available");
//        }
//
//        Destination randomDestination = destinations.get(new Random().nextInt(destinations.size()));
//
//        // Generate multiple-choice options (including the correct one)
//        List<String> allCities = destinations.stream().map(Destination::getCity).toList();
//        List<String> options = generateOptions(randomDestination.getCity(), allCities);
//
//        // Prepare response
//        Map<String, Object> response = new HashMap<>();
//        response.put("clues", randomDestination.getClues());
//        response.put("options", options);
//        response.put("correctAnswer", randomDestination.getCity());
//        response.put("destinationId", randomDestination.getId());
//
//        return response;
//    }
//
//    private List<String> generateOptions(String correctAnswer, List<String> allCities) {
//        List<String> options = new ArrayList<>();
//        options.add(correctAnswer);
//
//
//        List<String> mutableCities = new ArrayList<>(allCities);
//        Collections.shuffle(mutableCities);
//
//        for (String city : mutableCities) {
//            if (!city.equals(correctAnswer) && options.size() < 4) {
//                options.add(city);
//            }
//        }
//
//
//        while (options.size() < 4 && mutableCities.size() > options.size()) {
//            String randomCity = mutableCities.get(new Random().nextInt(mutableCities.size()));
//            if (!options.contains(randomCity)) {
//                options.add(randomCity);
//            }
//        }
//
//        Collections.shuffle(options);
//        return options;
//    }
//
//
//
//    public Map<String, Object> validateAnswer(Long destinationId, String answer) {
////        Destination destination = destinationRepository.findById(destinationId)
////                .orElseThrow(() -> new RuntimeException("Destination not found"));
//
//        Optional<Destination> optionalDestination = destinationRepository.findById(destinationId);
//        if (optionalDestination.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Destination ID");
//        }
//        Destination destination = optionalDestination.get();
//
//        boolean isCorrect = destination.getCity().equalsIgnoreCase(answer);
//
//
//        List<String> funFacts = destination.getFunFacts();
//        String funFactMessage = (funFacts != null && !funFacts.isEmpty())
//                ? funFacts.get(new Random().nextInt(funFacts.size()))
//                : "Fun fact unavailable for this destination.";
//
//        // Prepare the response
//        Map<String, Object> response = new HashMap<>();
//        response.put("correct", isCorrect);
//        response.put("funFact", funFactMessage);
//
//        return response;
//    }
//
//
//    public int updateScore(String username, boolean correct) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (correct) user.setScore(user.getScore() + 10);
//        userRepository.save(user);
//
//        return user.getScore();
//    }
//
//}

package com.example.globetrotter.Service;

import com.example.globetrotter.Model.User;
import com.example.globetrotter.repository.DestinationRepository;
import com.example.globetrotter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.globetrotter.Model.Destination;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;
    private final MapboxService mapboxService;

    public Map<String, Object> getNextQuestion() {
        List<Destination> destinations = destinationRepository.findAll();
        if (destinations.isEmpty()) {
            throw new RuntimeException("No destinations available");
        }

        Destination randomDestination = destinations.get(new Random().nextInt(destinations.size()));

        // Generate multiple-choice options (including the correct one)

       // List<String> allCities = destinations.stream().map(Destination::getCity).toList();
        //List<String> options = generateOptions(randomDestination.getCity(), allCities);

        List<Map<String, Object>> options = generateOptions(randomDestination, destinations);


        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("clues", randomDestination.getClues());
        response.put("options", options);
        response.put("correctAnswer", randomDestination.getCity());
        response.put("destinationId", randomDestination.getId()); // ✅ Ensure ID is Long

        return response;
    }

//    private List<String> generateOptions(String correctAnswer, List<String> allCities) {
//        List<String> options = new ArrayList<>();
//        options.add(correctAnswer);
//
//        List<String> mutableCities = new ArrayList<>(allCities);
//        Collections.shuffle(mutableCities);
//
//        for (String city : mutableCities) {
//            if (!city.equals(correctAnswer) && options.size() < 4) {
//                options.add(city);
//            }
//        }
//
//        while (options.size() < 4 && mutableCities.size() > options.size()) {
//            String randomCity = mutableCities.get(new Random().nextInt(mutableCities.size()));
//            if (!options.contains(randomCity)) {
//                options.add(randomCity);
//            }
//        }
//
//        Collections.shuffle(options);
//        return options;
//    }


    private List<Map<String, Object>> generateOptions(Destination correctDestination, List<Destination> allDestinations) {
        List<Map<String, Object>> options = new ArrayList<>();

//        options.add(getLocationData(correctDestination.getCity()));
//
//        List<Destination> mutableDestinations = new ArrayList<>(allDestinations);
//        Collections.shuffle(mutableDestinations);


        Set<String> selectedCities = new HashSet<>();

        options.add(mapboxService.getLocationData(correctDestination.getCity()));
        selectedCities.add(correctDestination.getCity());

        List<Destination> shuffledDestinations = new ArrayList<>(allDestinations);
        Collections.shuffle(shuffledDestinations);


//        for (Destination destination : mutableDestinations) {
//            if (!destination.getCity().equals(correctDestination.getCity()) && options.size() < 4) {
//                options.add(getLocationData(destination.getCity()));
//            }
//        }
//
//        while (options.size() < 4 && mutableDestinations.size() > options.size()) {
//            Destination randomDestination = mutableDestinations.get(new Random().nextInt(mutableDestinations.size()));
//            if (!options.contains(getLocationData(randomDestination.getCity()))) {
//                options.add(getLocationData(randomDestination.getCity()));
//            }
//        }
//
//        Collections.shuffle(options);
//        return options;
//    }

        for (Destination destination : shuffledDestinations) {
            if (options.size() < 4 && !selectedCities.contains(destination.getCity())) {
                options.add(mapboxService.getLocationData(destination.getCity()));
                selectedCities.add(destination.getCity());
            }
        }

        Collections.shuffle(options);
        return options;
    }





    public Map<String, Object> validateAnswer(UUID destinationId, String answer) { 
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Destination ID"));

        boolean isCorrect = destination.getCity().equalsIgnoreCase(answer);

        List<String> funFacts = destination.getFunFacts();
        String funFactMessage = (funFacts != null && !funFacts.isEmpty())
                ? funFacts.get(new Random().nextInt(funFacts.size()))
                : "Fun fact unavailable for this destination.";

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("correct", isCorrect);
        response.put("funFact", funFactMessage);

        return response;
    }

    public int updateScore(String username, boolean correct) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (correct) user.setScore(user.getScore() + 10);
        userRepository.save(user);

        return user.getScore();
    }
}


