package ru.miro.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.miro.user_service.dto.UserDTO;
import ru.miro.user_service.exception.UserNotFoundException;
import ru.miro.user_service.mapper.UserMapper;
import ru.miro.user_service.model.User;
import ru.miro.user_service.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(long id) {
        return usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findOne(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional
    public void save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        enrichCreatedUser(user);

        usersRepository.save(user);
    }

    // TODO: invalid data exception handler
    @Transactional
    public void update(long id, UserDTO userDTO) {
        Optional<User> user = usersRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        User updatedUser = userMapper.updateFromDto(userDTO, user.get());
        enrichUpdatedUser(updatedUser);

        usersRepository.save(updatedUser);
    }

    @Transactional
    public void delete(long id) {
        if (usersRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }

        usersRepository.deleteById(id);
    }

    private void enrichCreatedUser(User user) {
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());
    }

    private void enrichUpdatedUser(User user) {
        user.setUpdatedAt(System.currentTimeMillis());
    }

}
