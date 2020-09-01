package jarvisapi.controller;

import jarvisapi.entity.SignUpRequest;
import jarvisapi.entity.User;
import jarvisapi.exception.SignUpRequestNotFoundException;
import jarvisapi.payload.request.SignInRequest;
import jarvisapi.payload.response.SignInResponse;
import jarvisapi.security.JwtTokenUtil;
import jarvisapi.service.SignUpRequestService;
import jarvisapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SignUpRequestService signUpRequestService;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@Valid @RequestBody jarvisapi.payload.request.SignUpRequest signUp) {
        try {
            this.signUpRequestService.create(signUp.getFirstName(), signUp.getLastName(), signUp.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@Valid @RequestBody SignInRequest signInRequest) {
        /*
         * TODO: User connection
         * Verify user password
         * If the connection is authorized :
         *  Check authorized devices
         *  If this device is authorized :
         *   Save the new connection history
         *   Return JWT
         *  Else
         *   Return forbidden access
         * Else
         *  Return forbidden access
         */
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );
        UserDetails userDetails = this.userService.loadUserByUsername(authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new SignInResponse(token));
    }

    @GetMapping("/sign-up-request")
    public ResponseEntity getSignUpRequests() {
        try {
            List<SignUpRequest> signUpRequests = this.signUpRequestService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(signUpRequests);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/sign-up-request/{id}")
    public ResponseEntity getSignUpRequest(@PathVariable long id) {
        try {
            SignUpRequest signUpRequest = this.signUpRequestService.get(id);
            return ResponseEntity.status(HttpStatus.OK).body(signUpRequest);
        } catch(SignUpRequestNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/sign-up-request/{id}/accept")
    public ResponseEntity acceptSignUpRequest(@PathVariable long id) {
        try {
            this.signUpRequestService.accept(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (SignUpRequestNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/sign-up-request/{id}/reject")
    public ResponseEntity rejectSignUpRequest(@PathVariable long id) {
        try {
            this.signUpRequestService.reject(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (SignUpRequestNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
