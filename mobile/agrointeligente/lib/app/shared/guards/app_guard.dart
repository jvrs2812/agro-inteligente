import 'package:flutter_modular/flutter_modular.dart';

class AppGuard extends RouteGuard {
  AppGuard() : super();
  @override
  Future<bool> canActivate(String path, ModularRoute router) async {
    return true;
  }
}
