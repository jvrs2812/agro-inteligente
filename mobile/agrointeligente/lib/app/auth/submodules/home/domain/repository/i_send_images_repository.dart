import '../entities/analyze_response.dart';
import '../errors/analyze_failure.dart';
import '../utils/home_either.dart';

abstract class ISendImagesRepository {
  Future<HomeEither<IAnalyzeFailure, AnalyzeResponse>> sendImages(
      String idEnterprise, String path);
}
