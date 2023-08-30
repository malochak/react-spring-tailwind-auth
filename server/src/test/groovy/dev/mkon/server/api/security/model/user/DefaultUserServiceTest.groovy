package dev.mkon.server.api.security.model.user

import dev.mkon.server.api.dto.user.CreateUserDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class DefaultUserServiceTest extends Specification {

    static String DUMMY_EMAIL = "dummy@email.com"

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

    def "given not existing username (email) when loadByUserName should throw UsernameNotFoundException"() {
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

    @Unroll
    def "given CreateUserDto with #description when create then user is not created"() {
        given:
            CreateUserDto createUserDto = userDto

        when:
            ResponseEntity<Map<String, String>> response = userService.createUser(createUserDto)

        then:
            1 * userRepository.existsByEmail(DUMMY_EMAIL) >> existingEmail
            0 * passwordEncoder.encode(_)
            0 * userRepository.save(_)

            response.statusCode == HttpStatus.BAD_REQUEST
            response.body == [message: expectedMessage] as Map<String, String>

        where:
            description                              | existingEmail | userDto                                                             || expectedMessage
            "existing email"                         | true          | new CreateUserDto(DUMMY_EMAIL, "same_password", "same_password")    || "User with email '${userDto.email()}' already exists"
            "different passwords"                    | false         | new CreateUserDto(DUMMY_EMAIL, "same_password", "another_password") || "Passwords for user with email '${userDto.email()}' don't match"
            "existing email and different passwords" | true          | new CreateUserDto(DUMMY_EMAIL, "same_password", "another_password") || "User with email '${userDto.email()}' already exists"
    }

}
