import 'dart:convert';
import 'package:agrointeligente/app/auth/submodules/login/domain/errors/login_failure.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

import '../../../infra/datasource/i_authenticate_user_datasource.dart';
import '../../entities/request/login_credentials.dart';
import '../../entities/response/user_logged.dart';
import 'package:http/http.dart' as http;

class ApiUserDataSource implements IAuthenticateUserDatasource {
  @override
  Future<UserLogged> authenticateWithEmail(LoginCredentials credentials) async {
    String url_base = dotenv.get('BASE_URL');
    var result = await http.post(
      Uri.parse('${url_base}auth/login'),
      body: jsonEncode(credentials),
      headers: <String, String>{
        'Content-Type': 'application/json',
      },
    ).timeout(const Duration(seconds: 20),
        onTimeout: () => http.Response('Sem conexão', 408));

    if (result.statusCode == 408) {
      throw SocketFailure(message: "Sem conexão");
    }
    ;

    if (result.statusCode == 403) {
      throw DatasourceFailure(message: 'Usuário ou senha estão incorretos.');
    }
    var data = jsonDecode(result.body)['data'];

    return UserLogged.fromJson(data);
  }
}
