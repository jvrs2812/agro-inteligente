import 'dart:ui';

import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:agrointeligente/app/auth/submodules/login/domain/entities/request/sing_up_request.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'package:asuka/asuka.dart' as asuka;
import '../domain/usecases/register_user.dart';

class SingUpController extends Store<SingUpRequest> {
  SingUpController(this._regiteruser, this._authStore) : super(SingUpRequest());

  final IRegisterUserCase _regiteruser;

  final AuthStore _authStore;

  void setEmail(String value) => update(state.copyWith(email: value));

  void setPassword(String value) => update(state.copyWith(password: value));

  void setCpf(String value) => update(state.copyWith(cpf: value));

  void setName(String value) => update(state.copyWith(name: value));

  Future<void> onSingInPress(SingUpRequest register) async {
    setLoading(true);

    final result = await _regiteruser(state);

    result.fold(onFailure: (failure) {
      setLoading(false);
      return asuka.showSnackBar(SnackBar(
        content: Text('${failure.message}', textAlign: TextAlign.center),
        backgroundColor: Colors.red,
        duration: const Duration(milliseconds: 1500),
      ));
    }, onSuccess: (sucess) {
      _authStore.setUserLogged(sucess);
      update(state, force: true);
      Modular.to.pushNamed('/enterprise');
    });

    setLoading(false);
  }
}
