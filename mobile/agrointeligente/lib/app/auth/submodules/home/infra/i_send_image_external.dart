import '../domain/entities/analyze_response.dart';

abstract class ISendImageExternal {
  Future<AnalyzeResponse> sendImage(String idEnterprise, String path);
}
