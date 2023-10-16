import 'dart:convert';

import 'package:agrointeligente/app/auth/submodules/login/domain/entities/response/user_logged.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_modular/flutter_modular.dart';

import 'package:http/http.dart' as http;

class RefreshToken {
  Future<UserLogged> refresh(String refreshToken) async {
    String url_base = dotenv.get('BASE_URL');
    var result = await http.post(Uri.parse('${url_base}auth/refresh-token'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode({'refreshtoken': refreshToken}));

    if (result.statusCode == 401) {
      Modular.to.pushNamedAndRemoveUntil(
        '/login',
        (p0) => false,
      );
    }

    var data = jsonDecode(result.body)['data'];

    return UserLogged.fromJson(data);
  }
}
