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

    String url_base = dotenv.get('BASE_URL');
    var result = await http.get(
      Uri.parse('${url_base}my-enterprise'),
      headers: <String, String>{
        'Authorization': 'Bearer ${auth.state.acessToken}'
      },
    );

    if (result.statusCode == 403) {
      Modular.to.pushNamedAndRemoveUntil('/login', (p0) => false);
    }

    if (result.statusCode != 200) {
      throw DatasourceFailure(message: 'Algo deu errado ao pesquisar.');
    }

    List<EnterpriseResponse> enterprises = [];

    var lista = jsonDecode(result.body)['data'];

    for (var enterprise in lista) {
      enterprises.add(EnterpriseResponse.fromJson(enterprise));
    }

    return enterprises;
  }
}
