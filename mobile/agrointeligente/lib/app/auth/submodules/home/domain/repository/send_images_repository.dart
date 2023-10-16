import '../../infra/i_send_image_external.dart';
import '../entities/analyze_response.dart';
import '../errors/analyze_failure.dart';
import '../utils/home_either.dart';
import 'i_send_images_repository.dart';

class SendImagesRepository implements ISendImagesRepository {
  final ISendImageExternal _datasource;

  SendImagesRepository(this._datasource);

  @override
  Future<HomeEither<IAnalyzeFailure, AnalyzeResponse>> sendImages(
      String idEnterprise, String path) async {
    try {
      return SuccessResponse(await _datasource.sendImage(idEnterprise, path));
    } on IAnalyzeFailure catch (e) {
      return FailureResponse(DatasourceFailure(message: e.message));
    } catch (e) {
      return FailureResponse(DatasourceFailure(message: '$e'));
    }
  }
}
