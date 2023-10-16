import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/errors/analyze_failure.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/repository/i_get_images_repository.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/utils/home_either.dart';

abstract class IGetImage {
  Future<HomeEither<IAnalyzeFailure, List<AnalyzeResponse>>> call(
      String idEnterprise);
}

class GetImage implements IGetImage {
  final IGetImagesRepository _repository;

  GetImage(this._repository);

  @override
  Future<HomeEither<IAnalyzeFailure, List<AnalyzeResponse>>> call(
      String idEnterprise) async {
    return _repository.getImages(idEnterprise);
  }
}
