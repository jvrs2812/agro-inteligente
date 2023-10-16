import 'dart:convert';

import 'package:agrointeligente/app/auth/store/external/refresh_token.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../submodules/login/domain/entities/response/user_logged.dart';

class AuthStore extends Store<UserLogged> {
  AuthStore() : super(UserLogged());
  // Obtain shared preferences.

  final RefreshToken _refreshToken = RefreshToken();

  Future<UserLogged> userLogger() async {
    if (state.acessToken == "") {
      UserLogged auth = await getLocalStorage();
      update(state.copyWith(
          acessToken: auth.acessToken,
          expiredAt: auth.expiredAt,
          refreshToken: auth.refreshToken,
          dateGenerate: auth.dateGenerate,
          name: auth.name));
    }

    return state;
  }

  bool isAcessTokenValid() {
    return state.expirationTime.isAfter(DateTime.now());
  }

  Future<UserLogged> getLocalStorage() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();

    String json = prefs.getString("auth") ?? '';

    if (json == "") return UserLogged();

    return UserLogged.fromJson(jsonDecode(json));
  }

  Future<String> getRefreshToken() async {
    if (state.refreshToken == "") {
      return (await getLocalStorage()).refreshToken;
    }

    return state.refreshToken;
  }

  Future<bool> refreshAndAttToken() async {
    String refresh = await getRefreshToken();
    UserLogged user = await _refreshToken.refresh(refresh);

    setUserLogged(user);

    return true;
  }

  Future<void> setUserLogged(UserLogged user) async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();

    update(user);

    prefs.setString("auth", jsonEncode(user.toJson()));
  }

  Future<bool> isLoggedIn() async {
    return (await userLogger()).refreshToken != "";
  }
}
