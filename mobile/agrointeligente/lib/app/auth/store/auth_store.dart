import 'dart:convert';

import 'package:agrointeligente/app/auth/store/external/refresh_token.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
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

  Future<bool> isAcessTokenValid() async {
    if (!isTokenExpired(state.acessToken)) {
      return true;
    } else {
      if (!isTokenExpired(state.refreshToken)) {
        return await refreshAndAttToken();
      }
      return false;
    }
  }

  void logout() {
    setUserLogged(UserLogged());
    Modular.to.pushNamedAndRemoveUntil('/login', (p0) => false);
  }

  bool isTokenExpired(String jwtToken) {
    try {
      final Map<String, dynamic> decodedToken = JwtDecoder.decode(jwtToken);

      // Verifica se o campo "exp" está presente no token
      if (decodedToken.containsKey("exp")) {
        // Obtém o valor do campo "exp"
        int expValue = decodedToken["exp"];

        // Obtém o tempo atual em segundos
        int currentTimeInSeconds =
            DateTime.now().millisecondsSinceEpoch ~/ 1000;

        // Verifica se o token expirou
        return currentTimeInSeconds >= expValue;
      } else {
        // Se o campo "exp" não estiver presente, considere como token expirado
        return true;
      }
    } catch (e) {
      // Tratamento de erro para lidar com tokens inválidos ou malformados
      print("Erro ao decodificar o token: $e");
      return true;
    }
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

    await prefs.setString("auth", jsonEncode(user.toJson()));
  }

  Future<bool> isLoggedIn() async {
    return (await userLogger()).refreshToken != "";
  }
}
