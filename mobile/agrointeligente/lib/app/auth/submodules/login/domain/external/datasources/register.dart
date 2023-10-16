import 'dart:convert';

import 'package:agrointeligente/app/auth/submodules/login/domain/entities/request/sing_up_request.dart';
import 'package:agrointeligente/app/auth/submodules/login/domain/entities/response/user_logged.dart';
import 'package:agrointeligente/app/auth/submodules/login/domain/errors/login_failure.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;

import '../../../infra/datasource/i_register_user_datasource.dart';

class RegisterUser implements IRegisterUserDatasource {
  @override
  Future<UserLogged> registerUser(SingUpRequest credentials) async {
    String url_base = dotenv.get('BASE_URL');
    var result = await http.post(
      Uri.parse('${url_base}auth/register'),
      body: jsonEncode(credentials),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );

    if (result.statusCode != 200) {
      throw DatasourceFailure(
          message: jsonDecode(utf8.decode(result.bodyBytes))['error'][0]);
    }

    var data = jsonDecode(result.body)['data'];

    return UserLogged.fromJson(data);
  }
}
