import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/EnterpriseResponse.dart';

import 'package:agrointeligente/app/auth/submodules/enterprise/domain/errors/enterprise_failure.dart';

import 'package:agrointeligente/app/auth/submodules/enterprise/domain/utils/enterprise_either.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/infra/datasource/i_selection_enterprise_external.dart';

import 'i_selection_enterprise_repository.dart';

class SelectionEnterpriseRepository implements ISelectionEnterpriseRepository {
  final ISelectionEntepriseExternal _datasource;

  SelectionEnterpriseRepository(this._datasource);

  @override
  Future<EnterpriseEither<IEnterpriseFailure, List<EnterpriseResponse>>>
      selectionEnterprise() async {
    try {
      return SuccessResponse(await _datasource.selectionEnterprise());
    } on IEnterpriseFailure catch (e) {
      return FailureResponse(DatasourceFailure(message: e.message));
    } catch (e) {
      return FailureResponse(DatasourceFailure(message: '$e'));
    }
  }
}
