import '../entities/request/login_credentials.dart';
import '../entities/response/user_logged.dart';
import '../errors/login_failure.dart';
import '../utils/either/login_either.dart';

abstract class IAuthenticateUserRepository {
  Future<LoginEither<ILoginFailure, UserLogged>> authenticateWithEmail(
      LoginCredentials credentials);
}
