package test.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Base64;
import java.util.Objects;

public class Credentials {
  private final String email;
  private final String password;

  public Credentials(
    final String email,
    final String pass
  ) {
    this.email = email;
    this.password = pass;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Credentials that = (Credentials) o;
    return Objects.equals(getEmail(), that.getEmail()) &&
        Objects.equals(getPassword(), that.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEmail(), getPassword());
  }

  @JsonCreator
  static Credentials fromCLI(
    @JsonProperty("email") final String email,
    @JsonProperty("pass")  final String pass
  ) {
    return new Credentials(email, new String(Base64.getDecoder().decode(pass)));
  }
}
