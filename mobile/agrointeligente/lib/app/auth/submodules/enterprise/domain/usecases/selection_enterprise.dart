import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/EnterpriseResponse.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/repository/i_selection_enterprise_repository.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/utils/enterprise_either.dart';
import '../errors/enterprise_failure.dart';

abstract class ISelectionEnterprise {
  Future<EnterpriseEither<IEnterpriseFailure, List<EnterpriseResponse>>> call();
}

class SelectionEnterprise implements ISelectionEnterprise {
  final ISelectionEnterpriseRepository _repository;

  SelectionEnterprise(this._repository);

  @override
  Future<EnterpriseEither<IEnterpriseFailure, List<EnterpriseResponse>>>
      call() async {
    return _repository.selectionEnterprise();
  }
}
