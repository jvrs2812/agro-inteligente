import 'package:agrointeligente/app/auth/submodules/login/domain/repositories/i_register_user_repository.dart';
import 'package:agrointeligente/app/auth/submodules/login/domain/repositories/register_user_repository.dart';
import 'package:agrointeligente/app/auth/submodules/login/infra/datasource/i_register_user_datasource.dart';
import 'package:agrointeligente/app/auth/submodules/login/presenter/login_controller.dart';
import 'package:agrointeligente/app/auth/submodules/login/presenter/login_page.dart';
import 'package:agrointeligente/app/auth/submodules/login/presenter/sing_up_controller.dart';
import 'package:agrointeligente/app/auth/submodules/login/presenter/sing_up_page.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../main.dart';
import 'domain/external/datasources/authenticate.dart';
import 'domain/external/datasources/register.dart';
import 'domain/repositories/authenticate_user_repository.dart';
import 'domain/repositories/i_authenticate_user_repository.dart';
import 'domain/usecases/authenticate_user_with_email.dart';
import 'domain/usecases/register_user.dart';
import 'infra/datasource/i_authenticate_user_datasource.dart';

class LoginModule extends Module {
  @override
  List<Module> get imports => [MainModule()];

  @override
  void binds(i) {
    i.add<IAuthenticateUserDatasource>(ApiUserDataSource.new);
    i.add<IAuthenticateUserRepository>(AuthenticateUserRepository.new);
    i.add<IAuthenticateUserWithEmail>(AuthenticateUserWithEmail.new);
    i.addLazySingleton<LoginController>(LoginController.new);

    i.add<IRegisterUserDatasource>(RegisterUser.new);
    i.add<IRegisterUserRepository>(RegisterUserRepository.new);
    i.add<IRegisterUserCase>(RegisterUserCase.new);

    i.addLazySingleton<SingUpController>(SingUpController.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => const LoginPage());
    r.child(
      '/singup',
      child: (context) => const SingUpPage(),
    );
  }
}
