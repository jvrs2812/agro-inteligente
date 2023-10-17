import 'dart:convert';
import 'dart:io';

import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/enterprise_create.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/infra/datasource/i_create_enterprise_external.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;
import '../../../../store/auth_store.dart';
import '../errors/enterprise_failure.dart';

class CreateEnterpriseExternal extends ICreateEntepriseExternal {
  @override
  Future<bool> createEnterprise(EnterpriseCreate create) async {
    AuthStore auth = Modular.get<AuthStore>();

    String url_base = dotenv.get('BASE_URL');

    try {
      var result = await http
          .post(Uri.parse('${url_base}enterprise'),
              headers: <String, String>{
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ${auth.state.acessToken}'
              },
              body: jsonEncode(create))
          .timeout(const Duration(seconds: 20),
              onTimeout: () => http.Response('Sem conexão', 408));

      if (result.statusCode == 408) {
        throw SocketFailure(message: "Sem conexão");
      }

      if (result.statusCode == 403) {
        Modular.to.pushNamedAndRemoveUntil('/login', (p0) => false);
      }

      if (result.statusCode != 200) {
        throw DatasourceFailure(
            message: jsonDecode(utf8.decode(result.bodyBytes))['error'][0]);
      }
    } on SocketException catch (_) {
      throw SocketFailure(message: "Sem conexão com a internet.");
    }

    return true;
  }
}
