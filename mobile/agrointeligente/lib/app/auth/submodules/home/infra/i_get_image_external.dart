import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';

abstract class IGetImageExternal {
  Future<List<AnalyzeResponse>> getImage(String idEnterprise);
}
