import 'dart:convert';

import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/EnterpriseResponse.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/infra/datasource/i_selection_enterprise_external.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../errors/enterprise_failure.dart';

class SelectionEnterpriseExternal implements ISelectionEntepriseExternal {
  @override
  Future<List<EnterpriseResponse>> selectionEnterprise() async {
    AuthStore auth = Modular.get<AuthStore>();

    List<EnterpriseResponse> enterprises = [];

    String url_base = dotenv.get('BASE_URL');

    var result = await http.get(
      Uri.parse('${url_base}my-enterprise'),
      headers: <String, String>{
        'Authorization': 'Bearer ${auth.state.acessToken}'
      },
    ).timeout(const Duration(seconds: 10),
        onTimeout: () => http.Response('Sem conexão', 408));

    if (result.statusCode == 408) {
      throw SocketFailure(message: "Sem conexão");
    }

    if (result.statusCode == 403) {
      await Modular.to.pushNamedAndRemoveUntil('/login', (p0) => false);
    }

    if (result.statusCode != 200 && result.statusCode != 403) {
      throw DatasourceFailure(message: 'Algo deu errado ao pesquisar.');
    }

    if (result.statusCode == 403) {
      throw DatasourceFailure(message: 'A sessão expirou. Relogue');
    }

    var lista = jsonDecode(result.body)['data'];

    for (var enterprise in lista) {
      enterprises.add(EnterpriseResponse.fromJson(enterprise));
    }

    return enterprises;
  }
}
