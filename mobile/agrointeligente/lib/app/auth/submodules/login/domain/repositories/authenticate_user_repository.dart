import '../../infra/datasource/i_authenticate_user_datasource.dart';
import '../entities/request/login_credentials.dart';
import '../entities/response/user_logged.dart';
import '../errors/login_failure.dart';
import '../utils/either/login_either.dart';
import 'i_authenticate_user_repository.dart';

class AuthenticateUserRepository implements IAuthenticateUserRepository {
  final IAuthenticateUserDatasource _datasource;

  AuthenticateUserRepository(this._datasource);

  @override
  Future<LoginEither<ILoginFailure, UserLogged>> authenticateWithEmail(
      LoginCredentials credentials) async {
    try {
      return SuccessResponse(
          await _datasource.authenticateWithEmail(credentials));
    } on ILoginFailure catch (e) {
      return FailureResponse(DatasourceFailure(message: e.message));
    } catch (e) {
      return FailureResponse(DatasourceFailure(message: '$e'));
    }
  }
}
