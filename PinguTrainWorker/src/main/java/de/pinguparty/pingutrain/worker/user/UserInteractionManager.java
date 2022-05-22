package de.pinguparty.pingutrain.worker.user;

import de.pinguparty.pingutrain.worker.exception.UserNotFoundException;
import de.pinguparty.pingutrain.worker.interactions.Interaction;
import de.pinguparty.pingutrain.worker.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInteractionManager {

    @Autowired
    private UserRepository userRepository;

    public Optional<Interaction> getCurrentUserInteraction(long userID) {
        //Retrieve possibly existing user
        Optional<User> userOptional = userRepository.findById(userID);

        //Check if user exists
        if (userOptional.isEmpty()) return Optional.empty();

        //Extract current interaction from user
        Interaction currentInteraction = userOptional.get().getCurrentInteraction();

        //Check interaction
        if (currentInteraction == null) {
            return Optional.empty();
        }
        return Optional.of(currentInteraction);
    }

    public User resetCurrentUserInteraction(long userID) {
        try {
            return setCurrentUserInteraction(userID, null);
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    public User setCurrentUserInteraction(long userID, Interaction interaction) throws UserNotFoundException {
        //Retrieve possibly existing user
        Optional<User> userOptional = userRepository.findById(userID);

        //Check if user exists
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(String.format("User %s does not exist!", userID));
        }

        //Get user and update current interaction
        User user = userOptional.get().setCurrentInteraction(interaction).updateLastAction();

        //Update user entity in repository
        return userRepository.save(user);
    }

    /**
     * Updates the information about a certain user. In case the user is unknown, a corresponding new {@link User}
     * entity will be created.
     *
     * @param userID    The ID of the user to update
     * @param chatID    The latest chat ID of the user
     * @param firstName The first name of the user
     * @param lastName  The last name of the user
     * @return The updated {@link User}
     */
    public User updateUserInfo(long userID, long chatID, String firstName, String lastName) {
        //Retrieve possibly existing user
        Optional<User> userOptional = userRepository.findById(userID);

        //Check if user already exists
        if (userOptional.isPresent()) {
            //Retrieve user entity and update fields
            User oldUser = userOptional.get()
                    .setChatID(chatID)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .updateLastAction();

            //Update entity in repository
            return userRepository.save(oldUser);
        }

        //Create new user entity
        User newUser = new User().setUserID(userID).setChatID(chatID).setFirstName(firstName).setLastName(lastName)
                .updateLastAction();

        //Insert user entity into repository
        return userRepository.insert(newUser);
    }
}
