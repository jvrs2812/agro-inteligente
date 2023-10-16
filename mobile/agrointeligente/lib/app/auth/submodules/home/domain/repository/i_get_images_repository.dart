import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/errors/analyze_failure.dart';

import '../utils/home_either.dart';

abstract class IGetImagesRepository {
  Future<HomeEither<IAnalyzeFailure, List<AnalyzeResponse>>> getImages(
      String idEnterprise);
}
