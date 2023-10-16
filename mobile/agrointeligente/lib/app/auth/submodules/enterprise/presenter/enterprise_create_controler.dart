import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/enterprise_create.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/presenter/enterprise_controler.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'package:asuka/asuka.dart' as asuka;
import '../domain/repository/i_create_enterprise_repository.dart';

class EnterpriseCreateController extends Store<EnterpriseCreate> {
  EnterpriseCreateController(this._createEnteprise) : super(EnterpriseCreate());

  final ICreateEnterpriseRepository _createEnteprise;

  final EnterpriseController _enterpriseController = Modular.get();

  void setAdres(String value) => update(state.copyWith(adress: value));

  void setCnpj(String value) => update(state.copyWith(cnpj: value));

  void setNameFancy(String value) => update(state.copyWith(nameFancy: value));

  void setDistrict(String value) => update(state.copyWith(district: value));

  void setNumber(String value) => update(state.copyWith(number: value));

  Future<void> onRegisterInPress() async {
    setLoading(true);

    var lista = await _createEnteprise.createEnterprise(state);

    lista.fold(
      onSuccess: (sucess) async {
        asuka.showSnackBar(const SnackBar(
          content: Text(
            'Cadastro realizado com sucesso !!',
            textAlign: TextAlign.center,
          ),
          backgroundColor: Colors.green,
        ));
        await _enterpriseController.searchEnterprise();
        await Modular.to.pushNamed(
          '/enterprise',
        );
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
