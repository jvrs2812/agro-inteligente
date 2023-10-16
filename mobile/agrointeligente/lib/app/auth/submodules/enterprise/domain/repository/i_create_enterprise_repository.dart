import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/enterprise_create.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/utils/enterprise_either.dart';

import '../errors/enterprise_failure.dart';

abstract class ICreateEnterpriseRepository {
  Future<EnterpriseEither<IEnterpriseFailure, bool>> createEnterprise(
      EnterpriseCreate enterprise);
}
