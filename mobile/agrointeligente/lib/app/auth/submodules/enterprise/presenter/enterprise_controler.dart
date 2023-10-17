import 'package:agrointeligente/app/auth/store/enterprise_store.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/EnterpriseResponse.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'package:asuka/asuka.dart' as asuka;
import '../domain/repository/i_selection_enterprise_repository.dart';

class EnterpriseController extends Store<List<EnterpriseResponse>> {
  EnterpriseController(this._selectionEnteprise) : super([]) {
    searchEnterprise();
  }

  final ISelectionEnterpriseRepository _selectionEnteprise;

  final EnterpriseStore enterprise = Modular.get();

  void selectEnterprise(EnterpriseResponse enterpriseResponse) {
    enterprise.update(enterpriseResponse, force: true);
    Modular.to.pushNamed('/home');
  }

  Future<void> searchEnterprise() async {
    setLoading(true);

    state.clear();

    var lista = await _selectionEnteprise.selectionEnterprise();

    lista.fold(
      onSuccess: (sucess) {
        update(sucess, force: true);
        setLoading(false);
      },
      onFailure: (failure) {
        update(state, force: true);
        setLoading(false);
        return asuka.showSnackBar(SnackBar(
          content: Text('${failure.message}', textAlign: TextAlign.center),
          backgroundColor: Colors.red,
          duration: const Duration(seconds: 1),
        ));
      },
    );
  }
}
