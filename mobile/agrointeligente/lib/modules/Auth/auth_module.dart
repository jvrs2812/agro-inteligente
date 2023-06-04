import 'package:agrointeligente/modules/Auth/Presenter/splash_page.dart';
import 'package:flutter_modular/flutter_modular.dart';

import 'modules/Login/login_module.dart';

class AuthModule extends Module {
  @override
  List<Bind> get binds => [];

  @override
  List<ModularRoute> get routes => [
        ChildRoute('/', child: (context, args) => const SplashPage()),
        ModuleRoute('/login', module: LoginModule()),
      ];
}
