import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:flutter_modular/flutter_modular.dart';

class AppGuard extends RouteGuard {
  AppGuard() : super(redirectTo: '/login');
  @override
  Future<bool> canActivate(String path, ModularRoute router) async {
    AuthStore auth = Modular.get();

    if (!auth.isAcessTokenValid()) await auth.refreshAndAttToken();

    return auth.isAcessTokenValid();
  }
}
