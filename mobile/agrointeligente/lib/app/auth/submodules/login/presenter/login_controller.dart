import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'package:flutter_modular/flutter_modular.dart';
import '../domain/entities/request/login_credentials.dart';
import '../domain/usecases/authenticate_user_with_email.dart';
import 'package:asuka/asuka.dart' as asuka;

class LoginController extends Store<LoginCredentials> {
  LoginController(this._authStore, this._authenticateUserWithEmail)
      : super(LoginCredentials());

  final IAuthenticateUserWithEmail _authenticateUserWithEmail;

  final AuthStore _authStore;

  void setEmail(String value) => update(state.copyWith(email: value));

  void setPassword(String value) => update(state.copyWith(password: value));

  Future<void> onSingInPress(String email, String password) async {
    setLoading(true);

    final result = await _authenticateUserWithEmail(state);

    result.fold(onFailure: (failure) {
      update(state, force: true);
      setLoading(false);
      return asuka.showSnackBar(SnackBar(
        content: Text('${failure.message}', textAlign: TextAlign.center),
        backgroundColor: Colors.red,
        duration: const Duration(seconds: 1),
      ));
    }, onSuccess: (sucess) {
      update(state, force: true);
      _authStore.setUserLogged(sucess);
      Modular.to.pushNamed('/enterprise');
    });

    setLoading(false);
  }
}
