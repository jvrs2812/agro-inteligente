import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/enterprise_create.dart';

abstract class ICreateEntepriseExternal {
  Future<bool> createEnterprise(EnterpriseCreate create);
}
