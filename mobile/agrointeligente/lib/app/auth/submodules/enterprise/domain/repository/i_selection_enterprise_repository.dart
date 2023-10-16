import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/EnterpriseResponse.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/utils/enterprise_either.dart';

import '../errors/enterprise_failure.dart';

abstract class ISelectionEnterpriseRepository {
  Future<EnterpriseEither<IEnterpriseFailure, List<EnterpriseResponse>>>
      selectionEnterprise();
}
