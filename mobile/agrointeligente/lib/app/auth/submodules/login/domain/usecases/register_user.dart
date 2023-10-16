import 'package:agrointeligente/app/auth/submodules/login/domain/entities/request/sing_up_request.dart';

import '../entities/response/user_logged.dart';
import '../errors/login_failure.dart';
import '../repositories/i_register_user_repository.dart';
import '../utils/either/login_either.dart';

abstract class IRegisterUserCase {
  Future<LoginEither<ILoginFailure, UserLogged>> call(
      SingUpRequest credentials);
}

class RegisterUserCase implements IRegisterUserCase {
  final IRegisterUserRepository _repository;

  RegisterUserCase(this._repository);

  @override
  Future<LoginEither<ILoginFailure, UserLogged>> call(
      SingUpRequest credentials) async {
    return _repository.registerUser(credentials);
  }
}
