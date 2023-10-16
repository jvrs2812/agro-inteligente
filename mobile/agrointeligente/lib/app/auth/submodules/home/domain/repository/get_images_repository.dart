import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';

import 'package:agrointeligente/app/auth/submodules/home/domain/errors/analyze_failure.dart';

import 'package:agrointeligente/app/auth/submodules/home/domain/utils/home_either.dart';

import '../../infra/i_get_image_external.dart';
import 'i_get_images_repository.dart';

class GetImagesRepository implements IGetImagesRepository {
  final IGetImageExternal _datasource;

  GetImagesRepository(this._datasource);

  @override
  Future<HomeEither<IAnalyzeFailure, List<AnalyzeResponse>>> getImages(
      String idEnterprise) async {
    try {
      return SuccessResponse(await _datasource.getImage(idEnterprise));
    } on IAnalyzeFailure catch (e) {
      return FailureResponse(DatasourceFailure(message: e.message));
    } catch (e) {
      return FailureResponse(DatasourceFailure(message: '$e'));
    }
  }
}
