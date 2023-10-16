import 'package:agrointeligente/app/auth/submodules/enterprise/domain/external/create_enterprise_external.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/external/selection_enterprise_external.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/repository/create_enterprise_repository.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/repository/i_create_enterprise_repository.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/presenter/enterprise_controler.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/presenter/enterprise_create_controler.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/presenter/enterprise_create_page.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/presenter/enterprise_page.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../main.dart';

import '../../../shared/guards/app_guard.dart';
import 'domain/repository/i_selection_enterprise_repository.dart';
import 'domain/repository/selection_enterprise_repository.dart';
import 'infra/datasource/i_create_enterprise_external.dart';
import 'infra/datasource/i_selection_enterprise_external.dart';

class EnterpriseModule extends Module {
  @override
  void binds(i) {
    i.add<ISelectionEntepriseExternal>(SelectionEnterpriseExternal.new);
    i.add<ISelectionEnterpriseRepository>(SelectionEnterpriseRepository.new);
    i.add(EnterpriseController.new);

    i.add<ICreateEntepriseExternal>(CreateEnterpriseExternal.new);
    i.add<ICreateEnterpriseRepository>(CreateEnterpriseRepository.new);
    i.add(EnterpriseCreateController.new);
  }

  @override
  List<Module> get imports => [MainModule()];

  @override
  void routes(r) {
    r.child('/',
        child: (context) => const EnterprisePage(), guards: [AppGuard()]);
    r.child('/create-enterprise',
        child: (context) => EnterpriseCreatePage(), guards: [AppGuard()]);
  }
}
