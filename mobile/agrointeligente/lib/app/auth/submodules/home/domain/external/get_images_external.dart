import 'dart:convert';

import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../store/auth_store.dart';
import '../../infra/i_get_image_external.dart';
import 'package:http/http.dart' as http;

import '../errors/analyze_failure.dart';

class GetImageExternal extends IGetImageExternal {
  @override
  Future<List<AnalyzeResponse>> getImage(String idEnterprise) async {
    AuthStore auth = Modular.get<AuthStore>();

    String url_base = dotenv.get('BASE_URL');
    var result = await http.get(
      Uri.parse('${url_base}analyze/my-images/$idEnterprise'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer ${auth.state.acessToken}'
      },
    );

    if (result.statusCode == 403) {
      Modular.to.pushNamedAndRemoveUntil('/login', (p0) => false);
    }

    if (result.statusCode != 200) {
      throw DatasourceFailure(message: 'Algo deu errado ao pesquisar.');
    }

    List<AnalyzeResponse> analyze = [];

    var lista = jsonDecode(result.body)['data'];

    for (var analyzeitem in lista) {
      var base64 = await http.get(Uri.parse(analyzeitem["url"]));

      analyze.add(
          AnalyzeResponse.fromJson(analyzeitem, base64String: base64.body));
    }

    return analyze;
  }
}
