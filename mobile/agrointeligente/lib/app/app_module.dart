import 'package:agrointeligente/app/auth/submodules/home/home_module.dart';
import 'package:agrointeligente/main.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'auth/submodules/enterprise/enterprise_module.dart';
import 'auth/submodules/login/login_module.dart';
import 'auth/submodules/splash.dart';

class AppModule extends Module {
  @override
  void binds(i) {}

  @override
  List<Module> get imports => [MainModule()];

  @override
  void routes(r) {
    r.child(Modular.initialRoute, child: (context) => const SplashPage());
    r.module('/login', module: LoginModule());
    r.module('/enterprise', module: EnterpriseModule());
    r.module('/home', module: HomeModule());
  }
}
