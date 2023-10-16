import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/EnterpriseResponse.dart';

abstract class ISelectionEntepriseExternal {
  Future<List<EnterpriseResponse>> selectionEnterprise();
}
