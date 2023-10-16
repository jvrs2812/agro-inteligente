import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';

import '../../../../shared/components/text_field_container.dart';
import '../domain/entities/response/enterprise_create.dart';
import 'enterprise_create_controler.dart';

class EnterpriseCreatePage extends StatefulWidget {
  @override
  State<EnterpriseCreatePage> createState() => _EnterpriseCreatePageState();
}

class _EnterpriseCreatePageState extends State<EnterpriseCreatePage> {
  final GlobalKey<FormState> formKey = GlobalKey<FormState>();

  final EnterpriseCreateController controller = Modular.get();
  final TextEditingController cnpjController = TextEditingController();
  final TextEditingController adressController = TextEditingController();
  final TextEditingController nameFancyController = TextEditingController();
  final TextEditingController numberController = TextEditingController();
  final TextEditingController disctinctController = TextEditingController();

  final focusNodeAdress = FocusNode();
  final focusNodeDistrict = FocusNode();
  final focusNodecnpj = FocusNode();
  final focusNodenameFancy = FocusNode();
  final focusNodeNumber = FocusNode();
  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;
    return Scaffold(
      backgroundColor: Colors.white,
      body: SizedBox(
        height: double.infinity,
        width: size.height,
        child: Stack(
          alignment: Alignment.center,
          children: [
            SingleChildScrollView(
              child: Form(
                key: formKey,
                child:
                    ScopedBuilder<EnterpriseCreateController, EnterpriseCreate>(
                  store: controller,
                  onState: (context, state) {
                    return Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Image.asset(
                          "assets/logo.png",
                          height: size.height * 0.15,
                        ),
                        TextFieldContainer(
                          controller: cnpjController,
                          enabled: !controller.isLoading,
                          keyboardType: TextInputType.number,
                          autocorrect: false,
                          icon: Icons.account_box,
                          onFieldSubmitted: (_) =>
                              focusNodeAdress.requestFocus(),
                          focusNode: focusNodecnpj,
                          hintText: "CNPJ",
                          onChanged: controller.setCnpj,
                          onSaved: (cnpj) => controller.setCnpj(cnpj ?? ''),
                        ),
                        TextFieldContainer(
                          controller: adressController,
                          enabled: !controller.isLoading,
                          keyboardType: TextInputType.streetAddress,
                          autocorrect: false,
                          icon: Icons.email,
                          onFieldSubmitted: (_) =>
                              focusNodenameFancy.requestFocus(),
                          focusNode: focusNodeAdress,
                          hintText: "Endereço",
                          onChanged: controller.setAdres,
                          onSaved: (adress) =>
                              controller.setAdres(adress ?? ''),
                        ),
                        TextFieldContainer(
                          controller: nameFancyController,
                          enabled: !controller.isLoading,
                          icon: Icons.vpn_key,
                          focusNode: focusNodenameFancy,
                          onFieldSubmitted: (_) =>
                              focusNodeNumber.requestFocus(),
                          hintText: "Nome fantasia",
                          onChanged: controller.setNameFancy,
                          onSaved: (fancy) =>
                              controller.setNameFancy(fancy ?? ''),
                        ),
                        TextFieldContainer(
                          controller: numberController,
                          enabled: !controller.isLoading,
                          keyboardType: TextInputType.number,
                          autocorrect: false,
                          icon: Icons.business,
                          onFieldSubmitted: (_) =>
                              focusNodeDistrict.requestFocus(),
                          focusNode: focusNodeNumber,
                          hintText: "Número",
                          onChanged: controller.setNumber,
                          onSaved: (value) => controller.setNumber(value ?? ''),
                        ),
                        TextFieldContainer(
                          controller: disctinctController,
                          enabled: !controller.isLoading,
                          autocorrect: false,
                          icon: Icons.business,
                          onFieldSubmitted: (_) => focusNodeDistrict.unfocus(),
                          focusNode: focusNodeDistrict,
                          hintText: "Bairro",
                          onChanged: controller.setDistrict,
                          onSaved: (value) =>
                              controller.setDistrict(value ?? ''),
                        ),
                        ScopedBuilder<EnterpriseCreateController,
                            EnterpriseCreate>(
                          store: controller,
                          onLoading: (context) {
                            return const CircularProgressIndicator(
                              valueColor:
                                  AlwaysStoppedAnimation<Color>(Colors.green),
                            );
                          },
                          onState: (context, state) {
                            return Column(
                              children: [
                                Container(
                                  margin:
                                      const EdgeInsets.symmetric(vertical: 10),
                                  width: size.width * 0.8,
                                  child: ClipRRect(
                                    borderRadius: BorderRadius.circular(29),
                                    child: ElevatedButton(
                                      onPressed: () {
                                        focusNodeDistrict.unfocus();
                                        focusNodeAdress.unfocus();
                                        focusNodecnpj.unfocus();
                                        focusNodenameFancy.unfocus();
                                        focusNodeNumber.unfocus();
                                        if (formKey.currentState!.validate()) {
                                          controller.onRegisterInPress();
                                        }
                                      },
                                      style: ElevatedButton.styleFrom(
                                        padding: const EdgeInsets.symmetric(
                                            vertical: 20, horizontal: 40),
                                        primary: Colors.green,
                                      ),
                                      child: const Text(
                                        "Registrar",
                                        style: TextStyle(color: Colors.white),
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            );
                          },
                        ),
                        SizedBox(height: size.height * 0.03),
                      ],
                    );
                  },
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
