import '../entities/analyze_response.dart';
import '../errors/analyze_failure.dart';
import '../repository/i_send_images_repository.dart';
import '../utils/home_either.dart';

abstract class ISendImage {
  Future<HomeEither<IAnalyzeFailure, AnalyzeResponse>> call(
      String idEnterprise, String path);
}

class SelectionEnterprise implements ISendImage {
  final ISendImagesRepository _repository;

  SelectionEnterprise(this._repository);

  @override
  Future<HomeEither<IAnalyzeFailure, AnalyzeResponse>> call(
      String idEnterprise, String path) async {
    return _repository.sendImages(idEnterprise, path);
  }
}
