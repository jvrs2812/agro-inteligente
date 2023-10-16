import 'package:agrointeligente/app/auth/submodules/login/domain/entities/request/sing_up_request.dart';

import '../entities/response/user_logged.dart';
import '../errors/login_failure.dart';
import '../utils/either/login_either.dart';

abstract class IRegisterUserRepository {
  Future<LoginEither<ILoginFailure, UserLogged>> registerUser(
      SingUpRequest credentials);
}
