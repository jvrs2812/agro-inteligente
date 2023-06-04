import '../domain/auth.dart';
import 'package:flutter_triple/flutter_triple.dart';

class LoginController extends Store<Auth> {
  LoginController() : super(Auth(email: "", password: ""));
}
