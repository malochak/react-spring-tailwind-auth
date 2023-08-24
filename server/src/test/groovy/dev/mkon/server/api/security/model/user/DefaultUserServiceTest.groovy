package dev.mkon.server.api.security.model.user

import dev.mkon.server.api.dto.user.CreateUserDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Subject

class DefaultUserServiceTest extends Specification {

    String DUMMY_EMAIL = "dummy@email.com"

    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()

    @Subject
    UserService userService = new DefaultUserService(userRepository, passwordEncoder)

    def "given existing username (email) when loadByUserName should return User"() {
        given:
            String username = DUMMY_EMAIL
            User user = new User("dummy_id", username, "dummy_password")

        when:
            UserDetails result = userService.loadUserByUsername(username)

        then:
            1 * userRepository.findByEmail(username) >> Optional.of(user)
            result == user
    }

    def "given not existing username (email) when loadByUserName should return User"() {
        given:
            String username = DUMMY_EMAIL

        when:
            userService.loadUserByUsername(username)

        then:
            thrown(UsernameNotFoundException)
            1 * userRepository.findByEmail(username) >> Optional.empty()
    }

    def "given valid CreateUserDto when create then user is created"() {
        given:
            CreateUserDto createUserDto = new CreateUserDto(DUMMY_EMAIL, "same_password", "same_password")

        when:
            ResponseEntity<Map<String, String>> response = userService.createUser(createUserDto)

        then:
            1 * userRepository.existsByEmail(DUMMY_EMAIL) >> false
            1 * passwordEncoder.encode("same_password") >> "encoded_password"
            1 * userRepository.save(new User(null, DUMMY_EMAIL, "encoded_password")) >> new User("1wysadwe", DUMMY_EMAIL, "encoded_password")

            response.statusCode == HttpStatus.OK
            response.body == [id: "1wysadwe"]
    }

    def "given CreateUserDto with existing email when create then user is not created"() {
        given:
            CreateUserDto createUserDto = new CreateUserDto(DUMMY_EMAIL, "same_password", "same_password")

        when:
            ResponseEntity<Map<String, String>> response = userService.createUser(createUserDto)

        then:
            1 * userRepository.existsByEmail(DUMMY_EMAIL) >> true
            0 * passwordEncoder.encode(_)
            0 * userRepository.save(_)

            response.statusCode == HttpStatus.BAD_REQUEST
            response.body == [message: "User with email '${createUserDto.email()}' already exists"] as Map<String, String>
    }

    def "given CreateUserDto with different password and confirmPassword when create then user is not created"() {
        given:
            CreateUserDto createUserDto = new CreateUserDto(DUMMY_EMAIL, "same_password", "another_password")

        when:
            ResponseEntity<Map<String, String>> response = userService.createUser(createUserDto)

        then:
            1 * userRepository.existsByEmail(DUMMY_EMAIL) >> false
            0 * passwordEncoder.encode(_)
            0 * userRepository.save(_)

            response.statusCode == HttpStatus.BAD_REQUEST
            response.body == [message: "Passwords for user with email '${createUserDto.email()}' don't match"] as Map<String, String>
    }

}
