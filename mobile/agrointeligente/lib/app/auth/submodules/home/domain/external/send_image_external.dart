import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;
import '../../../enterprise/domain/errors/enterprise_failure.dart';
import '../../infra/i_send_image_external.dart';

class SendImageExternal extends ISendImageExternal {
  @override
  Future<AnalyzeResponse> sendImage(String idEnterprise, String path) async {
    AuthStore authStore = Modular.get();
    String url_base = dotenv.get('BASE_URL');
    var request = http.MultipartRequest(
        "POST", Uri.parse('${url_base}analyze/$idEnterprise'));

    request.files.add(await http.MultipartFile.fromPath('images', path));
    request.headers
        .addAll({'Authorization': 'Bearer ${authStore.state.acessToken}'});
    var response = await request.send();

    if (response.statusCode != 200) {
      throw DatasourceFailure(message: 'Falha ao enviar a imagem.');
    }

    return AnalyzeResponse();
  }
}
