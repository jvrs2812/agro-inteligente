import '../../infra/datasource/i_create_enterprise_external.dart';
import '../entities/response/enterprise_create.dart';
import '../errors/enterprise_failure.dart';
import '../utils/enterprise_either.dart';
import 'i_create_enterprise_repository.dart';

class CreateEnterpriseRepository implements ICreateEnterpriseRepository {
  final ICreateEntepriseExternal _datasource;

  CreateEnterpriseRepository(this._datasource);

  @override
  Future<EnterpriseEither<IEnterpriseFailure, bool>> createEnterprise(
      EnterpriseCreate enterprise) async {
    try {
      return SuccessResponse(await _datasource.createEnterprise(enterprise));
    } on IEnterpriseFailure catch (e) {
      return FailureResponse(DatasourceFailure(message: e.message));
    } catch (e) {
      return FailureResponse(DatasourceFailure(message: '$e'));
    }
  }
}
