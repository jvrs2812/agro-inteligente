import 'package:agrointeligente/app/auth/submodules/login/domain/entities/request/sing_up_request.dart';

import 'package:agrointeligente/app/auth/submodules/login/domain/entities/response/user_logged.dart';

import 'package:agrointeligente/app/auth/submodules/login/domain/errors/login_failure.dart';

import 'package:agrointeligente/app/auth/submodules/login/domain/utils/either/login_either.dart';

import '../../infra/datasource/i_register_user_datasource.dart';
import 'i_register_user_repository.dart';

class RegisterUserRepository implements IRegisterUserRepository {
  final IRegisterUserDatasource _datasource;

  RegisterUserRepository(this._datasource);

  @override
  Future<LoginEither<ILoginFailure, UserLogged>> registerUser(
      SingUpRequest credentials) async {
    try {
      return SuccessResponse(await _datasource.registerUser(credentials));
    } on ILoginFailure catch (e) {
      return FailureResponse(DatasourceFailure(message: e.message));
    } catch (e) {
      return FailureResponse(DatasourceFailure(message: '$e'));
    }
  }
}
