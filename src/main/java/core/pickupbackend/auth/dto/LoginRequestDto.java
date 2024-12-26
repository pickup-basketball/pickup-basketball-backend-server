package core.pickupbackend.auth.dto;

public class LoginRequestDto {

    private final String email;
    private final String password;

    public LoginRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
