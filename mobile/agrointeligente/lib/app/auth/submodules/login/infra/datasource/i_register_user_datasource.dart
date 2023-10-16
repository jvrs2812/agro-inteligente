import 'package:agrointeligente/app/auth/submodules/login/domain/entities/request/sing_up_request.dart';

import '../../domain/entities/response/user_logged.dart';

abstract class IRegisterUserDatasource {
  Future<UserLogged> registerUser(SingUpRequest credentials);
}
