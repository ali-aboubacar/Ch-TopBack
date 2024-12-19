package com.chaTop.Backend.service;

import com.chaTop.Backend.model.User;
import com.chaTop.Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class de service pour la gestion des utilisateurs.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * Verifie l'existance du nom dans le system.
     * @param name le nom a verifier.
     * @return true ou false apres verification
     */
    public Boolean findByName(String name){
        return userRepository.existsByName(name);
    }

    /**
     * Verifie l'existance de l'email dans le system.
     * @param email l'email a verifier.
     * @return true ou false apres verification
     */
    public Boolean findByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    /**
     * Creation d'un utilisateur.
     * @param user utilisateur a creer.
     * @return l'utilisateur creer.
     */
    public User createUser(User user){
        return userRepository.save(user);
    }
    /**
     * Recupere un seul utilisateur grace a son identifiant.
     * @param id l'identifiant de l'utilisateur.
     * @return un utilisateur.
     */
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
}
