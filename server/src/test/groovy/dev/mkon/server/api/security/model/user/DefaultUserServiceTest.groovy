package dev.mkon.server.api.security.model.user

import dev.mkon.server.api.dto.user.CreateUserDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Subject

class DefaultUserServiceTest extends Specification {

    UserRepository userRepository = Mock()
    PasswordEncoder passwordEncoder = Mock()

    @Subject
    UserService userService = new DefaultUserService(userRepository, passwordEncoder)


    def "given valid CreateUserDto when create then user is created"() {
        given:
            CreateUserDto createUserDto = new CreateUserDto("dummy@email.com", "same_password", "same_password")

        when:
            ResponseEntity<Map<String, String>> response = userService.createUser(createUserDto)

        then:
            1 * userRepository.existsByEmail("dummy@email.com") >> false
            1 * passwordEncoder.encode("same_password") >> "encoded_password"
            1 * userRepository.save(new User(null, "dummy@email.com", "encoded_password")) >> new User("1wysadwe", "dummy@email.com", "encoded_password")

            response.statusCode == HttpStatus.OK
            response.body == [id: "1wysadwe"]
    }

    def "given CreateUserDto with existing email when create then user is not created"() {
        given:
            CreateUserDto createUserDto = new CreateUserDto("dummy@email.com", "same_password", "same_password")

        when:
            ResponseEntity<Map<String, String>> response = userService.createUser(createUserDto)

        then:
            1 * userRepository.existsByEmail("dummy@email.com") >> true
            0 * passwordEncoder.encode(_)
            0 * userRepository.save(_)

            response.statusCode == HttpStatus.BAD_REQUEST
            response.body == [message: "User with email '${createUserDto.email()}' already exists"] as Map<String, String>
    }

}
